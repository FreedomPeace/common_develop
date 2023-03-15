package com.example.lib_kotlin_learn.class_object

class ClassLearn
    (/*val*/ name: String) {
    //first constructor
     var age = 0

    constructor(age: Int, name: String) : this(name) {
        this.age = age
    }

    private val informationAboutMe = "my name is $name , my age is $age , and so on"
    val getIntroduce: () -> String = { informationAboutMe }

    //    First initializer block that prints aizp
//    Second initializer block that prints 4
    init {
        println("First initializer block that prints $name")
    }

    init {
        println("Second initializer block that prints ${name.length}")
    }
}