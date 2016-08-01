package com.mikesu.retrofitcachecallback;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Map;

/**
 * Created by MikeSu on 01/08/16.
 * www.michalsulek.pl
 */
public class SharedPrefsUtils {

  private static final String TAG = SharedPrefsUtils.class.getName();

  private static final String PREF_CACHE_CALLBACK = "cacheCallback";

  public static void saveToCache(Context context, String url, String json) {
    if (context != null) {
      SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_CACHE_CALLBACK, Context.MODE_PRIVATE);
      sharedPreferences.edit().putString(url, json).apply();
      Log.d(TAG, "Data from url: " + url + " saved to cache");
    } else {
      Log.e(TAG, "Failed to cache from url " + url + " , Null Context");
    }
  }

  public static String readFromCache(Context context, String url) {
    if (context != null) {
      SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_CACHE_CALLBACK, Context.MODE_PRIVATE);
      String value = sharedPreferences.getString(url, "");
      if (value.isEmpty()) {
        Log.w(TAG, "Cache from " + url + " is empty");
      }
      return value;
    } else {
      Log.e(TAG, "Failed to get " + url + " cache, Null Context");
      return "";
    }
  }

  public static void removeAllFromCache(Context context) {
    if (context != null) {
      SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_CACHE_CALLBACK, Context.MODE_PRIVATE);
      for (Map.Entry entry : sharedPreferences.getAll().entrySet()) {
        sharedPreferences.edit().remove((String) entry.getKey()).apply();
      }
      sharedPreferences.edit().clear().apply();
    } else {
      Log.e(TAG, "Failed to remove cache, Null Context");
    }
  }

}
