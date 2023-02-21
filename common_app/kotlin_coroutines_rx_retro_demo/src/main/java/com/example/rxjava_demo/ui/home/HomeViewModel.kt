package com.example.rxjava_demo.ui.home

import Utils.toPrettyFormat
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rxjava_demo.bean.UserResponse
import com.example.rxjava_demo.github.GitHubApi
import com.example.rxjava_demo.github.RetrofitManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val mStr: MutableLiveData<String> = MutableLiveData()
    private val gitHubApi: GitHubApi = RetrofitManager.instance.retrofit.create(
        GitHubApi::class.java
    )
    init {
        mStr.value = "This is home fragment"
    }

    val text: LiveData<String?>
        get() = mStr

    fun getFreedomPeaceInfo() {
        //通过coroutines 实现异步获取数据
        viewModelScope.launch {
            //todo how to deal with exception
            async {
                Log.d(TAG, "getFreedomPeaceInfo: ${Thread.currentThread().name}")
                val response = getFreedomPeace2(gitHubApi)
                Log.d(TAG, "getFreedomPeaceInfo: ${Thread.currentThread().name}")
//                delay(5000)
                mStr.value = toPrettyFormat(response)
            }

            async {
                Log.d(TAG, "getReposInfo: ${Thread.currentThread().name}")
//                delay(2000)
                getReposInfo2(gitHubApi)
                Log.d(TAG, "getReposInfo: ${Thread.currentThread().name}")
            }
        }
        Log.d(TAG, "getFreedomPeaceInfo: ${Thread.currentThread().name} end")
    }

    private suspend fun getFreedomPeace2(gitHubApi: GitHubApi): UserResponse {
        return gitHubApi.freedomPeace2()
            .also { Log.d(TAG, "getFreedomPeaceInfo: ${toPrettyFormat(it)}") }
    }

    private suspend fun getReposInfo2(gitHubApi: GitHubApi): UserResponse {
        return gitHubApi.getReposInfo2("twbs")
            .also { Log.d(TAG, "getReposInfo2: ${toPrettyFormat(it)}") }
    }

    fun getReposInfo(username: String? = "twbs") {
        gitHubApi.getReposInfo(username).observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { o: UserResponse? ->
                val prettyFormat = toPrettyFormat(o)
                mStr.value = prettyFormat
                Log.d(TAG, prettyFormat)
            }.doOnError { t: Throwable -> mStr.setValue(t.message) }.subscribe()
    }

    fun getUsers(context: Context?) {
        gitHubApi.users.observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { s: List<UserResponse> ->
                Log.d(TAG, s.size.toString() + "")
                for (e in s) {
                    val prettyFormat = toPrettyFormat(e)
                    mStr.value = prettyFormat
                }
            }
            .doOnError { throwable: Throwable ->
                val message = throwable.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            .subscribe()
    }
    fun getUser2(x: Context?): Unit {
        viewModelScope.launch {
            val users2 = gitHubApi.users2()
            mStr.value = toPrettyFormat(users2[0])
        }
    }

    fun getUser3(): Unit {
        viewModelScope.launch {
            getDocs()
        }
    }

    private suspend fun getDocs() {                             // Dispatchers.Main
        val result = get("https://developer.android.com") // Dispatchers.IO for `get`
        // Dispatchers.Main
        Log.d(TAG, "fetchDocs: $result")
    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) {
        Log.d(TAG, "getDocs: ${Thread.currentThread().name}")
        url
    }

    companion object {
        const val TAG = "ddd"
    }
}