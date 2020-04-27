import AzureRM
import jetbrains.buildServer.configs.kotlin.v2019_2.*

version = "2019.2"

project(AzureRM(DslContext.getParameter("environment", "public")))