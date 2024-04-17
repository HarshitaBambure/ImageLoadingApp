package com.example.imageloadingapp.data.repository

import com.example.imageloadingapp.data.api.Constant
import com.example.imageloadingapp.data.api.ImageApi
import com.example.imageloadingapp.data.model.response.ImageListResponse

class ImageRepository(val imageApi: ImageApi) {

    suspend fun getUserData(page:Int,perPage:Int): ImageListResponse {
        return imageApi.getListPhotos(Constant.CLIENT_ID,page,perPage)

    }

}