package com.reactlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import javax.annotation.Nullable;

import com.applovin.sdk.AppLovinSdk;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.IronSourceAdapterConfiguration;

import java.util.HashMap;
import java.util.Map;

public class AdLibSDK extends ReactContextBaseJavaModule {

    public static final String EVENT_SDK_INITIALIZED = "onSDKInitialized";

    ReactApplicationContext mReactContext;

    public AdLibSDK(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AdLibSDK";
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    @ReactMethod
    public void initializeSDK(final String unitID, final String ironSourceId) {
        Log.i("Mopub SDK", "Initialization...");

        Handler mainHandler = new Handler(getCurrentActivity().getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                // declare your ironSource App Key and Ad Units
                Map<String, String> ironSourceSettings = new HashMap<>(); ironSourceSettings.put("applicationKey", ironSourceId);

                SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(unitID)
                        .withLogLevel(MoPubLog.LogLevel.DEBUG)
                        .withMediatedNetworkConfiguration(IronSourceAdapterConfiguration.class.getName(),
                                ironSourceSettings)
                        .build();

                MoPub.initializeSdk(getCurrentActivity(), sdkConfiguration, initSdkListener());
                AppLovinSdk.initializeSdk(getCurrentActivity());
            }

            private SdkInitializationListener initSdkListener() {
                return new SdkInitializationListener() {
                    @Override
                    public void onInitializationFinished() {
                        MoPub.getPersonalInformationManager().grantConsent();
                        sendEvent(EVENT_SDK_INITIALIZED, null);
                    }
                };
            }
        };
        mainHandler.post(myRunnable);
    }
}
