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


  /**
   * Json 返回值
   *
   *
   */
  public interface JsonResponse {
    public void onSuccess(JSONObject response, JSONArray responseArray);

    public void onError(VolleyError error);
  }

  public void get(Context context,String url,Map<String,String> params,  BaseNetApi.OnNetCallback callback){

    netApi.stringRequest(Request.Method.GET, context, url, params, callback);
  }
  public void post(Context context,String url, Map<String, String> params, BaseNetApi.OnNetCallback callback) {
    netApi.stringRequest(Request.Method.POST, context, url, params, callback);
  }
  public void put(Context context,String url, Map<String, String> params, BaseNetApi.OnNetCallback callback) {
    netApi.stringRequest(Request.Method.PUT, context, url, params, callback);
  }
  public void delete(Context context,String url, Map<String, String> params, BaseNetApi.OnNetCallback callback) {
    netApi.stringRequest(Request.Method.DELETE, context, url, params, callback);
  }

}
