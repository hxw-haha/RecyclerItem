package com.hxw.recycler.recycler_lib.empty;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.R;
import com.hxw.recycler.recycler_lib.RecyclerHolder;
import com.hxw.recycler.recycler_lib.databinding.LayoutEmptySimpleBinding;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/9</p>
 * <p>更改时间：2022/5/9</p>
 * <p>版本号：1</p>
 */
public class SimpleEmptyView extends EmptyView {

    @Override
    public int getItemLayoutRes() {
        return R.layout.layout_empty_simple;
    }

    @NonNull
    @Override
    public View getEmptyView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutEmptySimpleBinding) {
            return ((LayoutEmptySimpleBinding) holder.getDataBinding()).emptyContent;
        }
        throw new RuntimeException("SimpleEmptyView RuntimeException");
    }

    @Nullable
    @Override
    public View getLoadingView(@NonNull RecyclerHolder holder) {
        if (holder.getDataBinding() instanceof LayoutEmptySimpleBinding) {
            return ((LayoutEmptySimpleBinding) holder.getDataBinding()).emptyLoading;
        }
        return null;
    }
}
