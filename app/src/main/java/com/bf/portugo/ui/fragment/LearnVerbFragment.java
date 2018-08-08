package com.bf.portugo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsRecyclerViewAdapter;
import com.bf.portugo.common.Enums;
import com.bf.portugo.common.Enums.VerbTense;
import com.bf.portugo.helper.VerbHelper;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.LearnMainActivity;
import com.bf.portugo.ui.activity.LearnVerbActivity;
import com.bf.portugo.viewmodel.LearnVerbViewModel;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LearnVerbFragment extends Fragment{

    private static final String TAG = LearnVerbFragment.class.getSimpleName();

    private static final String KEY_VERBTENSE = "key_verbtense";
    private VerbTense mVerbTense;

    //private OnLearnVerbFragmentInteractionListener mListener;
    private LearnMainVerbsRecyclerViewAdapter mVerbsAdapter;

    @BindView(R.id.layout_card_verbtensegroup)
    CardView mCardTenseGroup;

    @BindView(R.id.tv_verbtense_i_pt)
    TextView mTvVerb_tense_I_pt;
    @BindView(R.id.tv_verbtense_i_en)
    TextView mTvVerb_tense_I_en;

    @BindView(R.id.tv_verbtense_you_pt)
    TextView mTvVerb_tense_You_pt;
    @BindView(R.id.tv_verbtense_you_en)
    TextView mTvVerb_tense_You_en;

    @BindView(R.id.tv_verbtense_heshe_pt)
    TextView mTvVerb_tense_HeShe_pt;
    @BindView(R.id.tv_verbtense_heshe_en)
    TextView mTvVerb_tense_HeShe_en;

    @BindView(R.id.tv_verbtense_we_pt)
    TextView mTvVerb_tense_We_pt;
    @BindView(R.id.tv_verbtense_we_en)
    TextView mTvVerb_tense_We_en;

    @BindView(R.id.tv_verbtense_they_pt)
    TextView mTvVerb_tense_They_pt;
    @BindView(R.id.tv_verbtense_they_en)
    TextView mTvVerb_tense_They_en;

/*
    public interface OnLearnVerbFragmentInteractionListener {
        void onVerbItemClick(Verb verbItem);
    }
*/

    public LearnVerbFragment() {
    // Mandatory empty public constructor
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
/*
    public static LearnMainVerbsFragment newInstance(int columnCount) {
        LearnMainVerbsFragment fragment = new LearnMainVerbsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
*/

    public static LearnVerbFragment newInstance(VerbTense verbTense) {
        LearnVerbFragment fragment = new LearnVerbFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_VERBTENSE, verbTense);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mVerbTense = (VerbTense) getArguments().getSerializable(KEY_VERBTENSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verbtenseitem, container, false);
        ButterKnife.bind(this, rootView);

        mTvVerb_tense_I_pt.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_You_pt.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_HeShe_pt.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_We_pt.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_They_pt.setTypeface(((LearnVerbActivity)getActivity()).getFont());

        mTvVerb_tense_I_en.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_You_en.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_HeShe_en.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_We_en.setTypeface(((LearnVerbActivity)getActivity()).getFont());
        mTvVerb_tense_They_en.setTypeface(((LearnVerbActivity)getActivity()).getFont());

        populateVerbByTense();

        return rootView;
    }


    private void populateVerbByTense(){
        LearnVerbViewModel vm = ((LearnVerbActivity)getActivity()).getViewModel();

        Verb verb = vm.getVerb();
        Log.d(TAG, "populateVerbByTense: Verb["+verb.getWord_pt()+"]");

        switch(mVerbTense){
            case PAST:
                mTvVerb_tense_I_pt.setText(verb.getTense_past_pt_i());
                mTvVerb_tense_You_pt.setText(verb.getTense_past_pt_you());
                mTvVerb_tense_HeShe_pt.setText(verb.getTense_past_pt_heshe());
                mTvVerb_tense_We_pt.setText(verb.getTense_past_pt_we());
                mTvVerb_tense_They_pt.setText(verb.getTense_past_pt_they());
                break;
            case PRESENT:
                mTvVerb_tense_I_pt.setText(verb.getTense_pres_pt_i());
                mTvVerb_tense_You_pt.setText(verb.getTense_pres_pt_you());
                mTvVerb_tense_HeShe_pt.setText(verb.getTense_pres_pt_heshe());
                mTvVerb_tense_We_pt.setText(verb.getTense_pres_pt_we());
                mTvVerb_tense_They_pt.setText(verb.getTense_pres_pt_they());
                break;
            case FUTURE:
                mTvVerb_tense_I_pt.setText(verb.getTense_fut_pt_i());
                mTvVerb_tense_You_pt.setText(verb.getTense_fut_pt_you());
                mTvVerb_tense_HeShe_pt.setText(verb.getTense_fut_pt_heshe());
                mTvVerb_tense_We_pt.setText(verb.getTense_fut_pt_we());
                mTvVerb_tense_They_pt.setText(verb.getTense_fut_pt_they());
                break;
        }

        mTvVerb_tense_I_en.setText(VerbHelper.buildEnglishString(getActivity(),mVerbTense, Enums.VerbPrefix_EN.I,verb));
        mTvVerb_tense_You_en.setText(VerbHelper.buildEnglishString(getActivity(),mVerbTense, Enums.VerbPrefix_EN.YOU,verb));
        mTvVerb_tense_HeShe_en.setText(VerbHelper.buildEnglishString(getActivity(),mVerbTense, Enums.VerbPrefix_EN.HESHE,verb));
        mTvVerb_tense_We_en.setText(VerbHelper.buildEnglishString(getActivity(),mVerbTense, Enums.VerbPrefix_EN.WE,verb));
        mTvVerb_tense_They_en.setText(VerbHelper.buildEnglishString(getActivity(),mVerbTense, Enums.VerbPrefix_EN.THEY,verb));

    }

/*
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        if (context instanceof OnLearnVerbFragmentInteractionListener) {
            mListener = (OnLearnVerbFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        mListener = null;
    }
*/

/*
    @BindView(R.id.tv_verbtense_you_pt)
    TextView mTvVerb_tense_You_pt;
    @BindView(R.id.tv_verbtense_you_en)
    TextView mTvVerb_tense_You_en;

    @BindView(R.id.tv_verbtense_heshe_pt)
    TextView mTvVerb_tense_HeShe_pt;
    @BindView(R.id.tv_verbtense_heshe_en)
    TextView mTvVerb_tense_HeShe_en;

    @BindView(R.id.tv_verbtense_we_pt)
    TextView mTvVerb_tense_We_pt;
    @BindView(R.id.tv_verbtense_we_en)
    TextView mTvVerb_tense_We_en;

    @BindView(R.id.tv_verbtense_they_pt)
    TextView mTvVerb_tense_They_pt;
    @BindView(R.id.tv_verbtense_they_en)
    TextView mTvVerb_tense_They_en;
*/

    private void speakTTS(String ttsText){
        ((LearnVerbActivity)getActivity()).handleTTSRequest(ttsText);
    }

    @OnClick(R.id.tv_verbtense_i_pt)
    public void tv_I_onClick(TextView tv){
        speakTTS(tv.getText().toString());
    }

    @OnClick(R.id.tv_verbtense_you_pt)
    public void tv_You_onClick(TextView tv){
        speakTTS(tv.getText().toString());
    }

    @OnClick(R.id.tv_verbtense_heshe_pt)
    public void tv_HeShe_onClick(TextView tv){
        speakTTS(tv.getText().toString());
    }

    @OnClick(R.id.tv_verbtense_we_pt)
    public void tv_We_onClick(TextView tv){
        speakTTS(tv.getText().toString());
    }

    @OnClick(R.id.tv_verbtense_they_pt)
    public void tv_They_onClick(TextView tv){
        speakTTS(tv.getText().toString());
    }

}
