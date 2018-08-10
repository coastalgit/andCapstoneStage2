package com.bf.portugo.model;

import java.util.List;

/*
 * @author frielb
 * Created on 10/08/2018
 */
public class QuestionCard {

    private Verb mVerb;
    private List<Verb> mWrongAnswers;

    public QuestionCard(Verb mVerb, List<Verb> mWrongAnswers) {
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
