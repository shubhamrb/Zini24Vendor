package com.mamits.zini24vendor.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.databinding.ActivityRegisterBinding;
import com.mamits.zini24vendor.ui.base.BaseActivity;
import com.mamits.zini24vendor.ui.customviews.CustomInputEditText;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.ui.navigator.activity.RegisterActivityNavigator;
import com.mamits.zini24vendor.ui.utils.commonClasses.CommonUtils;
import com.mamits.zini24vendor.ui.utils.constants.AppConstant;
import com.mamits.zini24vendor.viewmodel.activity.RegisterActivityViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterActivityViewModel>
        implements RegisterActivityNavigator, View.OnClickListener {

    String TAG = "RegisterActivity";
    @Inject
    RegisterActivityViewModel mViewModel;
    ActivityRegisterBinding binding;
    private String name, email, mobile, pass;
    private CustomTextView txt_resend;
    private BottomSheetDialog signOtpDialog;
    private int sent_otp;

    @Override
    public int getBindingVariable() {
        return BR.registerView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding = getViewDataBinding();
        mViewModel = getMyViewModel();
        mViewModel.setNavigator(this);

        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        binding.txtTerms.setOnClickListener(this);
    }

    @Override
    protected RegisterActivityViewModel getMyViewModel() {
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
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                onBackPressed();
                break;
            case R.id.txt_terms:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://zini24.com/vendor-term-condition"));
                startActivity(browserIntent);
                break;
            case R.id.btn_register:
                name = binding.etName.getText().toString();
                email = binding.etEmail.getText().toString();
                mobile = binding.etNumber.getText().toString();
                pass = binding.etPass.getText().toString();
                String cnf_pass = binding.etCnfPass.getText().toString();

                if (name.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CommonUtils.isEmailValid(email)) {
                    Toast.makeText(this, "Please enter valid email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobile.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your mobile no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.trim().length() == 0) {
                    Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cnf_pass.trim().length() == 0) {
                    Toast.makeText(this, "Please confirm password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(cnf_pass)) {
                    Toast.makeText(this, "Password did not match.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!binding.termsCheckbox.isChecked()) {
                    Toast.makeText(this, "Please accept the terms & conditions.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendOtp(mobile, false);
                break;
        }
    }

    private void sendOtp(String mobile, boolean isResend) {
        mViewModel.sendOtp(this, mobile, false);
    }

    @Override
    public void onSuccessSendOtp(JsonObject jsonObject, boolean isResend) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String number = jsonObject.get("data").getAsJsonObject().get("phone_number").getAsString();
                sent_otp = jsonObject.get("data").getAsJsonObject().get("otp").getAsInt();
                Toast.makeText(this, "OTP sent to your registered number.", Toast.LENGTH_SHORT).show();
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
        if (signOtpDialog == null) {
            signOtpDialog = new BottomSheetDialog(this);
            signOtpDialog.setContentView(R.layout.forgot_otp_bottomsheet);
            signOtpDialog.setCanceledOnTouchOutside(false);

            CustomTextView label_otp_sent = signOtpDialog.findViewById(R.id.label_otp_sent);
            CustomTextView header = signOtpDialog.findViewById(R.id.h2);
            header.setText("Verify\nYour Pin.");
            RelativeLayout btn_verify = signOtpDialog.findViewById(R.id.btn_verify);
            CustomInputEditText et_otp = signOtpDialog.findViewById(R.id.et_otp);
            txt_resend = signOtpDialog.findViewById(R.id.txt_resend);
            /*start timer*/
            startTimer();

            if (label_otp_sent != null)
                label_otp_sent.setText(String.format("Enter OTP Sent on +91%s", number));

            if (btn_verify != null) {
                btn_verify.setOnClickListener(v -> {
                    String otp = et_otp.getText().toString();
                    if (otp.trim().length() == 0) {
                        Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!otp.equals(String.valueOf(sent_otp))) {
                        Toast.makeText(this, "OTP is incorrect.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    verifyAndDoRegistration();
                });
            }

            if (txt_resend != null) {
                txt_resend.setOnClickListener(v -> {
                    if (txt_resend.getText().toString().equalsIgnoreCase("resend")) {
                        sendOtp(number, true);
                    }
                });
            }
            signOtpDialog.setOnDismissListener(dialog -> {
                signOtpDialog = null;
            });
            signOtpDialog.show();
        }
    }

    private void verifyAndDoRegistration() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                return;
            }
            // Get new FCM registration token
            if (task.isSuccessful()) {
                String device_id = task.getResult();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", name);
                    jsonObject.put("email", email);
                    jsonObject.put("mobile", mobile);
                    jsonObject.put("password", pass);
                    jsonObject.put("api_key", AppConstant.API_KEY);
                    jsonObject.put("device_type", AppConstant.DEVICE_TYPE);
                    jsonObject.put("device_token", device_id);

                    mViewModel.doRegistration(this, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onSuccessRegistration(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                if (signOtpDialog != null && signOtpDialog.isShowing()) {
                    signOtpDialog.dismiss();
                }
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            }
        }
    }
}