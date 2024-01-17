import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_realtime_clock_platform_interface.dart';

/// An implementation of [FlutterRealtimeClockPlatform] that uses method channels.
class MethodChannelFlutterRealtimeClock extends FlutterRealtimeClockPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_realtime_clock');

  /// The event channel used to interact with the native platform.
  @visibleForTesting
  final eventChannel = const EventChannel('flutter_realtime_clock/events');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Stream<DateTime>? get clockListener => eventChannel
      .receiveBroadcastStream()
      .cast<String>()
      .map((dateTime) => DateTime.parse(dateTime));
}
