package com.example.bkzhou.utilvolley;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.NetworkError;
import com.example.bkzhou.netapi.NetApi;
import com.example.bkzhou.network.BaseNetApi;
import com.example.bkzhou.network.NetError;
import com.example.bkzhou.utils.Log;

import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener{
  private Button stringVolleyBut;
  private Button jsonObjectVolleyBut;
  private Button jsonArrayVolleyBut;
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
  }


  @Override
  public void onClick(View view) {

    switch (view.getId()){
       case R.id.string_volley_test:
         NetApi.getInstance().stringRequest(this, "https://www.baidu.com", new HashMap<String, String>(),
           new BaseNetApi.OnNetCallback<String>() {

             @Override
             public void onSuccess(String result) {
               Log.d("MainActivity",result);
             }

             @Override
             public void onError(NetError error) {
               Log.d("MainActivity",error.getMessage());
             }
           });
        break;
      case R.id.jsonoarray_volley_test:
        break;
      case R.id.jsonobject_volley_test:
        break;
    }
  }
}

