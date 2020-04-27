import jetbrains.buildServer.configs.kotlin.v2019_2.Project

fun AzureRM(environment: String) : Project {
    return Project{
        vcsRoot(providerRepository)

        services.forEach { (serviceName, displayName) ->
            var defaultTestConfig = testConfiguration(defaultParallelism, defaultStartHour)
            var testConfig = serviceTestConfigurationOverrides.getOrDefault(serviceName, defaultTestConfig)
            var runNightly = runNightly.getOrDefault(environment, false)

            var service = serviceDetails(serviceName, displayName, environment)
            var buildConfig = service.buildConfiguration(runNightly, testConfig.startHour, testConfig.parallelism)
            buildType(buildConfig)
        }
    }
}

class testConfiguration(parallelism: Int, startHour: Int) {
    var parallelism = parallelism
    var startHour = startHour
}