package com.bf.portugo.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsPagerAdapter;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.fragment.LearnMainVerbsFragment;
import com.bf.portugo.viewmodel.LearnSamplesViewModel;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LearnSamplesActivity extends BaseActivity{

    private String TAG = LearnSamplesActivity.class.getSimpleName();
    public final static String KEY_VERB = "key_verb";

    private LearnSamplesViewModel mViewModel;

    @BindView(R.id.toolbar_learnsamples)
    Toolbar mToolbar;

    @BindView(R.id.sample_1)
    FrameLayout mLayoutSample1;
    @BindView(R.id.sample_2)
    FrameLayout mLayoutSample2;
    @BindView(R.id.sample_3)
    FrameLayout mLayoutSample3;

    FloatingActionButton mFab_SamplePlay1;
    TextView tvSample1_title;
    TextView tvSample1_pt;
    TextView tvSample1_en;

    FloatingActionButton mFab_SamplePlay2;
    TextView tvSample2_title;
    TextView tvSample2_pt;
    TextView tvSample2_en;

    FloatingActionButton mFab_SamplePlay3;
    TextView tvSample3_title;
    TextView tvSample3_pt;
    TextView tvSample3_en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_samples);

        ButterKnife.bind(this);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }


        mViewModel = ViewModelProviders.of(this).get(LearnSamplesViewModel.class);

        if (mViewModel.getVerb() == null){
            if (getIntent().hasExtra(KEY_VERB)) {
                mViewModel.setVerb((Verb) getIntent().getSerializableExtra(KEY_VERB));
                Log.d(TAG, "onCreate: Has INTENT. Verb is "+mViewModel.getVerb()==null?"NULL":"NOT NULL");
            }
        }
        else
            Log.d(TAG, "onCreate: VM has verb");

        tvSample1_title = (TextView) mLayoutSample1.findViewById(R.id.tv_sample_title);
        tvSample1_pt = (TextView) mLayoutSample1.findViewById(R.id.tv_sample_pt);
        tvSample1_pt.setTypeface(mFont);
        tvSample1_en = (TextView) mLayoutSample1.findViewById(R.id.tv_sample_en);
        tvSample1_en.setTypeface(mFont);
        mFab_SamplePlay1 = (FloatingActionButton) mLayoutSample1.findViewById(R.id.fab_sample_play);
        mFab_SamplePlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTTSRequest(mViewModel.getVerb().getSample_1_pt());
            }
        });

        tvSample2_title = (TextView) mLayoutSample2.findViewById(R.id.tv_sample_title);
        tvSample2_pt = (TextView) mLayoutSample2.findViewById(R.id.tv_sample_pt);
        tvSample2_pt.setTypeface(mFont);
        tvSample2_en = (TextView) mLayoutSample2.findViewById(R.id.tv_sample_en);
        tvSample2_en.setTypeface(mFont);
        mFab_SamplePlay2 = (FloatingActionButton) mLayoutSample2.findViewById(R.id.fab_sample_play);
        mFab_SamplePlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTTSRequest(mViewModel.getVerb().getSample_2_pt());
            }
        });

        tvSample3_title = (TextView) mLayoutSample3.findViewById(R.id.tv_sample_title);
        tvSample3_pt = (TextView) mLayoutSample3.findViewById(R.id.tv_sample_pt);
        tvSample3_pt.setTypeface(mFont);
        tvSample3_en = (TextView) mLayoutSample3.findViewById(R.id.tv_sample_en);
        tvSample3_en.setTypeface(mFont);
        mFab_SamplePlay3 = (FloatingActionButton) mLayoutSample3.findViewById(R.id.fab_sample_play);
        mFab_SamplePlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTTSRequest(mViewModel.getVerb().getSample_3_pt());
            }
        });

        populateSampleInfo(mViewModel.getVerb());

    }

    private void populateSampleInfo(Verb verb) {

        tvSample1_title.setText(getString(R.string.sample1));
        tvSample2_title.setText(getString(R.string.sample2));
        tvSample3_title.setText(getString(R.string.sample3));

        tvSample1_pt.setText(verb.getSample_1_pt());
        tvSample1_en.setText(verb.getSample_1_en());

        tvSample2_pt.setText(verb.getSample_2_pt());
        tvSample2_en.setText(verb.getSample_2_en());

        tvSample3_pt.setText(verb.getSample_3_pt());
        tvSample3_en.setText(verb.getSample_3_en());

        mLayoutSample1.setVisibility(TextUtils.isEmpty(verb.getSample_1_pt().trim())? View.GONE:View.VISIBLE);
        mLayoutSample2.setVisibility(TextUtils.isEmpty(verb.getSample_2_pt().trim())? View.GONE:View.VISIBLE);
        mLayoutSample3.setVisibility(TextUtils.isEmpty(verb.getSample_3_pt().trim())? View.GONE:View.VISIBLE);
    }

    @Override
    protected void actionHasTTS(boolean hasTTS) {
        // TODO: 08/08/2018 screen indication?
    }

    public void handleTTSRequest(String ttsText){
        Log.d(TAG, "handleTTSRequest: TTS Text["+ttsText+"]");
        if (getHasTTSEngine()){
            doTTSSpeak(ttsText);
        }
        else{
            Toast.makeText(this, "TODO - Handle TTS", Toast.LENGTH_SHORT).show();
        }
    }

}
