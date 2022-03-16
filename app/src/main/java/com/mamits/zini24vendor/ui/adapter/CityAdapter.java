package com.mamits.zini24vendor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mamits.zini24vendor.data.model.profile.CityModel;
import com.mamits.zini24vendor.data.model.profile.StateModel;

import java.util.List;

public class CityAdapter extends ArrayAdapter<CityModel> {


    public CityAdapter(Context context, int textViewResourceId,
                       List<CityModel> values) {
        super(context, textViewResourceId, values);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(this.getItem(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(this.getItem(position).getName());
        return label;
    }
}
