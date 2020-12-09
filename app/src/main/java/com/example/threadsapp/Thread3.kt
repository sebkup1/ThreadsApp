package com.example.threadsapp

import android.util.Log
import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantLock

class Thread3(val count: Int) : Runnable {

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
                val listToSend = list.clone()
                list.clear()
                // send over http
                countDownLatch = CountDownLatch(count)
                Log.d(TAG, "list full:")
                (listToSend as ArrayList<String>).forEach {
                    Log.d(TAG, it)
                }

                lock.unlock()
            } catch (e: InterruptedException) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    companion object {
        const val TAG = "Thread3"
    }
}