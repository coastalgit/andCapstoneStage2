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
import com.bf.portugo.model.Verb;
import com.bf.portugo.viewmodel.QuizMainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_score)
    TextView mTvScore;

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


        mViewModel = ViewModelProviders.of(this).get(QuizMainViewModel.class);
        subscribeUI();

//        if (mViewModel.getVerbsAll().getValue() == null){
//            mViewModel.buildNewQuiz();
//        }
//        else
//            Log.d(TAG, "onCreate: VM has a quiz");


    }

    private void subscribeUI(){
            mViewModel.getVerbsAll().observe(this, new Observer<List<Verb>>() {
                @Override
                public void onChanged(@Nullable List<Verb> verbs) {
                    Log.d(TAG, "onChanged(ALL):");
                    if ((verbs != null) && (verbs.size() > 0)) {
                        Log.d(TAG, "onChanged(ALL): verbs="+String.valueOf(verbs.size()));
                        //mViewModel.buildNewQuiz();

                        if (getListRecordCount(mViewModel.getQuestionCards()) < 1)
                            mViewModel.buildQuizBase(QuizMainActivity.this);

                        buildQuestionCardsViewPager();
                    }
                }
            });
//        mViewModel.getQuestionCards().observe(this, new Observer<List<Verb>>() {
//            @Override
//            public void onChanged(@Nullable List<Verb> qCards) {
//                Log.d(TAG, "onChanged(QUIZ):");
//                if ((qCards != null) && (qCards.size() > 0)) {
//                    Log.d(TAG, "onChanged(QUIZ): verbs="+String.valueOf(qCards.size()));
//                    mViewModel.buildNewQuiz();
//                }
//                mPagerAdapter.reloadAdapter(verbs);
//            }
//        });
    }

    @Override
    protected void actionHasTTS(boolean hasTTS) {

    }

    public QuizMainViewModel getViewModel() {
        return mViewModel;
    }

    private void setFABAccess(boolean enabled) {
        if (enabled)
            mFabQuizNext.show();
        else
            mFabQuizNext.hide();
    }

    private void setSkip(boolean enabled) {
        mTvSkip.setVisibility(enabled?View.VISIBLE:View.INVISIBLE);
    }

    private void buildQuestionCardsViewPager(){
        Log.d(TAG, "buildQuestionCardsViewPager: ");

        setFABAccess(false);
        setSkip(true);
        updateScoreLabel(mViewModel.getCurrentScore());
        
        mPagerAdapter = new QuestionCardPagerAdapter(this, mViewModel.getQuestionCards(), new QuestionCardPagerAdapter.IPagerAdapterAction() {
            @Override
            public void setFABEnabled(boolean enabled) {
                setFABAccess(enabled);
            }

            @Override
            public void setSkipEnabled(boolean enabled) {
                setSkip(enabled);
            }

            @Override
            public void adjustScore(boolean answeredCorrect) {
                int score = mViewModel.getCurrentScore();
                if (answeredCorrect) {
                    score++;
                    mViewModel.setCurrentScore(score);
                }
                updateScoreLabel(mViewModel.getCurrentScore());
            }
        });

        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d(TAG, "onPageScrolled: Pos="+String.valueOf(position));
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: Pos="+String.valueOf(position));
                mViewModel.setActiveCardIndex(position);
                if (position == QUIZ_QUESTION_COUNT-1) {
                    //Toast.makeText(QuizMainActivity.this, "LAST", Toast.LENGTH_SHORT).show();
                    mFabQuizNext.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryLight));
                    mViewModel.setLastPageReached(true);
                    setFABAccess(false);
                    setSkip(false);
                }
                else{
                    setFABAccess(false);
                    setSkip(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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


/*
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
*/

/*
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                //animatePosterVisibility();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/
    }


    private void updateScoreLabel(int score){
        String scoreStr = String.valueOf(score) + " / " + String.valueOf(QUIZ_QUESTION_COUNT);
        mTvScore.setText(scoreStr);
    }

    @OnClick(R.id.fab_quiznext)
    public void btnQuizNext_onClick(FloatingActionButton fab){
        if (!mViewModel.getLastPageReached())
            mViewPager.setCurrentItem(mViewModel.getActiveCardIndex()+1, true);
        else
            Toast.makeText(this, "Do finalized shit", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_skip)
    public void btnSkip_onClick(TextView tv){
        mViewPager.setCurrentItem(mViewModel.getActiveCardIndex()+1, true);
        updateScoreLabel(mViewModel.getCurrentScore());
    }

}
