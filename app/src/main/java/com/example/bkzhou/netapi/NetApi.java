package com.example.bkzhou.netapi;

import android.widget.BaseAdapter;

import com.example.bkzhou.network.BaseNetApi;

/**
 * 对基础netapi类的完善，可以在request包下添加自定义的request，并且
 * Created by bkzhou on 16-3-29.
 */
public class NetApi extends BaseNetApi {
  private volatile static NetApi instance;

  public static NetApi getInstance(){
    if (instance == null){
      synchronized (NetApi.class){
        if (instance == null){
          instance = new NetApi();
        }
      }
    }
    return instance;
  }
}
