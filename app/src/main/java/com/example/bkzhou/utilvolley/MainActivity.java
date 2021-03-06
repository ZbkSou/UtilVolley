package com.example.bkzhou.utilvolley;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bkzhou.base.BaseApplication;
import com.example.bkzhou.data.IModelResponse;
import com.example.bkzhou.data.WeatherInfoModel;
import com.example.bkzhou.model.WeatherInfo;
import com.example.bkzhou.netapi.NetApi;
import com.example.bkzhou.network.BaseNetApi;
import com.example.bkzhou.network.NetError;
import com.example.bkzhou.utils.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener{
  private Button stringVolleyBut;
  private Button jsonObjectVolleyBut;
  private Button jsonArrayVolleyBut;
  private Button imageVolleyBut;
  private NetworkImageView networkImageView;
  private TextView Testtext;
  private static  final  String TAG = "MainActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
  }
  private void  init(){
    stringVolleyBut = (Button) this.findViewById(R.id.string_volley_test);
    stringVolleyBut.setOnClickListener(this);
    jsonObjectVolleyBut = (Button) this.findViewById(R.id.jsonoarray_volley_test);
    jsonObjectVolleyBut.setOnClickListener(this);
    jsonArrayVolleyBut = (Button) this.findViewById(R.id.jsonobject_volley_test);
    jsonArrayVolleyBut .setOnClickListener(this);
    imageVolleyBut = (Button) this.findViewById(R.id.image_volley_test);
    imageVolleyBut.setOnClickListener(this);
    networkImageView = (NetworkImageView) findViewById(R.id.image);

    Testtext = (TextView) this.findViewById(R.id.test_text);

  }


  @Override
  public void onClick(View view) {

    switch (view.getId()){
       case R.id.string_volley_test:
         NetApi.getInstance().get(this, "http://www.weather.com.cn/data/sk/101010100.html", new HashMap<String, String>(), new NetApi.JsonResponse() {
           @Override
           public void onSuccess(JSONObject response, JSONArray responseArray) {
             Log.d(TAG,response.toString());

           }

           @Override
           public void onError(NetError error) {

           }
         });

        break;
      case R.id.jsonoarray_volley_test:
        break;
      case R.id.jsonobject_volley_test:
        WeatherInfoModel weatherInfoModel= new WeatherInfoModel(this);
        weatherInfoModel.getWeather(new IModelResponse<WeatherInfo>() {
          @Override
          public void onSuccess(WeatherInfo model, ArrayList<WeatherInfo> list) {
            Log.d(TAG,"城市"+model.getCity()+"雨"+model.getRain());
          }
          @Override
          public void onError(String msg) {
            Log.d(TAG,msg);
          }
        });
        break;
      case R.id.image_volley_test:
//        提供NetworkImageView获取图片的例子

        networkImageView.setImageUrl("https://sf-sponsor.b0.upaiyun.com/f4c70bef327dad72131cfa6f23b54a56.jpg", BaseApplication.getVolleyImageLoader().getmImageLoader());
//        向普通imageview提供图片
//        BaseApplication.getVolleyImageLoader().loadImage("https://sf-sponsor.b0.upaiyun.com/f4c70bef327dad72131cfa6f23b54a56.jpg", ImageView);

        break;
    }
  }
}

