package com.bf.portugo.model;

import java.util.List;

/*
 * @author frielb
 * Created on 10/08/2018
 */

public class QuestionCardData_Type2 extends QuestionCardData{

    private String mAnswerInput;
    private boolean mChosenAnswerCorrect;

    public QuestionCardData_Type2(Verb mVerb) {
        super(QuestionType.TYPE2, mVerb,null);
        mAnswerInput = "";
        mChosenAnswerCorrect = false;
    }

    public boolean getChosenAnswerCorrect() {
        return mChosenAnswerCorrect;
    }

    public void setChosenAnswerCorrect(boolean mChosenAnswerCorrect) {
        this.mChosenAnswerCorrect = mChosenAnswerCorrect;
    }

    public String getAnswerInput() {
        return mAnswerInput;
    }

    public void setAnswerInput(String mAnswerInput) {
        this.mAnswerInput = mAnswerInput;
    }

}
