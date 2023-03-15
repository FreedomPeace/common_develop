package com.example.lib_kotlin_learn.generic_in_out

class PRO_Consumer {

    interface IConsumer<in T > {
        fun get(t: T)
    }
    class Consumer :IConsumer<IB>{
        override fun get(t: IB) {
            t.a()
        }


    }
    interface IPro<out T > {
        fun get():T
    }
    class P:IPro<B>{
        override fun get(): B {
            return B()
        }
    }
    interface IB{
        fun a()
    }
    class B:IB {
        override fun a(): Unit {
            println("B")
        }
    }
    class B1:IB {
        override fun a(): Unit {
            println("b1")
        }
    }

    fun main() {
        val arr = arrayListOf<IB>()
        val c = Consumer()
        c.get(B1())
        c.get(B())
    }
}