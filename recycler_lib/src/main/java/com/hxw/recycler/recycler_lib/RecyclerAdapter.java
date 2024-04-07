package com.hxw.recycler.recycler_lib;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.hxw.recycler.recycler_lib.empty.EmptyView;
import com.hxw.recycler.recycler_lib.listener.OnDebounceClickListener;
import com.hxw.recycler.recycler_lib.loadmore.LoadMoreView;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/7</p>
 * <p>更改时间：2022/5/7</p>
 * <p>版本号：1</p>
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final LayoutInflater inflater;
    private WeakReference<RecyclerView> recyclerViewRef = null;
    private final List<RecyclerDataItem<?, RecyclerHolder>> dataSets = new ArrayList<>();
    private final SparseIntArray typePositions = new SparseIntArray();

    private int BASE_ITEM_TYPE_HEADER = 0x10000000;
    private int BASE_ITEM_TYPE_FOOTER = 0x20000000;
    private final SparseArray<View> headers = new SparseArray<>();
    private final SparseArray<View> footers = new SparseArray<>();

    public static final int BASE_ITEM_TYPE_LOADING = 0x30000000;
    private LoadMoreBuilder loadMoreBuilder = new LoadMoreBuilder();

    public static final int BASE_ITEM_TYPE_EMPTY = 0x40000000;
    private EmptyBuilder emptyBuilder = new EmptyBuilder();

    public static final int BASE_ITEM_TYPE_DEFAULT = 0x50000000;

    private AnimationBuilder animationBuilder = new AnimationBuilder();

    public RecyclerAdapter(@NonNull Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    public List<RecyclerDataItem<?, RecyclerHolder>> getDataSets() {
        return Collections.unmodifiableList(dataSets);
    }

    public void addHeaderView(@NonNull View view) {
        //HeaderView 没被添加
        if (headers.indexOfValue(view) < 0) {
            headers.put(BASE_ITEM_TYPE_HEADER++, view);
            notifyItemInserted(headers.size() - 1);
        }
    }

    public void removeHeaderView(@NonNull View view) {
        int indexOfValue = headers.indexOfValue(view);
        if (indexOfValue < 0) {
            return;
        }
        headers.removeAt(indexOfValue);
        notifyItemRemoved(indexOfValue);
    }

    public void addFooterView(@NonNull View view) {
        //FooterView 没被添加
        if (footers.indexOfValue(view) < 0) {
            footers.put(BASE_ITEM_TYPE_FOOTER++, view);
            notifyItemInserted(getItemTotalCount());
        }
    }

    public void removeFooterView(View view) {
        int indexOfValue = footers.indexOfValue(view);
        if (indexOfValue < 0) {
            return;
        }
        footers.removeAt(indexOfValue);
        //position代表的是在列表中分位置
        notifyItemRemoved(indexOfValue + getHeaderSize() + getOriginalItemSize());
    }

    public int getHeaderSize() {
        return headers.size();
    }

    public int getFooterSize() {
        return footers.size();
    }

    public int getOriginalItemSize() {
        return dataSets.size();
    }

    /**
     * 在指定为上添加RecyclerDataItem
     */
    public void addItemAt(int index, @NonNull RecyclerDataItem<?, RecyclerHolder> dataItem,
                          boolean notify) {
        setEmptyStatus(EmptyView.STATUS_DEFAULT);
        if (index >= 0) {
            dataSets.add(index, dataItem);
        } else {
            dataSets.add(dataItem);
        }

        int notifyPos = index >= 0 ? index : dataSets.size() - 1;
        if (notify) {
            notifyItemInserted(getHeaderSize() + notifyPos);
        }
        dataItem.setAdapter(this);
    }

    /**
     * 往现有集合的尾部逐年items集合
     */
    public void addItems(@NonNull List<RecyclerDataItem<?, RecyclerHolder>> items, boolean notify) {
        setEmptyStatus(EmptyView.STATUS_DEFAULT);
        int start = dataSets.size();
        for (RecyclerDataItem<?, RecyclerHolder> dataItem : items) {
            dataSets.add(dataItem);
            dataItem.setAdapter(this);
        }
        if (notify) {
            notifyItemRangeInserted(getHeaderSize() + start, items.size());
        }
    }

    /**
     * 从指定位置上移除item
     */
    @SuppressLint("NotifyDataSetChanged")
    public RecyclerDataItem<?, RecyclerHolder> removeItemAt(int index) {
        if (index >= 0 && index < dataSets.size()) {
            RecyclerDataItem<?, RecyclerHolder> remove = dataSets.remove(index);
            notifyItemRemoved(getHeaderSize() + index);

            if (dataSets.size() == 0) {
                setEmptyStatus(EmptyView.STATUS_EMPTY);
                notifyDataSetChanged();
            }
            return remove;
        } else {
            return null;
        }
    }

    /**
     * 移除指定item
     */
    public void removeItem(@NonNull RecyclerDataItem<?, RecyclerHolder> dataItem) {
        int index = dataSets.indexOf(dataItem);
        removeItemAt(index);
    }

    /**
     * 指定刷新 某个item的数据
     */
    public void refreshItem(@NonNull RecyclerDataItem<?, RecyclerHolder> dataItem) {
        int indexOf = dataSets.indexOf(dataItem);
        notifyItemChanged(getHeaderSize() + indexOf);
    }

    /**
     * 刷新全部数据
     *
     * @param dataItems
     */
    @SuppressLint("NotifyDataSetChanged")
    public void refreshAll(@NonNull List<RecyclerDataItem<?, RecyclerHolder>> dataItems) {
        dataSets.clear();
        setEmptyStatus(dataItems.size() > 0 ? EmptyView.STATUS_DEFAULT : EmptyView.STATUS_EMPTY);
        for (RecyclerDataItem<?, RecyclerHolder> dataItem : dataItems) {
            dataSets.add(dataItem);
            dataItem.setAdapter(this);
        }
        animationBuilder.setLastPosition(-1);
        loadMoreBuilder.getLoadMoreView().setLoadMoreEndGone(false);
        loadMoreBuilder.setNextLoadEnable(true);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearItems() {
        dataSets.clear();
        setEmptyStatus(EmptyView.STATUS_EMPTY);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (emptyBuilder.isUseEmpty()) {
            return BASE_ITEM_TYPE_EMPTY;
        }
        if (position == getItemTotalCount()) {
            return BASE_ITEM_TYPE_LOADING;
        }
        if (isHeaderPosition(position)) {
            return headers.keyAt(position);
        }
        if (isFooterPosition(position)) {
            int footerPosition = position - getHeaderSize() - getOriginalItemSize();
            return footers.keyAt(footerPosition);
        }
        int itemPosition = position - getHeaderSize();
        if (itemPosition >= 0 && itemPosition < getOriginalItemSize()) {
            RecyclerDataItem<?, RecyclerHolder> dataItem = dataSets.get(itemPosition);
            int type = dataItem.getClass().hashCode();
            typePositions.put(type, itemPosition);
            return type;
        }
        // 如果计算出的 itemPosition 不在有效范围内，返回一个默认值
        return BASE_ITEM_TYPE_DEFAULT;
    }

    private RecyclerHolder getLoadingView(@NonNull ViewGroup parent) {
        View view = inflater.inflate(loadMoreBuilder.getLoadMoreView().getItemLayoutRes(),
                parent, false);
        final RecyclerHolder loadMoreHolder = new RecyclerHolder(view) {
        };
        loadMoreHolder.itemView.setOnClickListener(new OnDebounceClickListener() {
            @Override
            protected void onDebounceClick(@NonNull View view) {
                if (loadMoreBuilder.getLoadMoreView().getLoadMoreStatus() == LoadMoreView.STATUS_FAIL) {
                    notifyLoadMoreToLoading();
                }
                if (loadMoreBuilder.isEnableLoadMoreEndClick()
                        && loadMoreBuilder.getLoadMoreView().getLoadMoreStatus() == LoadMoreView.STATUS_END) {
                    notifyLoadMoreToLoading();
                }
            }
        });
        loadMoreBuilder.getLoadMoreView().setHolder(loadMoreHolder);
        return loadMoreHolder;
    }

    private RecyclerHolder getEmptyView(@NonNull ViewGroup parent) {
        View view = inflater.inflate(emptyBuilder.getEmptyView().getItemLayoutRes(),
                parent, false);
        final RecyclerHolder emptyHolder = new RecyclerHolder(view) {
        };
        emptyBuilder.getEmptyView().setHolder(emptyHolder);
        emptyBuilder.getEmptyView().getEmptyView(emptyHolder).setOnClickListener(new OnDebounceClickListener() {
            @Override
            protected void onDebounceClick(@NonNull View view) {
                if (emptyBuilder.getEmptyListener() != null) {
                    setEmptyStatus(EmptyView.STATUS_LOADING);
                    emptyBuilder.getEmptyListener().onEmptyRequested();
                }
            }
        });
        return emptyHolder;
    }

    private RecyclerHolder getItemDefaultView(@NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_item_default, parent, false);
        return new RecyclerHolder(view) {
        };
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BASE_ITEM_TYPE_EMPTY) {
            return getEmptyView(parent);
        }
        if (viewType == BASE_ITEM_TYPE_LOADING) {
            return getLoadingView(parent);
        }
        if (headers.indexOfKey(viewType) >= 0) {
            View view = headers.get(viewType);
            return new RecyclerHolder(view) {
            };
        }
        if (footers.indexOfKey(viewType) >= 0) {
            View view = footers.get(viewType);
            return new RecyclerHolder(view) {
            };
        }
        if (viewType == BASE_ITEM_TYPE_DEFAULT) {
            // 处理默认类型
            return getItemDefaultView(parent);
        }
        int position = typePositions.get(viewType);
        RecyclerDataItem<?, RecyclerHolder> dataItem = dataSets.get(position);
        RecyclerHolder vh = dataItem.onCreateViewHolder(parent);
        if (vh != null) {
            return vh;
        }
        View view = dataItem.getItemView(parent);
        if (view == null) {
            int layoutRes = dataItem.getItemLayoutRes();
            if (layoutRes < 0) {
                throw new RuntimeException("dataItem:" + dataItem.getClass().getName() + " must override getItemView or getItemLayoutRes");
            }
            view = inflater.inflate(layoutRes, parent, false);
        }
        return createViewHolderInternal(dataItem, view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        if (emptyBuilder.isUseEmpty()) {
            return;
        }
        autoLoadMore(position);
        if (position == getItemTotalCount()) {
            loadMoreConvert();
            return;
        }
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        int itemPosition = position - getHeaderSize();
        if (itemPosition >= 0 && itemPosition < getOriginalItemSize()) {
            RecyclerDataItem<?, RecyclerHolder> dataItem = getItem(itemPosition);
            if (dataItem != null) {
                dataItem.onBindData(holder, itemPosition);
            }
        } else if (holder.getItemViewType() == BASE_ITEM_TYPE_DEFAULT) {
            // 处理默认类型的逻辑
        }
    }


    private RecyclerHolder createViewHolderInternal(@NonNull RecyclerDataItem<?, RecyclerHolder> dataItem,
                                                    @NonNull View view) {
        Type superclass = dataItem.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) superclass).getActualTypeArguments();
            for (Type argument : arguments) {
                if (argument instanceof Class<?>) {
                    if (RecyclerHolder.class.isAssignableFrom((Class<?>) argument)) {
                        try {
                            return (RecyclerHolder) ((Class<?>) argument).getConstructor(View.class).newInstance(view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return new RecyclerHolder(view) {
        };
    }

    private boolean isFooterPosition(int position) {
        return position >= getHeaderSize() + getOriginalItemSize() && position < getItemTotalCount();
    }

    private boolean isHeaderPosition(int position) {
        return position < headers.size();
    }

    public int getItemTotalCount() {
        return dataSets.size() + getHeaderSize() + getFooterSize();
    }

    @Override
    public int getItemCount() {
        if (emptyBuilder.isUseEmpty()) {
            return 1;
        }
        return dataSets.size() + getHeaderSize() + getFooterSize() + getLoadMoreViewCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerViewRef = new WeakReference<>(recyclerView);
        // 为列表上的item 适配网格布局
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {
                    if (isHeaderPosition(position) || isFooterPosition(position)) {
                        return spanCount;
                    }
                    int itemPosition = position - getHeaderSize();
                    if (itemPosition < dataSets.size()) {
                        RecyclerDataItem<?, RecyclerHolder> dataItem = getItem(itemPosition);
                        if (dataItem != null) {
                            int spanSize = dataItem.getSpanSize();
                            return spanSize <= 0 ? spanCount : spanSize;
                        }
                    }
                    return spanCount;
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (recyclerViewRef != null) {
            recyclerViewRef.clear();
            recyclerViewRef = null;
        }
    }

    @Nullable
    public RecyclerView getAttachRecyclerView() {
        if (recyclerViewRef != null) {
            return recyclerViewRef.get();
        }
        return null;
    }

    @Nullable
    public RecyclerDataItem<?, RecyclerHolder> getItem(int position) {
        if (position < 0 || position >= dataSets.size()) {
            return null;
        }
        return dataSets.get(position);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerHolder holder) {
        super.onViewAttachedToWindow(holder);
        RecyclerView recyclerView = getAttachRecyclerView();
        if (recyclerView != null) {
            //瀑布流的item占比适配
            int position = recyclerView.getChildAdapterPosition(holder.itemView);
            boolean isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position);
            int itemPosition = position - getHeaderSize();
            RecyclerDataItem<?, RecyclerHolder> dataItem = getItem(itemPosition);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if (holder.getItemViewType() == BASE_ITEM_TYPE_EMPTY
                        || holder.getItemViewType() == BASE_ITEM_TYPE_LOADING
                        || isHeaderFooter) {
                    ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
                    return;
                }
                if (dataItem != null) {
                    int spanSize = dataItem.getSpanSize();
                    if (manager != null && (spanSize >= manager.getSpanCount() || spanSize <= 0)) {
                        ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
                    }
                }
            }
            addAnimation(holder);
            if (dataItem != null) {
                dataItem.onViewAttachedToWindow(holder);
            }
        }
    }

    /**
     * add animation when you want to show time
     *
     * @param holder
     */
    private void addAnimation(@NonNull RecyclerView.ViewHolder holder) {
        if (!animationBuilder.isOpenAnimationEnable()) {
            return;
        }
        if (!animationBuilder.isFirstOnlyEnable()
                || holder.getLayoutPosition() > animationBuilder.getLastPosition()) {
            for (Animator anim : animationBuilder.getItemAnimation()
                    .getAnimators(holder.itemView)) {
                anim.setDuration(animationBuilder.getDuration()).start();
                anim.setInterpolator(animationBuilder.getInterpolator());
            }
            animationBuilder.setLastPosition(holder.getLayoutPosition());
        }
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int position = holder.getBindingAdapterPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        int itemPosition = position - getHeaderSize();
        RecyclerDataItem<?, RecyclerHolder> dataItem = getItem(itemPosition);
        if (dataItem == null) {
            return;
        }
        dataItem.onViewDetachedFromWindow(holder);
        if (holder.getDataBinding() != null) {
            holder.getDataBinding().unbind();
        }
    }

    private void autoLoadMore(int position) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        if (position < getItemCount() - loadMoreBuilder.getPreLoadNumber()) {
            return;
        }
        if (loadMoreBuilder.getLoadMoreView().getLoadMoreStatus() != LoadMoreView.STATUS_DEFAULT) {
            return;
        }
        loadMoreBuilder.getLoadMoreView().setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
        if (!loadMoreBuilder.isLoading()) {
            loadMoreBuilder.setLoading(true);
            if (getAttachRecyclerView() != null) {
                getAttachRecyclerView().post(new Runnable() {
                    @Override
                    public void run() {
                        if (loadMoreBuilder.getRequestLoadMoreListener() != null) {
                            loadMoreBuilder.getRequestLoadMoreListener().onLoadMoreRequested();
                        }
                    }
                });
            } else {
                if (loadMoreBuilder.getRequestLoadMoreListener() != null) {
                    loadMoreBuilder.getRequestLoadMoreListener().onLoadMoreRequested();
                }
            }
        }
    }

    public void setLoadMoreBuilder(@NonNull LoadMoreBuilder loadMoreBuilder) {
        this.loadMoreBuilder = loadMoreBuilder;
    }

    public void setEmptyBuilder(@NonNull EmptyBuilder emptyBuilder) {
        this.emptyBuilder = emptyBuilder;
    }

    public void setAnimationBuilder(@NonNull AnimationBuilder animationBuilder) {
        this.animationBuilder = animationBuilder;
    }

    /**
     * Returns LoadMoreBuilder.
     */
    @NonNull
    public LoadMoreBuilder getLoadMoreBuilder() {
        return loadMoreBuilder;
    }

    @NonNull
    public EmptyBuilder getEmptyBuilder() {
        return emptyBuilder;
    }

    /**
     * Load more view count
     *
     * @return 0 or 1
     */
    public int getLoadMoreViewCount() {
        if (!loadMoreBuilder.isLoadMoreEnable()) {
            return 0;
        }
        if (!loadMoreBuilder.isNextLoadEnable() && loadMoreBuilder.getLoadMoreView().isLoadEndMoreGone()) {
            return 0;
        }
        if (dataSets.size() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * The notification starts the callback and loads more
     */
    public void notifyLoadMoreToLoading() {
        if (loadMoreBuilder.getLoadMoreView().getLoadMoreStatus() == LoadMoreView.STATUS_LOADING
                || loadMoreBuilder.getLoadMoreView().getLoadMoreStatus() == LoadMoreView.STATUS_END) {
            return;
        }
        loadMoreBuilder.getLoadMoreView().setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
        if (loadMoreBuilder.getRequestLoadMoreListener() != null) {
            loadMoreBuilder.getRequestLoadMoreListener().onLoadMoreRequested();
        }
        loadMoreConvert();
    }

    /**
     * Refresh end, no more data
     */
    public void loadMoreEnd(boolean gone) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        setEmptyStatus(EmptyView.STATUS_DEFAULT);
        loadMoreBuilder.setLoading(false);
        loadMoreBuilder.setNextLoadEnable(false);
        loadMoreBuilder.getLoadMoreView().setLoadMoreEndGone(gone);
        if (gone) {
            loadMoreBuilder.getLoadMoreView().setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        } else {
            loadMoreBuilder.getLoadMoreView().setLoadMoreStatus(LoadMoreView.STATUS_END);
        }
        loadMoreConvert();
    }

    /**
     * Refresh complete
     */
    public void loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        setEmptyStatus(EmptyView.STATUS_DEFAULT);
        loadMoreBuilder.setLoading(false);
        loadMoreBuilder.setNextLoadEnable(true);
        loadMoreBuilder.getLoadMoreView().setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        loadMoreConvert();
    }

    /**
     * Refresh failed
     */
    public void loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        setEmptyStatus(EmptyView.STATUS_DEFAULT);
        loadMoreBuilder.setLoading(false);
        loadMoreBuilder.getLoadMoreView().setLoadMoreStatus(LoadMoreView.STATUS_FAIL);
        loadMoreConvert();
    }

    private void loadMoreConvert() {
        if (loadMoreBuilder.getLoadMoreView().isHolderEmpty()) {
            final RecyclerView attachRecyclerView = getAttachRecyclerView();
            if (attachRecyclerView != null) {
                onCreateViewHolder(attachRecyclerView, BASE_ITEM_TYPE_LOADING);
            }
        }
        loadMoreBuilder.getLoadMoreView().convert();
    }

    public void setEmptyStatus(int emptyStatus) {
        if (emptyStatus == EmptyView.STATUS_EMPTY) {
            if (getDataSets().size() == 0) {
                emptyBuilder.setEmptyStatus(EmptyView.STATUS_EMPTY);
            } else {
                emptyBuilder.setEmptyStatus(EmptyView.STATUS_DEFAULT);
            }
        } else {
            emptyBuilder.setEmptyStatus(emptyStatus);
        }
    }

}
