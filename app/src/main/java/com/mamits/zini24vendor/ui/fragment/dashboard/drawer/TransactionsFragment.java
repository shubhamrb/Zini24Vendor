package com.mamits.zini24vendor.ui.fragment.dashboard.drawer;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.payments.TransactionsDataModel;
import com.mamits.zini24vendor.databinding.FragmentTransactionsBinding;
import com.mamits.zini24vendor.ui.adapter.TransactionsAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.TransactionsNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.TransactionsViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TransactionsFragment extends BaseFragment<FragmentTransactionsBinding, TransactionsViewModel> implements TransactionsNavigator, View.OnClickListener {
    private String TAG = "TransactionsFragment";
    private FragmentTransactionsBinding binding;

    @Inject
    TransactionsViewModel mViewModel;
    private Context mContext;
    private List<TransactionsDataModel> paymentsList;
    private Gson mGson;
    private TransactionsAdapter transactionsAdapter;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_filter:
                openFilterDialog(v);
                break;
        }
    }

    @Override
    public TransactionsViewModel getMyViewModel() {
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
            binding.txtFilter.setOnClickListener(this);
            binding.progressBar.setVisibility(View.VISIBLE);
            setUpPayments();
            binding.swipe.setOnRefreshListener(() -> loadPayments("all", null));
        }
    }

    private void setUpPayments() {
        paymentsList = new ArrayList<>();
        mGson = new Gson();
        binding.recyclerPayments.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionsAdapter = new TransactionsAdapter(getActivity(), mViewModel);
        binding.recyclerPayments.setAdapter(transactionsAdapter);

        loadPayments("all", null);
    }

    private void loadPayments(String pType, PopupWindow popupWindow) {
        try {
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (pType) {
            case "all":
                binding.txtH1.setText("All Payments");
                binding.txtFilter.setText("All");
                break;
            case "vendor":
                binding.txtH1.setText("Pay To Vendor");
                binding.txtFilter.setText("Pay To Vendor");
                break;
            case "offer":
                binding.txtH1.setText("Offer");
                binding.txtFilter.setText("Offer");
                break;
            case "extra":
                binding.txtH1.setText("Extra Amount");
                binding.txtFilter.setText("Extra Amount");
                break;

        }

        mViewModel.fetchTransactions((Activity) mContext, pType);
    }

    private void openFilterDialog(View v) {
        LayoutInflater layoutInflater
                = (LayoutInflater) mContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.payments_filter, null);

        RelativeLayout rl_all = popupView.findViewById(R.id.rl_all);
        RelativeLayout rl_vendor = popupView.findViewById(R.id.rl_vendor);
        RelativeLayout rl_offer = popupView.findViewById(R.id.rl_offer);
        RelativeLayout rl_extra = popupView.findViewById(R.id.rl_extra);


        PopupWindow popupWindow = new PopupWindow(
                popupView,
                mContext.getResources().getDimensionPixelOffset(R.dimen._200sdp),
                ViewGroup.LayoutParams.WRAP_CONTENT);

        rl_all.setOnClickListener(v1 -> loadPayments("all", popupWindow));
        rl_vendor.setOnClickListener(v1 -> loadPayments("vendor", popupWindow));
        rl_offer.setOnClickListener(v1 -> loadPayments("offer", popupWindow));
        rl_extra.setOnClickListener(v1 -> loadPayments("extra", popupWindow));

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAsDropDown(v, 0, 0);
    }

    @Override
    public int getBindingVariable() {
        return BR.paymentsView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transactions;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
    }

    @Override
    public void throwable(Throwable throwable) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessTransactions(JsonObject jsonObject) {
        binding.progressBar.setVisibility(View.GONE);
        /*swipe off*/
        binding.swipe.setRefreshing(false);
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type orderDataList = new TypeToken<List<TransactionsDataModel>>() {
                }.getType();
                paymentsList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), orderDataList);

                if (paymentsList != null && paymentsList.size() > 0) {
                    transactionsAdapter.setList(paymentsList);
                    binding.recyclerPayments.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerPayments.setVisibility(View.GONE);
                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            }
        }
    }
}