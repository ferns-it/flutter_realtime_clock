package com.example.flutter_realtime_clock.adapter

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.example.flutter_realtime_clock.FlutterRealtimeClockPlugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ClockAdapter {

    private var mInstance: ClockAdapter? = null
    private var activity: Activity? = null

    private val timeTickReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            sendCurrentTimeToEventChannel()
        }
    }


    fun sendCurrentTimeToEventChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDateTime = LocalDateTime.now()
            // Format LocalDateTime to ISO String
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val formattedDateTime = currentDateTime.format(formatter)
            FlutterRealtimeClockPlugin.eventSink?.success(formattedDateTime)
            Log.d("Flutter Real Time Clock", "Event Sent Successfully!")
        }
    }


    fun getInstance(): ClockAdapter? {
        if (mInstance == null) {
            mInstance = this;
        }
        return mInstance
    }


    fun init(activity: Activity) {
        this.activity = activity
        sendCurrentTimeToEventChannel()
        Log.d("Flutter Real Time Clock", "Event Sent Successfully! (activity attached)")
        val intent = IntentFilter();
        intent.addAction(Intent.ACTION_TIME_TICK)
        intent.addAction(Intent.ACTION_TIME_CHANGED)
        intent.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        this.activity!!.registerReceiver(timeTickReceiver, intent)

    }

    fun destroy() {
        activity!!.unregisterReceiver(timeTickReceiver)
        activity = null
    }
}