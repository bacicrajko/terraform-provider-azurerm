import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.GolangFeature
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule

// NOTE: in time this could be pulled out into a separate Kotlin package

fun BuildFeatures.Golang() {
    feature(GolangFeature {
        testFormat = "json"
    })
}

fun BuildSteps.ConfigureGoEnv() {
    step(ScriptBuildStep {
        name = "Configure Go Version"
        scriptContent = "goenv install -s \$(goenv local) && goenv rehash"
    })
}

fun BuildSteps.RunAcceptanceTests(providerName : String, packageName: String) {
    var servicePath = "./%s/internal/services/%s/...".format(providerName, packageName)
    step(ScriptBuildStep {
        name = "Run Tests"
        scriptContent = "go test -v \"$servicePath\" -timeout=\"%TIMEOUT%\" -test.parallel=\"%PARALLELISM%\" -run=\"%TEST_PREFIX%\" -json"
    })
}

fun BuildSteps.RunAcceptanceTestsUsingOldMethod(providerName : String, packageName: String) {
    step(ScriptBuildStep {
        name = "Install tombuildsstuff/teamcity-go-test-json"
        scriptContent = "go install github.com/tombuildsstuff/teamcity-go-test-json"
    })

    var servicePath = "./%s/internal/services/%s/...".format(providerName, packageName)
    step(ScriptBuildStep {
        name = "Run Tests"
        scriptContent = "teamcity-go-test-json -scope \"$servicePath\" -prefix \"%TEST_PATTERN%\" -count=1 -parallelism=%ACCTEST_PARALLELISM% -timeout %TIMEOUT%"
    })
}

fun ParametrizedWithType.TerraformAcceptanceTestParameters(parallelism : Int, prefix : String, timeout: String) {
    text("PARALLELISM", "%d".format(parallelism))
    text("TEST_PREFIX", prefix)
    text("TIMEOUT", timeout)
}

fun ParametrizedWithType.ReadOnlySettings() {
    hiddenVariable("teamcity.ui.settings.readOnly", "true", "Requires build configurations be edited via Kotlin")
}

fun ParametrizedWithType.TerraformAcceptanceTestsFlag() {
    hiddenVariable("env.TF_ACC", "1", "Set to a value to run the Acceptance Tests")
}

fun ParametrizedWithType.TerraformShouldPanicForSchemaErrors() {
    hiddenVariable("env.TF_SCHEMA_PANIC_ON_ERROR", "1", "Panic if unknown/unmatched fields are set into the state")
}

fun ParametrizedWithType.hiddenVariable(name: String, value: String, description: String) {
    text(name, value, "", description, ParameterDisplay.HIDDEN)
}

fun ParametrizedWithType.hiddenPasswordVariable(name: String, value: String, description: String) {
    password(name, value, "", description, ParameterDisplay.HIDDEN)
}

fun Triggers.RunNightly(nightlyTestsEnabled: Boolean, startHour: Int) {
    schedule{
        enabled = nightlyTestsEnabled
        branchFilter = "+:refs/heads/master"

        schedulingPolicy = daily {
            hour = startHour
            timezone = "SERVER"
        }
    }
}