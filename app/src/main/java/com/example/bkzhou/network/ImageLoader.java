package com.example.bkzhou.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Field;

/**
 * Created by bkzhou on 16-3-24.
 */
public class ImageLoader {

  private final int MAXDISKCACHEBYTES = 10 * 1024 * 1024;
  private static volatile ImageLoader instance;
  private com.android.volley.toolbox.ImageLoader mImageLoader;

  private ImageLoader(final Context context) {
    RequestQueue requestQueue = Volley.newRequestQueue(context);
//    VolleyLruCache lruCache = new VolleyLruCache();
    mImageLoader = new com.android.volley.toolbox.ImageLoader(requestQueue, VolleyLruCache.getInstance(context));
  }

  public static ImageLoader getInstance(Context context) {
    if (instance == null) {
      synchronized (ImageLoader.class) {
        if (instance == null)
          instance = new ImageLoader(context);
      }
    }
    return instance;
  }
  public com.android.volley.toolbox.ImageLoader getmImageLoader(){
    return mImageLoader;
  }

  /**
   * 获取imageview的大小
   * @param object
   * @param fieldName
   * @return
   */
  private int getImageViewFieldValue(Object object, String fieldName) {
    int value = 0;
    try {
      Field field = ImageView.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      int fieldValue = (Integer) field.get(object);
      if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
        value = fieldValue;
      }
    } catch (Exception e) {
      e.printStackTrace();

    }
    return value;

  }

  /**
   * 加载图片回调
   */
  public interface OnLoadCallBack {
    void onLoadSuccess(Bitmap bitmap, String url);
    void onLoadFail(NetError error);
  }

  public void loadImage(String url,final ImageView imageView){
    loadImage(url,imageView,null);
  }
  public void loadImage(String url,final OnLoadCallBack listener){
    loadImage(url,0,0,listener);
  }
  /**
   * 带回调的加载图片
   * @param url 图片url
   * @param width 需要加载的图片宽
   * @param height 需要加载的图片高
   * @param listener 加载图片完成回调
   */
  public void loadImage(String url, int width, int height, final OnLoadCallBack listener){
    loadImage(url, width, height, null, listener);
  }
  /**
   * 带回调的加载图片
   * @param url 图片url
   * @param imageView 需要加载图片的视图
   * @param listener 加载图片的回调
   */
  public void loadImage(String url, final ImageView imageView, final OnLoadCallBack listener){
    int width = getImageViewFieldValue(imageView, "mMaxWidth");
    int height = getImageViewFieldValue(imageView, "mMaxHeight");

    loadImage(url, width, height, imageView, listener);
  }
  /**
   * 加载图片
   * @param url 图片url
   * @param width 图片加载的图片宽
   * @param height 图片加载的图片高
   * @param imageView 需要加载图片的视图
   */
  public void loadImage(String url, int width, int height, final ImageView imageView){
    loadImage(url, width, height, imageView, null);
  }
  /**
   * 加载图片
   * @param url 图片url
   * @param imageView 需要加载图片的视图
   * @param width 需要加载视图的宽
   * @param height 需要加载视图的高
   * @param listener 加载图片回调
   */
  public void loadImage(String url, int width, int height, final ImageView imageView, final OnLoadCallBack listener){
    mImageLoader.get(url, new com.android.volley.toolbox.ImageLoader.ImageListener() {
      @Override
      public void onResponse(com.android.volley.toolbox.ImageLoader.ImageContainer response, boolean isImmediate) {
        if (imageView != null)
          imageView.setImageBitmap(response.getBitmap());
        if (listener != null)
          listener.onLoadSuccess(response.getBitmap(), response.getRequestUrl());
      }

      @Override
      public void onErrorResponse(VolleyError error) {
        if (listener != null) {
          NetError netError = new NetError();
          netError.transferVolleyError(error);
          listener.onLoadFail(netError);
        }
      }
    }, width, height);
  }

  /**
   * 加载图片
   * @param url 图片url
   * @param imageView 需要加载该图片的url
   * @param defaultImageResId 加载图片时的默认资源id
   * @param errorImageResId 加载图片失败时显示的图片资源id
   *                        自动加载view的尺寸
   */
  public void loadImage(String url, final ImageView imageView, int defaultImageResId, int errorImageResId){
    int width = getImageViewFieldValue(imageView, "mMaxWidth");
    int height = getImageViewFieldValue(imageView, "mMaxHeight");

    loadImage(url,width, height, imageView, defaultImageResId, errorImageResId);
  }

  /**
   * 加载图片
   * @param url 图片url
   * @param imageView 需要加载该图片的url
   * @param defaultImageResId 加载图片时的默认资源id
   * @param errorImageResId 加载图片失败时显示的图片资源id
   * @param width 加载图片的宽度
   * @param height 加载图片的高度
   */
  public void loadImage(String url, int width, int height, final ImageView imageView,
                        int defaultImageResId, int errorImageResId){
    com.android.volley.toolbox.ImageLoader.ImageListener listener =
      com.android.volley.toolbox.ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
    mImageLoader.get(url, listener, width, height);
  }

}
