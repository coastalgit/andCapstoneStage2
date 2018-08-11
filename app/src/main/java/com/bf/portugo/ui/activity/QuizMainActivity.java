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

import com.bf.portugo.R;
import com.bf.portugo.adapter.QuestionCardPagerAdapter;
import com.bf.portugo.model.Verb;
import com.bf.portugo.viewmodel.QuizMainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bf.portugo.util.VerbHelper.getListRecordCount;
import static com.bf.portugo.util.VerbHelper.getLiveListRecordCount;

public class QuizMainActivity extends BaseActivity{

    private String TAG = QuizMainActivity.class.getSimpleName();

/*
    private static final String FRAGMENT_VERBS_ALL = "key_verbs_all";
    private static final String FRAGMENT_VERBS_ALL_TAG = "key_verbs_all_tag";
    private static final String FRAGMENT_VERBS_ESSENTIAL = "key_verbs_essen";
    private static final String FRAGMENT_VERBS_ESSENTIAL_TAG = "key_verbs_essen_tag";
*/

    private QuizMainViewModel mViewModel;
    private QuestionCardPagerAdapter mPagerAdapter;

    //private FragmentManager mFragmentManager;

    //QuestionCardPagerAdapter.IPagerAdapterAction mAdapterListener;

    @BindView(R.id.toolbar_quizmain)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_quizmain)
    ViewPager mViewPager;

    @BindView(R.id.fab_quiznext)
    FloatingActionButton mFabQuizNext;

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

//        populateVerbInfo(mViewModel.getVerb());
//        mFragmentManager = getSupportFragmentManager();

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

    private void buildQuestionCardsViewPager(){
        Log.d(TAG, "buildQuestionCardsViewPager: ");

        mPagerAdapter = new QuestionCardPagerAdapter(this, mViewModel.getQuestionCards(), new QuestionCardPagerAdapter.IPagerAdapterAction() {
            @Override
            public void setFABEnabled(boolean enabled) {
                mFabQuizNext.setEnabled(enabled);
            }
        });

        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: Pos="+String.valueOf(position));
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: Pos="+String.valueOf(position));
                mViewModel.setActiveCardIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        mViewPager.addOnPageChangeListener(pageChangeListener);

//        mViewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });

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


    @OnClick(R.id.fab_quiznext)
    public void btnQuizNext_onClick(FloatingActionButton fab){
        mViewPager.setCurrentItem(mViewModel.getActiveCardIndex()+1, true);
    }


//    private void showLearnVerbActivity(Verb verb){
//        Intent verbIntent = new Intent(this, LearnVerbActivity.class);
//        verbIntent.putExtra(LearnVerbActivity.KEY_VERB, verb);
//        startActivity(verbIntent);
//    }

//    @Override
//    public void onVerbItemClick(Verb verbItem) {
//        Log.d(TAG, "onVerbItemClick: verb["+verbItem.getWord_pt()+"]");
//        showLearnVerbActivity(verbItem);
//    }
}
