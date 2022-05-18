package com.hxw.recycler.recycler_lib.empty;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.RecyclerHolder;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/9</p>
 * <p>更改时间：2022/5/9</p>
 * <p>版本号：1</p>
 */
public abstract class EmptyView {

    public static final int STATUS_DEFAULT = 21;
    public static final int STATUS_EMPTY = 22;
    public static final int STATUS_LOADING = 23;

    private int emptyStatus = STATUS_DEFAULT;

    private RecyclerHolder holder;

    public void setHolder(@NonNull RecyclerHolder holder) {
        this.holder = holder;
    }

    public void setEmptyStatus(int emptyStatus) {
        this.emptyStatus = emptyStatus;
    }

    public int getEmptyStatus() {
        return emptyStatus;
    }

    @Nullable
    public RecyclerHolder getHolder() {
        return holder;
    }

    public void convert() {
        if (holder == null) {
            return;
        }
        switch (emptyStatus) {
            case STATUS_EMPTY:
                visibleEmpty(true);
                visibleLoading(false);
                break;
            case STATUS_LOADING:
                visibleEmpty(false);
                visibleLoading(true);
                break;
            default:
                visibleLoading(false);
                visibleEmpty(false);
                break;
        }
    }

    protected void visibleEmpty(boolean visible) {
        getEmptyView(holder).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    protected void visibleLoading(boolean visible) {
        View loadingView = getLoadingView(holder);
        if (loadingView != null) {
            loadingView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public abstract
    @LayoutRes
    int getItemLayoutRes();

    @NonNull
    public abstract View getEmptyView(@NonNull RecyclerHolder holder);

    @Nullable
    public abstract View getLoadingView(@NonNull RecyclerHolder holder);
}
