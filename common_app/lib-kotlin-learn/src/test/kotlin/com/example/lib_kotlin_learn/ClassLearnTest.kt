package com.example.lib_kotlin_learn

import com.example.lib_kotlin_learn.class_object.ClassLearn
import org.junit.Test

class ClassLearnTest {

    @Test
    fun getIntroduce() {
        val a = ClassLearn("aizp")
        println(a.getIntroduce.invoke())
        println(a.getIntroduce)
    }

    @Test
    fun readProperty() {
        val b = ClassLearn(6,"jim")
        println(b.age)
//        println(b.name) // name is not a variant becauseOf it has no  modifies eg val or var
    }
}