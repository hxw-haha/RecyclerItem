package com.hxw.recycler.recycler_lib;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.listener.OnDataItemToWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件描述：Recycler Base Data Item</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/7</p>
 * <p>更改时间：2022/5/7</p>
 * <p>版本号：1</p>
 */
public abstract class RecyclerDataItem<DATA, VH extends RecyclerHolder> {
    private final List<OnDataItemToWindow> dataItemToWindows = new ArrayList<>();
    public final DATA mData;
    public RecyclerAdapter adapter;
    private boolean isUiVisible;

    public RecyclerDataItem(@NonNull DATA data) {
        this.mData = data;
    }

    public boolean addDataItemToWindow(@NonNull OnDataItemToWindow dataItemToWindow) {
        if (this.dataItemToWindows.contains(dataItemToWindow)) {
            return false;
        }
        return this.dataItemToWindows.add(dataItemToWindow);
    }

    public boolean removeDataItemToWindow(@NonNull OnDataItemToWindow dataItemToWindow) {
        if (!this.dataItemToWindows.contains(dataItemToWindow)) {
            return false;
        }
        return this.dataItemToWindows.remove(dataItemToWindow);
    }

    /**
     * 绑定数据
     */
    protected abstract void onBindData(@NonNull VH holder, int position);

    /**
     * @return 返回该item的布局资源id
     */
    protected @LayoutRes
    int getItemLayoutRes() {
        return -1;
    }

    @Nullable
    protected View getItemView(ViewGroup parent) {
        return null;
    }

    public void setAdapter(@NonNull RecyclerAdapter recyclerAdapter) {
        this.adapter = recyclerAdapter;
    }

    @Nullable
    public VH onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    public int getSpanSize() {
        return 0;
    }

    public final boolean isUiVisible() {
        return isUiVisible;
    }

    /**
     * 该item被滑进屏幕
     */
    @CallSuper
    public void onViewAttachedToWindow(VH holder) {
        isUiVisible = true;
        if (dataItemToWindows.size() > 0) {
            for (OnDataItemToWindow dataItemToWindow : dataItemToWindows) {
                dataItemToWindow.onViewAttachedToWindow(holder.getBindingAdapterPosition());
            }
        }
    }

    /**
     * 该item被滑出屏幕
     */
    @CallSuper
    public void onViewDetachedFromWindow(VH holder) {
        isUiVisible = false;
        if (dataItemToWindows.size() > 0) {
            for (OnDataItemToWindow dataItemToWindow : dataItemToWindows) {
                dataItemToWindow.onViewDetachedFromWindow(holder.getBindingAdapterPosition());
            }
        }
    }

    public void showToast(@NonNull Context context, @NonNull CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
