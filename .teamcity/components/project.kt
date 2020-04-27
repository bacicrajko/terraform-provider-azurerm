import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

var defaultStartHour = 4

// this is per-package, not per-concurrent build
var defaultParallelism = 10

var environment = "public" // TODO: configurable

object AzureRM : Project({
    vcsRoot(providerRepository)

    services.forEach { serviceName, displayName ->
        var buildConfiguration = buildConfigurationForServiceName(serviceName, displayName)
        buildType(buildConfiguration)
    }
})

fun buildConfigurationForServiceName(serviceName : String, displayName : String) : BuildType {
    var defaultService = serviceTestConfiguration(defaultParallelism, defaultStartHour)
    var service = customParallelism.getOrDefault(serviceName, defaultService)
    return buildConfigurationForService(serviceName, displayName, service, environment)
}


