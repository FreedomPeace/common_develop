package com.example.lib_kotlin_learn

//https://kotlinlang.org/docs/type-aliases.html
//Type aliases do not introduce new types.
// They are equivalent to the corresponding underlying types
//别名没有引入新的类型，等价于对应当下的类型
class TypeAliases {

}
class A {
    inner class Inner
}
class B {
    inner class Inner
}

typealias AInner = A.Inner
typealias BInner = B.Inner

typealias Predicate<T> = (T) -> Boolean

fun foo(p: Predicate<Int>) = p(42)

fun main() {
    val f: (Int) -> Boolean = { it > 0 }
    println(foo(f)) // prints "true"

    val p: Predicate<Int> = { it > 0 }
    println(listOf(1, -2).filter(p)) // prints "[1]"
}