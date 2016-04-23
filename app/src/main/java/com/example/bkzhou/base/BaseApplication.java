package com.example.bkzhou.base;

import android.app.Application;
import android.content.Context;

import com.example.bkzhou.utils.Log;
import com.example.bkzhou.utilvolley.BuildConfig;

/**
 * Created by bkzhou on 16-4-1.
 */
public class BaseApplication extends Application {
  private static Context mContext;

  @Override
  public void onCreate() {
    super.onCreate();
    mContext = getApplicationContext();
    Log.setDebug(BuildConfig.DEBUG);
    AppConfig.setIsProduct(!BuildConfig.DEBUG);

  }
  public static Context getContext() {
    return mContext;
  }
}
