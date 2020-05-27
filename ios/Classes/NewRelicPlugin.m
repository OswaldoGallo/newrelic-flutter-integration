#import "NewRelicPlugin.h"
#if __has_include(<new_relic_plugin/new_relic_plugin-Swift.h>)
#import <new_relic_plugin/new_relic_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "new_relic_plugin-Swift.h"
#endif

@implementation NewRelicPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftNewRelicPlugin registerWithRegistrar:registrar];
}
@end
