package com.example.bkzhou.base;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.bkzhou.network.VolleyLruCache;
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
  /**
   * 提供ImageLoader
   * @return
   */
  public static com.example.bkzhou.network.ImageLoader getVolleyImageLoader(){
    return com.example.bkzhou.network.ImageLoader.getInstance(mContext);
  }
}
