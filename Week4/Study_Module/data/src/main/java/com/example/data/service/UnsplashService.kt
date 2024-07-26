package com.example.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {
    @GET("/photos")
    suspend fun getPhotoList(
        @Query("page") page: Int
    ): Response<ResponseBody>
}