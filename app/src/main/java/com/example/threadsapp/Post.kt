package com.example.threadsapp

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Post {
    @SerializedName("deadlyImportantPhoneData")
    @Expose
    private val data: ArrayList<String>? = null
}