package com.example.rxjava_demo.extension

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModelExt
private val handler = CoroutineExceptionHandler { _, exception ->
    Log.d("azp", "Default CoroutineExceptionHandler got $exception")
}
//CoroutineScope.()->Unit  拓展函数 ，返回对象本身。
fun ViewModel.launch(block: CoroutineScope.()->Unit): Job {
   return this.viewModelScope.launch (handler){
       block.invoke(this)
   }
}