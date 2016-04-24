package com.example.bkzhou.netapi;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.bkzhou.network.BaseNetApi;
import com.example.bkzhou.network.NetError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 对基础netapi类的完善，可以在request包下添加自定义的request，并且
 * Created by bkzhou on 16-3-29.
 */
public class NetApi extends BaseNetApi {
  private volatile static NetApi instance;
  private JsonResponse jsonResponse;

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

  /**
   * 转换成NetCallback
   * @param jsonResponse
   * @return
   */
  private OnNetCallback<String> getCallback(final JsonResponse jsonResponse){
    return new OnNetCallback<String>() {
      @Override
      public void onSuccess(String result) {
        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        try {
          if (result.trim().startsWith("[")) {
            jsonArr = new JSONArray(result);
          } else if (result.trim().startsWith("{")) {
            jsonObj = new JSONObject(result);
          }
          jsonResponse.onSuccess(jsonObj, jsonArr);

        } catch (JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          jsonResponse.onError(new NetError("convert response to JSONObject or JSONArray error"));
        }
      }

      @Override
      public void onError(NetError error) {
        jsonResponse.onError(error);

      }
    };
  }

  public void get(Context context,String url,Map<String,String> params,  JsonResponse jsonResponse){
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
    stringRequest(Request.Method.GET, context, newUrl.toString(), params, getCallback(jsonResponse));
  }



  public void post(Context context,String url, Map<String, String> params,JsonResponse jsonResponse) {
    stringRequest(Request.Method.POST, context, url, params, getCallback(jsonResponse));
  }
  public void put(Context context,String url, Map<String, String> params, JsonResponse jsonResponse) {
    stringRequest(Request.Method.PUT, context, url, params, getCallback(jsonResponse));
  }
  public void delete(Context context,String url, Map<String, String> params,JsonResponse jsonResponse) {
    stringRequest(Request.Method.DELETE, context, url, params, getCallback(jsonResponse));
  }
  /**
   * Json 返回值
   *
   * @author ihaveu
   */
  public interface JsonResponse {
    public void onSuccess(JSONObject response, JSONArray responseArray);

    public void onError(NetError error);
  }
}
