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
        "devtestlabs" to "DevTest Labs",
        "dns" to "DNS",
        "hdinsight" to "HD Insight",
        "keyvault" to "Key Vault",
        "iothub" to "IoTHub",
        "loganalytics" to "Log Analytics",
        "machinelearning" to "Machine Learning",
        "managedapplications" to "Managed Applications",
        "managementgroup" to "Management Group",
        "mariadb" to "Maria DB",
        "mixedreality" to "Mixed Reality",
        "notificationhub" to "Notification Hubs",
        "powerbi" to "Power BI",
        "privatedns" to "Private DNS",
        "recoveryservices" to "Recovery Services",
        "securitycenter" to "Security Center",
        "servicefabric" to "Service Fabric",
        "signalr" to "SignalR",
        "streamanalytics" to "Stream Analytics",
        "trafficmanager" to "Traffic Manager"
)