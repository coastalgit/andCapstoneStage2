package com.bf.portugo.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;


/*
 * @author frielb
 * Created on 10/08/2018
 */
public class QuestionCardPagerAdapter extends PagerAdapter{

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

}
