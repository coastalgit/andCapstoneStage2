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
public abstract class QuestionCardData {

    public enum QuestionType{
        TYPE1,TYPE2,TYPEEND
    }

    private QuestionType mQuestionType;
    private Verb mVerb;
    private List<Verb> mWrongAnswers;
    private boolean mChosenAnswerCorrect;

    public QuestionCardData(QuestionType questiontype, Verb mVerb, List<Verb> mWrongAnswers) {
        this.mQuestionType = questiontype;
        this.mVerb = mVerb;
        this.mWrongAnswers = mWrongAnswers;
        mChosenAnswerCorrect = false;
    }

    public QuestionType getQuestionType() {
        return mQuestionType;
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

    public boolean getChosenAnswerCorrect() {
        return mChosenAnswerCorrect;
    }

    public void setChosenAnswerCorrect(boolean mChosenAnswerCorrect) {
        this.mChosenAnswerCorrect = mChosenAnswerCorrect;
    }

}
