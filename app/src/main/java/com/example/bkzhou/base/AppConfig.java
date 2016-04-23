package com.example.bkzhou.base;

/**
 * Created by bkzhou on 16-4-1.
 */
public class AppConfig {

  private static boolean isProduct = false;

  public static void setIsProduct(boolean isProduct) {
    AppConfig.isProduct = isProduct;
  }

  /**
   * Product Config
   * Product - Development
   */
  public static String getULifImageServer() {
    return getValue("http://t0.ihaveu.net", "http://dulife.ihaveu.com");
  }

  public static String getApiHostImage() {
    return getValue("http://i0.ihaveu.com/", "http://dww.ihaveu.com");
  }

  public static String getApiHost() {
    return getValue("http://www.ihaveu.com/", "http://dww.ihaveu.com/");
  }

  public static String getTouchHost() {
    return getValue("http://touch.ihaveu.com/", "http://dtouch.ihaveu.com/");
  }

  public static String getMHost() {
    return getValue("http://m.ihaveu.com/", "http://dmm.ihaveu.com/");
  }

  public static String getTHost() {
    return getValue("http://t.ihaveu.com/", "http://t.ihaveu.com/");
  }

  public static String getDataHost() {
    return getValue("http://data.ihaveu.com/", "http://dev.data.ihaveu.com/");
  }

  public static String getMQTTHost() {
    return getValue("tcp://nm.ihaveu.com@1883", "tcp://dmm.ihaveu.com@1883");
  }

  public static String getCDN() {
    return getValue("", "dev.");
  }

  //ghq 06.13
  public static String getEPMHost() {
    return getValue("http://epm.ihaveu.net", "http://devepm.ihaveu.net");
  }

  /**
   * 获取消息后台地址
   *
   * @return
   */
  public static String getMessageHost() {
    return getValue("http://message.ihaveu.net", "http://dmessage.ihaveu.net");
  }

  /**
   * 后台通知对应的appid
   * @return
   */
  public static String getAppId() {
    return getValue("2", "4");
  }

  /**
   * Common Config *
   */
  //Response Type
  public final static String SUMMARY = "summary";
  public final static String SAMPLE = "sample";
  public final static String COMPLETE = "complete";


  private static String getValue(String proEnv, String devEnv) {
    return isProduct ? proEnv : devEnv;
  }
}
