package com.example.anmol.democrazy.ViewPagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by joginderpal on 22-04-2017.
 */
public class ViewPagerCust extends ViewPager {


    public ViewPagerCust(Context context) {
        super(context);
    }

    public ViewPagerCust(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true,new ViewPagerTranformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapping(ev));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptHoverEvent(swapping(event));
        swapping(event);
        return intercepted;
    }


    private MotionEvent swapping(MotionEvent e){

        float x=getWidth();
        float y=getHeight();
        float newx=(e.getY()/y)*x;
        float newy=(e.getX()/x)*y;
        e.setLocation(newx,newy);
        return e;

    }

}

