package com.hxw.recycler.recycler_lib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.loadmore.LoadMoreView;
import com.hxw.recycler.recycler_lib.listener.RequestLoadMoreListener;
import com.hxw.recycler.recycler_lib.loadmore.SimpleLoadMoreView;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/9</p>
 * <p>更改时间：2022/5/9</p>
 * <p>版本号：1</p>
 */
public class LoadMoreBuilder {

    private LoadMoreView loadMoreView = new SimpleLoadMoreView();
    private RequestLoadMoreListener requestLoadMoreListener;
    private boolean nextLoadEnable = false;
    private boolean loadMoreEnable = false;
    private boolean loading = false;
    private boolean enableLoadMoreEndClick = false;
    private int preLoadNumber = 3;

    public LoadMoreBuilder openLoadMore(@NonNull RequestLoadMoreListener requestLoadMoreListener) {
        this.requestLoadMoreListener = requestLoadMoreListener;
        this.nextLoadEnable = true;
        this.loadMoreEnable = true;
        this.loading = false;
        return this;
    }

    public LoadMoreBuilder setRequestLoadMoreListener(@NonNull RequestLoadMoreListener requestLoadMoreListener) {
        this.requestLoadMoreListener = requestLoadMoreListener;
        return this;
    }

    public LoadMoreBuilder setNextLoadEnable(boolean nextLoadEnable) {
        this.nextLoadEnable = nextLoadEnable;
        return this;
    }

    public LoadMoreBuilder setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
        return this;
    }

    public LoadMoreBuilder setLoading(boolean loading) {
        this.loading = loading;
        return this;
    }

    public LoadMoreBuilder setEnableLoadMoreEndClick(boolean enableLoadMoreEndClick) {
        this.enableLoadMoreEndClick = enableLoadMoreEndClick;
        return this;
    }

    public LoadMoreBuilder setPreLoadNumber(int preLoadNumber) {
        this.preLoadNumber = preLoadNumber;
        return this;
    }

    public LoadMoreBuilder setLoadMoreView(@NonNull LoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
        return this;
    }

    @NonNull
    public LoadMoreView getLoadMoreView() {
        return loadMoreView;
    }

    @Nullable
    public RequestLoadMoreListener getRequestLoadMoreListener() {
        return requestLoadMoreListener;
    }

    public boolean isNextLoadEnable() {
        return nextLoadEnable;
    }

    public boolean isLoadMoreEnable() {
        return loadMoreEnable;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isEnableLoadMoreEndClick() {
        return enableLoadMoreEndClick;
    }

    public int getPreLoadNumber() {
        return preLoadNumber;
    }

}
