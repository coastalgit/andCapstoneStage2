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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bf.portugo.R;
import com.bf.portugo.datacreation.DBFuncsActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Responsible for checking if TTS engine is resident, and
 */
public class HomeActivity extends BaseActivity {

    private String TAG = HomeActivity.class.getSimpleName();
    private static final int CODE_TTS_CHECK = 100;

    private KenBurnsView mKbv;

    @BindView(R.id.btn_home_learn)
    Button mBtnLearn;
    @BindView(R.id.btn_home_quiz)
    Button mBtnQuiz;

    @BindView(R.id.tv_home_loading)
    TextView mTvLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: (HOME)");
        ButterKnife.bind(this);

        setControlsVisibility(false);

        mKbv = (KenBurnsView) findViewById(R.id.iv_home);
//        mKbv.setTransitionListener(new KenBurnsView.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//
//            }
//            @Override
//            public void onTransitionEnd(Transition transition) {
//
//            }
//        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mKbv.resume();
//            }
//        }, 1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mKbv.setImageDrawable(getDrawable(R.drawable.splashland));
        }

    }

    @Override
    protected void actionHasTTS(boolean hasTTS) {
        if (hasTTS) {
            Toast.makeText(this, ">>> Vamos", Toast.LENGTH_SHORT).show();
            doTTSSpeak(getString(R.string.bemvindo));
            setControlsVisibility(true);
        }
        else
            Toast.makeText(this, ">>> TTS unavailable", Toast.LENGTH_SHORT).show();
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
        startActivity(intent);
    }

}
