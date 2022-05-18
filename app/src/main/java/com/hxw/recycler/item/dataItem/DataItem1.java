package com.hxw.recycler.item.dataItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.item.R;
import com.hxw.recycler.item.databinding.LayoutItem1Binding;
import com.hxw.recycler.recycler_lib.RecyclerDataItem;
import com.hxw.recycler.recycler_lib.RecyclerHolder;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/7</p>
 * <p>更改时间：2022/5/7</p>
 * <p>版本号：1</p>
 */
public class DataItem1 extends RecyclerDataItem<String, RecyclerHolder> {
    public DataItem1(String s) {
        super(s);
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.layout_item1;
    }

    @Override
    protected void onBindData(@NonNull RecyclerHolder holder, int position) {
        if (holder.getDataBinding() instanceof LayoutItem1Binding) {
            LayoutItem1Binding dataBinding = (LayoutItem1Binding) holder.getDataBinding();
            dataBinding.item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast(dataBinding.item1.getContext(), dataBinding.item1.getText());
                }
            });
        }
    }

    @Nullable
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).
                inflate(getItemLayoutRes(), parent, false);
        return new DataItem1Holder(inflate);
    }

    static class DataItem1Holder extends RecyclerHolder {
        public DataItem1Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
