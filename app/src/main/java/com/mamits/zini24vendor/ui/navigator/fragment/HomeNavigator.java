package com.mamits.zini24vendor.ui.navigator.fragment;


import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.navigator.base.BaseNavigator;

public interface HomeNavigator extends BaseNavigator {

    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

    void onSuccessHomeData(JsonObject jsonObject);

    void onSuccessPaymentKeys(JsonObject jsonObject);
}
