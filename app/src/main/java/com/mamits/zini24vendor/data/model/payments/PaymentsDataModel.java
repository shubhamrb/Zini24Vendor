package com.mamits.zini24vendor.data.model.payments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentsDataModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("vendor_id")
    int vendor_id;

    @SerializedName("pay_to")
    int pay_to;

    @SerializedName("payment_method")
    String payment_method;

    @SerializedName("paymentMode")
    String paymentMode;

    @SerializedName("transaction_id")
    String transaction_id;

    @SerializedName("amount")
    String amount;

    @SerializedName("payment_type")
    String payment_type;

    @SerializedName("message")
    String message;

    @SerializedName("status")
    String status;

    @SerializedName("payment_date_time")
    String payment_date_time;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("updated_at")
    String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public int getPay_to() {
        return pay_to;
    }

    public void setPay_to(int pay_to) {
        this.pay_to = pay_to;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_date_time() {
        return payment_date_time;
    }

    public void setPayment_date_time(String payment_date_time) {
        this.payment_date_time = payment_date_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "PaymentsDataModel{" +
                "id=" + id +
                ", vendor_id=" + vendor_id +
                ", pay_to=" + pay_to +
                ", payment_method='" + payment_method + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", amount='" + amount + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", payment_date_time='" + payment_date_time + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
