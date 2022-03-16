package com.mamits.zini24vendor.ui.navigator.activity;

import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.navigator.base.BaseNavigator;

public interface DashboardActivityNavigator extends BaseNavigator {
    void showLoader();

    void hideLoader();


    void checkValidation(int type, String message);

    void throwable(Throwable it);

    void checkInternetConnection(String message);

    void onSuccessStoreStatus(JsonObject jsonObject);

    void onSuccessFetchStoreStatus(JsonObject jsonObject);
}
