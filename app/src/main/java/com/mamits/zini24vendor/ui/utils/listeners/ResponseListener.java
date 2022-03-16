package com.mamits.zini24vendor.ui.utils.listeners;


import com.google.gson.JsonObject;

public interface ResponseListener {

    void  onSuccess(JsonObject jsonObject);

    void onFailed(Throwable t);
}
