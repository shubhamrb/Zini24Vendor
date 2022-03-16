package com.mamits.zini24vendor.data.model.payments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionsDataModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("amount")
    String amount;

    @SerializedName("type")
    String type;

    @SerializedName("payment_type")
    String payment_type;

    @SerializedName("description")
    String description;

    @SerializedName("created_at")
    String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "TransactionsDataModel{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", type='" + type + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", description='" + description + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
