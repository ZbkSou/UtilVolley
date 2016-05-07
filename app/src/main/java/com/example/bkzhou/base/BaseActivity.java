package com.example.bkzhou.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 所有的activity都继承此类，统一管理所有activity
 * Created by bkzhou on 16-5-7.
 */
public class BaseActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BaseApplication.setInstanceRef(this);
    ActivityManager.getInstance().addActivity(this);
  }
}
