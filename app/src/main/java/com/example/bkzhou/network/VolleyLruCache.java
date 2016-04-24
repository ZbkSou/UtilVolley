package com.example.bkzhou.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.android.volley.toolbox.*;
import com.android.volley.toolbox.ImageLoader;
import com.example.bkzhou.utils.Log;
import com.example.bkzhou.utils.Md5Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;


/**
 * Created by bkzhou on 16-3-24.
 */
public class VolleyLruCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {
  private static String cacheDir;
  /** 缓存目录 */
  private static VolleyLruCache cache;
  private static  int getCacheSize(){
    return (int)(Runtime.getRuntime().maxMemory()/1024/8);
  }
  /** 获取单例 */
  public static VolleyLruCache getInstance(Context context) {
    if (null == cache) {
      initilize(context);
      cache = new VolleyLruCache();
    }
    return cache;
  }

  /** 初始化方法，Application的onCreate中调用 */
  public static void initilize(Context context) {
    cacheDir = context.getCacheDir().toString()+File.separator;
  }
  public VolleyLruCache() {
    this(getCacheSize());
  }
  private VolleyLruCache(int size){
    super(size);
  }
  @Override
  public Bitmap getBitmap(String url) {
    try {
      String key = Md5Util.getMD5Str(url);
      if ( get(url) != null) {
        return get(url);
      } else {
        File file = new File(cacheDir + key);
        if (file.exists()) {
          Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
          return bitmap;
        }
      }
    } catch (Exception e) {
      Log.d("halfman", e.getMessage());
    }
    return null;

  }

  @Override
  public void putBitmap(String url, Bitmap bitmap) {
        try {
          String key =  Md5Util.getMD5Str(url);
          Bitmap temp = bitmap;
          File file = new File(cacheDir+key);
          FileOutputStream os;
          os = new FileOutputStream(file, false);
          temp.compress(Bitmap.CompressFormat.JPEG, 100, os);
          os.flush();
          os.close();
        } catch (Exception e) {
          e.printStackTrace();
        }


    put(url, bitmap);
  }
}