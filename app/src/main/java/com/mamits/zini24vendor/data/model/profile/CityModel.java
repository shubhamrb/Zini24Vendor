package com.mamits.zini24vendor.data.model.profile;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityModel implements Serializable {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    public CityModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
