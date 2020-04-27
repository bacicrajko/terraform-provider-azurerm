import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import java.io.File

object AzureRM : Project({
    vcsRoot(providerRepository)

    var serviceNames = getServiceNames()
    serviceNames.forEachLine { serviceName ->
        var buildConfiguration = buildConfigurationForServiceName(serviceName)
        buildType(buildConfiguration)
    }
})

fun buildConfigurationForServiceName(serviceName : String) : BuildType {
    var displayName = serviceDisplayNames.getOrDefault(serviceName, serviceName.capitalize())
    var defaultService = serviceDetails(displayName, defaultParallelism, defaultStartHour)
    var service = customParallelism.getOrDefault(serviceName, defaultService)
    return buildConfigurationForService(serviceName, service, environment)
}

fun getServiceNames() : File {
    return File("services.txt")
}

