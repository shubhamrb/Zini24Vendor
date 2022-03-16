package com.mamits.zini24vendor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.login.LoginDataModel;
import com.mamits.zini24vendor.databinding.ActivityMainBinding;
import com.mamits.zini24vendor.ui.base.BaseActivity;
import com.mamits.zini24vendor.ui.customviews.CustomInputEditText;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.ui.navigator.activity.MainActivityNavigator;
import com.mamits.zini24vendor.ui.utils.constants.AppConstant;
import com.mamits.zini24vendor.viewmodel.activity.MainActivityViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel>
        implements MainActivityNavigator, View.OnClickListener {

    String TAG = "MainActivity";
    @Inject
    MainActivityViewModel mViewModel;
    ActivityMainBinding binding;
    private BottomSheetDialog changePinDialog, forgotPinDialog, loginOtpDialog;
    private Gson mGson;
    private CustomTextView txt_resend;

    @Override
    public int getBindingVariable() {
        return BR.mainView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString("type");
            mViewModel.getmDataManger().setNotificationType(type);
        }
        if (mViewModel.getmDataManger().getCurrentUserId() != -1) {
            if (mViewModel.getmDataManger().getProfileStatus() == 1) {
                startActivity(new Intent(this, DashboardActivity.class));
            }else {
                startActivity(new Intent(this, ProfileActivity.class));
            }
            finishAffinity();
        }

        binding.btnLogin.setOnClickListener(this);
        binding.btnReg.setOnClickListener(this);
        binding.btnForgot.setOnClickListener(this);
    }

    @Override
    protected MainActivityViewModel getMyViewModel() {
        return mViewModel;
    }


    @Override
    public void showLoader() {
        showLoading();
    }

    @Override
    public void hideLoader() {
        hideLoading();
    }


    @Override
    public void checkValidation(int type, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable it) {
        it.printStackTrace();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessUserLogin(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                LoginDataModel model = mGson.fromJson(jsonObject.get("data").getAsJsonObject().toString(), LoginDataModel.class);
                mViewModel.getmDataManger().setUserData(model);
                mViewModel.getmDataManger().setAccessToken(model.getToken());
                mViewModel.getmDataManger().setCurrentUserId(model.getUser().getId());
                mViewModel.getmDataManger().setUsername(model.getUser().getName());
                mViewModel.getmDataManger().settUserNumber(model.getUser().getPhone());
                mViewModel.getmDataManger().settUserEmail(model.getUser().getEmail());
                mViewModel.getmDataManger().settProfileStatus(model.getUser().getProfile_status());

                if (model.getUser().getProfile_status() == 0) {
                    startActivity(new Intent(this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(this, DashboardActivity.class));
                }
                finishAffinity();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg:
                Intent browserIntent = new Intent(this, RegisterActivity.class);
                startActivity(browserIntent);
                break;
            case R.id.btn_login:
                String number = binding.etNumber.getText().toString();
                String pin = binding.etPin.getText().toString();
                if (number.length() == 0 || pin.length() == 0) {
                    Toast.makeText(this, "Invalid id or password", Toast.LENGTH_SHORT).show();
                    return;
                }

                doLogin(number, pin);
                break;
            case R.id.btn_forgot:
                showForgotOtpBottomDialog();
                break;
        }
    }

    private void doLogin(String number, String pin) {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (token != null && token.length() != 0) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile", number);
                    jsonObject.put("pin", pin);
                    jsonObject.put("api_key", AppConstant.API_KEY);
                    jsonObject.put("device_type", AppConstant.DEVICE_TYPE);
                    jsonObject.put("device_token", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mViewModel.userLogin(this, jsonObject.toString());
            }
        });

    }

    private void showForgotOtpBottomDialog() {
        if (forgotPinDialog == null) {
            forgotPinDialog = new BottomSheetDialog(this);
            forgotPinDialog.setContentView(R.layout.send_otp_bottomsheet);
            forgotPinDialog.setCanceledOnTouchOutside(false);
            RelativeLayout btn_send_otp = forgotPinDialog.findViewById(R.id.btn_send_otp);
            CustomInputEditText et_number = forgotPinDialog.findViewById(R.id.et_number);

            if (btn_send_otp != null) {
                btn_send_otp.setOnClickListener(v -> {
                    String number = et_number.getText().toString();
                    if (number.length() < 10) {
                        Toast.makeText(this, "Invalid mobile no.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sendOtp(number, false);
                });
            }
            forgotPinDialog.setOnDismissListener(dialog -> {
                forgotPinDialog = null;
            });
            forgotPinDialog.show();
        }
    }

    private void sendOtp(String number, boolean isResend) {
        mViewModel.sendOtp(this, number, isResend);
    }

    @Override
    public void onSuccessSendOtp(JsonObject jsonObject, boolean isResend) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String number = jsonObject.get("data").getAsJsonObject().get("phone_number").getAsString();
                Toast.makeText(this, "OTP sent to your registered number.", Toast.LENGTH_SHORT).show();
                if (!isResend && forgotPinDialog != null && forgotPinDialog.isShowing()) {
                    forgotPinDialog.dismiss();
                }
                startTimer();

                showVerifyOtpBottomDialog(number);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void startTimer() {
        new CountDownTimer(59000, 1000) {

            public void onTick(long millisUntilFinished) {
                txt_resend.setText("Resend OTP in " + millisUntilFinished / 1000);
                txt_resend.setTextColor(getResources().getColor(R.color.black));
            }

            public void onFinish() {
                txt_resend.setText("Resend");
                txt_resend.setTextColor(getResources().getColor(R.color.color_orange));
            }

        }.start();
    }

    private void showVerifyOtpBottomDialog(String number) {
        if (loginOtpDialog == null) {
            loginOtpDialog = new BottomSheetDialog(this);
            loginOtpDialog.setContentView(R.layout.forgot_otp_bottomsheet);
            loginOtpDialog.setCanceledOnTouchOutside(false);

            CustomTextView label_otp_sent = loginOtpDialog.findViewById(R.id.label_otp_sent);
            RelativeLayout btn_verify = loginOtpDialog.findViewById(R.id.btn_verify);
            CustomInputEditText et_otp = loginOtpDialog.findViewById(R.id.et_otp);
            txt_resend = loginOtpDialog.findViewById(R.id.txt_resend);

            if (label_otp_sent != null)
                label_otp_sent.setText(String.format("Enter OTP Sent on +91%s", number));

            if (btn_verify != null) {
                btn_verify.setOnClickListener(v -> {
                    String otp = et_otp.getText().toString();
                    if (otp.trim().length() == 0) {
                        Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    verifyOtp(number, otp);
                });
            }

            if (txt_resend != null) {
                txt_resend.setOnClickListener(v -> {
                    if (txt_resend.getText().toString().equalsIgnoreCase("resend")) {
                        sendOtp(number, true);
                    }
                });
            }
            loginOtpDialog.setOnDismissListener(dialog -> {
                loginOtpDialog = null;
            });
            loginOtpDialog.show();
        }
    }

    private void verifyOtp(String number, String otp) {
        mViewModel.verifyOtp(this, number, otp);
    }

    @Override
    public void onSuccessVerifyOtp(JsonObject jsonObject, String number) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                if (loginOtpDialog != null && loginOtpDialog.isShowing()) {
                    loginOtpDialog.dismiss();
                }
                showChangePinBottomDialog(number);
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void showChangePinBottomDialog(String number) {
        if (changePinDialog == null) {
            changePinDialog = new BottomSheetDialog(this);
            changePinDialog.setContentView(R.layout.change_pin_bottomsheet);
            changePinDialog.setCanceledOnTouchOutside(false);

            CustomInputEditText et_new_pin = changePinDialog.findViewById(R.id.et_new_pin);
            CustomInputEditText et_cnf_pin = changePinDialog.findViewById(R.id.et_cnf_pin);
            RelativeLayout btn_change_pin = changePinDialog.findViewById(R.id.btn_change_pin);

            if (btn_change_pin != null) {
                btn_change_pin.setOnClickListener(v -> {
                    String newPin = et_new_pin.getText().toString();
                    String cnfPin = et_cnf_pin.getText().toString();

                    if (newPin.trim().length() < 6) {
                        Toast.makeText(this, "Invalid pin.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (cnfPin.trim().length() < 6) {
                        Toast.makeText(this, "Please confirm pin.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!newPin.equals(cnfPin)) {
                        Toast.makeText(this, "Pin didn't match.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    updatePin(number, newPin);
                });
            }

            changePinDialog.setOnDismissListener(dialog -> {
                changePinDialog = null;
            });
            changePinDialog.show();
        }
    }

    private void updatePin(String number, String newPin) {
        mViewModel.updatePin(this, number, newPin);
    }

    @Override
    public void onSuccessPinUpdated(JsonObject jsonObject, String number) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                if (changePinDialog != null && changePinDialog.isShowing()) {
                    changePinDialog.dismiss();
                }
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}