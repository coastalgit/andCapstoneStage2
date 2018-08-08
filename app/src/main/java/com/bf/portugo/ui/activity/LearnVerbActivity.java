package com.bf.portugo.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsPagerAdapter;
import com.bf.portugo.adapter.LearnVerbPagerAdapter;
import com.bf.portugo.common.Constants;
import com.bf.portugo.model.Verb;
import com.bf.portugo.viewmodel.LearnVerbViewModel;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bf.portugo.common.Constants.Fonts.FONT_ITIM_REGULAR;

public class LearnVerbActivity extends AppCompatActivity {

    private String TAG = LearnVerbActivity.class.getSimpleName();

    public final static String KEY_VERB = "key_verb";

    private LearnVerbViewModel mViewModel;

    private FragmentManager mFragmentManager;
    private Fragment mFragmentVerbs_Past;
    private Fragment mFragmentVerbs_Present;
    private Fragment mFragmentVerbs_Future;

    private Typeface mFont;
    private Typeface mFontPhonetic;

    @BindView(R.id.toolbar_learnverb)
    Toolbar mToolbar;

    @BindView(R.id.viewpager_learnverb)
    ViewPager mViewPager;

    @BindView(R.id.tabs_learnverb)
    TabLayout mTabLayout;

    @BindView(R.id.tv_learnverb_verb_pt)
    TextView mTvVerb_word_pt;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_verb);

        ButterKnife.bind(this);

        mFont = Typeface.createFromAsset(this.getAssets(), FONT_ITIM_REGULAR);
        mFontPhonetic = Typeface.createFromAsset(this.getAssets(), Constants.Fonts.FONT_DOULUS_PHONETIC);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //mToolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }


        mViewModel = ViewModelProviders.of(this).get(LearnVerbViewModel.class);

        if (mViewModel.getVerb() == null){
            if (getIntent().hasExtra(KEY_VERB)) {
                mViewModel.setVerb((Verb) getIntent().getSerializableExtra(KEY_VERB));
                Log.d(TAG, "onCreate: Has INTENT. Verb is "+mViewModel.getVerb()==null?"NULL":"NOT NULL");
            }
        }
        else
            Log.d(TAG, "onCreate: VM has verb");

        populateVerbInfo(mViewModel.getVerb());

        mFragmentManager = getSupportFragmentManager();
        buildTabViewPager();
    }

    public LearnVerbViewModel getViewModel() {
        return mViewModel;
    }

    private void buildTabViewPager(){
        //mDetailsSectionsPagerAdapter = new DetailSectionsPagerAdapter(getSupportFragmentManager(), Details2Activity.this);
        LearnVerbPagerAdapter mPagerAdapter = new LearnVerbPagerAdapter(mFragmentManager);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void populateVerbInfo(Verb verb){

        mTvVerb_phonetic.setTypeface(mFontPhonetic);
        mTvVerb_phonetic.setText(verb.getPhonetic_pt());

        mTvVerb_word_pt.setTypeface(mFont);
        mTvVerb_word_pt.setText(verb.getWord_pt());
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
}
