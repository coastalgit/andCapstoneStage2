package com.bf.portugo.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsPagerAdapter;
import com.bf.portugo.adapter.QuestionCardPagerAdapter;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.fragment.LearnMainVerbsFragment;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;
import com.bf.portugo.viewmodel.QuizMainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.toolbar_quizmain)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_quizmain)
    ViewPager mViewPager;

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

        if (mViewModel.getVerbsAll() == null){
            mViewModel.buildNewQuiz();
        }
        else
            Log.d(TAG, "onCreate: VM has a quiz");

//        populateVerbInfo(mViewModel.getVerb());
//        mFragmentManager = getSupportFragmentManager();

        buildQuestionCardsViewPager();
    }

    @Override
    protected void actionHasTTS(boolean hasTTS) {

    }

    public QuizMainViewModel getViewModel() {
        return mViewModel;
    }

    private void buildQuestionCardsViewPager(){

        mPagerAdapter = new QuestionCardPagerAdapter(mViewModel.getQuestionCards());
        mViewPager.setAdapter(mPagerAdapter);


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
