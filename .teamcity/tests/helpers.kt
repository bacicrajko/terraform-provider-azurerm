package tests

import java.io.File

class Value(value: String) {
    val value = value

    fun existsInFile(file: File) : Boolean {
        var exists = false

        file.forEachLine {l ->
            if (value == l) {
                exists = true
            }
        }

        return exists
    }
}
