package com.mamits.zini24vendor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.orders.OrderDetailDataModel;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.viewmodel.fragment.OrderDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class FormDataAdapter extends RecyclerView.Adapter<FormDataAdapter.OrdersViewHolder> {

    private Context mContext;
    public List<OrderDetailDataModel> list;
    private OrderDetailViewModel mViewModel;
    private Activity activity;
    private downloadListener listener;

    public FormDataAdapter(Context mContex, List<OrderDetailDataModel> formList, OrderDetailViewModel mViewModel, downloadListener listener) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
        this.mViewModel = mViewModel;
        list = formList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.form_item, parent, false);
        return new OrdersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        if (list.size() > 0) {
            OrderDetailDataModel model = list.get(position);
            holder.btn_save.setVisibility(View.GONE);

            if (model.getName() != null) {
                if (model.getName().equalsIgnoreCase("upload your document")) {
                    holder.txt_label.setText("Uploaded Document");

                    if (model.getFiledata() != null && model.getFiledata().getUrl() != null && model.getFiledata().getUrl().trim().length() > 0) {
                        holder.txt_value.setText("");
                        holder.btn_save.setVisibility(View.VISIBLE);
                    } else {
                        holder.txt_value.setText("NA");
                    }
                } else {
                    holder.txt_label.setText(model.getName());
                    if (model.getValue() != null) {
                        holder.txt_value.setText(model.getValue());
                    } else {
                        if (model.getFiledata() != null && model.getFiledata().getUrl() != null && model.getFiledata().getUrl().trim().length() > 0) {
                            holder.txt_value.setText("");
                            holder.btn_save.setVisibility(View.VISIBLE);
                        } else {
                            holder.txt_value.setText("NA");
                        }
                    }
                }
            } else {
                holder.txt_label.setText("NA");
                if (model.getValue() != null) {
                    holder.txt_value.setText(model.getValue());
                } else {
                    if (model.getFiledata() != null && model.getFiledata().getUrl() != null && model.getFiledata().getUrl().trim().length() > 0) {
                        holder.txt_value.setText("");
                        holder.btn_save.setVisibility(View.VISIBLE);
                    } else {
                        holder.txt_value.setText("NA");
                    }
                }
            }
            holder.btn_save.setOnClickListener(v -> {
                listener.downloadFile(model.getFiledata().getUrl());
            });

        }

    }

    public interface downloadListener {
        void downloadFile(String url);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_label, txt_value;
        private ImageView btn_save;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_label = itemView.findViewById(R.id.txt_label);
            txt_value = itemView.findViewById(R.id.txt_value);
            btn_save = itemView.findViewById(R.id.btn_save);

        }
    }
}
