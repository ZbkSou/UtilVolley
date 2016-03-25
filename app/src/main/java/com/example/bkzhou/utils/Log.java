package com.example.bkzhou.utils;

/**
 * Created by bkzhou on 16-3-25.
 */
public class Log {
  private static boolean isDebug = true;

  public static void setDebug(boolean isdebug){
    isDebug = isdebug;
  }
  public static boolean isDebug(){
    return isDebug;
  }

  public static void d(String tag,String message){
    if(isDebug)
      android.util.Log.d(tag,message);
  }
  public static void e(String tag,String message){
    if(isDebug)
      android.util.Log.e(tag,message);
  }

  public static void e(String tag,String message,Throwable tr){
    if(isDebug){
      android.util.Log.e(tag, message, tr);
    }
  }
  public static void v(String tag,String message){
    if(isDebug)
      android.util.Log.v(tag,message);
  }
  public static void w(String tag,String message){
    if(isDebug)
      android.util.Log.w(tag,message);
  }
  public static void i(String tag,String message){
    if(isDebug)
      android.util.Log.i(tag,message);
  }
}
