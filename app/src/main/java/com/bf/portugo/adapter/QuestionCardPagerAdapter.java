package com.bf.portugo.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.View;

import com.bf.portugo.model.QuestionCard;

import java.util.List;


/*
 * @author frielb
 * Created on 10/08/2018
 */
public class QuestionCardPagerAdapter extends PagerAdapter{

    private List<CardView> mViews;
    private List<QuestionCard> mCards;
    //private float mBaseElevation;


    public QuestionCardPagerAdapter(List<CardView> mViews, List<QuestionCard> mCards) {
        this.mViews = mViews;
        this.mCards = mCards;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

}
