package com.bf.portugo.model;

/*
 * @author frielb
 * Created on 10/08/2018
 */
@SuppressWarnings("unused")
public class QuestionCardData_Type2 extends QuestionCardData{

    private String mAnswerInput;
    private boolean mAnswerAlreadyChecked;

    public QuestionCardData_Type2(Verb mVerb) {
        super(QuestionType.TYPE2, mVerb,null);
        mAnswerInput = "";
        mAnswerAlreadyChecked = false;
    }

    public boolean getAnswerAlreadyChecked() {
        return mAnswerAlreadyChecked;
    }

    public void setAnswerAlreadyChecked(boolean mAnswerAlreadyChecked) {
        this.mAnswerAlreadyChecked = mAnswerAlreadyChecked;
    }

    public String getAnswerInput() {
        return mAnswerInput;
    }

    public void setAnswerInput(String mAnswerInput) {
        this.mAnswerInput = mAnswerInput;
    }

}
