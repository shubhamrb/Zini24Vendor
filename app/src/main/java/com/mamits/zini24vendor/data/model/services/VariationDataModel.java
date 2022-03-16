package com.mamits.zini24vendor.data.model.services;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VariationDataModel implements Serializable {

    @SerializedName("name")
    String name;

    @SerializedName("value")
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "VariationDataModel{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
