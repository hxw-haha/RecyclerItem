package com.hxw.recycler.recycler_lib.listener;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/9</p>
 * <p>更改时间：2022/5/9</p>
 * <p>版本号：1</p>
 */
public abstract class OnDebounceClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 600;
    private static long lastClickTime;

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
            return;
        }
        lastClickTime = curClickTime;
        onDebounceClick(v);
    }

    protected abstract void onDebounceClick(@NonNull View view);
}
