package com.example.flutter_realtime_clock

import androidx.annotation.NonNull
import com.example.flutter_realtime_clock.adapter.ClockAdapter

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterRealtimeClockPlugin */
class FlutterRealtimeClockPlugin : FlutterPlugin, MethodCallHandler, ActivityAware,
    EventChannel.StreamHandler {

    private var adapter: ClockAdapter? = null
    private lateinit var channel: MethodChannel
    private lateinit var eventChannel: EventChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_realtime_clock")
        eventChannel =
            EventChannel(flutterPluginBinding.binaryMessenger, "flutter_realtime_clock/events")
        channel.setMethodCallHandler(this)
        eventChannel.setStreamHandler(this)
        adapter = ClockAdapter().getInstance()
    }

    companion object {
        var eventSink: EventChannel.EventSink? = null
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        adapter!!.init(binding.activity);
    }

    override fun onDetachedFromActivityForConfigChanges() {}

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        adapter!!.init(binding.activity);
    }

    override fun onDetachedFromActivity() {
        adapter!!.destroy()
    }

    override fun onListen(args: Any?, events: EventChannel.EventSink?) {
        eventSink = events
    }

    override fun onCancel(args: Any?) {
        eventSink = null
    }
}
