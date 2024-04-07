package com.hxw.recycler.recycler_lib.loadmore;

import android.view.View;

import androidx.annotation.NonNull;

import com.hxw.recycler.recycler_lib.R;
import com.hxw.recycler.recycler_lib.RecyclerHolder;
import com.hxw.recycler.recycler_lib.databinding.LayoutDownLoadMoreSimpleBinding;
import com.hxw.recycler.recycler_lib.databinding.LayoutLoadMoreSimpleBinding;


/**
 * Description:
 * <p>上拉加载更多
 * Author: hanxw
 * Date: 2023/7/26 9:25
 * Version: 1.0
 */
public class LoadMoreDownView extends LoadMoreDelegate {
    @Override
    public int getItemLayoutRes() {
        return R.layout.layout_down_load_more_simple;
    }

    @NonNull
    @Override
    protected View getLoadingView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutDownLoadMoreSimpleBinding) {
            return ((LayoutLoadMoreSimpleBinding) holder.getDataBinding()).loadMoreLoadingView;
        }
        throw new RuntimeException("SimpleLoadMoreView RuntimeException");
    }

    @Override
    public void convert() {
        switch (getLoadMoreStatus()) {
            case STATUS_LOADING:
                visibleLoading(true);
                break;
            case STATUS_DEFAULT:
            default:
                visibleLoading(false);
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
}
