package com.mikesu.retrofitcachecallback;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.UnknownHostException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MikeSu on 01/08/16.
 * www.michalsulek.pl
 */
public abstract class CacheCallback<T> implements Callback<T> {

  private static final String TAG = CacheCallback.class.getName();

  private Context context;
  private Gson gson;
  private Class bodyClass;
  private boolean useCache;

  public CacheCallback(Context context, Class bodyClass, boolean useCache, Gson gson) {
    this.context = context;
    this.gson = gson;
    this.bodyClass = bodyClass;
    this.useCache = useCache;
  }

  public CacheCallback(Context context, Class bodyClass, boolean useCache) {
    this(context, bodyClass, useCache, new Gson());
  }

  public CacheCallback() {
    this(null, null, false, new Gson());
  }

  @Override
  public void onResponse(Call<T> call, Response<T> response) {
    if (isSuccess(response)) {
      if (useCache) {
        CacheCallbackPrefs.saveToCache(context, call.request().url().toString(), gson.toJson(response.body()));
      }
      success(response.body(), false);
    } else {
      onFailure(call, new Throwable(getError(response)));
    }
  }

  private String getError(Response<T> response) {
    String error;
    try {
      error = response.errorBody().string();
    } catch (IOException e) {
      error = "Unknown error";
      Log.e(TAG, "getError", e);
    }
    return error;
  }

  private boolean isSuccess(Response<T> response) {
    return response.isSuccessful() && response.errorBody() == null && response.body() != null;
  }

  @Override
  public void onFailure(Call<T> call, Throwable throwable) {
    if (useCache && throwable instanceof UnknownHostException) {
      T body;
      try {
        body = (T) gson.fromJson(CacheCallbackPrefs.readFromCache(context, call.request().url().toString()), bodyClass);
      } catch (Exception e) {
        failure(call, throwable);
        Log.e(TAG, "onFailure", e);
        return;
      }
      if (body == null) {
        failure(call, throwable);
      } else {
        Log.d(TAG, "onFailure, get cache error");
        success(body, true);
      }
    } else {
      failure(call, throwable);
    }
  }

  public abstract void success(T body, boolean fromCache);

  public abstract void failure(Call<T> call, Throwable throwable);
}
