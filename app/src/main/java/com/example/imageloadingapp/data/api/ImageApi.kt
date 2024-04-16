package com.example.imageloadingapp.data.api

import com.example.imageloadingapp.data.model.response.ImageListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("/photos/")
    suspend fun getListPhotos(@Query("client_id") clientId: String,@Query("page") page: Int,@Query("per_page") per_page: Int): ImageListResponse

    companion object {
        fun getApi(): ImageApi? {
            return RetrofitClient.client?.create(ImageApi::class.java)
        }
    }
}
