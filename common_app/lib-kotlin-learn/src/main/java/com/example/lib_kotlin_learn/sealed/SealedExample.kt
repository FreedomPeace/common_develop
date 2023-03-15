package com.example.lib_kotlin_learn.sealed

import java.io.File
import javax.sql.DataSource

sealed interface Error

sealed class IOError(): Error

class FileReadError(val file: File): IOError()
class DatabaseError(val source: DataSource): IOError()

object RuntimeError : Error

fun log(e: Error) = when(e) {
    is FileReadError -> { println("Error while reading file ${e.file}") }
    is DatabaseError -> { println("Error while reading from database ${e.source}") }
    is RuntimeError ->  { println("Runtime error") }
    // the `else` clause is not required because all the cases are covered
}

fun main() {
    log(FileReadError(File("./ClassLearn")))
}