package com.example.bkzhou.base;

import android.app.Activity;

import com.example.bkzhou.utils.Log;

import java.util.Iterator;
import java.util.Stack;

/**
 * 用来管理所有的activity
 * Created by bkzhou on 16-5-7.
 */
public class ActivityManager {
  private static volatile  ActivityManager instance = null;
  private Stack<Activity> mStack = null;
  private ActivityManager(){
    mStack = new Stack<>();
  }
  public static ActivityManager getInstance(){
    if (instance == null){
      synchronized (ActivityManager.class){
        if (instance == null)
          instance = new ActivityManager();
      }
    }
    return instance;
  }
  /**
   * 获取栈的信息
   */
  public String getStackInfo() {
    StringBuilder sb = new StringBuilder();
    for (Activity temp : mStack){
      if (temp != null)
        sb.append(temp.toString()).append("\n");
    }
    return sb.toString();
  }
  /**
   * 将activity加入到栈中
   * @param activity 需要加入到栈中的activity
   */
  public void addActivity(Activity activity){
    mStack.push(activity);
  }
  /**
   * 删除栈中activity
   */
  public void removeActivity(Activity activity){
    mStack.remove(activity);
  }

  /**
   * @return 栈顶的activity
   */
  public Activity getActivity(){
    if (!mStack.isEmpty())
      return mStack.peek();
    Log.i("ActivityManager", "Activity 栈为空！！！");
    return null;
  }
  /**
   * 关闭并删除掉最上面一个的activity
   */
  public void finishActivity(){
    if (!mStack.isEmpty()) {
      Activity temp = mStack.pop();
      if (temp != null)
        temp.finish();
      return;
    }
    Log.e("ActivityManager", "Activity 栈为空！！！");
  }
  /***
   * 关闭并删除指定 activity
   */
  public void finishActivity(Activity activity){
    if (mStack.isEmpty()) {
      Log.e("ActivityManager", "Activity 栈为空！！！");
      return ;
    }
    try {
      mStack.remove(activity);
    }catch (Exception e){
      Log.e("ActivityManager", e.getMessage());
    }finally {
      if (activity != null)
        activity.finish();
    }
  }
  /**
   * 删除并关闭栈中该class对应的所有的该activity
   */
  public void finishAllActivity(Class<?> clazz){
    Iterator<Activity> iterator = mStack.iterator();
    while (iterator.hasNext()){
      Activity activity = iterator.next();
      if (activity!=null && activity.getClass().equals(clazz)) {
        iterator.remove();
        activity.finish();
      }
    }
  }
  /**
   * 删除并关闭栈中该class对应的第一个该activity,从栈顶开始
   */
  public void finishLastActivity(Class<?> clazz){
    Activity activity = null;
    Iterator<Activity> iterator = mStack.iterator();
    while (iterator.hasNext()){
      Activity temp = iterator.next();
      if (temp!=null && temp.getClass().equals(clazz))
        activity = temp;
    }
    if (activity != null)
      finishActivity(activity);
  }
  /**
   * 删除栈上该activity之上的所有activity
   */
  public void finishAfterActivity(Activity activity){
    if (activity!=null && mStack.search(activity) == -1){
      Log.e("ActivityManager","在栈中找不到该activity");
      return;
    }
    while (mStack.peek() != null){
      Activity temp = mStack.pop();
      if (temp!=null && temp.equals(activity)){
        mStack.push(temp);
        break;
      }
      if (temp!=null)
        temp.finish();
    }
  }
}
