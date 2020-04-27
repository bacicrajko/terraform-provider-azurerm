package tests

import customParallelism
import getServiceNames
import junit.framework.TestCase.assertTrue
import org.junit.Test
import serviceDisplayNames

class ServicesTest {
    @Test
    fun servicesDefinedForCustomParallelism() {
        val knownServiceNames = getServiceNames()
        for (item in customParallelism) {
            val serviceExists = Value(item.key).existsInFile(knownServiceNames)
            assertTrue("Service %s does not exist in the services list - run `make generate`".format(item.key), serviceExists)
        }
    }

    @Test
    fun servicesDefinedForServiceDisplayNames() {
        val knownServiceNames = getServiceNames()
        for (item in serviceDisplayNames) {
            val serviceExists = Value(item.key).existsInFile(knownServiceNames)
            assertTrue("Service %s does not exist in the services list - run `make generate`".format(item.key), serviceExists)
        }
    }
}
