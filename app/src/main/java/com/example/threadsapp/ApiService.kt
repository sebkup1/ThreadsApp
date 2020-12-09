package com.example.threadsapp

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

val retrofit = Retrofit.Builder()
    .baseUrl("https://httpbin.org")
    .build()

interface ServiceApi {

    @POST("/posts")
    @FormUrlEncoded
    fun postData(
        @Body requestBody: RequestBody
    ): Response<ResponseBody>
}

object Api {
    val retrofitService: ServiceApi by lazy { retrofit.create(ServiceApi::class.java) }
}