package com.hxw.recycler.recycler_lib.listener;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/7/22</p>
 * <p>更改时间：2022/7/22</p>
 * <p>版本号：1</p>
 */
public interface OnDataItemToWindow {
    /**
     * 该item被滑进屏幕
     */
    void onViewAttachedToWindow(int position);

    /**
     * 该item被滑出屏幕
     */
    void onViewDetachedFromWindow(int position);
}
