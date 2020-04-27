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

    @Test
    fun buildShouldHaveGoTestFeature() {
        val project = AzureRM("public")
        var exists = false
        project.features.items.forEach{ f ->
            if (f.type == "golang") {
                exists = true
            }
        }
        assertTrue("The Go Test Json Feature should be enabled", exists)
    }

    @Test
    fun buildShouldHaveTrigger() {
        val project = AzureRM("public")
        var exists = false
        project.buildTypes.forEach{ bt ->
            bt.triggers.items.forEach { t ->
                if (t.type == "schedulingTrigger") {
                    exists = true
                }
            }
        }
        assertTrue("The Build Configuration should have a Trigger", exists)
    }
}
