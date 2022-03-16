package com.mamits.zini24vendor.ui.navigator.fragment;


import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.navigator.base.BaseNavigator;

public interface OrderDetailNavigator extends BaseNavigator {


    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

    void onSuccessOrderStatusUpdated(JsonObject jsonObject, String status);

    void onSuccessPaymentStatus(JsonObject jsonObject, String des, String pType);

    void onSuccessOrderCompleted(JsonObject jsonObject);
}
