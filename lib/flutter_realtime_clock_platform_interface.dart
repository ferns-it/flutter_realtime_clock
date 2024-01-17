import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_realtime_clock_method_channel.dart';

abstract class FlutterRealtimeClockPlatform extends PlatformInterface {
  /// Constructs a FlutterRealtimeClockPlatform.
  FlutterRealtimeClockPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterRealtimeClockPlatform _instance =
      MethodChannelFlutterRealtimeClock();

  /// The default instance of [FlutterRealtimeClockPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterRealtimeClock].
  static FlutterRealtimeClockPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterRealtimeClockPlatform] when
  /// they register themselves.
  static set instance(FlutterRealtimeClockPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Stream<DateTime>? get clockListener;
}
