package com.bf.portugo.model;

import java.util.List;

/*
 * @author frielb
 * Created on 10/08/2018
 */

public class QuestionCardData_End extends QuestionCardData{

    private String mLabelScore;
    private String mLabelMessage;

    public QuestionCardData_End() {
        super(QuestionType.TYPEEND,null,null);
        mLabelScore = String.valueOf(0);
        mLabelMessage = "";
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
