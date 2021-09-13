package com.reactlibrary;

import android.content.Context;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.mopub.common.MoPub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by usamaazam on 29/03/2019.
 */

public class MoPubBannerManager extends SimpleViewManager<RNMoPubBanner> {
    public static final String REACT_CLASS = "RNMoPubBanner";


    RNMoPubBanner rnMoPubBanner;
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNMoPubBanner createViewInstance(final ThemedReactContext reactContext) {
        rnMoPubBanner = new RNMoPubBanner(reactContext);
        return rnMoPubBanner;
    }

    @ReactProp(name = "adUnitId")
    public void setAdUnitId(RNMoPubBanner view, String adUnitId) {

        if(MoPub.isSdkInitialized()) {
            view.setAdUnitId(adUnitId);
            view.loadAd();
        } else {
            // AdLibSDK.initializeAdSDK(view, adUnitId, rnMoPubBanner.mContext.getCurrentActivity());
        }

    }

    @ReactProp(name = "testing", defaultBoolean = false)
    public void setTesting(RNMoPubBanner view, Boolean testing) {
        view.setTesting(testing);
    }

    @ReactProp(name = "autoRefresh", defaultBoolean = true)
    public void setAutoRefresh(RNMoPubBanner view, Boolean autoRefresh) {
        view.setAutorefreshEnabled(autoRefresh);
    }

    @ReactProp(name = "localExtras")
    public void setLocalExtras(RNMoPubBanner view, @Nullable ReadableMap localExtras) {
        view.setLocalExtras(toHashMap(localExtras));
    }

    @ReactProp(name = "keywords")
    public void setKeywords(RNMoPubBanner view, @Nullable String keywords) {
        view.setKeywords(keywords);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        builder.put(RNMoPubBanner.EVENT_LOADED, MapBuilder.of("registrationName", RNMoPubBanner.EVENT_LOADED));
        builder.put(RNMoPubBanner.EVENT_FAILED, MapBuilder.of("registrationName", RNMoPubBanner.EVENT_FAILED));
        builder.put(RNMoPubBanner.EVENT_CLICKED, MapBuilder.of("registrationName", RNMoPubBanner.EVENT_CLICKED));
        builder.put(RNMoPubBanner.EVENT_EXPANDED, MapBuilder.of("registrationName", RNMoPubBanner.EVENT_EXPANDED));
        builder.put(RNMoPubBanner.EVENT_COLLAPSED, MapBuilder.of("registrationName", RNMoPubBanner.EVENT_COLLAPSED));
        return builder.build();
    }

    private HashMap<String, Object> toHashMap(ReadableMap map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        ReadableMapKeySetIterator iterator = map.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (map.getType(key)) {
                case Null:
                    hashMap.put(key, null);
                    break;
                case Boolean:
                    hashMap.put(key, map.getBoolean(key));
                    break;
                case Number:
                    hashMap.put(key, map.getDouble(key));
                    break;
                case String:
                    hashMap.put(key, map.getString(key));
                    break;
                case Map:
                    hashMap.put(key,toHashMap(map.getMap(key)));
                    break;
                case Array:
                    hashMap.put(key, toArrayList(map.getArray(key)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
            }
        }
        return hashMap;
    }

    private ArrayList<Object> toArrayList(ReadableArray array) {
        ArrayList<Object> arrayList = new ArrayList<>(array.size());
        for (int i = 0, size = array.size(); i < size; i++) {
            switch (array.getType(i)) {
                case Null:
                    arrayList.add(null);
                    break;
                case Boolean:
                    arrayList.add(array.getBoolean(i));
                    break;
                case Number:
                    arrayList.add(array.getDouble(i));
                    break;
                case String:
                    arrayList.add(array.getString(i));
                    break;
                case Map:
                    arrayList.add(toHashMap(array.getMap(i)));
                    break;
                case Array:
                    arrayList.add(toArrayList(array.getArray(i)));
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object at index: " + i + ".");
            }
        }
        return arrayList;
    }
}
