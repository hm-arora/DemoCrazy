package com.example.anmol.democrazy.viewpagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.example.anmol.democrazy.util.ScrollerCustomDuration;

import java.lang.reflect.Field;

/**
 * Created by himanshu on 31/8/17.
 */

public class VerticalPager extends ViewPager {


    public VerticalPager(Context context) {
        super(context);
        postInitViewPager();
    }

    public VerticalPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
        postInitViewPager();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(swapping(event));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptHoverEvent(swapping(event));
        swapping(event);
        return intercepted;
    }

    private MotionEvent swapping(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float newValueX = (event.getY() / height) * width;
        float newValueY = (event.getX() / width) * height;

        event.setLocation(newValueX, newValueY);
        return event;
    }

    private ScrollerCustomDuration mScroller = null;

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }
}