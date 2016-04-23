package com.example.bkzhou.data;

import java.util.ArrayList;

/**
 * Model数据返回接口
 *
 * Created by bkzhou on 16-4-1.
 */
public interface IModelResponse<T> {
  public void onSuccess(T model, ArrayList<T> list);
  public void onError(String msg);
}
