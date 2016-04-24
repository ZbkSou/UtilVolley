package com.example.bkzhou.netapi;


import com.example.bkzhou.data.IModelResponse;

import java.util.ArrayList;

/**
 * Created by fuqiang on 15/3/20.
 */
public class MultiRequestCommon extends MultiRequest implements IModelResponse {

    private IModelResponse mResponse;

    public MultiRequestCommon(IModelResponse response) {
        mResponse = response;
    }

    @Override
    public void onSuccess(Object model, ArrayList list) {
        if (mResponse != null)
            mResponse.onSuccess(model, list);
        onReady(MultiRequest.RequestState.SUCCESS);
    }

    @Override
    public void onError(String msg) {
        if (mResponse != null)
            mResponse.onError(msg);
        onReady(RequestState.ERROR);
    }
}
