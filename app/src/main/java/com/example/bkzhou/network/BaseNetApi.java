package com.example.bkzhou.network;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by bkzhou on 16-3-18.
 */
public abstract class BaseNetApi {
  private RequestQueue requestQueue;

  private RequestQueue getRequestQueue(Context context) {
    if (requestQueue == null) {
      requestQueue = Volley.newRequestQueue(context );
    }
    return requestQueue;
  }

  public interface OnNetCallback<T> {
    void onSuccess(T result);

    void onError(NetworkError error);
  }

  private boolean checkIfExtendsRequest(Class clazz) {
    while (clazz.getSuperclass() != null) {
      clazz = clazz.getSuperclass();
      if (clazz == Request.class) {
        return true;
      }
    }
    return false;
  }

  protected <T> void makeRequest(final Context context, Class<?> clazz, String url, final Map<String, String> params, final OnNetCallback<T> callback) {
    Request request = null;
    Response.ErrorListener errorListener = null;
    Response.Listener listener = null;
    if (callback != null) {
      errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
          if (context instanceof Activity && (((Activity) (context)).isFinishing())) {
            Log.i("BaseNetApi", "activity finish, not callback");
            return;
          }
          NetworkError networkError = new NetworkError();
//          networkError.transferVolleyError(volleyError);
          callback.onError(networkError);

        }
      };
      listener = new Response.Listener<T>() {
        @Override
        public void onResponse(T response) {
          if (context instanceof Activity && (((Activity) (context)).isFinishing())) {
            Log.i("BaseNetApi", "activity finish, not callback");
            return;
          }
          callback.onSuccess(response);
        }
      };

    }
    if (clazz == ImageRequest.class) {
      throw new IllegalArgumentException("please use imageloader");

    } else if (checkIfExtendsRequest(clazz)) {
      try {
        Constructor constructor = clazz.getConstructor(int.class, String.class, Response.Listener.class, Response.ErrorListener.class, Map.class);
        int method = Request.Method.GET;
        if (params != null) {
          method = Request.Method.POST;
        }
        request = (Request) constructor.newInstance(method, url, listener, errorListener, params);

      } catch (Exception e) {
        Log.d("BaseNetApi", e.toString());
        return;
      }
    } else {
      throw new IllegalArgumentException("unsupported type");
    }
    getRequestQueue(context).add(request);
  }

  /**
   * 对StringRequest封装
   */
  private static class StringRequsetImpl extends StringRequest {
    private Map<String, String> params;
    public StringRequsetImpl(int method,String url,Response.Listener<String> listener,Response.ErrorListener errorListener, Map<String,String> params){
      super(method,url,listener,errorListener);
      this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
      return params;
    }
  }

  /**
   * 对JsonObjectReques封装
   */
  private static class JsonObjectRequestImpl extends JsonObjectRequest {
    private Map<String ,String> params;
    public JsonObjectRequestImpl(int method,String url ,Response.Listener<JSONObject>listener,Response.ErrorListener errorListener,Map<String ,String> params){
      super(method,url,null,listener,errorListener);
      this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
      return params;
    }
  }
  private static class JsonArrayRequestImpl extends JsonRequest<JSONArray> {
    private Map<String ,String> params;


    public JsonArrayRequestImpl(int method,String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
      super(method, url, (String)null, listener, errorListener);
    }

    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
      try {
        String je = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        return Response.success(new JSONArray(je), HttpHeaderParser.parseCacheHeaders(response));
      } catch (UnsupportedEncodingException var3) {
        return Response.error(new ParseError(var3));
      } catch (JSONException var4) {
        return Response.error(new ParseError(var4));
      }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
      return params;
    }
  }
  /**
   * string 请求
   * @param context 相关上下文
   * @param url　网络访问url
   * @param params 网络请求参数
   * @param callback　网络请求回调
   */
  public void stringRequest(Context context, String url, Map<String, String> params, OnNetCallback<String> callback){
    makeRequest(context, StringRequsetImpl.class, url, params, callback);
  }

  /**
   * jsonObject 请求
   * @param context 相关上下文
   * @param url　网络访问url
   * @param params 网络请求参数
   * @param callback　网络请求回调
   */
  public void jsonObjectRequest(Context context, String url, Map<String, String> params, OnNetCallback<JSONObject> callback){
    makeRequest(context, JsonObjectRequestImpl.class, url, params, callback);
  }

  /**
   * jsonArray 请求
   * @param context 相关上下文
   * @param url　网络访问url
   * @param params 网络请求参数
   * @param callback　网络请求回调
   */
  public void jsonArrayRequest(Context context, String url, Map<String, String> params, OnNetCallback<JSONArray> callback){
    makeRequest(context, JsonArrayRequestImpl.class, url, params, callback);
  }

}


