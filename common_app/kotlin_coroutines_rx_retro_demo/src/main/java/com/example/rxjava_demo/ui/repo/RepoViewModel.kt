package com.example.rxjava_demo.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RepoViewModel : ViewModel() {
    private val mRepo: MutableLiveData<String>

    init {
        mRepo = MutableLiveData()
        mRepo.value = "This is mRepo fragment"
    }

    val text: LiveData<String>
        get() = mRepo
}