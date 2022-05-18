package com.hxw.recycler.recycler_lib;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.hxw.recycler.recycler_lib.listener.RequestEmptyListener;
import com.hxw.recycler.recycler_lib.listener.RequestLoadMoreListener;

import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/12</p>
 * <p>更改时间：2022/5/12</p>
 * <p>版本号：1</p>
 */
public abstract class BaseRecyclerFragment extends Fragment
        implements RequestLoadMoreListener, RequestEmptyListener {
    protected final MutableLiveData<Boolean> emptyRequestedLiveData = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> loadMoreRequestedLiveData = new MutableLiveData<>();
    private RecyclerAdapter recyclerAdapter;
    private int currentPage = 1;

    public enum LoadStatus {
        EMPTY_SUCCESS,
        LOAD_MORE_FAIL,
        LOAD_MORE_COMPLETE,
        LOAD_MORE_END
    }

    @NonNull
    protected abstract RecyclerView getRecyclerView();

    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 是否显示加载更多请求
     *
     * @return true
     */
    public boolean isLoadMoreEnable() {
        return true;
    }

    /**
     * 是否显示->没有更多数据
     *
     * @return true 显示
     */
    public boolean isLoadMoreEnd() {
        return true;
    }

    /**
     * 是否显示空布局
     *
     * @return true 显示
     */
    public boolean isEmptyVisible() {
        return true;
    }

    @NonNull
    public RecyclerAdapter getAdapter() {
        if (recyclerAdapter == null) {
            recyclerAdapter = new RecyclerAdapter(getRecyclerView().getContext());
        }
        return recyclerAdapter;
    }

    @NonNull
    public EmptyBuilder getEmptyBuilder() {
        return getAdapter().getEmptyBuilder();
    }

    @NonNull
    public LoadMoreBuilder getLoadMoreBuilder() {
        return getAdapter().getLoadMoreBuilder();
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoadMoreBuilder().setLoadMoreEnable(isLoadMoreEnable());
        if (isLoadMoreEnable()) {
            getLoadMoreBuilder().openLoadMore(this);
        }
        getEmptyBuilder().setEmptyVisible(isEmptyVisible());
        if (isEmptyVisible()) {
            getEmptyBuilder().setEmptyListener(this);
        }
        getRecyclerView().setAdapter(getAdapter());
    }

    @CallSuper
    @Override
    public void onEmptyRequested() {
        currentPage = 1;
        emptyRequestedLiveData.postValue(true);
    }

    @CallSuper
    @Override
    public void onLoadMoreRequested() {
        ++currentPage;
        loadMoreRequestedLiveData.postValue(true);
    }

    public final void loadSuccess(@NonNull LoadStatus status,
                                  List<RecyclerDataItem<?, RecyclerHolder>> items) {
        boolean isNonEmpty = items != null && items.size() > 0;
        switch (status) {
            case EMPTY_SUCCESS:
                if (isNonEmpty) {
                    getAdapter().refreshAll(items);
                }
                break;
            case LOAD_MORE_COMPLETE:
                if (isNonEmpty) {
                    getAdapter().addItems(items, true);
                    getAdapter().loadMoreComplete();
                } else {
                    if (currentPage > 1) {
                        --currentPage;
                    }
                    getAdapter().loadMoreFail();
                }
                break;
            case LOAD_MORE_FAIL:
                if (currentPage > 1) {
                    --currentPage;
                }
                getAdapter().loadMoreFail();
                break;
            case LOAD_MORE_END:
                if (isNonEmpty) {
                    getAdapter().addItems(items, true);
                }
                getAdapter().loadMoreEnd(!isLoadMoreEnd());
                break;
        }
    }
}
