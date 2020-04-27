import jetbrains.buildServer.configs.kotlin.v2019_2.*

class serviceDetails(name: String, displayName: String, environment: String) {
    val packageName = name
    val displayName = displayName
    val environment = environment

    fun buildConfiguration(nightlyTestsEnabled: Boolean, startHour: Int, parallelism: Int) : BuildType {
        return BuildType {
            // TC needs a consistent ID for dynamically generated packages
            id(uniqueID())

            name = "%s - Acceptance Tests".format(displayName)

            vcs {
                root(providerRepository)
                cleanCheckout = true
            }

            steps {
                ConfigureGoEnv()
                RunAcceptanceTests("azurerm", packageName)
            }

            failureConditions {
                errorMessage = true
            }

            features {
                Golang()
            }

            params {
                TerraformAcceptanceTestParameters(parallelism, "TestAcc", "12h")
                TerraformAcceptanceTestsFlag()
                TerraformShouldPanicForSchemaErrors()
                ReadOnlySettings()
            }

            triggers {
                RunNightly(nightlyTestsEnabled, startHour)
            }
        }
    }

    fun uniqueID() : String {
        return "AZURE_SERVICE_%s_%s".format(environment.toUpperCase(), packageName.toUpperCase())
    }
}
