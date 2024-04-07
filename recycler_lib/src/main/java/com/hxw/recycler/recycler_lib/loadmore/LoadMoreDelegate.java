package com.hxw.recycler.recycler_lib.loadmore;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.RecyclerHolder;


/**
 * Description:
 * <p>
 * Author: hanxw
 * Date: 2023/7/26 10:45
 * Version: 1.0
 */
public abstract class LoadMoreDelegate {
    public static final int STATUS_DEFAULT = 11;
    public static final int STATUS_LOADING = 12;
    public static final int STATUS_FAIL = 13;
    public static final int STATUS_END = 14;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private RecyclerHolder holder;

    private boolean mLoadMoreEndGone = false;

    @Nullable
    public RecyclerHolder getHolder() {
        return holder;
    }

    public final void setHolder(@NonNull RecyclerHolder holder) {
        this.holder = holder;
    }

    public final boolean isHolderEmpty() {
        return holder == null;
    }

    public final void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public final int getLoadMoreStatus() {
        return mLoadMoreStatus;
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

    @LayoutRes
    public abstract int getItemLayoutRes();

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
    @Nullable
    protected View getLoadFailView(@NonNull RecyclerHolder holder) {
        return null;
    }

    /**
     * load end view, you can return 0
     *
     * @return
     */
    @Nullable
    protected View getLoadEndView(@NonNull RecyclerHolder holder) {
        return null;
    }

    public abstract void convert();
}
