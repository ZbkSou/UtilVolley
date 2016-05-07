package com.example.bkzhou.utils;

import android.view.View;
import android.widget.Toast;

import com.example.bkzhou.base.BaseApplication;

/**
 * Created by bkzhou on 16-5-7.
 */
public class T {
  private volatile static T instance;
  private int mGravity = -1;
  private int xOffset = 0;
  private int yOffset = 0;
  private Toast mTemp;

  public static T getInstance(){
    if (instance == null){
      synchronized (T.class){
        if (instance == null){
          instance = new T();
        }
      }
    }
    return instance;
  }
  private T(){
  }
  /**
   * 设置该toast的显示位置，只对当前toast有效
   */
  public T setGravity(int mGravity) {
    this.mGravity = mGravity;
    return getInstance();
  }
  public T setxOffset(int xOffset) {
    this.xOffset = xOffset;
    return getInstance();
  }
  public T setyOffset(int yOffset) {
    this.yOffset = yOffset;
    return getInstance();
  }
  public void showShort(String message){
    showShort(message, null);
  }
  public void showShort(String message, View v){
    //防止一堆toast的显示堆积
    if (mTemp != null)
      mTemp.cancel();
    mTemp = Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_SHORT);
    if (mGravity != -1)
      mTemp.setGravity(mGravity, xOffset, yOffset);
    if (v != null){
      mTemp.setView(v);
    }
    mTemp.show();
    reset();
  }
  public void showLong(String message){
    showLong(message, null);
  }

  /**
   * 需要在toast中显示的v
   */
  public void showLong(String message, View v){
    //防止一堆toast的显示堆积
    if (mTemp != null)
      mTemp.cancel();
    mTemp = Toast.makeText(BaseApplication.getInstance(), message, Toast.LENGTH_LONG);
    if (mGravity != -1)
      mTemp.setGravity(mGravity, xOffset, yOffset);
    if (v != null){
      mTemp.setView(v);
    }
    mTemp.show();
    reset();
  }
  /**
   * 重置位置
   */
  private void reset(){
    mGravity = -1;
    xOffset = 0;
    yOffset = 0;
  }

}
