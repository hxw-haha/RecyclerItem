package com.hxw.recycler.recycler_lib;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.hxw.recycler.recycler_lib.animation.AlphaInAnimation;
import com.hxw.recycler.recycler_lib.animation.BaseAnimation;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2022/5/18</p>
 * <p>更改时间：2022/5/18</p>
 * <p>版本号：1</p>
 */
public class AnimationBuilder {
    private BaseAnimation itemAnimation = new AlphaInAnimation();
    private Interpolator interpolator = new LinearInterpolator();
    private boolean openAnimationEnable = true;
    private boolean firstOnlyEnable = true;
    private int duration = 300;
    private int lastPosition = -1;

    /**
     * To open the animation when loading
     */
    public void openLoadAnimation() {
        this.openAnimationEnable = true;
    }

    /**
     * Set Custom ObjectAnimator
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(@NonNull BaseAnimation animation) {
        this.openAnimationEnable = true;
        this.itemAnimation = animation;
    }

    /**
     * To close the animation when loading
     */
    public void closeLoadAnimation() {
        this.openAnimationEnable = false;
    }

    public AnimationBuilder setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public AnimationBuilder setItemAnimation(BaseAnimation itemAnimation) {
        this.itemAnimation = itemAnimation;
        return this;
    }

    public AnimationBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public AnimationBuilder setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
        return this;
    }

    public AnimationBuilder setFirstOnlyEnable(boolean firstOnlyEnable) {
        this.firstOnlyEnable = firstOnlyEnable;
        return this;
    }

    public AnimationBuilder setOpenAnimationEnable(boolean openAnimationEnable) {
        this.openAnimationEnable = openAnimationEnable;
        return this;
    }

    public BaseAnimation getItemAnimation() {
        return itemAnimation;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public int getDuration() {
        return duration;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public boolean isOpenAnimationEnable() {
        return openAnimationEnable;
    }

    public boolean isFirstOnlyEnable() {
        return firstOnlyEnable;
    }
}
