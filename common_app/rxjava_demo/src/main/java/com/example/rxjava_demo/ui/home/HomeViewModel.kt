package com.example.rxjava_demo.ui.home

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
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Objects

class HomeViewModel : ViewModel() {

    private val mStr: MutableLiveData<String> = MutableLiveData()

    init {
        mStr.value = "This is home fragment"
    }

    val text: LiveData<String?>
        get() = mStr

    fun getFreedomPeaceInfo() {
        val gitHubApi: GitHubApi = RetrofitManager.instance.retrofit.create(
            GitHubApi::class.java
        )
//        gitHubApi.freedomPeace
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//            Consumer<UserResponse> {
//                val prettyFormat = toPrettyFormat(it)
//                mStr.value = prettyFormat
//            }
//            , Consumer {  mStr.value = it.message }
//        )
        //通过coroutines 实现异步获取数据
        viewModelScope.launch {
            Log.d(TAG, "getFreedomPeaceInfo: ${Thread.currentThread().name}")
            val response = gitHubApi.freedomPeace2()
            delay(2000)
            Log.d(TAG, "getFreedomPeaceInfo: ${Thread.currentThread().name}")
            mStr.value = toPrettyFormat(response)
        }
    }

    fun getReposInfo(context: Context?, username: String? = "twbs") {
        val gitHubApi: GitHubApi = RetrofitManager.instance.retrofit.create(
            GitHubApi::class.java
        )
        gitHubApi.getReposInfo(username).observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { o: UserResponse? ->
            val prettyFormat = toPrettyFormat(o)
            mStr.value = prettyFormat
            Log.d(TAG, prettyFormat)
        }.doOnError { t: Throwable -> mStr.setValue(t.message) }.subscribe()
    }

    fun getUsers(context: Context?) {
        val gitHubApi: GitHubApi = RetrofitManager.instance.retrofit.create(
            GitHubApi::class.java
        )
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

    companion object {
        const val TAG = "ddd"
        fun toPrettyFormat(json: Any?): String {
            val gson = Gson()
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(gson.toJson(json)).asJsonObject
            val gson2 = GsonBuilder().setPrettyPrinting().create()
            return gson2.toJson(jsonObject)
        }
    }
}