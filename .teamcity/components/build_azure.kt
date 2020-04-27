import jetbrains.buildServer.configs.kotlin.v2019_2.ParametrizedWithType

class ClientConfiguration(var clientId: String, var clientSecret: String, val subscriptionId : String, val tenantId : String) {
}

class LocationConfiguration(var primary : String, var secondary : String, var ternary : String, var rotate : Boolean) {
}

fun ParametrizedWithType.ConfigureAzureSpecificTestParameters(environment: String, config: ClientConfiguration, locationsForEnv: LocationConfiguration) {
    hiddenVariable("env.ARM_CLIENT_ID", "credentialsJSON:88a1d063-d674-4b80-953d-148e2570bb82", "The ID of the Service Principal used for Testing")
    hiddenPasswordVariable("env.ARM_CLIENT_SECRET", config.clientSecret, "The Client Secret of the Service Principal used for Testing")
    hiddenVariable("env.ARM_ENVIRONMENT", environment, "The Azure Environment in which the tests are running")
    hiddenVariable("env.ARM_PROVIDER_DYNAMIC_TEST", "%b".format(locationsForEnv.rotate), "Should tests rotate between the supported regions?")
    hiddenVariable("env.ARM_SUBSCRIPTION_ID", config.subscriptionId, "The ID of the Azure Subscription used for Testing")
    hiddenVariable("env.ARM_TENANT_ID", config.tenantId, "The ID of the Azure Tenant used for Testing")
    hiddenVariable("env.ARM_TEST_LOCATION", locationsForEnv.primary, "The Primary region which should be used for testing")
    hiddenVariable("env.ARM_TEST_LOCATION_ALT", locationsForEnv.secondary, "The Primary region which should be used for testing")
    hiddenVariable("env.ARM_TEST_LOCATION_ALT2", locationsForEnv.ternary, "The Primary region which should be used for testing")
}