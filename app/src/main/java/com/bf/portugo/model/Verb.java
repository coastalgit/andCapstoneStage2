package com.bf.portugo.model;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/*
 * @author frielb
 * Created on 31/07/2018
 */
public class Verb {

    @PrimaryKey
    @NonNull
    private String word_en; // english words to be used as unique
    private String word_pt;
    private boolean isIrregular;
    private String phonetic_pt;
    //private int difficultyLevel;
    private int categoryId;
    private int classification; // essential, top 50, top 100 etc.

    private String pres_part_en;
    private String pres_part_pt;
    private String past_part_en;
    private String past_part_pt;

    private String tense_pres_en_all;
    private String tense_pres_en_heshe;

    private String tense_pres_pt_i;
    //private String tense_pres_en_i;
    private String tense_pres_pt_you;
    //private String tense_pres_en_you;
    private String tense_pres_pt_heshe;
    //private String tense_pres_en_heshe;
    private String tense_pres_pt_we;
    //private String tense_pres_en_we;
    private String tense_pres_pt_they;
    //private String tense_pres_en_they;

    private String tense_past_pt_i;
    //private String tense_past_en_i;
    private String tense_past_pt_you;
    //private String tense_past_en_you;
    private String tense_past_pt_heshe;
    //private String tense_past_en_heshe;
    private String tense_past_pt_we;
    //private String tense_past_en_we;
    private String tense_past_pt_they;
    //private String tense_past_en_they;

    private String tense_fut_en_all;
    private String tense_fut_pt_i;
    //private String tense_fut_en_i;
    private String tense_fut_pt_you;
    //private String tense_fut_en_you;
    private String tense_fut_pt_heshe;
    //private String tense_fut_en_heshe;
    private String tense_fut_pt_we;
    //private String tense_fut_en_we;
    private String tense_fut_pt_they;
    //private String tense_fut_en_they;

    private String sample_1_pt;
    private String sample_1_en;
    private String sample_2_pt;
    private String sample_2_en;
    private String sample_3_pt;
    private String sample_3_en;

    //region CONSTRUCTORS
    //public Verb() {} // empty CTR for Firebase

    /**
     *
     * @param word_en
     * @param word_pt
     * @param isIrregular
     * @param phonetic_pt
     * @param categoryId
     * @param classification
     * @param pres_part_pt
     * @param past_part_pt
     * @param tense_pres_en_all
     * @param tense_pres_en_heshe
     * @param tense_pres_pt_i
     * @param tense_pres_pt_you
     * @param tense_pres_pt_heshe
     * @param tense_pres_pt_we
     * @param tense_pres_pt_they
     * @param tense_past_pt_i
     * @param tense_past_pt_you
     * @param tense_past_pt_heshe
     * @param tense_past_pt_we
     * @param tense_past_pt_they
     * @param tense_fut_en_all
     * @param tense_fut_pt_i
     * @param tense_fut_pt_you
     * @param tense_fut_pt_heshe
     * @param tense_fut_pt_we
     * @param tense_fut_pt_they
     * @param sample_1_pt
     * @param sample_1_en
     * @param sample_2_pt
     * @param sample_2_en
     * @param sample_3_pt
     * @param sample_3_en
     */
    public Verb(@NonNull String word_en,
                String word_pt,
                boolean isIrregular,
                String phonetic_pt,
                int categoryId,
                int classification,
                String pres_part_en,
                String pres_part_pt,
                String past_part_en,
                String past_part_pt,
                String tense_pres_en_all,
                String tense_pres_en_heshe,
                String tense_pres_pt_i,
                String tense_pres_pt_you,
                String tense_pres_pt_heshe,
                String tense_pres_pt_we,
                String tense_pres_pt_they,
                String tense_past_pt_i,
                String tense_past_pt_you,
                String tense_past_pt_heshe,
                String tense_past_pt_we,
                String tense_past_pt_they,
                String tense_fut_en_all,
                String tense_fut_pt_i,
                String tense_fut_pt_you,
                String tense_fut_pt_heshe,
                String tense_fut_pt_we,
                String tense_fut_pt_they,
                String sample_1_pt,
                String sample_1_en,
                String sample_2_pt,
                String sample_2_en,
                String sample_3_pt,
                String sample_3_en) {
        this.word_en = word_en;
        this.word_pt = word_pt;
        this.isIrregular = isIrregular;
        this.phonetic_pt = phonetic_pt;
        this.categoryId = categoryId;
        this.classification = classification;
        this.pres_part_en = pres_part_en;
        this.pres_part_pt = pres_part_pt;
        this.past_part_en = past_part_en;
        this.past_part_pt = past_part_pt;
        this.tense_pres_en_all = tense_pres_en_all;
        this.tense_pres_en_heshe = tense_pres_en_heshe;
        this.tense_pres_pt_i = tense_pres_pt_i;
        this.tense_pres_pt_you = tense_pres_pt_you;
        this.tense_pres_pt_heshe = tense_pres_pt_heshe;
        this.tense_pres_pt_we = tense_pres_pt_we;
        this.tense_pres_pt_they = tense_pres_pt_they;
        this.tense_past_pt_i = tense_past_pt_i;
        this.tense_past_pt_you = tense_past_pt_you;
        this.tense_past_pt_heshe = tense_past_pt_heshe;
        this.tense_past_pt_we = tense_past_pt_we;
        this.tense_past_pt_they = tense_past_pt_they;
        this.tense_fut_en_all = tense_fut_en_all;
        this.tense_fut_pt_i = tense_fut_pt_i;
        this.tense_fut_pt_you = tense_fut_pt_you;
        this.tense_fut_pt_heshe = tense_fut_pt_heshe;
        this.tense_fut_pt_we = tense_fut_pt_we;
        this.tense_fut_pt_they = tense_fut_pt_they;
        this.sample_1_pt = sample_1_pt;
        this.sample_1_en = sample_1_en;
        this.sample_2_pt = sample_2_pt;
        this.sample_2_en = sample_2_en;
        this.sample_3_pt = sample_3_pt;
        this.sample_3_en = sample_3_en;
    }
    //endregion CONSTRUCTORS

    //region GET SET

    @NonNull
    public String getWord_en() {
        return word_en;
    }

    public void setWord_en(@NonNull String word_en) {
        this.word_en = word_en;
    }

    public String getWord_pt() {
        return word_pt;
    }

    public void setWord_pt(String word_pt) {
        this.word_pt = word_pt;
    }

    public boolean getIrregular() {
        return isIrregular;
    }

    public void setIrregular(boolean irregular) {
        isIrregular = irregular;
    }

    public String getPhonetic_pt() {
        return phonetic_pt;
    }

    public void setPhonetic_pt(String phonetic_pt) {
        this.phonetic_pt = phonetic_pt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public String getPres_part_en() {
        return pres_part_en;
    }

    public void setPres_part_en(String pres_part_en) {
        this.pres_part_en = pres_part_en;
    }

    public String getPres_part_pt() {
        return pres_part_pt;
    }

    public void setPres_part_pt(String pres_part_pt) {
        this.pres_part_pt = pres_part_pt;
    }

    public String getPast_part_en() {
        return past_part_en;
    }

    public void setPast_part_en(String past_part_en) {
        this.past_part_en = past_part_en;
    }

    public String getPast_part_pt() {
        return past_part_pt;
    }

    public void setPast_part_pt(String past_part_pt) {
        this.past_part_pt = past_part_pt;
    }

    public String getTense_pres_en_all() {
        return tense_pres_en_all;
    }

    public void setTense_pres_en_all(String tense_pres_en_all) {
        this.tense_pres_en_all = tense_pres_en_all;
    }

    public String getTense_pres_en_heshe() {
        return tense_pres_en_heshe;
    }

    public void setTense_pres_en_heshe(String tense_pres_en_heshe) {
        this.tense_pres_en_heshe = tense_pres_en_heshe;
    }

    public String getTense_pres_pt_i() {
        return tense_pres_pt_i;
    }

    public void setTense_pres_pt_i(String tense_pres_pt_i) {
        this.tense_pres_pt_i = tense_pres_pt_i;
    }

    public String getTense_pres_pt_you() {
        return tense_pres_pt_you;
    }

    public void setTense_pres_pt_you(String tense_pres_pt_you) {
        this.tense_pres_pt_you = tense_pres_pt_you;
    }

    public String getTense_pres_pt_heshe() {
        return tense_pres_pt_heshe;
    }

    public void setTense_pres_pt_heshe(String tense_pres_pt_heshe) {
        this.tense_pres_pt_heshe = tense_pres_pt_heshe;
    }

    public String getTense_pres_pt_we() {
        return tense_pres_pt_we;
    }

    public void setTense_pres_pt_we(String tense_pres_pt_we) {
        this.tense_pres_pt_we = tense_pres_pt_we;
    }

    public String getTense_pres_pt_they() {
        return tense_pres_pt_they;
    }

    public void setTense_pres_pt_they(String tense_pres_pt_they) {
        this.tense_pres_pt_they = tense_pres_pt_they;
    }

    public String getTense_past_pt_i() {
        return tense_past_pt_i;
    }

    public void setTense_past_pt_i(String tense_past_pt_i) {
        this.tense_past_pt_i = tense_past_pt_i;
    }

    public String getTense_past_pt_you() {
        return tense_past_pt_you;
    }

    public void setTense_past_pt_you(String tense_past_pt_you) {
        this.tense_past_pt_you = tense_past_pt_you;
    }

    public String getTense_past_pt_heshe() {
        return tense_past_pt_heshe;
    }

    public void setTense_past_pt_heshe(String tense_past_pt_heshe) {
        this.tense_past_pt_heshe = tense_past_pt_heshe;
    }

    public String getTense_past_pt_we() {
        return tense_past_pt_we;
    }

    public void setTense_past_pt_we(String tense_past_pt_we) {
        this.tense_past_pt_we = tense_past_pt_we;
    }

    public String getTense_past_pt_they() {
        return tense_past_pt_they;
    }

    public void setTense_past_pt_they(String tense_past_pt_they) {
        this.tense_past_pt_they = tense_past_pt_they;
    }

    public String getTense_fut_en_all() {
        return tense_fut_en_all;
    }

    public void setTense_fut_en_all(String tense_fut_en_all) {
        this.tense_fut_en_all = tense_fut_en_all;
    }

    public String getTense_fut_pt_i() {
        return tense_fut_pt_i;
    }

    public void setTense_fut_pt_i(String tense_fut_pt_i) {
        this.tense_fut_pt_i = tense_fut_pt_i;
    }

    public String getTense_fut_pt_you() {
        return tense_fut_pt_you;
    }

    public void setTense_fut_pt_you(String tense_fut_pt_you) {
        this.tense_fut_pt_you = tense_fut_pt_you;
    }

    public String getTense_fut_pt_heshe() {
        return tense_fut_pt_heshe;
    }

    public void setTense_fut_pt_heshe(String tense_fut_pt_heshe) {
        this.tense_fut_pt_heshe = tense_fut_pt_heshe;
    }

    public String getTense_fut_pt_we() {
        return tense_fut_pt_we;
    }

    public void setTense_fut_pt_we(String tense_fut_pt_we) {
        this.tense_fut_pt_we = tense_fut_pt_we;
    }

    public String getTense_fut_pt_they() {
        return tense_fut_pt_they;
    }

    public void setTense_fut_pt_they(String tense_fut_pt_they) {
        this.tense_fut_pt_they = tense_fut_pt_they;
    }

    public String getSample_1_pt() {
        return sample_1_pt;
    }

    public void setSample_1_pt(String sample_1_pt) {
        this.sample_1_pt = sample_1_pt;
    }

    public String getSample_1_en() {
        return sample_1_en;
    }

    public void setSample_1_en(String sample_1_en) {
        this.sample_1_en = sample_1_en;
    }

    public String getSample_2_pt() {
        return sample_2_pt;
    }

    public void setSample_2_pt(String sample_2_pt) {
        this.sample_2_pt = sample_2_pt;
    }

    public String getSample_2_en() {
        return sample_2_en;
    }

    public void setSample_2_en(String sample_2_en) {
        this.sample_2_en = sample_2_en;
    }

    public String getSample_3_pt() {
        return sample_3_pt;
    }

    public void setSample_3_pt(String sample_3_pt) {
        this.sample_3_pt = sample_3_pt;
    }

    public String getSample_3_en() {
        return sample_3_en;
    }

    public void setSample_3_en(String sample_3_en) {
        this.sample_3_en = sample_3_en;
    }

    //endregion GET SET

}
