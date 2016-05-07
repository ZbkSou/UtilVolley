package com.example.bkzhou.base;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.bkzhou.network.VolleyLruCache;
import com.example.bkzhou.utils.Log;
import com.example.bkzhou.utilvolley.BuildConfig;

import java.lang.ref.WeakReference;

/**
 * Created by bkzhou on 16-4-1.
 */
public class BaseApplication extends Application {
  private static Context mContext;
  /** 用来保存最新打开页面的context */
  private volatile static WeakReference<Context> instanceRef = null;
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
  public static Context getInstance(){
    if (instanceRef == null || instanceRef.get() == null){
      synchronized (BaseApplication.class) {
        if (instanceRef == null || instanceRef.get() == null) {
          Context context = ActivityManager.getInstance().getActivity();
          if (context != null)
            instanceRef = new WeakReference<>(context);
          else {
            instanceRef = new WeakReference<>(mContext);
          }
        }
      }
    }
    return instanceRef.get();
  }
  /**
   * 将{@link #instanceRef}设置为最新页面的context
   * @param context 最新页面的context
   */
  public static void setInstanceRef(Context context){
    instanceRef = new WeakReference<>(context);
  }
}
