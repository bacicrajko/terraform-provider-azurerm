package tests

import serviceTestConfigurationOverrides
import junit.framework.TestCase.assertTrue
import org.junit.Test
import services

class ParallelismTest {
    @Test
    fun servicesDefinedForCustomParallelism() {
        for (item in serviceTestConfigurationOverrides) {
            val serviceExists = Service(item.key).exists(services)
            assertTrue("Service %s does not exist in the services list - run `make generate`".format(item.key), serviceExists)
        }
    }
}
