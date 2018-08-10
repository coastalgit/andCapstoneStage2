package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.bf.portugo.data.VerbDao;
import com.bf.portugo.data.VerbDatabase;
import com.bf.portugo.model.QuestionCard;
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
    private List<Verb> mVerbsAll;
    private List<QuestionCard> mQuestionCards;

    public QuizMainViewModel(@NonNull Application application) {
        super(application);
        mRepo = new VerbRoomRepository(application);
    }

    public void buildNewQuiz(){
        mActiveCardIndex = 0;
        mVerbsAll = mRepo.getVerbsSync();
        buildQuizBase();
    }

    public List<Verb> getVerbsAll() {
        return mVerbsAll;
    }

    public List<QuestionCard> getQuestionCards() {
        return mQuestionCards;
    }

    private void buildQuizBase(){
        List<Verb> qVerbs = VerbHelper.generateRandomVerbList(QUIZ_QUESTION_COUNT, getVerbsAll());
        Log.d(TAG, "buildQuizBase: Count:"+qVerbs==null?"Null":String.valueOf(qVerbs.size()));

        mQuestionCards = new ArrayList<>();
        for (Verb v:qVerbs) {
            List<Verb> wrongAnswers = VerbHelper.generateQuizAnswersForVerb(v,WRONG_ANSWER_COUNT,getVerbsAll());
            QuestionCard qc = new QuestionCard(v,wrongAnswers);
            mQuestionCards.add(qc);
        }
    }

    public int getActiveCardIndex() {
        return mActiveCardIndex;
    }

    public void setActiveCardIndex(int mActiveCardIndex) {
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
