package com.example.rxjava_demo.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rxjava_demo.api.card.RechargeCard

class CardViewModel : ViewModel() {
    val cardList: MutableLiveData<List<RechargeCard>> = MutableLiveData()

}