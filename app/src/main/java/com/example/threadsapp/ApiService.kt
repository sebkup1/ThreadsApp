package com.example.threadsapp

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

val retrofit = Retrofit.Builder()
    .baseUrl("https://wp.pl")
    .build()

interface ServiceApi {

    @POST("/posts")
    suspend
    fun postData(
        @Body requestBody: RequestBody
    ): Response<ResponseBody>
}

object Api {
    val retrofitService: ServiceApi by lazy { retrofit.create(ServiceApi::class.java) }
}