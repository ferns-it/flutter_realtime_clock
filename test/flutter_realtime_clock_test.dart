import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_realtime_clock/flutter_realtime_clock.dart';
import 'package:flutter_realtime_clock/flutter_realtime_clock_platform_interface.dart';
import 'package:flutter_realtime_clock/flutter_realtime_clock_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterRealtimeClockPlatform
    with MockPlatformInterfaceMixin
    implements FlutterRealtimeClockPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Stream<DateTime>? get clockListener => throw UnimplementedError();
}

void main() {
  final FlutterRealtimeClockPlatform initialPlatform =
      FlutterRealtimeClockPlatform.instance;

  test('$MethodChannelFlutterRealtimeClock is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterRealtimeClock>());
  });

  test('getPlatformVersion', () async {
    FlutterRealtimeClock flutterRealtimeClockPlugin = FlutterRealtimeClock();
    MockFlutterRealtimeClockPlatform fakePlatform =
        MockFlutterRealtimeClockPlatform();
    FlutterRealtimeClockPlatform.instance = fakePlatform;

    expect(await flutterRealtimeClockPlugin.getPlatformVersion(), '42');
  });
}
