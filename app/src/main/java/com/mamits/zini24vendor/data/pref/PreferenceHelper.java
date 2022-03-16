package com.mamits.zini24vendor.data.pref;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mamits.zini24vendor.data.model.login.LoginDataModel;
import com.mamits.zini24vendor.ui.utils.constants.AppConstant;

import javax.inject.Inject;


public class PreferenceHelper implements IPreferenceHelper {

    private SharedPreferences mSharedPreferences;

    @Inject
    public PreferenceHelper(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    @Override
    public void setAccessToken(String accessToken) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getUserData() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_USER_DATA, null);
    }

    @Override
    public void setUserData(LoginDataModel loginDataModel) {
        Gson gson = new Gson();
        String json = gson.toJson(loginDataModel);
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_USER_DATA, json).apply();
    }

    @Override
    public String getAccessToken() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setCurrentUserId(int userId) {
        mSharedPreferences.edit().putInt(AppConstant.PREF_KEY_USER_ID, userId).apply();
    }

    @Override
    public int getCurrentUserId() {
        return mSharedPreferences.getInt(AppConstant.PREF_KEY_USER_ID, -1);
    }

    @Override
    public String getUsername() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_USER_NAME, null);
    }

    @Override
    public void setUsername(String username) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_USER_NAME, username).apply();
    }

    @Override
    public String getUserNumber() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_USER_NUMBER, null);
    }

    @Override
    public void settUserNumber(String number) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_USER_NUMBER, number).apply();
    }

    @Override
    public String getUserEmail() {
        return mSharedPreferences.getString(AppConstant.PREF_KEY_USER_EMAIL, null);
    }

    @Override
    public void settUserEmail(String email) {
        mSharedPreferences.edit().putString(AppConstant.PREF_KEY_USER_EMAIL, email).apply();
    }

    @Override
    public String getNotificationType() {
        return mSharedPreferences.getString(AppConstant.PREF_NOTIFICATION_TYPE, null);
    }

    @Override
    public void setNotificationType(String type) {
        mSharedPreferences.edit().putString(AppConstant.PREF_NOTIFICATION_TYPE, type).apply();
    }

    @Override
    public int getProfileStatus() {
        return mSharedPreferences.getInt(AppConstant.PREF_PROFILE_STATUS, -1);
    }

    @Override
    public void settProfileStatus(int status) {
        mSharedPreferences.edit().putInt(AppConstant.PREF_PROFILE_STATUS, status).apply();
    }

    @Override
    public boolean isPaymentOpen() {
        return mSharedPreferences.getBoolean(AppConstant.PREF_PAYMENT_ACTIVITY_OPEN, false);
    }

    @Override
    public void setPaymentOpen(boolean isOpen) {
        mSharedPreferences.edit().putBoolean(AppConstant.PREF_PAYMENT_ACTIVITY_OPEN, isOpen).apply();
    }

    @Override
    public void clearAllPreference() {
        mSharedPreferences.edit().clear().apply();
    }


}
