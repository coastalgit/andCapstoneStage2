package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bf.portugo.model.QuestionCardData;
import com.bf.portugo.model.QuestionCardData_Type1;
import com.bf.portugo.model.QuestionCardData_Type2;
import com.bf.portugo.model.Verb;
import com.bf.portugo.repo.VerbRoomRepository;
import com.bf.portugo.util.VerbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.bf.portugo.common.Constants.QUIZ_QUESTION_COUNT;
import static com.bf.portugo.common.Constants.QUIZ_QUESTION_TYPE_DIVISOR;
import static com.bf.portugo.common.Constants.WRONG_ANSWER_COUNT;


/*
 * @author frielb
 * Created on 05/08/2018
 */
@SuppressWarnings("CanBeFinal")
public class QuizMainViewModel extends AndroidViewModel {

    private final String TAG = QuizMainViewModel.class.getSimpleName();

    private VerbRoomRepository mRepo;
    private int mActiveCardIndex;
    private int mCurrentScore;
    private boolean mLastPageReached;
    private boolean mFinalCardShown;
    private LiveData<List<Verb>> mVerbsAll;
    private List<QuestionCardData> mQuestionCards;

    public QuizMainViewModel(@NonNull Application application) {
        super(application);
        mRepo = new VerbRoomRepository(application);
        buildNewQuiz();
    }

    private void buildNewQuiz(){
        mActiveCardIndex = 0;
        mCurrentScore = 0;
        mLastPageReached = false;
        mFinalCardShown = false;
        mVerbsAll = mRepo.getVerbs(false);
    }

    public LiveData<List<Verb>> getVerbsAll() {
        return mVerbsAll;
    }

    public List<QuestionCardData> getQuestionCards() {
        return mQuestionCards;
    }

    public void buildQuizBase(boolean hasAudio){
        List<Verb> qVerbs = VerbHelper.generateRandomVerbList(QUIZ_QUESTION_COUNT, mVerbsAll.getValue());
        Log.d(TAG, "buildQuizBase: Count="+VerbHelper.getListRecordCount(qVerbs));

        mQuestionCards = new ArrayList<>();
        int count = 0;
        assert qVerbs != null;
        for (Verb v:qVerbs) {

            QuestionCardData qc;

            // generate mixed questions
            if (count!=0 && (count%QUIZ_QUESTION_TYPE_DIVISOR==0) && hasAudio){
                qc = new QuestionCardData_Type2(v);
            }
            else{
                List<Verb> wrongAnswers = VerbHelper.generateQuizAnswersForVerb(v,WRONG_ANSWER_COUNT,qVerbs);
                qc = new QuestionCardData_Type1(v,wrongAnswers);
            }
            mQuestionCards.add(qc);
            count++;
        }
    }

    public int getActiveCardIndex() {
        Log.d(TAG, "getActiveCardIndex: mActiveCardIndex="+String.valueOf(mActiveCardIndex));
        return mActiveCardIndex;
    }

    public void setActiveCardIndex(int mActiveCardIndex) {
        Log.d(TAG, "setActiveCardIndex: index="+String.valueOf(mActiveCardIndex));
        this.mActiveCardIndex = mActiveCardIndex;
    }

    public int getCurrentScore() {
        return mCurrentScore;
    }

    public void setCurrentScore(int mCurrentScore) {
        this.mCurrentScore = mCurrentScore;
    }

    public boolean getLastPageReached() {
        return mLastPageReached;
    }

    public void setLastPageReached(boolean mLastPageReached) {
        this.mLastPageReached = mLastPageReached;
    }

    public boolean getFinalCardShown() {
        return mFinalCardShown;
    }

    public void setFinalCardShown(boolean mFinalCardShown) {
        this.mFinalCardShown = mFinalCardShown;
    }

}
