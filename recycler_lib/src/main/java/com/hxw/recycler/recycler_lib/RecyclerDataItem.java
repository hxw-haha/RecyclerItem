package com.hxw.recycler.recycler_lib;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/7</p>
 * <p>更改时间：2022/5/7</p>
 * <p>版本号：1</p>
 */
public abstract class RecyclerDataItem<DATA, VH extends RecyclerHolder> {
    public final DATA mData;
    public RecyclerAdapter adapter;

    protected RecyclerDataItem(DATA data) {
        this.mData = data;
    }

    /**
     * 绑定数据
     */
    protected abstract void onBindData(VH holder, int position);

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

    /**
     * 该item被滑进屏幕
     */
    public void onViewAttachedToWindow(VH holder) {

    }

    /**
     * 该item被滑出屏幕
     */
    public void onViewDetachedFromWindow(VH holder) {

    }

    public void showToast(@NonNull Context context, @NonNull CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
