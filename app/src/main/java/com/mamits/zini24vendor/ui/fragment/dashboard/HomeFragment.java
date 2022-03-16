package com.mamits.zini24vendor.ui.fragment.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.home.HomeDataModel;
import com.mamits.zini24vendor.data.model.home.KeysDataModel;
import com.mamits.zini24vendor.databinding.FragmentHomeBinding;
import com.mamits.zini24vendor.ui.activity.PaymentActivity;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.customviews.CustomInputEditText;
import com.mamits.zini24vendor.ui.navigator.fragment.HomeNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator, View.OnClickListener {
    private String TAG = "HomeFragment";
    private FragmentHomeBinding binding;

    @Inject
    HomeViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private BottomSheetDialog paymentDialog;
    private ArrayAdapter payMethodAdapter;
    private HomeDataModel model;
    private KeysDataModel keyModel;
    private String orderId;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_pay:
                if (model.getPaytoadmin() == 0) {
                    return;
                }
                openPaymentBottomDialog();
                break;
        }
    }

    @Override
    public HomeViewModel getMyViewModel() {
        return mViewModel;
    }

    @Override
    protected void initView(View view, boolean isRefresh) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);
        if (getActivity() != null) {
            mContext = getActivity();
        } else if (getBaseActivity() != null) {
            mContext = getBaseActivity();
        } else if (view.getContext() != null) {
            mContext = view.getContext();
        }
        if (isRefresh) {
            fetchData();
            binding.rlPay.setOnClickListener(this);

            binding.swipe.setOnRefreshListener(this::fetchData);
        }
    }

    private void fetchData() {
        mViewModel.fetchData((Activity) mContext);
        mViewModel.fetchPaymentKeys((Activity) mContext);
    }

    private void openPaymentBottomDialog() {
        if (paymentDialog == null) {
            paymentDialog = new BottomSheetDialog(mContext);
            paymentDialog.setContentView(R.layout.payment_bottomsheet);
            paymentDialog.setCanceledOnTouchOutside(false);

            RelativeLayout btn_pay = paymentDialog.findViewById(R.id.btn_pay);
            AppCompatSpinner spinner_method = paymentDialog.findViewById(R.id.spinner_method);
            CustomInputEditText et_amount = paymentDialog.findViewById(R.id.et_amount);
            et_amount.setText(String.format("₹ %s", model.getPaytoadmin()));

            List<String> methodList = new ArrayList<>();
            methodList.add("Cashfree");
            //methodList.add("Paytm");

            payMethodAdapter = new ArrayAdapter(mContext, R.layout.spinner_layout, methodList);
            spinner_method.setAdapter(payMethodAdapter);

            btn_pay.setOnClickListener(v -> {
                try {
                    double amount = model.getPaytoadmin();
                    if (amount == 0) {
                        Log.e(TAG, "Amount is 0.");
                        return;
                    }
                    int position = spinner_method.getSelectedItemPosition();
                    Intent payIntent = new Intent(mContext, PaymentActivity.class);
                    payIntent.putExtra("amount", String.valueOf(model.getPaytoadmin()));
                    if (position == 0) {
                        paymentDialog.dismiss();
                        /*cashfree*/

                        payIntent.putExtra("appid", keyModel.getAppid());//"936476e4b0e75a0300a64fc14639"
                    } else {
                        /*paytm*/
                        payIntent.putExtra("m_id", keyModel.getPaytm_merchant_mid());
                    }

                    startActivity(payIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            paymentDialog.setOnDismissListener(dialog -> {
                paymentDialog = null;
            });
            paymentDialog.show();
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.HomeView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        /*swipe off*/
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        /*swipe off*/
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
        /*swipe off*/
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void onSuccessHomeData(JsonObject jsonObject) {
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                model = mGson.fromJson(jsonObject.get("data").getAsJsonObject().toString(), HomeDataModel.class);
                binding.txtTotalOrder.setText(model.getTotalorder() + "");
                binding.txtOrderAccept.setText(model.getTotalaccept() + "");
                binding.txtOrderReject.setText(model.getTotalreject() + "");
                binding.txtPendingOrder.setText(model.getTotalpending() + "");
                binding.txtOrderComplete.setText(model.getTotalcomplete() + "");
                binding.txtPayNow.setText("₹ " + model.getPaytoadmin() + "");


            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onSuccessPaymentKeys(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                keyModel = mGson.fromJson(jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().toString(), KeysDataModel.class);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

}