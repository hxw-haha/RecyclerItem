package com.hxw.recycler.recycler_lib.loadmore;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.RecyclerDataItem;
import com.hxw.recycler.recycler_lib.RecyclerHolder;

public abstract class LoadMoreView {

    public static final int STATUS_DEFAULT = 11;
    public static final int STATUS_LOADING = 12;
    public static final int STATUS_FAIL = 13;
    public static final int STATUS_END = 14;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean mLoadMoreEndGone = false;

    private RecyclerHolder holder;

    public void setHolder(@NonNull RecyclerHolder holder) {
        this.holder = holder;
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert() {
        if (holder == null) {
            return;
        }
        switch (mLoadMoreStatus) {
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
        getLoadingView(holder).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void visibleLoadFail(boolean visible) {
        getLoadFailView(holder).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void visibleLoadEnd(boolean visible) {
        final View loadEndView = getLoadEndView(holder);
        if (loadEndView != null) {
            loadEndView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone() {
        if (holder == null || getLoadEndView(holder) == null) {
            return true;
        }
        return mLoadMoreEndGone;
    }

    public abstract
    @LayoutRes
    int getItemLayoutRes();


    /**
     * loading view
     *
     * @return
     */
    @NonNull
    protected abstract View getLoadingView(@NonNull RecyclerHolder holder);

    /**
     * load fail view
     *
     * @return
     */
    @NonNull
    protected abstract View getLoadFailView(@NonNull RecyclerHolder holder);

    /**
     * load end view, you can return 0
     *
     * @return
     */
    @Nullable
    protected View getLoadEndView(@NonNull RecyclerHolder holder) {
        return null;
    }

}
