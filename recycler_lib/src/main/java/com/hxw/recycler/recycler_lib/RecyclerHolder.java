package com.hxw.recycler.recycler_lib;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/7</p>
 * <p>更改时间：2022/5/7</p>
 * <p>版本号：1</p>
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding dataBinding;

    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        try {
            dataBinding = DataBindingUtil.bind(itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public ViewDataBinding getDataBinding() {
        return dataBinding;
    }

    public void bindData(int variableId, @Nullable Object value) {
        if (dataBinding != null) {
            dataBinding.setVariable(variableId, value);
        }
    }
}
