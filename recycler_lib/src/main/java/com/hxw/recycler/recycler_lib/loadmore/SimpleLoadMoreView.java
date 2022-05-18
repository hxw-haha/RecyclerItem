package com.hxw.recycler.recycler_lib.loadmore;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.R;
import com.hxw.recycler.recycler_lib.RecyclerHolder;
import com.hxw.recycler.recycler_lib.databinding.LayoutLoadMoreSimpleBinding;

public final class SimpleLoadMoreView extends LoadMoreView {

    @Override
    public int getItemLayoutRes() {
        return R.layout.layout_load_more_simple;
    }

    @NonNull
    @Override
    protected View getLoadingView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutLoadMoreSimpleBinding) {
            return ((LayoutLoadMoreSimpleBinding) holder.getDataBinding()).loadMoreLoadingView;
        }
        throw new RuntimeException("SimpleLoadMoreView RuntimeException");
    }

    @NonNull
    @Override
    protected View getLoadFailView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutLoadMoreSimpleBinding) {
            return ((LayoutLoadMoreSimpleBinding) holder.getDataBinding()).loadMoreLoadFailView;
        }
        throw new RuntimeException("SimpleLoadMoreView RuntimeException");
    }

    @Nullable
    @Override
    protected View getLoadEndView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutLoadMoreSimpleBinding) {
            return ((LayoutLoadMoreSimpleBinding) holder.getDataBinding()).loadMoreLoadEndView;
        }
        return null;
    }
}
