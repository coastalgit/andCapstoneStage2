package com.bf.portugo.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bf.portugo.R;

import butterknife.ButterKnife;

public class LearnMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

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
