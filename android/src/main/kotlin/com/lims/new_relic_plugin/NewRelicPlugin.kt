package com.lims.new_relic_plugin

import androidx.annotation.NonNull
import com.newrelic.agent.android.NewRelic

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** NewRelicPlugin */
public class NewRelicPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.flutterEngine.dartExecutor, "new_relic_plugin")
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    private const val SUCCESS = "Success: The provided attribute was successfully submitted to New Relic -> "
    private const val ERROR = "Error: Cannot submit the provided attribute -> "
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "new_relic_plugin")
      channel.setMethodCallHandler(NewRelicPlugin())
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
        "getPlatformVersion" -> {
          result.success("Android ${android.os.Build.VERSION.RELEASE}")
        }
        "setStringAttribute" -> {
          val name: String = call.argument("name")!!
          val value: String? = call.argument("value")
          if (NewRelic.setAttribute(name, value)) {
            result.success("$SUCCESS $name: $value")
          } else {
            result.error("$ERROR $name: $value", null, null)
          }

        }
        else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  //  Add session-level custom attributes

  private fun setAttribute(name: String, value: String) {
    NewRelic.setAttribute(name, value)
  }

  private fun setAttribute(name: String, value: Double) {
    NewRelic.setAttribute(name, value)
  }

//  fun incrementAttribute(name: String, value: Float? = null, boolean: Boolean? = null) {
//    NewRelic.incrementAttribute(name, value)
//  }

}
