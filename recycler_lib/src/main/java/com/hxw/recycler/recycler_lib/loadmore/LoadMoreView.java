package com.hxw.recycler.recycler_lib.loadmore;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.R;
import com.hxw.recycler.recycler_lib.RecyclerHolder;
import com.hxw.recycler.recycler_lib.databinding.LayoutLoadMoreSimpleBinding;


public class LoadMoreView extends LoadMoreDelegate {

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

    @Nullable
    @Override
    protected View getLoadFailView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutLoadMoreSimpleBinding) {
            return ((LayoutLoadMoreSimpleBinding) holder.getDataBinding()).loadMoreLoadFailView;
        }
        return null;
    }

    @Nullable
    @Override
    protected View getLoadEndView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutLoadMoreSimpleBinding) {
            return ((LayoutLoadMoreSimpleBinding) holder.getDataBinding()).loadMoreLoadEndView;
        }
        return null;
    }

    @Override
    public void convert() {
        if (getHolder() == null) {
            return;
        }
        switch (getLoadMoreStatus()) {
            case STATUS_LOADING:
                visibleLoading(true);
                visibleLoadFail(false);
                visibleLoadEnd(false);
                break;
            case STATUS_FAIL:
                visibleLoading(false);
                visibleLoadFail(true);
                visibleLoadEnd(false);
                break;
            case STATUS_END:
                visibleLoading(false);
                visibleLoadFail(false);
                visibleLoadEnd(true);
                break;
            case STATUS_DEFAULT:
                visibleLoading(false);
                visibleLoadFail(false);
                visibleLoadEnd(false);
                break;
            default:
                break;
        }
    }

    private void visibleLoading(boolean visible) {
        if (getHolder() == null) {
            return;
        }
        final View loadingView = getLoadingView(getHolder());
        loadingView.post(() -> loadingView.setVisibility(visible ? View.VISIBLE : View.GONE));
    }

    private void visibleLoadFail(boolean visible) {
        if (getHolder() == null) {
            return;
        }
        final View loadFailView = getLoadFailView(getHolder());
        if (loadFailView != null) {
            loadFailView.post(() -> loadFailView.setVisibility(visible ? View.VISIBLE : View.GONE));
        }
    }

    private void visibleLoadEnd(boolean visible) {
        if (getHolder() == null) {
            return;
        }
        final View loadEndView = getLoadEndView(getHolder());
        if (loadEndView != null) {
            loadEndView.post(() -> loadEndView.setVisibility(visible ? View.VISIBLE : View.GONE));
        }
    }


}
