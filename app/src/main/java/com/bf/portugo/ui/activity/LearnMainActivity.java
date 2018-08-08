package com.bf.portugo.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bf.portugo.R;
import com.bf.portugo.ui.fragment.LearnVerbItemsFragment;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(LearnVerbsMainViewModel.class);
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: NO INSTANCE");
            applyFragment(mFragmentVerbs_All, R.id.layout_main_steps, FRAGMENT_STEPS_TAG);

            mFragmentInstructions = RecipeInstructionFragment.newInstance(mViewModel.getRecipe());
            if (mIsTwoPane) {
                applyFragment(mFragmentInstructions, R.id.layout_main_instructions, FRAGMENT_INSTRUCTION_TAG);
            }
        }
        else{
            Log.d(TAG, "onCreate: HAVE INSTANCE");
            mFragmentSteps = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STEPS);
            mFragmentInstructions = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_INSTRUCTION);
        }

    }

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

    private void buildTabViewPager(){
        //mDetailsSectionsPagerAdapter = new DetailSectionsPagerAdapter(getSupportFragmentManager(), Details2Activity.this);
        DetailSectionsPagerAdapter mDetailsSectionsPagerAdapter = new DetailSectionsPagerAdapter(getSupportFragmentManager(), mMovie);

        mViewPager = (FixedViewPager) findViewById(R.id.viewpager_sections);
        mViewPager.setAdapter(mDetailsSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabsSections);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                animatePosterVisibility();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
