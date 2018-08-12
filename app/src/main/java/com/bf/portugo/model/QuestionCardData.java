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
public abstract class QuestionCardData {

//    public static int QUESTION_TYPE_1 = 1; // EN phrase with selectable PT answers
//    public static int QUESTION_TYPE_2 = 2; // Listen to PT phrase and type in (TTS)
// TODO: 12/08/2018 Remove
//    public static int QUESTION_TYPE_3 = 3; // PT phrase with selectable EN answers

    public enum QuestionType{
        TYPE1,TYPE2,TYPEEND
    }

    private QuestionType mQuestionType;
    private Verb mVerb;
    private List<Verb> mWrongAnswers;
//    private int mChosenAnswer;
    //private int mAnswerPosition;
    private boolean mChosenAnswerCorrect;
/*
    public QuestionCardData(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, Verb mVerb, List<Verb> mWrongAnswers) {
        super(context, attrs, defStyleAttr);
        this.mVerb = mVerb;
        this.mWrongAnswers = mWrongAnswers;
    }
*/

    public QuestionCardData(QuestionType questiontype, Verb mVerb, List<Verb> mWrongAnswers) {
        this.mQuestionType = questiontype;
        this.mVerb = mVerb;
        this.mWrongAnswers = mWrongAnswers;
        //mChosenAnswer = -1;
        mChosenAnswerCorrect = false;
        //mAnswerPosition = -1;
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

    public void setWrongAnswers(List<Verb> mWrongAnswers) {
        this.mWrongAnswers = mWrongAnswers;
    }

/*
    public int getChosenAnswer() {
        return mChosenAnswer;
    }

    public void setChosenAnswer(int mChosenAnswer) {
        this.mChosenAnswer = mChosenAnswer;
    }


    public int getAnswerPosition() {
        return mAnswerPosition;
    }

    public void setAnswerPosition(int mAnswerPosition) {
        this.mAnswerPosition = mAnswerPosition;
    }
*/

    public boolean getChosenAnswerCorrect() {
        return mChosenAnswerCorrect;
    }

    public void setChosenAnswerCorrect(boolean mChosenAnswerCorrect) {
        this.mChosenAnswerCorrect = mChosenAnswerCorrect;
    }

}
