package com.mamits.zini24vendor.ui.fragment.dashboard.drawer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.gson.JsonObject;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.databinding.FragmentChangePasswordBinding;
import com.mamits.zini24vendor.ui.activity.DashboardActivity;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.ChangePasswordNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.ChangePasswordViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;


public class ChangePasswordFragment extends BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>
        implements ChangePasswordNavigator, View.OnClickListener {

    private String TAG = "ChangePasswordFragment";
    private FragmentChangePasswordBinding binding;

    @Inject
    ChangePasswordViewModel mViewModel;
    private Context mContext;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String oldPass = binding.etOldPass.getText().toString();
                String newPass = binding.etNewPass.getText().toString();
                String cnfPass = binding.etCnfPass.getText().toString();

                if (oldPass.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter Old password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPass.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter New password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cnfPass.trim().length() == 0) {
                    Toast.makeText(mContext, "Please Confirm password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPass.equals(cnfPass)) {
                    Toast.makeText(mContext, "Password did not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("oldpassword", oldPass);
                    jsonObject.put("newpassword", newPass);

                    changePassword(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void changePassword(JSONObject couponObject) {
        mViewModel.changePassword((Activity) mContext, couponObject);
    }


    @Override
    public ChangePasswordViewModel getMyViewModel() {
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
            binding.btnSubmit.setOnClickListener(this);
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.changePassView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessPasswordChanged(JsonObject jsonObject) {
        if (jsonObject != null) {

            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(((DashboardActivity) mContext).findViewById(R.id.nav_host_fragment)).navigateUp();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}