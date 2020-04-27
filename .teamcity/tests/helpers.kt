package tests

class Service(name: String) {
    val name = name

    fun exists(services: Map<String, String>) : Boolean {
        val exists = services.get(name)
        return exists != null
    }
}
