package com.bf.portugo.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnVerbPagerAdapter;
import com.bf.portugo.common.Constants;
import com.bf.portugo.model.Verb;
import com.bf.portugo.util.SimpleIdlingResource;
import com.bf.portugo.util.VerbHelper;
import com.bf.portugo.viewmodel.LearnVerbViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess", "CanBeFinal", "unused"})
public class LearnVerbActivity extends BaseActivity {

    private final String TAG = LearnVerbActivity.class.getSimpleName();

    public final static String KEY_VERB = "key_verb";
    private static int TAB_INDEX_PRESENT = 1;

    private LearnVerbViewModel mViewModel;
    private FragmentManager mFragmentManager;
    private Typeface mFontPhonetic;

    @BindView(R.id.toolbar_collap_learnverb)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.toolbar_learnverb)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_learnverb)
    ViewPager mViewPager;

    @BindView(R.id.tabs_learnverb)
    TabLayout mTabLayout;

    @BindView(R.id.tv_learnverb_verb_phonetic)
    TextView mTvVerb_phonetic;
    @BindView(R.id.tv_learnverb_verb_en)
    TextView mTvVerb_word_en;
    @BindView(R.id.tv_learnverb_prespart_pt)
    TextView mTvVerb_prespart_pt;
    @BindView(R.id.tv_learnverb_prespart_en)
    TextView mTvVerb_prespart_en;
    @BindView(R.id.tv_learnverb_pastpart_pt)
    TextView mTvVerb_pastpart_pt;
    @BindView(R.id.tv_learnverb_pastpart_en)
    TextView mTvVerb_pastpart_en;

    //region Espresso
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
    //endregion Espresso

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_learn_verb);
        setContentView(R.layout.activity_learn_verb);

        ButterKnife.bind(this);

        //mFont = Typeface.createFromAsset(this.getAssets(), FONT_ITIM_REGULAR);
        mFontPhonetic = Typeface.createFromAsset(this.getAssets(), Constants.Fonts.FONT_DOULUS_PHONETIC);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        mViewModel = ViewModelProviders.of(this).get(LearnVerbViewModel.class);

        if (mViewModel.getVerb() == null){
            if (getIntent().hasExtra(KEY_VERB)) {
                Verb intentVerb = (Verb) getIntent().getSerializableExtra(KEY_VERB);
                mViewModel.setVerb(intentVerb);
                Log.d(TAG, "onCreate: Has INTENT. Verb is "+mViewModel.getVerb()==null?"NULL":mViewModel.getVerb().getWord_pt());
            }
            mViewModel.setTabPosition(TAB_INDEX_PRESENT);
        }
        else
            Log.d(TAG, "onCreate: VM has verb");

        populateVerbInfo(mViewModel.getVerb());


        mFragmentManager = getSupportFragmentManager();
        buildTabViewPager();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(KEY_VERB)) {
            Verb v = (Verb) intent.getSerializableExtra(KEY_VERB);
            Log.d(TAG, "onNewIntent: WIDGET VERB:["+v.getWord_pt()+"");
        }
    }

    @Override
    protected void actionHasTTS(boolean hasTTS) {
        //
    }

    private void buildTabViewPager(){
        LearnVerbPagerAdapter mPagerAdapter = new LearnVerbPagerAdapter(mFragmentManager);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: index="+String.valueOf(tab.getPosition()));
                mViewModel.setTabPosition(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //
            }
        });
        mViewPager.setCurrentItem(mViewModel.getTabPosition());
    }

    private void populateVerbInfo(Verb verb){

        mCollapsingToolbar.setExpandedTitleTypeface(mFont);
        mCollapsingToolbar.setTitleEnabled(true);
        mCollapsingToolbar.setTitle(mViewModel.getVerb().getWord_pt());

        mTvVerb_phonetic.setTypeface(mFontPhonetic);
        mTvVerb_phonetic.setText(verb.getPhonetic_pt());

        mTvVerb_word_en.setTypeface(mFont);
        mTvVerb_word_en.setText(verb.getWord_en());

        mTvVerb_prespart_pt.setTypeface(mFont);
        mTvVerb_prespart_pt.setText(verb.getPres_part_pt());
        mTvVerb_prespart_en.setTypeface(mFont);
        mTvVerb_prespart_en.setText(verb.getPres_part_en());

        mTvVerb_pastpart_pt.setTypeface(mFont);
        mTvVerb_pastpart_pt.setText(verb.getPast_part_pt());
        mTvVerb_pastpart_en.setTypeface(mFont);
        mTvVerb_pastpart_en.setText(verb.getPast_part_en());
    }

    public LearnVerbViewModel getViewModel() {
        return mViewModel;
    }

    public Typeface getFont() {
        return mFont;
    }

    private void showLearnSamplesActivity(Verb verb){
        if (VerbHelper.hasSample(verb)) {
            Intent verbIntent = new Intent(this, LearnSamplesActivity.class);
            verbIntent.putExtra(LearnSamplesActivity.KEY_VERB, verb);
            startActivity(verbIntent);
        }
        else
            Toast.makeText(this, R.string.no_samples_avail, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.layout_toucharea)
    public void layout_touchArea_onClick(LinearLayout area){
        handleTTSRequest(mViewModel.getVerb().getWord_pt());
    }

    @OnClick(R.id.tv_learnverb_prespart_pt)
    public void tv_presentParticiple_onClick(TextView tv){
        handleTTSRequest(tv.getText().toString());
    }

    @OnClick(R.id.tv_learnverb_pastpart_pt)
    public void tv_pastParticiple_onClick(TextView tv){
        handleTTSRequest(tv.getText().toString());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fab_learnverb)
    public void btnLearnSamples_onClick(FloatingActionButton fab){
        showLearnSamplesActivity(getViewModel().getVerb());
    }

}
