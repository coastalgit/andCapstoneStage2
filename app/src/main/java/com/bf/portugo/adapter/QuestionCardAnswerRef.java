package com.bf.portugo.adapter;

import android.widget.FrameLayout;
import android.widget.TextView;

/*
 * @author frielb
 * Created on 10/08/2018
 */
@SuppressWarnings("WeakerAccess")
public class QuestionCardAnswerRef {

    public FrameLayout getCardFrame() {
        return cardFrame;
    }

    public void setCardFrame(FrameLayout cardFrame) {
        this.cardFrame = cardFrame;
    }

    public TextView getCardText() {
        return cardText;
    }

    public void setCardText(TextView cardText) {
        this.cardText = cardText;
    }

    private FrameLayout cardFrame;
    private TextView cardText;

    public QuestionCardAnswerRef(FrameLayout cardFrame, TextView cardText) {
        this.cardFrame = cardFrame;
        this.cardText = cardText;
    }
}
