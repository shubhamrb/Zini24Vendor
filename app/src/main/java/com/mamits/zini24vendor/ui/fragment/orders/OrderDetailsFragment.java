package com.mamits.zini24vendor.ui.fragment.orders;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.orders.OrderDetailDataModel;
import com.mamits.zini24vendor.data.model.orders.OrdersDataModel;
import com.mamits.zini24vendor.databinding.FragmentOrderDetailsBinding;
import com.mamits.zini24vendor.ui.adapter.FormDataAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.customviews.CustomInputEditText;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.ui.navigator.fragment.OrderDetailNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.OrderDetailViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class OrderDetailsFragment extends BaseFragment<FragmentOrderDetailsBinding, OrderDetailViewModel>
        implements OrderDetailNavigator, View.OnClickListener, FormDataAdapter.downloadListener, PickiTCallbacks {

    private String TAG = "OrderDetailsFragment";
    private FragmentOrderDetailsBinding binding;

    @Inject
    OrderDetailViewModel mViewModel;
    private Context mContext;
    private OrdersDataModel model;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private BottomSheetDialog acceptOrderDialog, completeOrderDialog;
    private String tType = "", pType = "";
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private File uploadedFile = null;
    private CustomTextView et_upload;
    private PickiT pickiT;
    private ArrayList<String> payMethod;
    private ArrayAdapter<String> payMethodAdapter;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accept:
                openAcceptBottomDialog();
                break;
            case R.id.btn_reject:
                update("Reject", null, null, null);
                break;
            case R.id.btn_chat:
                goToChat(v);
                break;
            case R.id.btn_complete:
                openCompleteBottomDialog();
                break;
        }
    }

    private void openCompleteBottomDialog() {
        if (completeOrderDialog == null) {
            uploadedFile = null;
            completeOrderDialog = new BottomSheetDialog(mContext);
            completeOrderDialog.setContentView(R.layout.complete_order_bottomsheet);
            completeOrderDialog.setCanceledOnTouchOutside(false);

            CustomInputEditText et_des = completeOrderDialog.findViewById(R.id.et_des);
            et_upload = completeOrderDialog.findViewById(R.id.et_upload);
            LinearLayout btn_upload = completeOrderDialog.findViewById(R.id.ll_upload);
            RelativeLayout btn_submit = completeOrderDialog.findViewById(R.id.btn_submit);
            AppCompatSpinner spin = completeOrderDialog.findViewById(R.id.spinner);

            payMethod = new ArrayList<>();
            if (model.getPayment_type() == null || model.getPayment_type().equals("")) {
                payMethod.add("Pay on shop");
                payMethod.add("Upi");
                payMethod.add("Online");
            } else {
                payMethod.add(model.getPayment_type());
            }


            payMethodAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_layout, payMethod);
            spin.setAdapter(payMethodAdapter);

            btn_upload.setOnClickListener(v -> {
                if (checkPermission()) {
                    openFileChooser();
                } else {
                    requestPermission();
                }
            });
            btn_submit.setOnClickListener(v -> {
                String des = et_des.getText().toString();
                if (des.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter the description.", Toast.LENGTH_SHORT).show();
                    return;
                }
                pType=spin.getSelectedItem().toString();
                if (pType.equalsIgnoreCase("pay on shop")){
                    pType="Offline";
                }
                if (model.getPayment_type() == null || model.getPayment_type().equals("")) {
                    /*fetch current status*/
                    checkPaymentStatus(String.valueOf(model.getId()), des, pType);
                } else {
                    completeOrder(des, pType);
                }

            });

            completeOrderDialog.setOnDismissListener(dialog -> {
                completeOrderDialog = null;
            });
            completeOrderDialog.show();
        }
    }

    private void checkPaymentStatus(String order_id, String des, String pType) {
        mViewModel.checkPaymentStatus((Activity) mContext, order_id, des, pType);
    }

    /*open file chooser*/
    private void openFileChooser() {
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");

        someActivityResultLauncher.launch(chooserIntent);
    }

    private void requestPermission() {
        requestPermissions(new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void goToChat(View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("userid", model.getUsers().getId());
        bundle.putSerializable("orderid", model.getId());
        bundle.putInt("status", model.getStatus());
        bundle.putString("name", model.getUsers().getName());
        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_out_right)
                .setExitAnim(R.anim.slide_in).setPopEnterAnim(0).setPopExitAnim(R.anim.slide_out1)
                .build();
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.nav_message, bundle, options);
    }

    private void openAcceptBottomDialog() {
        if (acceptOrderDialog == null) {

            acceptOrderDialog = new BottomSheetDialog(mContext);
            acceptOrderDialog.setContentView(R.layout.accept_order_bottomsheet);
            acceptOrderDialog.setCanceledOnTouchOutside(false);

            CustomTextView txt_label_time = acceptOrderDialog.findViewById(R.id.txt_label_time);
            CustomInputEditText et_time = acceptOrderDialog.findViewById(R.id.et_time);
            CustomInputEditText et_amount = acceptOrderDialog.findViewById(R.id.et_amount);
            RelativeLayout btn_submit = acceptOrderDialog.findViewById(R.id.btn_submit);
            AppCompatSpinner spin = acceptOrderDialog.findViewById(R.id.spinner);

            if (model.getOrder_amount().equals("0.00")) {
                et_amount.setEnabled(true);
            } else {
                et_amount.setText(model.getOrder_amount());
                et_amount.setEnabled(false);
            }

            ArrayList<String> list = new ArrayList<>();
            list.add("Minutes");
            list.add("Hour");

            ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.spinner_layout, list);
            spin.setAdapter(adapter);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    tType = adapterView.getItemAtPosition(i).toString();
                    txt_label_time.setText("Time in " + tType.toLowerCase());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    tType = "";
                }
            });

            btn_submit.setOnClickListener(v -> {
                String time = et_time.getText().toString();
                String amount = et_amount.getText().toString();
                if (time.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter the time.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (amount.trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter the amount.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Double.parseDouble(amount) == 0) {
                    Toast.makeText(mContext, "Amount should be greater than 0.", Toast.LENGTH_SHORT).show();
                    return;
                }

                update("Accept", time, tType.toLowerCase(), amount);
            });
            acceptOrderDialog.setOnDismissListener(dialog -> {
                acceptOrderDialog = null;
            });
            acceptOrderDialog.show();
        }
    }

    private void completeOrder(String des, String pType) {
        mViewModel.completeOrder((Activity) mContext, des, model.getId(), pType, uploadedFile);
    }

    private void update(String status, String time, String type, String order_amount) {
        mViewModel.updateOrderStatus((Activity) mContext, status, model.getId(), time, type, order_amount);
    }

    @Override
    public OrderDetailViewModel getMyViewModel() {
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
            model = (OrdersDataModel) getArguments().getSerializable("order");
            setData();
            permission();
            someActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            try {
                                Intent data = result.getData();

                                if (data != null) {
                                    pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);

                                } else {
                                    Log.e(TAG, "Data is null");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            pickiT = new PickiT(mContext, this, (Activity) mContext);

            binding.btnAccept.setOnClickListener(this);
            binding.btnReject.setOnClickListener(this);
            binding.btnChat.setOnClickListener(this);
            binding.btnComplete.setOnClickListener(this);
        }
    }

    private void permission() {
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(mContext, "Permission granted, Please download", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, "This permission is required to download the file.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setData() {
        binding.txtDate.setText(model.getCreated_at());
        binding.txtOrderId.setText(String.format("#%s", model.getOrder_id()));
        binding.txtUsername.setText(model.getUsers().getName());
        binding.txtServiceCategory.setText(model.getProducts().getName());

        binding.bottom.setVisibility(View.GONE);
        binding.chatBottom.setVisibility(View.GONE);
        switch (model.getStatus()) {
            case 1:
                binding.txtStatus.setText("Pending");
                binding.txtH1.setText("Pending Order");
                binding.txtStatus.setTextColor(mContext.getResources().getColor(R.color.yellow_ffb302));
                binding.bottom.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.txtStatus.setText("Accept");
                binding.txtH1.setText("Accepted Order");
                binding.txtStatus.setTextColor(mContext.getResources().getColor(R.color.green_39ae00));
                binding.chatBottom.setVisibility(View.VISIBLE);
                break;
            case 5:
                binding.txtStatus.setText("Complete");
                binding.txtH1.setText("Completed Order");
                binding.txtStatus.setTextColor(mContext.getResources().getColor(R.color.green_39ae00));
                binding.chatBottom.setVisibility(View.VISIBLE);
                binding.btnComplete.setVisibility(View.GONE);
                binding.chatBottom.setWeightSum(1);
                break;
            case 4:
                binding.txtStatus.setText("Cancel");
                binding.txtH1.setText("Canceled Order");
                binding.txtStatus.setTextColor(mContext.getResources().getColor(R.color.red_ff2502));
                break;
            case 3:
                binding.txtStatus.setText("Reject");
                binding.txtH1.setText("Rejected Order");
                binding.txtStatus.setTextColor(mContext.getResources().getColor(R.color.red_ff2502));

                break;
        }
        binding.txtPrice.setText("₹ " + model.getOrder_amount());

        Glide.with(mContext).load(model.getProducts().getProduct_image()).into(binding.img);

        /*user details*/
        binding.txtName.setText(model.getUsers().getName());
        if (model.getUsers().getPhone() != null)
            binding.txtMobile.setText(model.getUsers().getPhone());
        if (model.getUsers().getEmail() != null)
            binding.txtEmail.setText(model.getUsers().getEmail());

        setFormData();
    }

    private void setFormData() {
        List<OrderDetailDataModel> formList = model.getOrder_detail();
        binding.recyclerForm.setLayoutManager(new LinearLayoutManager(getActivity()));
        FormDataAdapter formDataAdapter = new FormDataAdapter(getActivity(), formList, mViewModel, this);
        binding.recyclerForm.setAdapter(formDataAdapter);
    }

    @Override
    public int getBindingVariable() {
        return BR.orderDetailView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_details;
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
    public void onSuccessOrderStatusUpdated(JsonObject jsonObject, String status) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                if (status.equalsIgnoreCase("accept")) {
                    if (acceptOrderDialog != null && acceptOrderDialog.isShowing()) {
                        acceptOrderDialog.dismiss();
                    }
                    if (model!=null){
                        model.setStatus(2);
                    }
                    binding.txtStatus.setText("Accept");
                    binding.txtH1.setText("Accepted Order");
                    binding.chatBottom.setVisibility(View.VISIBLE);
                }
                binding.bottom.setVisibility(View.GONE);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessPaymentStatus(JsonObject jsonObject, String des, String pType) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                try {
                    int paymentStatus = jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("payment_status").getAsInt();
                    if (paymentStatus == 1) {
                        /*payment already made*/
                        payMethod.clear();
                        payMethod.add("Online");
                        payMethodAdapter.notifyDataSetChanged();
                        model.setPayment_type("Online");
                        Toast.makeText(mContext, "Payment has been already made by user through online mode. Please continue.", Toast.LENGTH_LONG).show();
                    } else {
                        completeOrder(des, pType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessOrderCompleted(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                String message = jsonObject.get("message").getAsString();
                if (completeOrderDialog != null && completeOrderDialog.isShowing()) {
                    completeOrderDialog.dismiss();
                }
                if (model!=null){
                    model.setStatus(5);
                }
                binding.txtStatus.setText("Complete");
                binding.txtH1.setText("Completed Order");

                binding.btnComplete.setVisibility(View.GONE);
                binding.chatBottom.setWeightSum(1);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void downloadFile(String url) {
        if (ContextCompat.checkSelfPermission(
                mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            String title = URLUtil.guessFileName(url, null, null);
            request.setTitle(title);
            request.setDescription("Downloading...");
            String cookie = CookieManager.getInstance().getCookie(url);
            request.addRequestHeader("cookie", cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

            DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(mContext, "Downloading...", Toast.LENGTH_LONG).show();
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void PickiTonUriReturned() {
        mViewModel.getmNavigator().get().showProgressBars();
    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String finalFilePath, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        mViewModel.getmNavigator().get().hideProgressBars();
        if (wasSuccessful) {
            if (finalFilePath != null) {
                uploadedFile = new File(finalFilePath);

                long fileSizeInBytes = uploadedFile.length();
                long fileSizeInKB = fileSizeInBytes / 1024;
                long fileSizeInMB = fileSizeInKB / 1024;

                if (fileSizeInMB > 20) {
                    Toast.makeText(mContext, "File size is too big. (Max : 20 MB)", Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri file = Uri.fromFile(uploadedFile);

                et_upload.setText(file.getLastPathSegment());
            } else {
                Log.e(TAG, "filename is null");
            }
        } else {
            Toast.makeText(mContext, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }
}