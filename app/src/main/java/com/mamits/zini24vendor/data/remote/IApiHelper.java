package com.mamits.zini24vendor.data.remote;

import android.app.Activity;

import com.mamits.zini24vendor.ui.utils.listeners.ResponseListener;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public interface IApiHelper {

    void userLogin(Activity activity, String jsonObject, ResponseListener responseListener);

    void fetchHomeData(Activity mActivity, String accessToken, ResponseListener responseListener);

    void fetchPaymentKeys(Activity mActivity, String accessToken, ResponseListener responseListener);

    void fetchCfsToken(Activity mActivity, String accessToken, String orderId, String amount, ResponseListener responseListener);

    void fetchPaytmToken(Activity mActivity, String accessToken, String orderId, String amount, String customerPhone, String customerEmail, ResponseListener responseListener);

    void fetchOrders(Activity mActivity, String accessToken, int status, ResponseListener responseListener);

    void fetchStateCity(Activity mActivity, String accessToken, ResponseListener responseListener);

    void submitProfile(Activity mActivity, String accessToken, List<String> categoryArray, String shop_name, String owner_name, String et_email, String et_number, String et_address, int selectedState, int selectedCity, String tv_lat, String tv_lng, String et_zipcode, String et_open, String et_close, List<String> payModeArray, File ownerFile, File shopFile, File banner, File banner1, File banner2, File banner3, String et_desc, File qrFile, String et_upi, String et_whatsapp, String et_account, String et_bank, String et_ifsc, ResponseListener responseListener);

    void fetchPayments(Activity mActivity, String accessToken, ResponseListener responseListener);

    void fetchTransactions(Activity mActivity, String accessToken, String pType, ResponseListener responseListener);

    void fetchHelp(Activity mActivity, String accessToken, ResponseListener responseListener);

    void sendOtp(Activity mActivity, String number, ResponseListener responseListener);

    void signUp(Activity mActivity, String number, ResponseListener responseListener);

    void doRegistration(Activity mActivity, JSONObject jsonObject, ResponseListener responseListener);

    void verifyOtp(Activity mActivity, String number, String otp, ResponseListener responseListener);

    void updatePin(Activity mActivity, String number, String newPin, ResponseListener responseListener);

    void updateOrderStatus(Activity mActivity, String accessToken, String status, int order_id, String time, String type, String order_amount, ResponseListener responseListener);

    void checkPaymentStatus(Activity mActivity, String accessToken, String order_id, ResponseListener responseListener);

    void completeOrder(Activity mActivity, String accessToken, String des, int order_id, String pType, File uploadedFile, ResponseListener responseListener);

    void fetchMessage(Activity mActivity, String accessToken, int user_id, int order_id, ResponseListener responseListener);

    void sendMessage(Activity mActivity, String accessToken, int user_id, int order_id, String message, File uploadedFile, ResponseListener responseListener);

    void fetchCoupons(Activity mActivity, String accessToken, ResponseListener responseListener);

    void createCoupon(Activity mActivity, String accessToken, JSONObject couponObject, ResponseListener responseListener);

    void fetchServices(Activity mActivity, String accessToken, String category, String subCategory, ResponseListener responseListener);

    void fetchCategorySubcategory(Activity mActivity, String accessToken, ResponseListener responseListener);

    void fetchAllCategory(Activity mActivity, String accessToken, ResponseListener responseListener);

    void updateCategory(Activity mActivity, String accessToken, JSONObject jsonObject, ResponseListener responseListener);

    void deleteCoupon(Activity mActivity, String accessToken, String couponid, ResponseListener responseListener);

    void deleteService(Activity mActivity, String accessToken, String inventoryId, ResponseListener responseListener);

    void fetchProducts(Activity mActivity, String accessToken, String cat, String sub_cat, ResponseListener responseListener);

    void addService(Activity mActivity, String accessToken, JSONObject productDataModel, ResponseListener responseListener);

    void updateService(Activity mActivity, String accessToken, JSONObject productDataModel, ResponseListener responseListener);

    void changePassword(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener);

    void openStore(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener);

    void fetchStoreStatus(Activity mActivity, String accessToken, ResponseListener responseListener);

    void savePaymentResponse(Activity mActivity, String accessToken, JSONObject object, ResponseListener responseListener);
}
