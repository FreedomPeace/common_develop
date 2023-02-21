package com.example.rxjava_demo.bean

data class User(var firstName:String?,var secondName:String? = null)

fun sayHi(x: String, y: String): Unit {
    val user = User("ai")
    print(user.firstName)
}
