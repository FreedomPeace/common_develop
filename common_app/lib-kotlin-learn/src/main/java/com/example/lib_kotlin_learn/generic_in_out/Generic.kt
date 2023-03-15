package com.example.lib_kotlin_learn.generic_in_out

//Classes in Kotlin can have type parameters, just like in Java:
class Box<T>(t: T) {
    val value = t
}

val box = Box(6)
val box2: Box<Int> = Box<Int>(7)

//https://kotlinlang.org/docs/generics.html#variance
//Variance


val intArr = arrayOf(1, 2, 3)
val anyArr = arrayOf("3")