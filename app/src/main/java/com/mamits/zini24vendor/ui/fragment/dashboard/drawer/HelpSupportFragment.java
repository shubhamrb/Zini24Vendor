package com.mamits.zini24vendor.ui.fragment.dashboard.drawer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.databinding.FragmentHelpSupportBinding;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.HelpSupportNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.HelpSupportViewModel;

import javax.inject.Inject;

public class HelpSupportFragment extends BaseFragment<FragmentHelpSupportBinding, HelpSupportViewModel> implements HelpSupportNavigator {
    private String TAG = "HelpSupportFragment";
    private FragmentHelpSupportBinding binding;

    @Inject
    HelpSupportViewModel mViewModel;
    private Context mContext;

    @Override
    public HelpSupportViewModel getMyViewModel() {
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
            loadHelpData();
        }
    }


    private void loadHelpData() {
        mViewModel.fetchHelp((Activity) mContext);
    }


    @Override
    public int getBindingVariable() {
        return BR.helpView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_help_support;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {

    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {

    }

    @Override
    public void throwable(Throwable throwable) {

    }

    @Override
    public void onSuccessHelp(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                JsonObject data = jsonObject.get("data").getAsJsonObject();
                String number = data.get("phone").getAsString();
                String name = data.get("name").getAsString();
                String email = data.get("Email").getAsString();

                if (number != null && number.trim().length() != 0) {
                    binding.txtName.setText(name);
                    binding.txtEmail.setText(email);
                    binding.txtNumber.setText(number);
                    binding.llForm.setVisibility(View.VISIBLE);
                } else {
                    String message = data.get("message").getAsString();
                    binding.txtLabel.setText(name);
                    binding.txtMsg.setText(message);
                    binding.llSub.setVisibility(View.VISIBLE);
                }


            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            }
        }
    }
}