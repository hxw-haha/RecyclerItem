package com.hxw.recycler.recycler_lib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hxw.recycler.recycler_lib.listener.RequestLoadMoreListener;
import com.hxw.recycler.recycler_lib.loadmore.LoadMoreDelegate;
import com.hxw.recycler.recycler_lib.loadmore.LoadMoreDownView;
import com.hxw.recycler.recycler_lib.loadmore.LoadMoreView;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/9</p>
 * <p>更改时间：2022/5/9</p>
 * <p>版本号：1</p>
 */
public class LoadMoreBuilder {

    private LoadMoreDelegate loadMoreDelegate = new LoadMoreView();
    private RequestLoadMoreListener requestLoadMoreListener;
    private boolean nextLoadEnable = false;
    private boolean loadMoreEnable = false;
    private boolean loading = false;
    private boolean enableLoadMoreEndClick = false;
    private int preLoadNumber = 3;
    private boolean pullDownLoading = false;

    /**
     * 开启上拉加载更多
     *
     * @param requestLoadMoreListener
     * @return
     */
    public LoadMoreBuilder openLoadMore(@NonNull RequestLoadMoreListener requestLoadMoreListener) {
        this.loadMoreDelegate = new LoadMoreView();
        this.requestLoadMoreListener = requestLoadMoreListener;
        this.nextLoadEnable = true;
        this.loadMoreEnable = true;
        this.loading = false;
        this.pullDownLoading = false;
        return this;
    }

    /**
     * 开启下拉加载更多
     *
     * @param requestLoadMoreListener
     * @return
     */
    public LoadMoreBuilder openDownLoadMore(@NonNull RequestLoadMoreListener requestLoadMoreListener) {
        this.loadMoreDelegate = new LoadMoreDownView();
        this.requestLoadMoreListener = requestLoadMoreListener;
        this.nextLoadEnable = true;
        this.loadMoreEnable = false;
        this.loading = false;
        this.pullDownLoading = true;
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

    public LoadMoreBuilder setLoadMoreView(@NonNull LoadMoreDelegate loadMoreDelegate) {
        this.loadMoreDelegate = loadMoreDelegate;
        return this;
    }

    @NonNull
    public LoadMoreDelegate getLoadMoreView() {
        return loadMoreDelegate;
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

    public boolean isPullDownLoading() {
        return pullDownLoading;
    }
}
