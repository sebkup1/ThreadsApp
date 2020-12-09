package com.example.threadsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


const val A = 1
const val B = 2
const val C = 4
const val TAG = "MainActivity"


object VeryImportantPhoneDataProvider {
    var location = "54,27 28,45"
    var batteryUsage = "32"
}

class MainActivity : AppCompatActivity() {
    lateinit var t1: Thread
    lateinit var t2: Thread
    lateinit var t3: Thread3

    var threadStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "main: ${Thread.currentThread().id}, ${Thread.currentThread().name}")
        t1 = initializeT1()
        t2 = initializeT2()
        t3 = Thread3(C)

        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            if (!threadStarted) {
                t1.start()
                t2.start()
                t3.start()
            }
            threadStarted = true
        }

        stop_button.setOnClickListener {
            t1.interrupt()
            t2.interrupt()
            t3.interrupt()
            t1 = initializeT1()
            t2 = initializeT2()
            t3 = Thread3(C)
            threadStarted = false
        }
    }

    private fun initializeT1() = Thread {
        while (true) {
            if (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep((B * 1000).toLong())
                    Log.d(
                        TAG,
                        "thread 1: ${Thread.currentThread().id}, ${Thread.currentThread().name}"
                    )
                    t3.addImportantPhoneData(VeryImportantPhoneDataProvider.location)
                } catch (e: InterruptedException) {
                    break
                }
            } else {
                break
            }

        }
    }

    private fun initializeT2() = Thread {
        while (true) {
            if (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep((B * 1000).toLong())
                    Log.d(
                        TAG,
                        "thread 2: ${Thread.currentThread().id}, ${Thread.currentThread().name}"
                    )
                    t3.addImportantPhoneData(VeryImportantPhoneDataProvider.batteryUsage)
                } catch (e: InterruptedException) {
                    break
                }
            } else {
                break
            }

        }
    }

}