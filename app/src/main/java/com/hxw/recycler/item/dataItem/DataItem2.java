package com.hxw.recycler.item.dataItem;

import androidx.annotation.NonNull;

import com.hxw.recycler.item.R;
import com.hxw.recycler.recycler_lib.RecyclerDataItem;
import com.hxw.recycler.recycler_lib.RecyclerHolder;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/7</p>
 * <p>更改时间：2022/5/7</p>
 * <p>版本号：1</p>
 */
public class DataItem2 extends RecyclerDataItem<String, RecyclerHolder> {
    public DataItem2(String s) {
        super(s);
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.layout_item2;
    }


    @Override
    protected void onBindData(@NonNull RecyclerHolder holder, int position) {
    }
}
