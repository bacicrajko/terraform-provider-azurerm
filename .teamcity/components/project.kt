import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

var defaultStartHour = 4

// this is per-package, not per-concurrent build
var defaultParallelism = 10

var environment = DslContext.getParameter("environment", "public")

object AzureRM : Project({
    vcsRoot(providerRepository)

    services.forEach { serviceName, displayName ->
        var defaultService = testConfiguration(defaultParallelism, defaultStartHour)
        var service = customParallelism.getOrDefault(serviceName, defaultService)
        var buildConfig = buildConfigurationForService(serviceName, displayName, service, environment)
        buildType(buildConfig)
    }
})
