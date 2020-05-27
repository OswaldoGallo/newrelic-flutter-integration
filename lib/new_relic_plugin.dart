import 'dart:async';

import 'package:flutter/services.dart';

class NewRelicPlugin {
  static const MethodChannel _channel =
      const MethodChannel('new_relic_plugin');

  static Future<bool> setStringAttribute(String name, String value) async {
    final Map<String, dynamic> params = <String, String> {
      'name': name,
      'value': value
    };
    final bool result = await _channel.invokeMethod('setStringAttribute', params);
    return result;
  }

  static Future<bool> setDoubleAttribute(String name, double value) async {
    final Map<String, dynamic> params = <String, dynamic> {
      'name': name,
      'value': value
    };
    final bool result = await _channel.invokeMethod('setDoubleAttribute', params);
    return result;
  }

  static Future<bool> setBooleanAttribute(String name, bool value) async {
    final Map<String, dynamic> params = <String, dynamic> {
      'name': name,
      'value': value
    };
    final bool result = await _channel.invokeMethod('setBooleanAttribute', params);
    return result;
  }

  static Future<bool> incrementAttribute(String name, {double value = 1.0}) async {
    final Map<String, dynamic> params = <String, dynamic> {
      'name': name,
      'value': value
    };
    final bool result = await _channel.invokeMethod('incrementAttribute', params);
    return result;
  }

  static Future<bool> recordBreadcrumb(String breadcrumbName, { Map<String, dynamic> attributes} ) async {
    final Map<String, dynamic> params = <String, dynamic> {
      'breadcrumbName': breadcrumbName,
      'attributes': attributes
    };
    final bool result = await _channel.invokeMethod('recordBreadcrumb', params);
    return result;
  }

  static Future<String> startInteraction(String actionName) async {
    final Map<String, String> params = <String, String> {
      'actionName': actionName
    };
    final String result = await _channel.invokeMethod('startInteraction', params);
    return result;
  }

  static void endInteraction(String id) async {
    final Map<String, String> params = <String, String> { 'id': id };
    print(await _channel.invokeMethod('endInteraction', params));
  }

  static Future<bool> recordCustomEvent(String eventType, {String eventName = "", Map<String, dynamic> eventAttributes} ) async {
    final Map<String, dynamic> params = <String, dynamic> {
      'eventType': eventType,
      'eventName': eventName,
      'eventAttributes': eventAttributes
    };
    final bool result = await _channel.invokeMethod('recordCustomEvent', params);
    return result;
  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
