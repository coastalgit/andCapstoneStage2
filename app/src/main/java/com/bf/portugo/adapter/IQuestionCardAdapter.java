package com.bf.portugo.adapter;

import android.support.v7.widget.CardView;

/*
 * @author frielb
 * Created on 10/08/2018
 */
public interface IQuestionCardAdapter {

    // TODO: 10/08/2018 Tidy up

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();

}
