package com.mamits.zini24vendor.ui.navigator.activity;

import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.navigator.base.BaseNavigator;

public interface ProfileActivityNavigator extends BaseNavigator {
    void showLoader();

    void hideLoader();


    void checkValidation(int type, String message);

    void throwable(Throwable it);

    void checkInternetConnection(String message);

    void onSuccessStateCity(JsonObject jsonObject);

    void onSuccessCategory(JsonObject jsonObject);

    void onSuccessProfileUpdated(JsonObject jsonObject);
}
