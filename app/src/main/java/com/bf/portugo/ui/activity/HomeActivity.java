package com.bf.portugo.ui.activity;

/*
        Copyright [2018] [flavioarfaria]
        https://github.com/flavioarfaria/KenBurnsView

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.portugo.R;
import com.bf.portugo.datacreation.DBFuncsActivity;
import com.bf.portugo.viewmodel.HomeViewModel;
import com.flaviofaria.kenburnsview.KenBurnsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bf.portugo.common.Constants.Fonts.FONT_ITIM_REGULAR;
import static com.bf.portugo.common.Constants.Fonts.FONT_LOBSTER_REGULAR;

/**
 * Responsible for checking if TTS engine is resident, and
 */
public class HomeActivity extends BaseActivity {

    private String TAG = HomeActivity.class.getSimpleName();
    private HomeViewModel mViewModel;

    private Typeface mTitleFont;

    private KenBurnsView mKbv;

    @BindView(R.id.tv_home_title)
    TextView mTvTitle;

    @BindView(R.id.btn_home_learn)
    Button mBtnLearn;
    @BindView(R.id.btn_home_quiz)
    Button mBtnQuiz;
    @BindView(R.id.btn_home_audiotoggle)
    Button mBtnAudioToggle;

    @BindView(R.id.tv_home_loading)
    TextView mTvLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: (HOME)");
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        setControlsVisibility(false);

        mTitleFont = Typeface.createFromAsset(this.getAssets(), FONT_LOBSTER_REGULAR);
        mTvTitle.setTypeface(mTitleFont);

        mKbv = (KenBurnsView) findViewById(R.id.iv_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mKbv.setImageDrawable(getDrawable(R.drawable.splashland));
        }

        updateAudioButton(getHasTTSEngine(),getHasAudio());
    }

    @Override
    protected void actionHasTTS(boolean hasTTS) {
        if (hasTTS) {
            updateAudioButton(true,getHasAudio());
            Toast.makeText(this, ">>> Vamos", Toast.LENGTH_SHORT).show();
            if (getHasAudio()) {
                if (!mViewModel.getHasAlreadySpokenWelcome()) {
                    sayWelcomeMessage();
                    mViewModel.setHasAlreadySpokenWelcome(true);
                }
            }
            setControlsVisibility(true);
        }
        else {
            Toast.makeText(this, ">>> TTS unavailable", Toast.LENGTH_SHORT).show();
            updateAudioButton(false,false);
        }
    }

    private void sayWelcomeMessage(){
        doTTSSpeak(getString(R.string.bemvindo));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mKbv.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mKbv.pause();
        mTTS.stop();
    }

    private void setControlsVisibility(boolean visible){
        mTvLoading.setVisibility(visible?View.INVISIBLE:View.VISIBLE);
        mBtnLearn.setVisibility(visible?View.VISIBLE:View.INVISIBLE);
        mBtnQuiz.setVisibility(visible?View.VISIBLE:View.INVISIBLE);
    }

    //private void checkSetup

    @OnClick(R.id.btn_populatedb_fb)
    public void btnPopulateDb_onClick(Button btn){
        Intent intent = new Intent(this, DBFuncsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_home_learn)
    public void btnHomeLearn_onClick(Button btn){
        Intent intent = new Intent(this, LearnMainActivity.class);
        determineProgress(intent, false);
    }

    @OnClick(R.id.btn_home_quiz)
    public void btnHomeQuiz_onClick(Button btn){
        Intent intent = new Intent(this, QuizMainActivity.class);
        determineProgress(intent, true);
    }

    private void launchNextActivity(Intent intent){
        startActivity(intent);
    };

    private void determineProgress(Intent intent, boolean isQuiz){
        hasVerbsInRoom(new IBaseActivityRoomFunc() {
            @Override
            public void hasRoomVerbRecords(boolean hasVerbs) {
                if (getNetworkUtils().isConnected(HomeActivity.this)){
                    if (hasVerbs)
                        launchNextActivity(intent);
                    else{
                        if (isQuiz)
                            // populates Room DB from Firebase
                            showDialogLearningRequired();
                        else
                            launchNextActivity(intent);
                    }
                }
                else{
                    if (hasVerbs)
                        launchNextActivity(intent);
                    else
                        showDialogNoOfflineAbility();
                }
            }
        });
    }

    @OnClick(R.id.btn_home_audiotoggle)
    public void btnAudioToggle_onClick(Button btn){
        boolean currentState = getHasAudio();
        setHasAudio(!currentState);
        updateAudioButton(true,getHasAudio());
    }

    private void updateAudioButton(boolean isVisible, boolean hasAudio){
        mBtnAudioToggle.setVisibility(isVisible?View.VISIBLE:View.INVISIBLE);
        mBtnAudioToggle.setText(hasAudio?"DISABLE AUDIO":"ENABLE AUDIO");
    }

}
