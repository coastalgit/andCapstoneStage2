package com.bf.portugo.model;

import java.util.List;

/*
 * @author frielb
 * Created on 10/08/2018
 */
public class QuestionCard {

    private Verb mVerb;
    private List<String> mWrongAnswers;

    public QuestionCard(Verb mVerb, List<String> mWrongAnswers) {
        this.mVerb = mVerb;
        this.mWrongAnswers = mWrongAnswers;
    }

    public Verb getVerb() {
        return mVerb;
    }

    public void setVerb(Verb mVerb) {
        this.mVerb = mVerb;
    }

    public List<String> getWrongAnswers() {
        return mWrongAnswers;
    }

    public void setWrongAnswers(List<String> mWrongAnswers) {
        this.mWrongAnswers = mWrongAnswers;
    }

}
