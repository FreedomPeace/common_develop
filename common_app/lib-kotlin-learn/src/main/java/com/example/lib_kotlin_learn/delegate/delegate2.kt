package com.example.lib_kotlin_learn.delegate


interface Base {
    fun print()
    val a: Int
}

class BaseImpl2 : Base {
    override fun print() {}
    override var a: Int
        get() = 0
        set(value) {
            println(value)
        }
}

class BaseImpl(override val a: Int) : Base {
    override fun print() {
        println(a)
    }
}

class Derived(b: Base) : Base by b

fun main() {
    val b = BaseImpl(10)
    BaseImpl2().also { it.a = 6 }

    val derived = Derived(b)/*.also { a = 6 }*/

    derived.print()
}