package com.mamits.zini24vendor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mamits.zini24vendor.R;
import com.mamits.zini24vendor.data.model.services.CategoryDataModel;
import com.mamits.zini24vendor.ui.customviews.CustomTextView;
import com.mamits.zini24vendor.viewmodel.fragment.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.OrdersViewHolder> {

    private Context mContext;
    public List<CategoryDataModel> list;
    private CategoryViewModel mViewModel;
    private Activity activity;
    private ArrayList<Integer> catList;
    private CategorySelectListener listener;

    public CategoryAdapter(Context mContex, CategoryViewModel mViewModel, CategorySelectListener listener) {
        this.mContext = mContex;
        activity = ((Activity) mContex);
        list = new ArrayList<>();
        this.mViewModel = mViewModel;
        catList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.category_list_item, parent, false);
        return new OrdersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        if (list.size() > 0) {
            CategoryDataModel model = list.get(position);
            holder.txt_username.setText(model.getName());
            holder.checkBox.setChecked(model.isStatus());

            holder.itemView.setOnClickListener(v -> {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
                if (holder.checkBox.isChecked()) {
                    catList.add(model.getId());
                } else {
                    catList.remove((Integer) model.getId());
                }
                listener.categorySelected(catList);
            });
        }
    }

    public interface CategorySelectListener {
        void categorySelected(ArrayList<Integer> list);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CategoryDataModel> orderList, ArrayList<Integer> catList) {
        this.catList = catList;
        list = orderList;
        notifyDataSetChanged();
    }

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView txt_username;
        private CheckBox checkBox;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_username = itemView.findViewById(R.id.txt_username);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
