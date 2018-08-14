package com.bf.portugo.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.portugo.R;
import com.bf.portugo.adapter.QuestionCardPagerAdapter;
import com.bf.portugo.model.QuestionCardData;
import com.bf.portugo.model.QuestionCardData_End;
import com.bf.portugo.model.Verb;
import com.bf.portugo.util.VerbHelper;
import com.bf.portugo.viewmodel.QuizMainViewModel;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BuildConfig;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bf.portugo.BuildConfig.BUILD_FREE;
import static com.bf.portugo.common.Constants.QUIZ_INTERSTITIAL_AD_INDEX;
import static com.bf.portugo.common.Constants.QUIZ_QUESTION_COUNT;
import static com.bf.portugo.util.VerbHelper.getListRecordCount;

public class QuizMainActivity extends BaseActivity{

    private String TAG = QuizMainActivity.class.getSimpleName();

    private QuizMainViewModel mViewModel;
    private QuestionCardPagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar_quizmain)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_quizmain)
    ViewPager mViewPager;

    @BindView(R.id.fab_quiznext)
    FloatingActionButton mFabQuizNext;


    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        Log.d(TAG, "onCreate: Current saved best score="+String.valueOf(getScoreBest()));

        mViewModel = ViewModelProviders.of(this).get(QuizMainViewModel.class);

        if (BUILD_FREE)
            createInterstitialAd();

        subscribeUI();

    }

    private void createInterstitialAd() {
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_unit_test));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                    //
                super.onAdClosed();
            }
        });
    }

    private void subscribeUI(){
            mViewModel.getVerbsAll().observe(this, new Observer<List<Verb>>() {
                @Override
                public void onChanged(@Nullable List<Verb> verbs) {
                    Log.d(TAG, "onChanged(ALL):");
                    if ((verbs != null) && (verbs.size() > 0)) {
                        Log.d(TAG, "onChanged(ALL): verbs="+String.valueOf(verbs.size()));

                        if (getListRecordCount(mViewModel.getQuestionCards()) < 1)
                            mViewModel.buildQuizBase(getHasAudio());

                        if (!mViewModel.getLastPageReached()) {
                            loadQuestionCardPager();                        }
                        else{
                            if (mViewModel.getFinalCardShown())
                                showFinalCard();
                            else
                                loadQuestionCardPager();
                        }
                    }
                }
            });
    }

    private void loadQuestionCardPager(){
        setFABAccess(false);
        setSkipAccess(true);
        buildQuestionCardsViewPager(mViewModel.getQuestionCards());
    }

    @Override
    protected void actionHasTTS(boolean hasTTS) {
        //
    }

    public QuizMainViewModel getViewModel() {
        return mViewModel;
    }

    private void setFABAccess(boolean enabled) {
        if (enabled){
            if (mFabQuizNext.getVisibility() == View.INVISIBLE)
                mFabQuizNext.setVisibility(View.VISIBLE);

            mFabQuizNext.show();
        }
        else
            mFabQuizNext.hide();
    }

    private void setSkipAccess(boolean enabled) {
        mTvSkip.setVisibility(enabled?View.VISIBLE:View.INVISIBLE);
    }

    private void buildQuestionCardsViewPager(List<QuestionCardData> cardData){
        Log.d(TAG, "buildQuestionCardsViewPager: ");

        //updateScoreLabel(mViewModel.getCurrentScore());
        updateProgessLabel(mViewModel.getLastPageReached()?QUIZ_QUESTION_COUNT:mViewModel.getActiveCardIndex()+1);
        
        mPagerAdapter = new QuestionCardPagerAdapter(this, cardData, new QuestionCardPagerAdapter.IPagerAdapterAction() {
            @Override
            public void setFABEnabled(boolean enabled) {
                setFABAccess(enabled);
            }

            @Override
            public void setSkipEnabled(boolean enabled) {
                setSkipAccess(enabled);
            }

            @Override
            public void adjustScore(boolean answeredCorrect) {
                int score = mViewModel.getCurrentScore();
                if (answeredCorrect) {
                    score++;
                    mViewModel.setCurrentScore(score);
                }
                // TODO: 13/08/2018  
                //updateScoreLabel(mViewModel.getCurrentScore());
            }

            @Override
            public void speakWord(String wordText) {
                doTTSSpeak(wordText);
            }

            @Override
            public void displayMessage(String message) {
                Toast.makeText(QuizMainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void endQuiz() {
                finish();
            }
        });

        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: Pos="+String.valueOf(position));
                if (!mViewModel.getLastPageReached()) {
                    mViewModel.setActiveCardIndex(position);
                    updateProgessLabel(position+1);
                    setFABAccess(false);

                    // Load interstitial ad
                    if ((mViewModel.getActiveCardIndex() == QUIZ_INTERSTITIAL_AD_INDEX) && BUILD_FREE){
                        mInterstitialAd.show();
                    }

                    if (position == QUIZ_QUESTION_COUNT - 1) {
                        mFabQuizNext.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryLight));
                        Log.d(TAG, "onPageSelected: LAST PAGE");
                        mViewModel.setLastPageReached(true);
                        setSkipAccess(false);
                    } else {
                        setSkipAccess(true);
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        };

        mViewPager.addOnPageChangeListener(pageChangeListener);

        // No swiping
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mViewModel.getActiveCardIndex());
    }


/*
    private void updateScoreLabel(int score){
        String scoreStr = String.valueOf(score) + " / " + String.valueOf(QUIZ_QUESTION_COUNT);
        mTvScore.setText(scoreStr);
    }
*/
    private void updateProgessLabel(int pos){
        String scoreStr = String.valueOf(pos) + " / " + String.valueOf(QUIZ_QUESTION_COUNT);
        mTvProgress.setText(scoreStr);
    }

    @OnClick(R.id.fab_quiznext)
    public void btnQuizNext_onClick(FloatingActionButton fab){
        if (!mViewModel.getLastPageReached())
            mViewPager.setCurrentItem(mViewModel.getActiveCardIndex()+1, true);
        else {
            mViewModel.setActiveCardIndex(0);
            //Toast.makeText(this, "Do finalised stuff", Toast.LENGTH_SHORT).show();
            updateProgessLabel(QUIZ_QUESTION_COUNT);
            showFinalCard();
        }
    }

    private void updateSavedScores(int recentScore){
        setScorePrevious(recentScore);
        if (getScoreBest()<=recentScore)
            setScoreBest(recentScore);
    }

    private void showFinalCard(){
        mViewModel.setFinalCardShown(true);
        setFABAccess(false);
        setSkipAccess(false);
        List<QuestionCardData> listCard = new ArrayList<>();
        QuestionCardData_End endCard = new QuestionCardData_End(String.valueOf(mViewModel.getCurrentScore()), VerbHelper.buildEndCardMessage(this, mViewModel.getCurrentScore(), getScorePrevious(), getScoreBest()));
        listCard.add(endCard);
        buildQuestionCardsViewPager(listCard);
        updateSavedScores(mViewModel.getCurrentScore());
    }

    @OnClick(R.id.tv_skip)
    public void btnSkip_onClick(TextView tv){
        mViewPager.setCurrentItem(mViewModel.getActiveCardIndex()+1, true);
        //updateScoreLabel(mViewModel.getCurrentScore());
        updateProgessLabel(mViewModel.getActiveCardIndex()+1);
    }

}
