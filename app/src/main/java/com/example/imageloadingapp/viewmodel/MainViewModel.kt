package com.example.imageloadingapp.viewmodel

import com.example.imageloadingapp.data.api.ImageApi
import com.example.imageloadingapp.data.model.response.ImageListResponse
import com.example.imageloadingapp.repository.ImageRepository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageloadingapp.data.api.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel :ViewModel(){
    val api = ImageApi.getApi()
    val repository = api?.let { ImageRepository(it) }
    val listData = MutableLiveData<BaseResponse<ImageListResponse>>()
    fun getImageList(page: Int, perPage: Int) {
        listData.value=BaseResponse.Loading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository?.getUserData(page, perPage)
                listData.postValue(BaseResponse.Success(response))
            } catch (ex: Exception) {
                listData.postValue(BaseResponse.Failed(ex.message))
                Log.e("Error", ex.message.toString())
            }
        }
    }
}