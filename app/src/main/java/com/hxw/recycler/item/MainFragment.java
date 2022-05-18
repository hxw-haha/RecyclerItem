package com.hxw.recycler.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hxw.recycler.item.dataItem.DataItem1;
import com.hxw.recycler.item.dataItem.DataItem2;
import com.hxw.recycler.item.dataItem.DataItem3;
import com.hxw.recycler.item.databinding.FragmentMainBinding;
import com.hxw.recycler.recycler_lib.BaseRecyclerFragment;
import com.hxw.recycler.recycler_lib.RecyclerDataItem;
import com.hxw.recycler.recycler_lib.RecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/13</p>
 * <p>更改时间：2022/5/13</p>
 * <p>版本号：1</p>
 */
public class MainFragment extends BaseRecyclerFragment {
    private FragmentMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @NonNull
    @Override
    protected RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }

    @Override
    public boolean isEmptyVisible() {
        return true;
    }

    @Override
    public boolean isLoadMoreEnable() {
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.delete.setOnClickListener(this::onDeleteClick);

        getAdapter().refreshAll(new ArrayList<>());

        getAdapter().addHeaderView(LayoutInflater.from(getContext())
                .inflate(R.layout.layout_header, getRecyclerView(), false));
        getAdapter().addFooterView(LayoutInflater.from(getContext())
                .inflate(R.layout.layout_footer, getRecyclerView(), false));

    }

    private List<RecyclerDataItem<?, RecyclerHolder>> create9Item() {
        List<RecyclerDataItem<?, RecyclerHolder>> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add(new DataItem1(""));
            items.add(new DataItem2(""));
            items.add(new DataItem3(""));
        }
        return items;
    }

    public void onDeleteClick(View view) {
        getAdapter().removeItemAt(getAdapter().getDataSets().size() - 1);
    }

    @Override
    public void onEmptyRequested() {
        super.onEmptyRequested();
        getRecyclerView().postDelayed(new Runnable() {
            @Override
            public void run() {
                index = 0;
                loadSuccess(LoadStatus.EMPTY_SUCCESS, create9Item());
            }
        }, 1000);
    }

    int index = 0;

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        getRecyclerView().postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                if (index < 3) {
                    loadSuccess(LoadStatus.LOAD_MORE_FAIL, null);
                } else if (index < 6) {
                    loadSuccess(LoadStatus.LOAD_MORE_COMPLETE, create9Item());
                } else {
                    loadSuccess(LoadStatus.LOAD_MORE_END, create9Item());
                }
            }
        }, 1000);
    }
}
