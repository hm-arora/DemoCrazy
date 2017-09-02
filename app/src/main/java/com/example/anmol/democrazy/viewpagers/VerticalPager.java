package com.example.anmol.democrazy.viewpagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by himanshu on 31/8/17.
 */

public class VerticalPager extends ViewPager {


    public VerticalPager(Context context) {
        super(context);
    }

    public VerticalPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
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
}