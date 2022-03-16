package com.mamits.zini24vendor.ui.utils.listeners;


import com.mamits.zini24vendor.data.model.ErrorObject;

public interface RetrofitListener {
    void onResponseSuccess(String responseBodyString, int apiFlag);

    void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag);
}
