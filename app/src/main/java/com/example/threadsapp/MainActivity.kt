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
    val t3 = Thread3(C)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "main: ${Thread.currentThread().id}, ${Thread.currentThread().name}")
        t1 = Thread {
            while (true) {
                Thread.sleep((A * 1000).toLong())
                Log.d(TAG, "thread 1: ${Thread.currentThread().id}, ${Thread.currentThread().name}")
                t3.addImportantPhoneData(VeryImportantPhoneDataProvider.location)
            }
        }
        t2 = Thread {
            while (true) {
                Thread.sleep((B * 1000).toLong())
                Log.d(TAG, "thread 2: ${Thread.currentThread().id}, ${Thread.currentThread().name}")
                t3.addImportantPhoneData(VeryImportantPhoneDataProvider.batteryUsage)
            }
        }

        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            t1.start()
            t2.start()
            t3.start()
        }

        stop_button.setOnClickListener {
//            t1.interrupt()
//            t2.interrupt()
//            t3.interrupt()
        }
    }

}