package com.mamits.zini24vendor.ui.navigator.activity;

import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.navigator.base.BaseNavigator;

public interface MainActivityNavigator extends BaseNavigator {
    void showLoader();

    void hideLoader();


    void checkValidation(int type, String message);

    void throwable(Throwable it);

    void checkInternetConnection(String message);

    void onSuccessUserLogin(JsonObject jsonObject);

    void onSuccessSendOtp(JsonObject jsonObject, boolean isResend);

    void onSuccessVerifyOtp(JsonObject jsonObject, String number);

    void onSuccessPinUpdated(JsonObject jsonObject, String number);
}
