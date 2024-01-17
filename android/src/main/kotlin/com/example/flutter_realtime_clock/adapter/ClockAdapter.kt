package com.example.flutter_realtime_clock.adapter

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
    private var mContext: Context? = null

    private val timeTickReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            sendCurrentTimeToEventChannel()
        }
    }


    private fun sendCurrentTimeToEventChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDateTime = LocalDateTime.now()
            // Format LocalDateTime to ISO String
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val formattedDateTime = currentDateTime.format(formatter)
            FlutterRealtimeClockPlugin.eventSink?.success(formattedDateTime)
            Log.d("Flutter Real Time", "current date & time: $formattedDateTime")
        }
    }


    fun getInstance(): ClockAdapter? {
        if (mInstance == null) {
            mInstance = this;
        }
        return mInstance
    }


    fun init(reactContext: Context?) {
        mContext = reactContext
        val intent = IntentFilter();
        intent.addAction(Intent.ACTION_TIME_TICK)
        intent.addAction(Intent.ACTION_TIME_CHANGED)
        intent.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        mContext!!.registerReceiver(timeTickReceiver, intent)
        sendCurrentTimeToEventChannel()
    }

    fun destroy() {
        mContext!!.unregisterReceiver(timeTickReceiver)
        mContext = null
    }
}