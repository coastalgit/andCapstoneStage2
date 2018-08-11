package com.bf.portugo.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import java.util.List;

/*
 * @author frielb
 * Created on 10/08/2018
 */
//public class QuestionCardData extends CardView {
public class QuestionCardData {

    private Verb mVerb;
    private List<Verb> mWrongAnswers;

/*
    public QuestionCardData(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, Verb mVerb, List<Verb> mWrongAnswers) {
        super(context, attrs, defStyleAttr);
        this.mVerb = mVerb;
        this.mWrongAnswers = mWrongAnswers;
    }
*/

    public QuestionCardData(Verb mVerb, List<Verb> mWrongAnswers) {
        this.mVerb = mVerb;
        this.mWrongAnswers = mWrongAnswers;
    }


    public Verb getVerb() {
        return mVerb;
    }

    public void setVerb(Verb mVerb) {
        this.mVerb = mVerb;
    }

    public List<Verb> getWrongAnswers() {
        return mWrongAnswers;
    }

    public void setWrongAnswers(List<Verb> mWrongAnswers) {
        this.mWrongAnswers = mWrongAnswers;
    }

}
