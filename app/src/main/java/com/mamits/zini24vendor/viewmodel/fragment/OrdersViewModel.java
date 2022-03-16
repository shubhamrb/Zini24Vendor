package com.mamits.zini24vendor.viewmodel.fragment;


import android.app.Activity;

import com.androidnetworking.error.ANError;
import com.google.gson.JsonObject;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.datamanager.IDataManager;
import com.mamits.zini24vendor.ui.navigator.fragment.OrdersNavigator;
import com.mamits.zini24vendor.ui.utils.commonClasses.NetworkUtils;
import com.mamits.zini24vendor.ui.utils.listeners.ResponseListener;
import com.mamits.zini24vendor.ui.utils.rx.ISchedulerProvider;
import com.mamits.zini24vendor.viewmodel.base.BaseViewModel;

import org.json.JSONObject;

public class OrdersViewModel extends BaseViewModel<OrdersNavigator> {

    public OrdersViewModel(IDataManager dataManager, ISchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchOrders(Activity mActivity, int status) {

        if (NetworkUtils.isNetworkConnected(mActivity)) {
            getmDataManger().fetchOrders(mActivity, getmDataManger().getAccessToken(), status, new ResponseListener() {
                @Override
                public void onSuccess(JsonObject jsonObject) {
                    try {
                        getmNavigator().get().hideProgressBars();

                        getmNavigator().get().onSuccessOrders(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Throwable throwable) {
                    try {
                        getmNavigator().get().hideProgressBars();

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
