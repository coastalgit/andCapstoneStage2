package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bf.portugo.model.QuestionCardData;
import com.bf.portugo.model.Verb;
import com.bf.portugo.repo.VerbRoomRepository;
import com.bf.portugo.util.VerbHelper;

import java.util.ArrayList;
import java.util.List;


/*
 * @author frielb
 * Created on 05/08/2018
 */
public class QuizMainViewModel extends AndroidViewModel {

    private String TAG = QuizMainViewModel.class.getSimpleName();
    private int QUIZ_QUESTION_COUNT = 5;
    private int WRONG_ANSWER_COUNT = 3;

    private VerbRoomRepository mRepo;
    private int mActiveCardIndex;
    private LiveData<List<Verb>> mVerbsAll;
    private List<QuestionCardData> mQuestionCards;

    public QuizMainViewModel(@NonNull Application application) {
        super(application);
        mRepo = new VerbRoomRepository(application);
        //mVerbsAll = new MutableLiveData<>();
        buildNewQuiz();
    }

    public void buildNewQuiz(){
        mActiveCardIndex = 0;
        mVerbsAll = mRepo.getVerbs(false);
    }

    public LiveData<List<Verb>> getVerbsAll() {
        return mVerbsAll;
    }

    public List<QuestionCardData> getQuestionCards() {
        return mQuestionCards;
    }

    public void buildQuizBase(Context context){
        Log.d(TAG, "buildQuizBase: ");
        List<Verb> qVerbs = VerbHelper.generateRandomVerbList(QUIZ_QUESTION_COUNT, mVerbsAll.getValue());
        Log.d(TAG, "buildQuizBase: Count:"+qVerbs==null?"Null":String.valueOf(qVerbs.size()));

        mQuestionCards = new ArrayList<>();
        for (Verb v:qVerbs) {
            List<Verb> wrongAnswers = VerbHelper.generateQuizAnswersForVerb(v,WRONG_ANSWER_COUNT,qVerbs);
            QuestionCardData qc = new QuestionCardData(v,wrongAnswers);
            mQuestionCards.add(qc);
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


/*
    public Verb getVerb() {
        return mVerb;
    }

    public void setVerb(Verb mVerb) {
        this.mVerb = mVerb;
    }

    ArrayList<Verb> verbList;

    String questionTitles [] = {"Question 1", "Question 2", "Question 3", "Question 4", "Question 5",};

    String  detailsArray [] = {
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
            "Time table details radom, Lorem ipsum characters ment for testing of programs and characters or displaying random informations",
    };
*/

}
