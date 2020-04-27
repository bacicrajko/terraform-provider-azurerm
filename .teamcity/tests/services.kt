package tests

import customParallelism
import junit.framework.TestCase.assertTrue
import org.junit.Test
import services

class ParallelismTest {
    @Test
    fun servicesDefinedForCustomParallelism() {
        for (item in customParallelism) {
            val serviceExists = Service(item.key).exists(services)
            assertTrue("Service %s does not exist in the getServices list - run `make generate`".format(item.key), serviceExists)
        }
    }
}
