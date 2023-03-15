package com.example.lib_kotlin_learn.nothing


data class Person2(val name:String?)

fun main() {
    val person = Person2(null)
//    val s = person.name ?: throw IllegalArgumentException("Name required")
    val s = person.name ?: Fail.fail("null name")
    println(s)
    println("end")
}
 object Fail{
    @JvmStatic
    fun fail(message: String): Unit {
        throw IllegalArgumentException(message)
    }
}
