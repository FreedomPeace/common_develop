package com.example.rxjava_demo.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rxjava_demo.api.card.RechargeCard
import com.example.rxjava_demo.viewmodel.BasicViewModel
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.SingleSubject

class CardViewModel : BasicViewModel() {
    val cardList: MutableLiveData<List<RechargeCard>> = MutableLiveData()
    var  isCancelRequest:Boolean = false
    var subject =  SingleSubject.create<String>()
    init {
        subject.subscribe(Consumer { t ->
            println(t)
            print("66666")
        })
    }
}