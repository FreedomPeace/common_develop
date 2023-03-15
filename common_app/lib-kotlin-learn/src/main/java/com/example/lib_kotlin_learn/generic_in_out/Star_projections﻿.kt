package com.example.lib_kotlin_learn.generic_in_out

//https://kotlinlang.org/docs/generics.html#star-projections
/**
 * Sometimes you want to say that you know nothing about the type argument,
 * but you still want to use it in a safe way.
 * The safe way here is to define such a projection of the generic type,
 * that every concrete instantiation of that generic type will be a subtype of that projection.
 */

//For Foo<T : TUpper>, where T is an invariant type parameter with the upper bound TUpper,
// Foo<*> is equivalent to Foo<out TUpper> for reading values and to Foo<in Nothing>
// for writing values.
open class Animal(open val name:String)
class Dog(override val name: String) :Animal(name)
class Foo< T:Animal>(val animal: T){
//    fun show(): Unit {
//        println(animal.name)
//    }
//val show:(Foo<*>)->Unit = { println(it.animal.name)}
}
val show:(Foo<*>)->Unit = { println(it.animal.name)} // *  ==  out Animal
//val f = Foo<Animal>()
fun main() {
    show.invoke(Foo(Dog("dog")))
    show.invoke(Foo(Animal("animal")))
}


//For Foo<out T : TUpper>, where T is a covariant type parameter with the upper bound TUpper,
// Foo<*> is equivalent to Foo<out TUpper>. This means that when the T is unknown you can safely
// read values of TUpper from Foo<*>.
//

//For Foo<in T>, where T is a contravariant type parameter,
// Foo<*> is equivalent to Foo<in Nothing>.
// This means there is nothing you can write to Foo<*> in a safe way when T is unknown.
//

//
//If a generic type has several type parameters,
// each of them can be projected independently.
// For example, if the type is declared as interface Function<in T, out U>
// you could use the following star-projections:
//

//Function<*, String> means Function<in Nothing, String>.
//
//Function<Int, *> means Function<Int, out Any?>.
//
//Function<*, *> means Function<in Nothing, out Any?>.