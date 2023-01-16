package com.jphr.lastmarket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jphr.lastmarket.service.UserInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInfoViewModel :ViewModel() {
    public fun tmp() {
        viewModelScope.launch {
            getcategory()

        }
    }
    suspend fun getcategory(): LiveData<MutableList<String>> {
        val job=viewModelScope.async {
            withContext(Dispatchers.IO){
                return@withContext UserInfoService().getCategory()
            }
        }
        return job.await()
    }

}