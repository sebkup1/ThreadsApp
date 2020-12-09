package com.example.threadsapp

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantLock

class Thread3(val count: Int) : Thread() {

    private var countDownLatch = CountDownLatch(count)
    private val lock = ReentrantLock()

    private val list = ArrayList<String>()

    fun addImportantPhoneData(data: String) {
        lock.lock()
        list.add(data)
        countDownLatch.countDown()
        lock.unlock()
    }

    override fun run() {
        while (true) {
            try {
                countDownLatch.await()
                lock.lock()
                Log.d(TAG, "thread 3: ${Thread.currentThread().id}, ${Thread.currentThread().name}")
                val listToSend = list.clone()
                list.clear()
                countDownLatch = CountDownLatch(count)
                lock.unlock()
                // send over http
                sendOverHttp(listToSend as ArrayList<String>)

            } catch (e: InterruptedException) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    // this will block thread3
    private fun sendOverHttp(list : ArrayList<String>) {
        val jsonObject = JSONObject()
        jsonObject.put("deadlyImportantPhoneData", list)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = Api.retrofitService.postData(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d(TAG, "success")
                } else {
                    Log.e(TAG, response.code().toString())
                }
            }
        }

    }

    companion object {
        const val TAG = "Thread3"
    }
}