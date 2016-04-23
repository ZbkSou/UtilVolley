package com.example.bkzhou.network;

import java.util.ArrayList;

/**
 * 多请求管理
 * Created by bkzhou on 16-4-1.
 */
public class MultiRequestManager {
  private final String TAG = "MultiRequestManager";
  private ArrayList<MultiRequest> mRequests;
  private boolean mIsReady = false;
  private IOnReady mOnReadyHandler;

  public MultiRequestManager() {
    mRequests = new ArrayList<MultiRequest>();
  }
  public MultiRequestManager(IOnReady handler) {
    mRequests = new ArrayList<MultiRequest>();
    mOnReadyHandler = handler;
  }

  /**
   * 添加请求
   * @param request
   */
  public void attach (final MultiRequest request){
    request.setOnReadyHandler(new IOnReady() {
      @Override
      public void onReady(int id, MultiRequest.RequestState state) {
      if(mOnReadyHandler != null){
          MultiRequest.RequestState s  = MultiRequest.RequestState.DEFAULT;
          for (MultiRequest r : mRequests){
            //如果有Default 没有Ready
            if (r.getState().equals(MultiRequest.RequestState.DEFAULT)) {
              return;
              // 如果其中一个Error 返回Error
            } else if (r.getState().equals(MultiRequest.RequestState.ERROR)) {
              mIsReady = true;
              mOnReadyHandler.onReady(id, MultiRequest.RequestState.ERROR);
              return;
            }
          }
        // 否则认为所有都成功返回
        mIsReady = true;
        mOnReadyHandler.onReady(id, MultiRequest.RequestState.SUCCESS);
        }
      }
    });
    mRequests.add(request);

  }

  /**
   * 移除请求
   * @param request
   * @return
   */
  public boolean dettach(final MultiRequest request) {
    return mRequests.remove(request);
  }

  /**
   * 清除请求
   */
  public void clear() {
    mRequests.clear();
  }
  public int size() {
    return mRequests.size();
  }

  public boolean isReady() {
    return mIsReady;
  }
  public interface IOnReady {
    public void onReady(int id, MultiRequest.RequestState state);
  }
}
