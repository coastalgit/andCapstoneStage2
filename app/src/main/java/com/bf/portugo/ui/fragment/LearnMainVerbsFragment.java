package com.bf.portugo.ui.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsRecyclerViewAdapter;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.LearnMainActivity;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

import static com.bf.portugo.common.Enums.*;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class LearnMainVerbsFragment extends Fragment{

    private static final String TAG = LearnMainVerbsFragment.class.getSimpleName();

    private static final String KEY_VERBFILTER = "key_verbfilter";
    private static final String EXTRA_LISTSTATE = "recyclerpos";

    private VerbFilter mVerbFilter = VerbFilter.ALL;
    private OnLearnMainVerbFragmentInteractionListener mListener;
    private LearnMainVerbsRecyclerViewAdapter mVerbsAdapter;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mListState;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mVerbFilter = (VerbFilter) getArguments().getSerializable(KEY_VERBFILTER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView("+mVerbFilter.toString()+"): ");
        View rootView = inflater.inflate(R.layout.fragment_mainverbitems_list, container, false);
        ButterKnife.bind(this, rootView);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewVerbs.setLayoutManager(mLayoutManager);
        mRecyclerViewVerbs.setHasFixedSize(true);

        //noinspection ConstantConditions
        mVerbsAdapter = new LearnMainVerbsRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerViewVerbs.setAdapter(mVerbsAdapter);
        subscribeUI();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onSaveInstanceState("+mVerbFilter.toString()+"): ");
        super.onSaveInstanceState(savedInstanceState);
        mListState = mLayoutManager.onSaveInstanceState();
        savedInstanceState.putParcelable(EXTRA_LISTSTATE, mListState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated("+mVerbFilter.toString()+"): ");
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mListState = savedInstanceState.getParcelable(EXTRA_LISTSTATE);
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume("+mVerbFilter.toString()+"):");
        super.onResume();
        if (mListState != null) {
            new Handler().postDelayed(() -> {
                mLayoutManager.onRestoreInstanceState(mListState);
                //mRecyclerViewVerbs.scrollToPosition(mLayoutManager.findFirstVisibleItemPosition());
            }, 300);
        }
    }

    @SuppressWarnings("Convert2Lambda")
    private void subscribeUI(){
        @SuppressWarnings("ConstantConditions") LearnVerbsMainViewModel vm = ((LearnMainActivity)getActivity()).getViewModel();

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
        Log.d(TAG, "onAttach("+mVerbFilter.toString()+"): ");
        super.onAttach(context);
        if (context instanceof OnLearnMainVerbFragmentInteractionListener) {
            mListener = (OnLearnMainVerbFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach("+mVerbFilter.toString()+"): ");
        super.onDetach();
        mListener = null;
    }

}
