package com.mamits.zini24vendor.ui.fragment.dashboard.drawer;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.login.LoginDataModel;
import com.mamits.zini24vendor.databinding.FragmentProfileBinding;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.ProfileNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.ProfileViewModel;

import javax.inject.Inject;


public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel>
        implements ProfileNavigator, View.OnClickListener {

    private String TAG = "FragmentProfileBinding";
    private FragmentProfileBinding binding;

    @Inject
    ProfileViewModel mViewModel;
    private Context mContext;
    private LoginDataModel model;

    @Override
    public void onClick(View v) {
    }

    @Override
    public ProfileViewModel getMyViewModel() {
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
        try {
            String json = mViewModel.getmDataManger().getUserData();
            model = new Gson().fromJson(json, LoginDataModel.class);

            setData();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setData() {
        binding.etShopName.setText(model.getStore().getName());
        binding.etOwnerName.setText(model.getUser().getName());
        binding.etNumber.setText(model.getUser().getPhone());
        binding.etEmail.setText(model.getUser().getEmail());
        binding.etAddress.setText(model.getStore().getAddress());
        binding.etOpenTime.setText(model.getStore().getOpeningtime());
        binding.etCloseTime.setText(model.getStore().getClosingtime());
        binding.etPayMethod.setText(model.getStore().getPayment_accept_mode());
        binding.etUpi.setText(model.getStore().getUpi_number());
        binding.etWhatsapp.setText(model.getStore().getWhatsapp_no());
        binding.etAccount.setText(model.getStore().getAccount_number());
        binding.etBank.setText(model.getStore().getBank_name());
        binding.etIfsc.setText(model.getStore().getIfsc_code());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.etDes.setText(Html.fromHtml(model.getStore().getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.etDes.setText(Html.fromHtml(model.getStore().getDescription()));
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.profileView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
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

}