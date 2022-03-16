package com.mamits.zini24vendor.ui.fragment.dashboard;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.mamits.zini24vendor.BR;
import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.chat.MessageDataModel;
import com.mamits.zini24vendor.databinding.FragmentMessageBinding;
import com.mamits.zini24vendor.ui.adapter.MessengerAdapter;
import com.mamits.zini24vendor.ui.base.BaseFragment;
import com.mamits.zini24vendor.ui.navigator.fragment.MessageNavigator;
import com.mamits.zini24vendor.viewmodel.fragment.MessageViewModel;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MessageFragment extends BaseFragment<FragmentMessageBinding, MessageViewModel>
        implements MessageNavigator, View.OnClickListener, PickiTCallbacks {
    private String TAG = "MessageFragment";
    private FragmentMessageBinding binding;

    @Inject
    MessageViewModel mViewModel;
    private Context mContext;
    private Gson mGson;
    private int user_id, order_id, status;
    private List<MessageDataModel> messagesList;
    private MessengerAdapter messageAdapter;
    private boolean isScrollToBottom = true;
    private CountDownTimer timer;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private File uploadedFile = null;
    private String message = "";
    private String name;
    private PickiT pickiT;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                message = binding.etMessage.getText().toString();
                if (message.trim().length() == 0 && uploadedFile == null) {
                    Toast.makeText(mContext, "Can't send empty message.", Toast.LENGTH_SHORT).show();
                    return;
                }
                binding.etMessage.setText("");
                sendMessage(message, uploadedFile);
                discardUpload();
                break;
            case R.id.btn_attach:
                /*open file chooser*/
                if (checkPermission()) {
                    openFileChooser();
                } else {
                    requestPermission();
                }
                break;
            case R.id.btn_discard:
                discardUpload();
                break;
        }
    }

    private void discardUpload() {
        uploadedFile = null;
        binding.txtFileName.setText("");
        binding.rlFile.setVisibility(View.GONE);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissions(new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

    private void sendMessage(String message, File uploadedFile) {
        mViewModel.sendMessage((Activity) mContext, user_id, order_id, uploadedFile, message);
    }

    /*open file chooser*/
    private void openFileChooser() {

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        someActivityResultLauncher.launch(chooserIntent);
    }

    @Override
    public MessageViewModel getMyViewModel() {
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
            Bundle bundle = getArguments();
            if (bundle != null) {
                user_id = bundle.getInt("userid", -1);
                order_id = bundle.getInt("orderid", -1);
                status = bundle.getInt("status", -1);
                name = bundle.getString("name");
                String[] nameSplit = name.split(" ");
                String nameCode = (nameSplit[0].charAt(0) + nameSplit[nameSplit.length - 1].substring(0, 1)).toUpperCase();
                binding.nameCode.setText(nameCode);
                binding.userName.setText(name);
            }
            if (status == 2) {
                binding.bottomCard.setVisibility(View.VISIBLE);
            } else if (status == 5) {
                binding.bottomCard.setVisibility(View.GONE);
            }
            setUpMessages();

            binding.btnAttach.setOnClickListener(this);
            binding.btnSend.setOnClickListener(this);
            binding.btnDiscard.setOnClickListener(this);

            someActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            try {
                                Intent data = result.getData();

                                if (data != null) {

//                                    String finalFilePath = FileUtils.getPath(mContext, data.getData());
                                    pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);

                                    /*create file*/
                                    /*if (finalFilePath != null) {
                                        uploadedFile = new File(finalFilePath);

                                        long fileSizeInBytes = uploadedFile.length();
                                        long fileSizeInKB = fileSizeInBytes / 1024;
                                        long fileSizeInMB = fileSizeInKB / 1024;

                                        if (fileSizeInMB > 20) {
                                            Toast.makeText(mContext, "File size is too big. (Max : 20 MB)", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Uri file = Uri.fromFile(uploadedFile);

                                        binding.txtFileName.setText(file.getLastPathSegment());
                                        binding.rlFile.setVisibility(View.VISIBLE);
                                    } else {
                                        Log.e(TAG, "filename is null");
                                    }*/

                                } else {
                                    Log.e(TAG, "Data is null");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            pickiT = new PickiT(mContext, this, (Activity) mContext);
        }
    }

    private void setUpMessages() {
        messagesList = new ArrayList<>();
        mGson = new Gson();
        binding.recyclerMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageAdapter = new MessengerAdapter(getActivity(), mViewModel);
        binding.recyclerMessages.setAdapter(messageAdapter);

        loadMessages();
    }

    private void loadMessages() {
        mViewModel.fetchMessages((Activity) mContext, user_id, order_id);
        binding.recyclerMessages.setVisibility(View.VISIBLE);
    }

    @Override
    public int getBindingVariable() {
        return BR.messageView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void showProgressBars() {
        showsLoading();
    }

    @Override
    public void checkInternetConnection(String message) {
        Log.e(TAG, message);
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBars() {
        hidesLoading();
    }

    @Override
    public void checkValidation(int errorCode, String message) {
        Log.e(TAG, message);
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void throwable(Throwable throwable) {
        throwable.printStackTrace();
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessMessages(JsonObject jsonObject) {
        binding.progressBar.setVisibility(View.GONE);
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                mGson = new Gson();
                Type messages = new TypeToken<List<MessageDataModel>>() {
                }.getType();
                messagesList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), messages);

                if (messagesList != null && messagesList.size() > 0) {
                    messageAdapter.setList(messagesList);
                    if (isScrollToBottom) {
                        binding.recyclerMessages.scrollToPosition(messagesList.size() - 1);
                        isScrollToBottom = false;
                    }
                    if (timer != null) {
                        timer.cancel();
                    }
                    /*start timer*/
                    startTimer();
                } else {
                    binding.recyclerMessages.setVisibility(View.GONE);
                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessMessageSend(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.get("status").getAsBoolean()) {
                binding.recyclerMessages.setVisibility(View.VISIBLE);
                mGson = new Gson();
                Type messages = new TypeToken<List<MessageDataModel>>() {
                }.getType();
                messagesList = mGson.fromJson(jsonObject.get("data").getAsJsonArray().toString(), messages);

                if (messagesList != null && messagesList.size() > 0) {
                    messageAdapter.setList(messagesList);
                    binding.recyclerMessages.scrollToPosition(messagesList.size() - 1);

                    if (timer != null) {
                        timer.cancel();
                    }
                    /*start timer*/
                    startTimer();
                } else {
                    binding.recyclerMessages.setVisibility(View.GONE);
                }

            } else {
                int messageId = jsonObject.get("messageId").getAsInt();
                String message = jsonObject.get("message").getAsString();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                loadMessages();
            }

        }.start();
    }

    @Override
    public void onPause() {
        if (timer != null) {
            timer.cancel();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (timer != null) {
            timer.start();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        if (pickiT != null) {
            pickiT.deleteTemporaryFile(mContext);
        }
        super.onDestroy();
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

                binding.txtFileName.setText(file.getLastPathSegment());
                binding.rlFile.setVisibility(View.VISIBLE);
            } else {
                Log.e(TAG, "filename is null");
            }
        }
        else {
            Toast.makeText(mContext,"Something went wrong.",Toast.LENGTH_SHORT).show();
        }
    }

}