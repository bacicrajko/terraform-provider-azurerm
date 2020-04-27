var defaultStartHour = 4

// this is per-package, not per-concurrent build
var defaultParallelism = 10

var customParallelism = mapOf(
        "containers" to serviceDetails("Containers", 5, 5),
        "compute" to serviceDetails("Compute", 5, 4)
)

var environment = "public" // TODO: configurable

var serviceDisplayNames = mapOf(
        "analysisservices" to "Analysis Services",
        "apimanagement" to "API Management",
        "appconfiguration" to "App Configuration",
        "applicationinsights" to "Application Insights",
        "appplatform" to "App Platform",
        "cdn" to "CDN",
        "costmanagement" to "Cost Management",
        "customproviders" to "Custom Providers",
        "databasemigration" to "Database Migration",
        "datafactory" to "Data Factory",
        "devtestlabs" to "DevTest Labs"
)