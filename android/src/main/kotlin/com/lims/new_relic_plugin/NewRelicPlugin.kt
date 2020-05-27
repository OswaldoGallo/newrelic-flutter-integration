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
public class NewRelicPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

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

            // Add session-level custom attributes

            "setStringAttribute" -> {
                val name: String = call.argument("name")!!
                val value: String = call.argument("value")!!
                val bool = NewRelic.setAttribute(name, value)
                result.success(bool)
            }

            "setDoubleAttribute" -> {
                val name: String = call.argument("name")!!
                val value: Double = call.argument("value")!!
                val bool = NewRelic.setAttribute(name, value)
                result.success(bool)
            }

            "setBooleanAttribute" -> {
                val name: String = call.argument("name")!!
                val value: Boolean = call.argument("value")!!
                val bool = NewRelic.setAttribute(name, value)
                result.success(bool)
            }

            "incrementAttribute" -> {
                val name: String = call.argument("name")!!
                val value: Double = call.argument("value")!!
                val bool = NewRelic.incrementAttribute(name, value)
                result.success(bool)
            }

            // Record breadcrumbs

            "recordBreadcrumb" -> {
                val breadcrumbName: String = call.argument("breadcrumbName")!!
                val attributes: Map<String, Any>? = call.argument("attributes")
                val bool = NewRelic.recordBreadcrumb(breadcrumbName, attributes)
                result.success(bool)
            }

            // Create custom interactions

            "startInteraction" -> {
                val actionName: String = call.argument("actionName")!!
                val id : String? = NewRelic.startInteraction(actionName)
                if (!id.isNullOrEmpty()) {
                    result.success(id)
                } else {
                    result.error("Error!", null, null)
                }
            }

            "endInteraction" -> {
                val id: String = call.argument("id")!!
                NewRelic.endInteraction(id)
                result.success("Interaction $id ended")
            }

            // Record custom events

            "recordCustomEvent" -> {
                val eventType: String = call.argument("eventType")!!
                val eventName: String? = call.argument("eventName")!!
                val eventAttributes: Map<String, Any>? = call.argument("eventAttributes")!!
                val bool = NewRelic.recordCustomEvent(eventType, eventName, eventAttributes)
                result.success(bool)
            }

            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

}
