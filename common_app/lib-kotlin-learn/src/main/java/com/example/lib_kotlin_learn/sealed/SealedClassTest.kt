package com.example.lib_kotlin_learn.sealed

//We can add the modifier abstract to a sealed class,
// but this is redundant because sealed classes are abstract by default.
// Sealed classes cannot have the open or final modifier.
// We are also free to declare data classes and objects as subclasses to a sealed class
// (they still need to be declared in the same file-> package path is the same).
abstract sealed class ClickBase

sealed interface Clickable{
    val name:String
}

data class ClickA(override val name:String = "clicka") : ClickBase(),Clickable

fun main() {
    ClickA().name.also {
        println(it)
    }
}