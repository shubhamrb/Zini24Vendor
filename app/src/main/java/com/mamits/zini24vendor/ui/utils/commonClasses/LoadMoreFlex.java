package com.mamits.zini24vendor.ui.utils.commonClasses;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoadMoreFlex {

    private RecyclerView mRecyclerView;

    private Context mContext = null;
    private boolean loading = false;
    private int lastVisibleItem = 0;
    private int totalItemCount = 0;
    private int visibleThreshold = 2;
    private OnLoadMoreFlexListener onLoadMoreFlexListener = null;

    public LoadMoreFlex(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        if (mRecyclerView.getLayoutManager() != null) {
            GridLayoutManager flexboxLayoutManager = (GridLayoutManager) mRecyclerView
                    .getLayoutManager();

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = flexboxLayoutManager.getItemCount();
                    lastVisibleItem = flexboxLayoutManager.findLastVisibleItemPosition();
                    Log.v(
                            "TAG",
                            "last  " + (totalItemCount <= lastVisibleItem + visibleThreshold)
                    );
                    if (loading
                            && totalItemCount <= lastVisibleItem + visibleThreshold
                    ) {
                        if (onLoadMoreFlexListener != null) {
                            onLoadMoreFlexListener.onLoadMoreFlex();
                        }
                        loading = false;
                    }
                }
            });
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreFlexListener onLoadMoreFlexListener) {
        this.onLoadMoreFlexListener = onLoadMoreFlexListener;
    }

    public void setLoadingMore(boolean status) {
        loading = status;
    }

    public interface OnLoadMoreFlexListener {
        public void onLoadMoreFlex();
    }

}
