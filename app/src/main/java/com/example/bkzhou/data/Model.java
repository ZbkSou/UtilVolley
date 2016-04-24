package com.example.bkzhou.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.bkzhou.netapi.NetApi;
import com.example.bkzhou.network.BaseNetApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * 所有model的请求基类
 * Created by bkzhou on 16-4-1.
 */
public class Model {
  private static String TAG = "Model";
  protected static NetApi netApi;

  public Model(Context context){
    netApi = NetApi.getInstance();

  }



  public void get(Context context,String url,Map<String,String> params,  NetApi.JsonResponse jsonResponse){

    netApi.get(context, url, params, jsonResponse);
  }
  public void post(Context context,String url, Map<String, String> params, NetApi.JsonResponse jsonResponse) {
    netApi.post(context, url, params, jsonResponse);
  }
  public void put(Context context,String url, Map<String, String> params, NetApi.JsonResponse jsonResponse) {
    netApi.put( context, url, params, jsonResponse);
  }
  public void delete(Context context,String url, Map<String, String> params, NetApi.JsonResponse jsonResponse) {
    netApi.delete( context, url, params, jsonResponse);
  }

}
