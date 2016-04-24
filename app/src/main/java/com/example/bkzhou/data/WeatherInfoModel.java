package com.example.bkzhou.data;

import android.content.Context;

import com.example.bkzhou.model.WeatherInfo;
import com.example.bkzhou.netapi.NetApi;
import com.example.bkzhou.network.NetError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bkzhou on 16-4-23.
 */
public class WeatherInfoModel extends Model {
  private String TAG = "WeatherInfoModel";
  private String url ="http://www.weather.com.cn/data/sk/101010100.html";
  private Context context;

  public WeatherInfoModel(Context context) {
    super(context);
    this.context =context;
  }

  public void  getWeather(final IModelResponse<WeatherInfo> response){
    get(context, url, null, new NetApi.JsonResponse() {
      @Override
      public void onSuccess(JSONObject responseJson, JSONArray responseArray) {
        Gson gson = new Gson();
//        ArrayList<WeatherInfo> list = gson.fromJson(responseArray.toString(), new TypeToken<ArrayList<BrandPageComplete>>() {
//        }.getType());
        WeatherInfo weatherInfo = null;
        try {
          weatherInfo = gson.fromJson(responseJson.getString("weatherinfo"),WeatherInfo.class);
        } catch (JSONException e) {
          e.printStackTrace();
          response.onError(e.getMessage());
        }
        response.onSuccess(weatherInfo,null);
      }

      @Override
      public void onError(NetError error) {
        response.onError(error.getMessage());
      }
    });
  }



}
