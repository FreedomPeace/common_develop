package com.example.rxjava_demo.ui.home

import GlobalTag.TAG_ME
import Utils.toPrettyFormat
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rxjava_demo.bean.UserResponse
import com.example.rxjava_demo.extension.launch
import com.example.rxjava_demo.github.GitHubApi
import com.example.rxjava_demo.github.RetrofitManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * todo how to deal with exception [ViewModelExt.kt]
 */
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
        launch {
            Dispatchers.IO
            Dispatchers.Default
            Dispatchers.Main.immediate
            async {
                Log.d(TAG_ME, "getFreedomPeaceInfo: ${Thread.currentThread().name}")
                val response = getFreedomPeace2(gitHubApi)
                Log.d(TAG_ME, "getFreedomPeaceInfo: ${Thread.currentThread().name}")
//                delay(5000)
                mStr.value = toPrettyFormat(response)
                throw NullPointerException()
            }

            async {
                Log.d(TAG_ME, "getReposInfo: ${Thread.currentThread().name}")//Dispatch.MAIN
//                delay(2000)
                getReposInfo2(gitHubApi)                                //Dispatch.IO
                Log.d(TAG_ME, "getReposInfo: ${Thread.currentThread().name}")//Dispatch.MAIN
                throw IndexOutOfBoundsException()
            }
        }
        Log.d(TAG_ME, "getFreedomPeaceInfo: ${Thread.currentThread().name} end")
    }

    private suspend fun getFreedomPeace2(gitHubApi: GitHubApi): UserResponse {
        return gitHubApi.freedomPeace2()
            .also { Log.d(TAG_ME, "getFreedomPeaceInfo: ${toPrettyFormat(it)}") }
    }

    private suspend fun getReposInfo2(gitHubApi: GitHubApi): UserResponse {
        return gitHubApi.getReposInfo2("twbs")
            .also { Log.d(TAG_ME, "getReposInfo2: ${toPrettyFormat(it)}") }
    }

    fun getReposInfo(username: String? = "twbs") {
        gitHubApi.getReposInfo(username).observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { o: UserResponse? ->
                val prettyFormat = toPrettyFormat(o)
                mStr.value = prettyFormat
                Log.d(TAG_ME, prettyFormat)
            }.doOnError { t: Throwable -> mStr.setValue(t.message) }.subscribe()
    }

    fun getUsers(context: Context?) {
        gitHubApi.users.observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { s: List<UserResponse> ->
                Log.d(TAG_ME, s.size.toString() + "")
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

    fun getFirstUser(): Unit {
        getFirstUserFromLaunch()
//        getFirstUserFromAsync()
    }

    private fun getFirstUserFromLaunch(): Unit {
        repeat(5) {
            viewModelScope.launch(handler) {
                Log.d(TAG_ME, "$it ->start getUser2: ${Thread.currentThread().name}")//Dispatch.MAIN
//                delay(200)
                val users2 =
                    getUser2()/*.apply { Log.d(TAG_ME, "getFirstUser:suspend ${toPrettyFormat(this[0])}") }*/
                mStr.value = toPrettyFormat(users2[0]).plus(it)
                Log.d(TAG_ME, "$it -> end getUser2: ${Thread.currentThread().name}")//Dispatch.MAIN

            }
        }
    }

   private fun getFirstUserFromAsync(): Unit {
        repeat(10) {
            val deferred = viewModelScope.async(handler) {
                Log.d(TAG_ME, "$it ->start getUser2: ${Thread.currentThread().name}")//Dispatch.MAIN
//                delay(200)
                val users2 =
                    getUser2()/*.apply { Log.d(TAG_ME, "getFirstUser:suspend ${toPrettyFormat(this[0])}") }*/
                mStr.value = toPrettyFormat(users2[0]).plus(it)
                Log.d(TAG_ME, "$it -> end getUser2: ${Thread.currentThread().name}")//Dispatch.MAIN

            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("azp", "Default CoroutineExceptionHandler got $exception")
    }

    private suspend fun getUser2(): List<UserResponse> {
        Log.d(TAG_ME, "suspend getUser2: ${Thread.currentThread().name}")
        return gitHubApi.users2()
    }

    fun getUser3(): Unit {
        viewModelScope.launch {
            getDocs()
        }
    }

    private suspend fun getDocs() {                             // Dispatchers.Main
        val result = get("https://developer.android.com") // Dispatchers.IO for `get`
        // Dispatchers.Main
        Log.d(TAG_ME, "fetchDocs: $result")
    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) {
        Log.d(TAG_ME, "getDocs: ${Thread.currentThread().name}")
        url
    }

    companion object {

    }
}