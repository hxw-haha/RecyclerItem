package com.hxw.recycler.recycler_lib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.empty.EmptyView;
import com.hxw.recycler.recycler_lib.empty.SimpleEmptyView;
import com.hxw.recycler.recycler_lib.listener.RequestEmptyListener;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/9</p>
 * <p>更改时间：2022/5/9</p>
 * <p>版本号：1</p>
 */
public class EmptyBuilder {
    private EmptyView emptyView = new SimpleEmptyView();
    private RequestEmptyListener emptyListener;
    /**
     * 设置是否显示空布局
     */
    private boolean isEmptyVisible = false;

    public void setEmptyStatus(int emptyStatus) {
        emptyView.setEmptyStatus(emptyStatus);
        emptyView.convert();
    }

    public EmptyBuilder setEmptyView(@NonNull EmptyView emptyView) {
        this.emptyView = emptyView;
        setEmptyStatus(EmptyView.STATUS_LOADING);
        return this;
    }

    public EmptyBuilder setEmptyVisible(boolean emptyVisible) {
        isEmptyVisible = emptyVisible;
        return this;
    }

    public EmptyBuilder setEmptyListener(@NonNull RequestEmptyListener emptyListener) {
        this.emptyListener = emptyListener;
        return this;
    }

    @NonNull
    public EmptyView getEmptyView() {
        return emptyView;
    }

    public boolean isUseEmpty() {
        return emptyView.getEmptyStatus() != EmptyView.STATUS_DEFAULT
                && isEmptyVisible;
    }

    @Nullable
    public RequestEmptyListener getEmptyListener() {
        return emptyListener;
    }
}
