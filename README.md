# New Relic Plugin

Android plugin to [adding custom data to New Relic Mobile](https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile/maintenance/add-custom-data-new-relic-mobile).

## Android
Add android New Relic agent as usually. 

Merge the following into your project-level build.gradle file:
```
buildscript {
  repositories {
    mavenCentral()
  }

  dependencies {
    classpath "com.newrelic.agent.android:agent-gradle-plugin:5.+"
  }
}
```

Merge the following into your app-level build.gradle file:
```
repositories {
  mavenCentral()
}

apply plugin: 'android'
apply plugin: 'newrelic'

dependencies {
  implementation "com.newrelic.agent.android:android-agent:5.+"
}

```

APP PERMISSIONS:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

START THE AGENT:

In your default Activity import the NewRelic class.
```java
import com.newrelic.agent.android.NewRelic;
```

And in the onCreate() method add this call to initialize New Relic. (The unique token included below has been generated for your app ChedrauiTest specifically)
```java
NewRelic.withApplicationToken( "YOUR_KEY" ).start(this.getApplication());
```

Then import this plugin in your app pubspec.yaml
```yaml
dependencies:
  new_relic_plugin:
    git:
      url: git://github.com/OswaldoGallo/NewRelicPlugin.git
```

## Getting Started

Import NewRelicPlugin

```dart
import 'package:new_relic_plugin/new_relic_plugin.dart';
```

### Set Attributes

```dart
// String Attribute
// Future<bool> setStringAttribute(String name, String value)

NewRelicPlugin.setStringAttribute('attr_name', 'attr_value');

// Double Attribute
// Future<bool> setDoubleAttribute(String name, double value)

NewRelicPlugin.setDoubleAttribute('attr_name', 1.0);

// Boolean Attribute
// Future<bool> setBooleanAttribute(String name, bool value)

NewRelicPlugin.setBooleanAttribute('attr_name', true);
```

### Increment Attribute

```dart
// Future<bool> incrementAttribute(String name, {double value = 1.0})

// Increments 1.0
NewRelicPlugin.incrementAttribute('double_attr_name');

// Increments custom value
NewRelicPlugin.incrementAttribute('double_attr_name', value: 2.0);
```

### Record Breadcrumbs

```dart
// Future<bool> recordBreadcrumb(String breadcrumbName, { Map<String, dynamic> attributes} )

final Map<String, dynamic> attributes = <String, dynamic> {
  'attr_name': 'test_value'
};

NewRelicPlugin.recordBreadcrumb('TEST_BREADCRUMB', attributes: attributes);
```

### Interactions

```dart
// Future<String> startInteraction(String actionName)
// void endInteraction(String id)

// Start Interaction
final String interaction_id = await NewRelicPlugin.startInteraction('test_interaction');

// End Interaction
NewRelicPlugin.endInteraction(interaction_id);
```

### Record Custom Event
```dart
// Future<bool> recordCustomEvent(String eventType, {String eventName = "", Map<String, dynamic> eventAttributes} )

NewRelicPlugin.recordCustomEvent('event_type');
NewRelicPlugin.recordCustomEvent('event_type', eventName: 'event_name');

final Map<String, dynamic> attributes = <String, dynamic> {
  'attr_name': 'test_value'
};
NewRelicPlugin.recordCustomEvent('event_type', eventName: 'event_name', eventAttributes: attributes);
```


