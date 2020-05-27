# New Relic Plugin

Android plugin to [adding custom data to New Relic Mobile](https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile/maintenance/add-custom-data-new-relic-mobile).

pubspec.yaml

```
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

```
// String Attribute
NewRelicPlugin.setStringAttribute( "name", "value" )
```