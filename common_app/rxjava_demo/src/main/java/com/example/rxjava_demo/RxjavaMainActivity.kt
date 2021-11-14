package com.example.rxjava_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.functions.Consumer

class RxjavaMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_main)
        val subscribe = Observable.just("1", "2").subscribe(Consumer {
            println(it)
        })
        subscribe.dispose()
    }

}
