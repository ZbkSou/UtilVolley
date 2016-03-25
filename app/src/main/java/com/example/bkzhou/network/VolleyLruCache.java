package com.example.bkzhou.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.*;
import com.android.volley.toolbox.ImageLoader;


/**
 * Created by bkzhou on 16-3-24.
 */
public class VolleyLruCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {
  private static  int getCacheSize(){
    return (int)(Runtime.getRuntime().maxMemory()/1024/8);
  }

  public VolleyLruCache() {
    this(getCacheSize());
  }
  private VolleyLruCache(int size){
    super(size);
  }
  @Override
  public Bitmap getBitmap(String url) {
    return get(url);
  }

  @Override
  public void putBitmap(String url, Bitmap bitmap) {
    put(url, bitmap);
  }
}