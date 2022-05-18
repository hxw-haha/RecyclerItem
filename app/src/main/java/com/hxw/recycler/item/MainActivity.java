package com.hxw.recycler.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.hxw.recycler.item.dataItem.DataItem1;
import com.hxw.recycler.item.dataItem.DataItem2;
import com.hxw.recycler.item.dataItem.DataItem3;
import com.hxw.recycler.recycler_lib.RecyclerAdapter;
import com.hxw.recycler.recycler_lib.RecyclerDataItem;
import com.hxw.recycler.recycler_lib.RecyclerHolder;
import com.hxw.recycler.recycler_lib.listener.RequestEmptyListener;
import com.hxw.recycler.recycler_lib.listener.RequestLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainFragment()).commit();

    }

}