package com.example.threadsapp

import android.util.Log
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
//                Log.d(TAG, "list full:")
                Log.d(TAG, "thread 3: ${Thread.currentThread().id}, ${Thread.currentThread().name}")
                val listToSend = list.clone()
                list.clear()
                countDownLatch = CountDownLatch(count)
                lock.unlock()
                // send over http
                sendOverHttp(listToSend as ArrayList<String>)
//                (listToSend as ArrayList<String>).forEach {
//                    Log.d(TAG, it)
//                }

            } catch (e: InterruptedException) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    // this will block thread3
    private fun sendOverHttp(list : ArrayList<String>) {
        val response = Api.retrofitService.postData(list)

        if (response.isSuccessful){
            Log.d("success","success")
        } else {
            Log.e("RETROFIT_ERROR", response.code().toString())
        }

    }

    companion object {
        const val TAG = "Thread3"
    }
}