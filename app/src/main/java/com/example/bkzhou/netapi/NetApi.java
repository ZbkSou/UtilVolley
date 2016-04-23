package com.example.bkzhou.netapi;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.bkzhou.network.BaseNetApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

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
  public void get(Context context,String url,Map<String,String> params,  BaseNetApi.OnNetCallback callback){
    StringBuilder newUrl = new StringBuilder();
    newUrl.append(url);
    if (params != null) {
      if (url.indexOf("?") < 0) {
        newUrl.append("?");
      }
      for (String key : params.keySet()) {
        newUrl.append(key);
        newUrl.append("=");
        newUrl.append(params.get(key));
        newUrl.append("&");
      }
      newUrl.deleteCharAt(newUrl.length() - 1);
    }
    stringRequest(Request.Method.GET, context, newUrl.toString(), params, callback);
  }
//  public void get(Context context,String url,  BaseNetApi.OnNetCallback callback){
//    stringRequest(Request.Method.GET, context, url, null, callback);
//  }

  public void post(Context context,String url, Map<String, String> params, BaseNetApi.OnNetCallback callback) {
    stringRequest(Request.Method.POST, context, url, params, callback);
  }
  public void put(Context context,String url, Map<String, String> params, BaseNetApi.OnNetCallback callback) {
    stringRequest(Request.Method.PUT, context, url, params, callback);
  }
  public void delete(Context context,String url, Map<String, String> params, BaseNetApi.OnNetCallback callback) {
    stringRequest(Request.Method.DELETE, context, url, params, callback);
  }

}
