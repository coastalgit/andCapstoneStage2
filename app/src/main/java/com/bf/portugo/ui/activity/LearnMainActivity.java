package com.bf.portugo.ui.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsPagerAdapter;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.fragment.LearnMainVerbsFragment;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnMainActivity extends AppCompatActivity implements LearnMainVerbsFragment.OnLearnMainVerbFragmentInteractionListener {

    private String TAG = LearnMainActivity.class.getSimpleName();

    private LearnVerbsMainViewModel mViewModel;
    private FragmentManager mFragmentManager;
    private Snackbar mSnackbar;

    @BindView(R.id.toolbar_learnmain)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_learnmain)
    ViewPager mViewPager;

    @BindView(R.id.tabs_learnmain)
    TabLayout mTabLayout;

    @BindView(R.id.layout_learnmain_root)
    CoordinatorLayout mLayoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_main);

        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        mViewModel = ViewModelProviders.of(this).get(LearnVerbsMainViewModel.class);

        if (!mViewModel.getHasAlreadyPolledDataSource()) {
            snackBarShow(getString(R.string.pleasewait), false);
            mViewModel.setHasAlreadyPolledDataSource(true);
        }

        mFragmentManager = getSupportFragmentManager();
        buildTabViewPager();
        mViewModel.subscribeToChildUpdates();
    }

    public LearnVerbsMainViewModel getViewModel() {
        return mViewModel;
    }

    private void buildTabViewPager(){
        LearnMainVerbsPagerAdapter mPagerAdapter = new LearnMainVerbsPagerAdapter(mFragmentManager);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void showLearnVerbActivity(Verb verb){
        Intent verbIntent = new Intent(this, LearnVerbActivity.class);
        verbIntent.putExtra(LearnVerbActivity.KEY_VERB, verb);
        startActivity(verbIntent);
    }

    private void snackBarShow(String message, boolean asIndefinite){
        Log.d(TAG, "snackBarShow: ");
        mSnackbar = Snackbar.make(mLayoutRoot,message, asIndefinite?Snackbar.LENGTH_INDEFINITE:Snackbar.LENGTH_SHORT);
        mSnackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        mSnackbar.setActionTextColor(Color.WHITE);
        mSnackbar.show();
    }

    private void snackBarDismiass(){
        if (mSnackbar != null && mSnackbar.isShown())
            mSnackbar.dismiss();
    }

    @Override
    public void onVerbItemClick(Verb verbItem) {
        Log.d(TAG, "onVerbItemClick: verb["+verbItem.getWord_pt()+"]");
        showLearnVerbActivity(verbItem);
    }
}
