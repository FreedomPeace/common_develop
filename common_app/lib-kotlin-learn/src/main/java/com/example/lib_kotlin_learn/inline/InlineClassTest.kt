package com.example.lib_kotlin_learn.inline

/**
 * see https://kotlinlang.org/docs/inline-classes.html
 */
//内联类允许函数、init 块、以及没有 backing field 的属性。
//内联类只允许继承接口
// 而不允许继承类，也不允许被其他类继承[becauseOf it is final class]。
//inline class just hold value  ，don't has assignment value 。【没有 backing field 的属性。】
@JvmInline
value class InlineClassTest(private val name: String) {
    val age:Int
        get() = 8
    init {
        println("test$name")
    }
    // 内联函数 makeTest()
    //Value class cannot have properties with backing fields
//    val test: () -> Unit = { println("test$name") }

    fun test2(): Unit {
        println("test$name")
    }
}
interface Eat
class CommonClazz(private val name: String)/*:InlineClassTest [error becauseOf it is final class]。*/{
    val test: () -> Unit = { println("test$name") }
    var age:Int
        get() = 8
        set(value)= println(value)
}

//duration 参数可能会让人迷惑：它的单位是什么？秒或者毫秒？
// （虽然注释可以解决一切问题，但它不在讨论范围之内）这个时候，就可以用到内联类。 包装原来的类型。
class Animation(duration: Int)

class AnimationPro(duration: Millis)
@JvmInline
value class Millis(val value: Int)
@JvmInline
value class Second(val value: Int)

interface I

@JvmInline
value class Foo(val i: Int) : I

fun asInline(f: Foo) {}
fun <T> asGeneric(x: T) {}
fun asInterface(i: I) {}
fun asNullable(i: Foo?) {}

fun <T> id(x: T): T = x

fun main() {
    val f = Foo(42)

    asInline(f)    // unboxed: used as Foo itself
    asGeneric(f)   // boxed: used as generic type T  //wrapper
    asInterface(f) // boxed: used as type I
    asNullable(f)  // boxed: used as Foo?, which is different from Foo

    // below, 'f' first is boxed (while being passed to 'id') and then unboxed (when returned from 'id')
    // In the end, 'c' contains unboxed representation (just '42'), as 'f'
    val c = id(f)
    id(44)
    println(c.i)
}
