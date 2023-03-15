package com.example.lib_kotlin_learn.generic_in_out


//out==========================
interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // This is OK, since T is an out-parameter
    // ...
}



fun copy(from: Array</*out*/ Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}

//Array<in String> corresponds to Java's Array<? super String>.
// This means that you can pass an array of CharSequence or an array of Object
// to the fill() function.
fun fill(dest: Array<in String>, value: String) {

}

fun main() {
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
//    copy(ints, any)
//   ^ type is Array<Int> but Array<Any> was expected
}
