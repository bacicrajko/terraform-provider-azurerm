package tests

import AzureRM
import org.junit.Assert.assertTrue
import org.junit.Test

class ConfigurationTests {
    @Test
    fun buildShouldFailOnError() {
        val project = AzureRM("public")
        project.buildTypes.forEach { bt ->
            assertTrue("Build '${bt.id}' should fail on errors!", bt.failureConditions.errorMessage)
        }
    }
}
