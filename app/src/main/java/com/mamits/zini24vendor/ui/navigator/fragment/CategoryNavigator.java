
package com.mamits.zini24vendor.ui.navigator.fragment;


import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.navigator.base.BaseNavigator;

public interface CategoryNavigator extends BaseNavigator {

    void showProgressBars();

    void checkInternetConnection(String message);

    void hideProgressBars();

    void checkValidation(int errorCode, String message);

    void throwable(Throwable throwable);

    void onSuccessCategory(JsonObject jsonObject);

    void onSuccessUpdateCategory(JsonObject jsonObject);
}
