package com.bf.portugo.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsPagerAdapter;
import com.bf.portugo.ui.fragment.LearnVerbItemsFragment;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnMainActivity extends AppCompatActivity {

    private String TAG = LearnMainActivity.class.getSimpleName();

    private static final String FRAGMENT_VERBS_ALL = "key_verbs_all";
    private static final String FRAGMENT_VERBS_ALL_TAG = "key_verbs_all_tag";
    private static final String FRAGMENT_VERBS_ESSENTIAL = "key_verbs_essen";
    private static final String FRAGMENT_VERBS_ESSENTIAL_TAG = "key_verbs_essen_tag";

    private LearnVerbsMainViewModel mViewModel;

    private FragmentManager mFragmentManager;
    private Fragment mFragmentVerbs_All;
    private Fragment mFragmentVerbs_Essential;

    @BindView(R.id.toolbar_learnmain)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_learnmain)
    ViewPager mViewPager;

    @BindView(R.id.tabs_learnmain)
    TabLayout mTabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_main);

        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //mToolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }


        mViewModel = ViewModelProviders.of(this).get(LearnVerbsMainViewModel.class);
        mFragmentManager = getSupportFragmentManager();
        buildTabViewPager();

        // TODO: 08/08/2018 Check connectivity and Room content (for offline)
        mViewModel.subscribeToChildUpdates();

//        if (savedInstanceState == null) {
//            Log.d(TAG, "onCreate: NO INSTANCE");
//            applyFragment(mFragmentVerbs_All, R.id.layout_main_steps, FRAGMENT_STEPS_TAG);
//
//            mFragmentInstructions = RecipeInstructionFragment.newInstance(mViewModel.getRecipe());
//            if (mIsTwoPane) {
//                applyFragment(mFragmentInstructions, R.id.layout_main_instructions, FRAGMENT_INSTRUCTION_TAG);
//            }
//        }
//        else{
//            Log.d(TAG, "onCreate: HAVE INSTANCE");
//            mFragmentSteps = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STEPS);
//            mFragmentInstructions = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_INSTRUCTION);
//        }

    }

/*
    private void applyFragment(Fragment frag, int layoutId, String fragTag){
        if (frag != null)
            mFragmentManager.beginTransaction().replace(layoutId, frag, fragTag).commit();
        else{
            switch (fragTag){
                case FRAGMENT_VERBS_ESSENTIAL:
                    frag = mFragmentVerbs_Essential = LearnVerbItemsFragment.newInstance();
                    break;
                case FRAGMENT_VERBS_ALL:
                    frag = mFragmentVerbs_All = LearnVerbItemsFragment.newInstance();
                    break;
            }
            mFragmentManager.beginTransaction()
                    .add(layoutId, frag, fragTag)
                    .commit();
        }
    }
*/

    public LearnVerbsMainViewModel getViewModel() {
        return mViewModel;
    }

    private void buildTabViewPager(){
        //mDetailsSectionsPagerAdapter = new DetailSectionsPagerAdapter(getSupportFragmentManager(), Details2Activity.this);
        LearnMainVerbsPagerAdapter mPagerAdapter = new LearnMainVerbsPagerAdapter(mFragmentManager);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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

}
