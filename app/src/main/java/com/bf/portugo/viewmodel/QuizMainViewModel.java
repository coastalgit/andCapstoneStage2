package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.ListView;

import com.bf.portugo.data.VerbDao;
import com.bf.portugo.data.VerbDatabase;
import com.bf.portugo.model.Verb;
import com.bf.portugo.repo.VerbRoomRepository;

import java.util.ArrayList;
import java.util.List;


/*
 * @author frielb
 * Created on 05/08/2018
 */
public class QuizMainViewModel extends ViewModel {

    private String TAG = QuizMainViewModel.class.getSimpleName();

    private VerbRoomRepository mRepo;
    //private Verb mVerb;
    private List<Verb> mVerbsAll;

    public QuizMainViewModel(Application application) {
        mRepo = new VerbRoomRepository(application);
        mVerbsAll = (List<Verb>) mRepo.getVerbs();
    }

    public List<Verb> getVerbsAll() {
        return mVerbsAll;
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
