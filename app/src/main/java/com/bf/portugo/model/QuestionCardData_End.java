package com.bf.portugo.model;

/*
 * @author frielb
 * Created on 10/08/2018
 */

@SuppressWarnings("unused")
public class QuestionCardData_End extends QuestionCardData{

    private String mLabelScore;
    private String mLabelMessage;

    public QuestionCardData_End(String mLabelScore, String mLabelMessage) {
        super(QuestionType.TYPEEND,null,null);
        this.mLabelScore = mLabelScore;
        this.mLabelMessage = mLabelMessage;
    }

    public String getLabelScore() {
        return mLabelScore;
    }

    public void setLabelScore(String mLabelScore) {
        this.mLabelScore = mLabelScore;
    }

    public String getLabelMessage() {
        return mLabelMessage;
    }

    public void setLabelMessage(String mLabelMessage) {
        this.mLabelMessage = mLabelMessage;
    }


}
