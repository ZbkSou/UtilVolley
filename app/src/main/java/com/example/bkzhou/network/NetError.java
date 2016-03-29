package com.example.bkzhou.network;

import com.android.volley.VolleyError;

/**
 * VolleyError 2 Exception
 * Created by bkzhou on 16-3-25.
 */
public class NetError extends Exception {
  public int errorCode;
  public String errorMessage;

  @Override
  public String getMessage() {
    return errorMessage;
  }

  /**
   * 将volley的错误信息转换成通用的信息
   */
  public void transferVolleyError(VolleyError error){
    if (error.networkResponse != null)
      this.errorCode = error.networkResponse.statusCode;
    this.errorMessage = error.toString();
  }
}
