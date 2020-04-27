import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.golang
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule

class serviceDetails(name: String, displayName: String, environment: String) {
    val packageName = name
    val displayName = displayName
    val environment = environment

    fun buildConfiguration(runNightly: Boolean, startHour: Int, parallelism: Int) : BuildType {
        return BuildType {
            // TC needs a consistent ID for dynamically generated packages
            id(uniqueID())

            name = "%s - Acceptance Tests".format(displayName)

            vcs {
                root(providerRepository)
                cleanCheckout = true
            }

            steps {
                script {
                    name = "Configure Go Version"
                    scriptContent = "goenv install -s \$(goenv local) && goenv rehash"
                }

                var servicePath = "./azurerm/internal/services/%s/...".format(packageName)
                script {
                    name = "Run Tests"
                    scriptContent = "go test -v $servicePath -timeout=%TIMEOUT% -test.parallel=%PARALLELISM% -run=%TEST_PREFIX% -json"
                }
            }

            failureConditions {
                errorMessage = true
            }

            features {
                golang {
                    testFormat = "json"
                }
            }

            params {
                text("PARALLELISM", "%d".format(parallelism))
                text("TEST_PREFIX", "TestAcc")
                text("TIMEOUT", "12h")
                text("env.TF_ACC", "1")
                text("env.TF_SCHEMA_PANIC_ON_ERROR", "1")
            }

            triggers {
                schedule {
                    enabled = runNightly
                    type = "schedulingTrigger"
                    branchFilter = "+:refs/heads/master"

                    schedulingPolicy = daily {
                        hour = startHour
                        timezone = "SERVER"
                    }
                }
            }
        }
    }

    fun uniqueID() : String {
        return "AZURE_SERVICE_%s_%s".format(environment.toUpperCase(), packageName.toUpperCase())
    }
}
