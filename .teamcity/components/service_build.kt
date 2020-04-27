import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.golang
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule
import org.w3c.dom.Text

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
                buildStepGoEnv()
                buildStepRunAcceptanceTests("azurerm", packageName)
            }

            failureConditions {
                errorMessage = true
            }

            features {
                buildFeatureGolang()
            }

            params {
                text("PARALLELISM", "%d".format(parallelism))
                text("TEST_PREFIX", "TestAcc")
                text("TIMEOUT", "12h")

                TerraformAcceptanceTestsFlag()
                TerraformShouldPanicForSchemaErrors()
                ReadOnlySettings()
            }

            triggers {
                RunNightly(runNightly, startHour)
            }
        }
    }

    fun uniqueID() : String {
        return "AZURE_SERVICE_%s_%s".format(environment.toUpperCase(), packageName.toUpperCase())
    }
}
