import 'package:flutter/material.dart';
import 'package:flutter_realtime_clock/flutter_realtime_clock.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _flutterRealtimeClockPlugin = FlutterRealtimeClock();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: StreamBuilder(
              stream: _flutterRealtimeClockPlugin.clockListener,
              builder: (context, snapshot) {
                final currentTime = snapshot.data ?? DateTime.now();
                return Text("Current Time: ${currentTime.toIso8601String()}");
              }),
        ), 
      ),
    );
  }
}
