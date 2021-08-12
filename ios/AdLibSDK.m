//
//  AdLibSDK.m
//  DoubleConversion
//
//  Created by Usama Azam on 29/03/2019.
//

#import "AdLibSDK.h"
#import "MoPub.h"
#import "MPMoPubConfiguration.h"
#import "MPGoogleGlobalMediationSettings.h"
#import "VungleInstanceMediationSettings.h"
#import "UnityAdsInstanceMediationSettings.h"
#import "TapjoyGlobalMediationSettings.h"

@implementation AdLibSDK

RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents {
    return @[
             @"onSDKInitialized",
            ];
}


RCT_EXPORT_METHOD(initializeSDK:(NSString *)unitID) 
{
    MPGoogleGlobalMediationSettings *mpGoogleMediationSettings = [[MPGoogleGlobalMediationSettings alloc] init];
    VungleInstanceMediationSettings *vungleMediationSettings = [[VungleInstanceMediationSettings alloc] init];
    UnityAdsInstanceMediationSettings *unityAdsMediationSettings = [[UnityAdsInstanceMediationSettings alloc] init];
    TapjoyGlobalMediationSettings *tapJoyMediationSettings = [[TapjoyGlobalMediationSettings alloc] init];
    MPMoPubConfiguration *sdkConfig = [[MPMoPubConfiguration alloc] initWithAdUnitIdForAppInitialization: unitID];
    sdkConfig.globalMediationSettings = [[NSArray alloc] initWithObjects: @[mpGoogleMediationSettings, vungleMediationSettings, unityAdsMediationSettings, tapJoyMediationSettings], nil];
    sdkConfig.loggingLevel = MPBLogLevelDebug;
    [[MoPub sharedInstance] grantConsent];
    [[MoPub sharedInstance] initializeSdkWithConfiguration:sdkConfig completion:^{
        NSLog(@"SDK initialization complete");
        [self sendEventWithName:@"onSDKInitialized" body:nil];
    }];
}

@end
