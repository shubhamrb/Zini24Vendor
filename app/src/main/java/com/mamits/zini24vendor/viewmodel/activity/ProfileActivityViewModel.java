package com.mamits.zini24vendor.viewmodel.activity;

import android.app.Activity;

import com.androidnetworking.error.ANError;
import com.google.gson.JsonObject;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.activity.ProfileActivity;
import com.mamits.zini24vendor.ui.navigator.activity.ProfileActivityNavigator;
import com.mamits.zini24vendor.ui.utils.commonClasses.NetworkUtils;
import com.mamits.zini24vendor.ui.utils.listeners.ResponseListener;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.base.BaseViewModel;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class ProfileActivityViewModel extends BaseViewModel<ProfileActivityNavigator> {


    public ProfileActivityViewModel(IDataManager mDataManager, ISchedulerProvider mSchedulerProvider) {
        super(mDataManager, mSchedulerProvider);
    }

    public void fetchStateCity(Activity mActivity) {

        if (NetworkUtils.isNetworkConnected(mActivity)) {
            getmNavigator().get().showLoader();
            getmDataManger().fetchStateCity(mActivity, getmDataManger().getAccessToken(),  new ResponseListener() {
                @Override
                public void onSuccess(JsonObject jsonObject) {
                    try {
                        getmNavigator().get().hideLoader();

                        getmNavigator().get().onSuccessStateCity(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    try {
                        getmNavigator().get().hideLoader();

                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            if (anError.getErrorBody() != null) {
                                JSONObject object = new JSONObject(anError.getErrorBody());
                                try {
                                    getmNavigator().get().checkValidation(anError.getErrorCode(), object.optString("message"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } else {
                            throwable.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            getmNavigator().get().checkInternetConnection(mActivity.getResources().getString(R.string.check_internet_connection));

        }
    }

    public void submitProfile(Activity mActivity, List<String> categoryArray, String shop_name, String owner_name, String et_email,
                              String et_number, String et_address, int selectedState, int selectedCity,
                              String tv_lat, String tv_lng, String et_zipcode, String et_open, String et_close,
                              List<String> payModeArray, File ownerFile, File shopFile, File banner, File banner1,
                              File banner2, File banner3, String et_desc, File qrFile, String et_upi, String et_whatsapp,
                              String et_account, String et_bank, String et_ifsc) {
        getmNavigator().get().showLoader();
        if (NetworkUtils.isNetworkConnected(mActivity)) {
            getmDataManger().submitProfile(mActivity, getmDataManger().getAccessToken(),categoryArray,shop_name,owner_name,et_email,
                    et_number,et_address,selectedState,selectedCity,tv_lat,tv_lng,et_zipcode,et_open,et_close,payModeArray
                    ,ownerFile,shopFile,banner,banner1,banner2,banner3,et_desc,qrFile,et_upi,et_whatsapp,et_account,et_bank,et_ifsc,  new ResponseListener() {
                @Override
                public void onSuccess(JsonObject jsonObject) {
                    try {
                        getmNavigator().get().hideLoader();
                        getmNavigator().get().onSuccessProfileUpdated(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    try {
                        getmNavigator().get().hideLoader();

                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            if (anError.getErrorBody() != null) {
                                JSONObject object = new JSONObject(anError.getErrorBody());
                                try {
                                    getmNavigator().get().checkValidation(anError.getErrorCode(), object.optString("message"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } else {
                            throwable.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            getmNavigator().get().checkInternetConnection(mActivity.getResources().getString(R.string.check_internet_connection));

        }
    }

    public void fetchCategory(Activity mActivity) {
        if (NetworkUtils.isNetworkConnected(mActivity)) {
            getmDataManger().fetchAllCategory(mActivity, getmDataManger().getAccessToken(), new ResponseListener() {
                @Override
                public void onSuccess(JsonObject jsonObject) {
                    try {
                        getmNavigator().get().onSuccessCategory(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    try {
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            if (anError.getErrorBody() != null) {
                                JSONObject object = new JSONObject(anError.getErrorBody());
                                try {
                                    getmNavigator().get().checkValidation(anError.getErrorCode(), object.optString("message"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            throwable.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            getmNavigator().get().checkInternetConnection(mActivity.getResources().getString(R.string.check_internet_connection));

        }
    }
}
