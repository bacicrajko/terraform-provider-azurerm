import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

fun AzureRM(environment: String) : Project {
    return Project{
        vcsRoot(providerRepository)

        var buildConfigs = buildConfigurationsForServices(services, "azurerm", environment)
        buildConfigs.forEach { buildConfiguration ->
            buildType(buildConfiguration)
        }
    }
}

fun buildConfigurationsForServices(services: Map<String, String>, providerName : String, environment: String): List<BuildType> {
    var list = ArrayList<BuildType>()

    services.forEach { (serviceName, displayName) ->
        var defaultTestConfig = testConfiguration(defaultParallelism, defaultStartHour)
        var testConfig = serviceTestConfigurationOverrides.getOrDefault(serviceName, defaultTestConfig)
        var runNightly = runNightly.getOrDefault(environment, false)

        var service = serviceDetails(serviceName, displayName, environment)
        var buildConfig = service.buildConfiguration(providerName, runNightly, testConfig.startHour, testConfig.parallelism)
        list.add(buildConfig)
    }

    return list
}

class testConfiguration(parallelism: Int, startHour: Int) {
    var parallelism = parallelism
    var startHour = startHour
}