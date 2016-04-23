package com.example.bkzhou.model;

/**
 * Created by bkzhou on 16-4-23.
 */
public class WeatherInfo {
  /**
   * weatherinfo : {"city":"北京","cityid":"101010100","temp":"18","WD":"东南风","WS":"1级","SD":"17%","WSE":"1","time":"17:05","isRadar":"1","Radar":"JC_RADAR_AZ9010_JB","njd":"暂无实况","qy":"1011","rain":"0"}
   */
    /**
     * city : 北京
     * cityid : 101010100
     * temp : 18
     * WD : 东南风
     * WS : 1级
     * SD : 17%
     * WSE : 1
     * time : 17:05
     * isRadar : 1
     * Radar : JC_RADAR_AZ9010_JB
     * njd : 暂无实况
     * qy : 1011
     * rain : 0
     */

    private String city;
    private String cityid;
    private String temp;
    private String WD;
    private String WS;
    private String SD;
    private String rain;

    public void setCity(String city) {
      this.city = city;
    }

    public void setCityid(String cityid) {
      this.cityid = cityid;
    }

    public void setTemp(String temp) {
      this.temp = temp;
    }

    public void setWD(String WD) {
      this.WD = WD;
    }

    public void setWS(String WS) {
      this.WS = WS;
    }

    public void setSD(String SD) {
      this.SD = SD;
    }

    public void setRain(String rain) {
      this.rain = rain;
    }

    public String getCity() {
      return city;
    }

    public String getCityid() {
      return cityid;
    }

    public String getTemp() {
      return temp;
    }

    public String getWD() {
      return WD;
    }

    public String getWS() {
      return WS;
    }

    public String getSD() {
      return SD;
    }

    public String getRain() {
      return rain;
    }

  /**
   * city: "北京",
   cityid: "101010100",
   temp: "18",
   WD: "东南风",
   WS: "1级",
   SD: "17%",
   WSE: "1",
   time: "17:05",
   isRadar: "1",
   Radar: "JC_RADAR_AZ9010_JB",
   njd: "暂无实况",
   qy: "1011",
   rain: "0"
   */

}
