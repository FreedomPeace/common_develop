package com.example.rxjava_demo.ui.users

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rxjava_demo.bean.UserResponse
import com.example.rxjava_demo.github.GitHubApi
import com.example.rxjava_demo.github.RetrofitManager
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {
    private val mText: MutableLiveData<String>
    private val mUsers: MutableLiveData<List<UserResponse>>
    private val gitHubApi: GitHubApi = RetrofitManager.instance.retrofit.create(
        GitHubApi::class.java
    )

    init {
        mText = MutableLiveData()
        mUsers = MutableLiveData()
        mText.value = "This is gallery fragment"
    }
    fun getUser2(context: Context): Unit {
        viewModelScope.launch {
            val users2 = gitHubApi.users2()
            mUsers.value = users2
        }
    }
    val text: LiveData<String>
        get() = mText
    val users: LiveData<List<UserResponse>>
        get() = mUsers
}