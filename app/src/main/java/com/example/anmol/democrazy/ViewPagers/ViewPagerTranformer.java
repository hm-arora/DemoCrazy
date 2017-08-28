package com.example.anmol.democrazy.ViewPagers;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by joginderpal on 22-04-2017.
 */
public class ViewPagerTranformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;


    @Override
    public void transformPage(View page, float position) {

        page.setTranslationX(page.getWidth()*-1*position);

        if (position<-1){
            page.setAlpha(0);
        }
        else if (position<=0){
            page.setTranslationY(page.getHeight()*position);
        }

        else if (position<=1f){
            final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1f - 0.25f* Math.abs(position));
            page.setAlpha(1-position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }
        else{
            page.setAlpha(0);
        }

    }
}
