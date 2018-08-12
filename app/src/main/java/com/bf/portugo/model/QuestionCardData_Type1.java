package com.bf.portugo.model;

import java.util.List;

/*
 * @author frielb
 * Created on 10/08/2018
 */
//public class QuestionCardData extends CardView {
public class QuestionCardData_Type1 extends QuestionCardData{

    private int mChosenAnswer;
    private int mAnswerPosition;

    public QuestionCardData_Type1(Verb mVerb, List<Verb> mWrongAnswers) {
        super(QuestionType.TYPE1, mVerb,mWrongAnswers);
        mChosenAnswer = -1;
        mAnswerPosition = -1;
    }

    public int getChosenAnswer() {
        return mChosenAnswer;
    }

    public void setChosenAnswer(int mChosenAnswer) {
        this.mChosenAnswer = mChosenAnswer;
    }

/*
    public boolean getChosenAnswerCorrect() {
        return mChosenAnswerCorrect;
    }

    public void setChosenAnswerCorrect(boolean mChosenAnswerCorrect) {
        this.mChosenAnswerCorrect = mChosenAnswerCorrect;
    }
*/

    public int getAnswerPosition() {
        return mAnswerPosition;
    }

    public void setAnswerPosition(int mAnswerPosition) {
        this.mAnswerPosition = mAnswerPosition;
    }

}
