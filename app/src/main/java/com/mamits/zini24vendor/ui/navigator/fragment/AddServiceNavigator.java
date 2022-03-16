package com.mamits.zini24vendor.ui.navigator.fragment;


import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.navigator.base.BaseNavigator;

public interface AddServiceNavigator extends BaseNavigator {


    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

    void onSuccessServiceAdded(JsonObject jsonObject);

    void onSuccessCatSubCat(JsonObject jsonObject);
    void onSuccessProducts(JsonObject jsonObject);
}
