package com.bf.portugo.ui.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bf.portugo.R;

import com.bf.portugo.util.NetworkUtils;
import com.bf.portugo.util.VerbHelper;
import com.bf.portugo.viewmodel.BaseViewModel;

import java.util.Locale;

import static com.bf.portugo.common.Constants.Fonts.FONT_ITIM_REGULAR;

/*
 * @author frielb
 * Created on 02/02/2018
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private static final int CODE_TTS_CHECK = 100;

    protected BaseViewModel mViewModelBase;
    protected TextToSpeech mTTS;
    protected Typeface mFont;

    private NetworkUtils mNetworkUtils;
    private int mTTSResult;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    private boolean mHasAudio;
    private static final String PREFKEY_HASAUDIO = "k_hasaudio";

    private boolean mHasTTSEngine;
    private static final String PREFKEY_HASTTS = "k_hastts";

    private int mScorePrevious, mScoreBest;
    private static final String PREFKEY_SCOREPREV = "k_scoreprev";
    private static final String PREFKEY_SCOREBEST = "k_scorebest";
    private static final String UTTERANCE_ID = "utteranceid";

    public interface IBaseActivityRoomFunc {
        void hasRoomVerbRecords(boolean hasVerbs);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate (BASE)");

        mNetworkUtils = new NetworkUtils();
        mFont = Typeface.createFromAsset(this.getAssets(), FONT_ITIM_REGULAR);

        mViewModelBase = ViewModelProviders.of(this).get(BaseViewModel.class);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mPrefsEditor = mPrefs.edit();
        refreshPrefs();
        initTTSEngine();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: (BASE)");
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
    }

    //region PREFS
    private void refreshPrefs() {
        mHasAudio = mPrefs.getBoolean(PREFKEY_HASAUDIO, true);
        mHasTTSEngine = mPrefs.getBoolean(PREFKEY_HASTTS, false);
        mScoreBest = mPrefs.getInt(PREFKEY_SCOREBEST, 0);
        mScorePrevious = mPrefs.getInt(PREFKEY_SCOREPREV, 0);
    }

    public boolean getHasAudio() {
        return mHasAudio;
    }

    protected void setHasAudio(boolean hasAudio) {
        this.mHasAudio = hasAudio;
        mPrefsEditor.putBoolean(PREFKEY_HASAUDIO, hasAudio);
        mPrefsEditor.commit();
        refreshPrefs();
    }

    public boolean getHasTTSEngine() {
        return mHasTTSEngine;
    }

    protected void setHasTTSEngine(boolean hasTTSEngine) {
        this.mHasTTSEngine = hasTTSEngine;
        mPrefsEditor.putBoolean(PREFKEY_HASTTS, hasTTSEngine);
        mPrefsEditor.commit();
        refreshPrefs();
        actionHasTTS(hasTTSEngine);
    }

    protected int getScorePrevious() {
        return mScorePrevious;
    }

    protected void setScorePrevious(int scorePrevious) {
        this.mScorePrevious = scorePrevious;
        mPrefsEditor.putInt(PREFKEY_SCOREPREV, scorePrevious);
        mPrefsEditor.commit();
        refreshPrefs();
    }

    protected int getScoreBest() {
        return mScoreBest;
    }

    protected void setScoreBest(int scoreBest) {
        this.mScoreBest = scoreBest;
        mPrefsEditor.putInt(PREFKEY_SCOREBEST, scoreBest);
        mPrefsEditor.commit();
        refreshPrefs();
    }
    //endregion PREFS

    public void hasVerbsInRoom(IBaseActivityRoomFunc listener) {
        mViewModelBase.hasVerbRecordsInRoom(verbs -> {
            if (listener != null){
                listener.hasRoomVerbRecords(VerbHelper.getListRecordCount(verbs) > 0);
            }
        });
    }

    @SuppressWarnings("Convert2Lambda")
    private void initTTSEngine(){
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    mTTSResult = mTTS.setLanguage(new Locale("pt_PT"));
                    //mTTSResult = mTTS.setLanguage(Locale.ENGLISH);
                    if (mTTSResult == TextToSpeech.LANG_MISSING_DATA || mTTSResult == TextToSpeech.LANG_NOT_SUPPORTED){
                        // TODO: 14/08/2018
                        // Toast.makeText(BaseActivity.this, "Lang not available (show dlg)", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        setHasTTSEngine(true);
                        mTTS.setPitch((float) 1.0);
                    }
                }
                else{
                    setHasTTSEngine(false);
                    setHasAudio(false);
                }
            }
        });
        mViewModelBase.setHasCheckedForTTS(true);
    }

    private void launchSystemTTSActivity(){
        Intent installTTSIntent = new Intent();
        installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        startActivityForResult(installTTSIntent, CODE_TTS_CHECK);
    }

    protected void doTTSSpeak(String ttsText){
        if (mTTS != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mTTS.speak(ttsText, TextToSpeech.QUEUE_FLUSH, null, UTTERANCE_ID);
            else
                mTTS.speak(ttsText, TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: (BASE)");
        if (requestCode== CODE_TTS_CHECK) {
            if(resultCode== TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                // if TTS resources are available, then init
                initTTSEngine();
            }
            else{
                setHasTTSEngine(false);
                setHasAudio(false);
            }
        }
    }

    // TODO: 15/08/2018  
    public void showDialogTTSAction() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.audio)
                .setMessage(R.string.app_needs_tts)
                .setPositiveButton(R.string.install_tts, (dialog1, which) -> launchSystemTTSActivity())
                .setNegativeButton(R.string.cancel, null)
                .create();
        dialog.show();
    }

    public void showDialogNoOfflineAbility() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.connectivity)
                .setMessage(R.string.connection_friendly_msg)
                .setNegativeButton(R.string.ok, null)
                .create();
        dialog.show();
    }

    public void showDialogLearningRequired() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.learning)
                .setMessage(R.string.quiz_learn_friendly_msg)
                .setNegativeButton(R.string.ok, null)
                .create();
        dialog.show();
    }

    public NetworkUtils getNetworkUtils() {
        return mNetworkUtils;
    }

    protected abstract void actionHasTTS(boolean hasTTS);

}
