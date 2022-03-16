package com.mamits.zini24vendor.data.remote;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.mamits.zini24vendor.ui.utils.constants.AppConstant;
import com.mamits.zini24vendor.ui.utils.listeners.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ApiHelper implements IApiHelper {

    @Inject
    public ApiHelper() {
    }


    @Override
    public void userLogin(Activity mActivity, String jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.userLogin(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });


    }

    @Override
    public void fetchHomeData(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchHomeData("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchPaymentKeys(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchPaymentKeys("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchCfsToken(Activity mActivity, String accessToken, String orderId, String amount, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
            jsonObject.put("orderAmount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchCfsToken("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchPaytmToken(Activity mActivity, String accessToken, String orderId, String amount, String customerPhone, String customerEmail, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
            jsonObject.put("orderAmount", amount);
            jsonObject.put("mobile", customerPhone);
            jsonObject.put("email", customerEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchPaytmToken("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchOrders(Activity mActivity, String accessToken, int status, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        call.fetchOrders("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchStateCity(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        call.fetchStateCity("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void submitProfile(Activity mActivity, String accessToken, List<String> categoryArray, String shop_name, String owner_name, String et_email
            , String et_number, String et_address, int selectedState, int selectedCity, String tv_lat, String tv_lng,
                              String et_zipcode, String et_open, String et_close, List<String> payModeArray,
                              File ownerFile, File shopFile, File banner, File banner1, File banner2, File banner3,
                              String et_desc, File qrFile, String et_upi, String et_whatsapp, String et_account, String et_bank,
                              String et_ifsc, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        try {
            MultipartBody.Part bannerImage = null;
            MultipartBody.Part banner1Image = null;
            MultipartBody.Part banner2Image = null;
            MultipartBody.Part banner3Image = null;
            MultipartBody.Part ownerImage = null;
            MultipartBody.Part shopImage = null;
            MultipartBody.Part qrImage = null;
            if (banner != null) {
                RequestBody bannerFile = RequestBody.create(MediaType.parse("mutlipart/form-data"), banner);
                bannerImage = MultipartBody.Part.createFormData("banner", banner.getName(), bannerFile);
            }
            if (banner1 != null) {
                RequestBody bannerFile = RequestBody.create(MediaType.parse("mutlipart/form-data"), banner1);
                banner1Image = MultipartBody.Part.createFormData("banner1", banner1.getName(), bannerFile);
            }
            if (banner2 != null) {
                RequestBody bannerFile = RequestBody.create(MediaType.parse("mutlipart/form-data"), banner2);
                banner2Image = MultipartBody.Part.createFormData("banner2", banner2.getName(), bannerFile);
            }
            if (banner3 != null) {
                RequestBody bannerFile = RequestBody.create(MediaType.parse("mutlipart/form-data"), banner3);
                banner3Image = MultipartBody.Part.createFormData("banner3", banner3.getName(), bannerFile);
            }
            if (ownerFile != null) {
                RequestBody file = RequestBody.create(MediaType.parse("mutlipart/form-data"), ownerFile);
                ownerImage = MultipartBody.Part.createFormData("owner_image", ownerFile.getName(), file);
            }
            if (shopFile != null) {
                RequestBody file = RequestBody.create(MediaType.parse("mutlipart/form-data"), shopFile);
                shopImage = MultipartBody.Part.createFormData("storelogo", shopFile.getName(), file);
            }
            if (qrFile != null) {
                RequestBody file = RequestBody.create(MediaType.parse("mutlipart/form-data"), qrFile);
                qrImage = MultipartBody.Part.createFormData("qrcode", qrFile.getName(), file);
            }

            RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), shop_name);
            RequestBody ownername = RequestBody.create(MediaType.parse("multipart/form-data"), owner_name);
            RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), et_email);
            RequestBody mobile_number = RequestBody.create(MediaType.parse("multipart/form-data"), et_number);
            RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), et_address);
            RequestBody state = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(selectedState));
            RequestBody city = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(selectedCity));
            RequestBody latitude = RequestBody.create(MediaType.parse("multipart/form-data"), tv_lat);
            RequestBody longitude = RequestBody.create(MediaType.parse("multipart/form-data"), tv_lng);
            RequestBody zipcode = RequestBody.create(MediaType.parse("multipart/form-data"), et_zipcode);
            RequestBody openingtime = RequestBody.create(MediaType.parse("multipart/form-data"), et_open);
            RequestBody closingtime = RequestBody.create(MediaType.parse("multipart/form-data"), et_close);
            RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), et_desc);
            RequestBody upi_number = RequestBody.create(MediaType.parse("multipart/form-data"), et_upi);
            RequestBody whatsapp_no = RequestBody.create(MediaType.parse("multipart/form-data"), et_whatsapp);
            RequestBody account_number = RequestBody.create(MediaType.parse("multipart/form-data"), et_account);
            RequestBody bank_name = RequestBody.create(MediaType.parse("multipart/form-data"), et_bank);
            RequestBody ifsc_code = RequestBody.create(MediaType.parse("multipart/form-data"), et_ifsc);

            call.submitProfile("Bearer " + accessToken, categoryArray, name, ownername, email, mobile_number, address, state, city,
                    latitude, longitude, zipcode, openingtime, closingtime, description, payModeArray,
                    upi_number, whatsapp_no, account_number, bank_name, ifsc_code,
                    bannerImage, banner1Image, banner2Image, banner3Image, ownerImage, shopImage, qrImage).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    if (response.body() != null) {
                        responseListener.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    responseListener.onFailed(t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void fetchPayments(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchPayments("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchTransactions(Activity mActivity, String accessToken, String pType, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("payment_type", pType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        call.fetchTransactions("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchHelp(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchHelp("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void sendOtp(Activity mActivity, String number, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("mobile", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.sendOtp(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void signUp(Activity mActivity, String number, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("mobile", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.signUp(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void doRegistration(Activity mActivity, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.doRegistration(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void verifyOtp(Activity mActivity, String number, String otp, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("mobile", number);
            jsonObject.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.verifyOtp(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void updatePin(Activity mActivity, String number, String newPin, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("api_key", AppConstant.API_KEY);
            jsonObject.put("mobile", number);
            jsonObject.put("password", newPin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.updatePin(jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void updateOrderStatus(Activity mActivity, String accessToken, String status, int order_id, String time, String type, String order_amount, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderid", order_id);
            jsonObject.put("status", status);
            if (status.equalsIgnoreCase("accept") && time != null && type != null && order_amount != null) {
                jsonObject.put("time", time);
                jsonObject.put("type", type);
                jsonObject.put("order_amount", order_amount);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.updateOrderStatus("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void checkPaymentStatus(Activity mActivity, String accessToken, String order_id, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.checkPaymentStatus("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void completeOrder(Activity mActivity, String accessToken, String des, int order_id, String pType, File uploadedFile, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        try {
            MultipartBody.Part requestImage = null;
            if (uploadedFile != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("mutlipart/form-data"), uploadedFile);
                requestImage = MultipartBody.Part.createFormData("file", uploadedFile.getName(), requestFile);
            }

            RequestBody orderid = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(order_id));
            RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), des);
            RequestBody payment_accept_mode = RequestBody.create(MediaType.parse("multipart/form-data"), pType);

            call.completeOrder("Bearer " + accessToken, orderid, description, payment_accept_mode, requestImage).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    if (response.body() != null) {
                        responseListener.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    responseListener.onFailed(t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchMessage(Activity mActivity, String accessToken, int user_id, int order_id, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderid", order_id);
            jsonObject.put("userid", user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchMessages("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void sendMessage(Activity mActivity, String accessToken, int user_id, int order_id, String message, File uploadedFile, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        try {
            MultipartBody.Part requestImage = null;
            if (uploadedFile != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("mutlipart/form-data"), uploadedFile);
                requestImage = MultipartBody.Part.createFormData("chatfile", uploadedFile.getName(), requestFile);
            }


            RequestBody orderid = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(order_id));
            RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_id));
            RequestBody msg = RequestBody.create(MediaType.parse("multipart/form-data"), message);

            call.sendMessages("Bearer " + accessToken, orderid, userid, msg, requestImage).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    if (response.body() != null) {
                        responseListener.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    responseListener.onFailed(t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchCoupons(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchCoupons("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void createCoupon(Activity mActivity, String accessToken, JSONObject couponObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.createCoupon("Bearer " + accessToken, couponObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchServices(Activity mActivity, String accessToken, String category, String subCategory, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category", category);
            jsonObject.put("subcategory", subCategory);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchServices("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchCategorySubcategory(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchCatSubCategory("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchAllCategory(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchAllCategory("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void updateCategory(Activity mActivity, String accessToken, JSONObject jsonObject, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.updateCategory("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void deleteCoupon(Activity mActivity, String accessToken, String couponid, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("couponid", couponid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.deleteCoupon("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void deleteService(Activity mActivity, String accessToken, String inventoryId, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("inventoryid", inventoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.deleteService("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchProducts(Activity mActivity, String accessToken, String cat, String sub_cat, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category", cat);
            jsonObject.put("subcategory", sub_cat);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.fetchProducts("Bearer " + accessToken, jsonObject.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void addService(Activity mActivity, String accessToken, JSONObject productDataModel, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.addService("Bearer " + accessToken, productDataModel.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void updateService(Activity mActivity, String accessToken, JSONObject productDataModel, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.updateService("Bearer " + accessToken, productDataModel.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void changePassword(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.changePassword("Bearer " + accessToken, object.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void openStore(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.storeOpen("Bearer " + accessToken, object.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void fetchStoreStatus(Activity mActivity, String accessToken, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.fetchStoreStatus("Bearer " + accessToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }

    @Override
    public void savePaymentResponse(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener) {
        RetrofitInterface call = new RetrofitBase(mActivity, true).retrofit.create(RetrofitInterface.class);

        call.savePaymentResponse("Bearer " + accessToken, object.toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.body() != null) {
                    responseListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                responseListener.onFailed(t);
            }
        });
    }
}
