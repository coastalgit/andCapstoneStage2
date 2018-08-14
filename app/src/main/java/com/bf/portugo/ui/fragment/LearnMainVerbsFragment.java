package com.bf.portugo.ui.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.portugo.BuildConfig;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsRecyclerViewAdapter;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.LearnMainActivity;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;
import com.google.android.gms.ads.MobileAds;

import static com.bf.portugo.common.Enums.*;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnMainVerbsFragment extends Fragment{

    private static final String TAG = LearnMainVerbsFragment.class.getSimpleName();

    private static final String KEY_VERBFILTER = "key_verbfilter";

    private VerbFilter mVerbFilter = VerbFilter.ALL;
    private OnLearnMainVerbFragmentInteractionListener mListener;
    private LearnMainVerbsRecyclerViewAdapter mVerbsAdapter;

    @BindView(R.id.recyclerview_learnmain)
    RecyclerView mRecyclerViewVerbs;

    public interface OnLearnMainVerbFragmentInteractionListener {
        void onVerbItemClick(Verb verbItem);
    }

    public LearnMainVerbsFragment() {
        //Mandatory empty public constructor
    }

    public static LearnMainVerbsFragment newInstance(VerbFilter verbFilter) {
        LearnMainVerbsFragment fragment = new LearnMainVerbsFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_VERBFILTER, verbFilter);
        fragment.setArguments(args);
        return fragment;
    }

    private void addAdMob(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mVerbFilter = (VerbFilter) getArguments().getSerializable(KEY_VERBFILTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainverbitems_list, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewVerbs.setLayoutManager(linearLayoutManager);
        mRecyclerViewVerbs.setHasFixedSize(true);

        mVerbsAdapter = new LearnMainVerbsRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerViewVerbs.setAdapter(mVerbsAdapter);
        subscribeUI();

        return rootView;
    }


    private void subscribeUI(){
        LearnVerbsMainViewModel vm = ((LearnMainActivity)getActivity()).getViewModel();

        if (mVerbFilter == VerbFilter.ESSENTIAL){
            vm.getObservableVerbsEssential().observe(this, new Observer<List<Verb>>() {
                @Override
                public void onChanged(@Nullable List<Verb> verbs) {
                    Log.d(TAG, "onChanged(ESS):");
                    if ((verbs != null) && (verbs.size() > 0)) {
                        Log.d(TAG, "onChanged(ESS): verbs="+String.valueOf(verbs.size()));
                    }
                    mVerbsAdapter.reloadAdapter(verbs);
                }
            });
        }
        else{
            vm.getObservableVerbsAll().observe(this, new Observer<List<Verb>>() {
                @Override
                public void onChanged(@Nullable List<Verb> verbs) {
                    Log.d(TAG, "onChanged(ALL):");
                    if ((verbs != null) && (verbs.size() > 0)) {
                        Log.d(TAG, "onChanged(ALL): verbs="+String.valueOf(verbs.size()));
                    }
                    mVerbsAdapter.reloadAdapter(verbs);
                }
            });
        }

    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        if (context instanceof OnLearnMainVerbFragmentInteractionListener) {
            mListener = (OnLearnMainVerbFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        mListener = null;
    }

    public LearnMainVerbsRecyclerViewAdapter getVerbsAdapter() {
        return mVerbsAdapter;
    }

}
