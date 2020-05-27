import 'dart:async';

import 'package:flutter/services.dart';

class NewRelicPlugin {
  static const MethodChannel _channel =
      const MethodChannel('new_relic_plugin');

  static Future<String> setStringAttribute(String name, dynamic value) async {

    final Map<String, dynamic> params = <String, dynamic> {
      'name': name,
      'value': value
    };

    final String result = await _channel.invokeMethod('setStringAttribute', params);

    return result;

  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
