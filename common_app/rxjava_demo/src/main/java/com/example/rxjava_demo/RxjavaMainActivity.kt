package com.example.rxjava_demo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.observe
import com.example.rxjava_demo.api.distinctUntilChangedAPI
import com.example.rxjava_demo.api.card.RechargeCard
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer

/**
 * map
 * flatmap
 * catMap
 * filter：集合进行过滤
    each：遍历集合
    take：取出集合中的前几个
    skip：跳过前几个元素
 */
class RxjavaMainActivity : AppCompatActivity() {
    private var data = MutableLiveData<List<RechargeCard>>()
    private val distinct = distinctUntilChangedAPI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_main)
        val subscribe = Observable.just("1", "2").subscribe {
            Log.d("azp", it)
        }
        subscribe.dispose()


        val distinctUntilChanged = Transformations.distinctUntilChanged(data)
        distinctUntilChanged.observe(this){
            if (it == null) {
                Log.d("azp"," list null")
                return@observe
            }
            for (rechargeCard in it) {
                Log.d("azp", "onCreate: size"+it.size)
                Log.d("azp", rechargeCard.chargedPower.toString())
            }
        }
    }

    fun getData(view: View) {
        val fakeData = distinct.fakeData
        data.value = fakeData
    }

}
