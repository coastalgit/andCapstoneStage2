package com.bf.portugo.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsRecyclerViewAdapter;
import com.bf.portugo.data.VerbStockData;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.LearnMainActivity;
import com.bf.portugo.ui.dummy.DummyContent;
import com.bf.portugo.viewmodel.LearnVerbsMainViewModel;

import static com.bf.portugo.common.Enums.*;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnVerbItemsFragment extends Fragment{

    private static final String TAG = LearnVerbItemsFragment.class.getSimpleName();


    // TODO: Customize parameter argument names
    //private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String KEY_VERBFILTER = "key_verbfilter";
    //private int mColumnCount = 1;
    private VerbFilter mVerbFilter = VerbFilter.ALL;

    private OnLearnVerbFragmentInteractionListener mListener;
    private LearnMainVerbsRecyclerViewAdapter mVerbsAdapter;

    @BindView(R.id.recyclerview_learnmain)
    RecyclerView mRecyclerViewVerbs;

    public interface OnLearnVerbFragmentInteractionListener {
        void onVerbItemClick(Verb verbItem);
    }

    public LearnVerbItemsFragment() {
    // Mandatory empty public constructor
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
/*
    public static LearnVerbItemsFragment newInstance(int columnCount) {
        LearnVerbItemsFragment fragment = new LearnVerbItemsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
*/

    public static LearnVerbItemsFragment newInstance(VerbFilter verbFilter) {
        LearnVerbItemsFragment fragment = new LearnVerbItemsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verbitem_list, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewVerbs.setLayoutManager(linearLayoutManager);
        mRecyclerViewVerbs.setHasFixedSize(true);
        mVerbsAdapter = new LearnMainVerbsRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerViewVerbs.setAdapter(mVerbsAdapter);
        subscribeUI();
/*
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new LearnMainVerbsRecyclerViewAdapter(getActivity(), DummyContent.ITEMS, mListener));
        }
*/
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
                        Log.d(TAG, "onChanged(ESS): wordset="+String.valueOf(verbs.size()));
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
                        Log.d(TAG, "onChanged(ALL): wordset="+String.valueOf(verbs.size()));
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

    public LearnMainVerbsRecyclerViewAdapter getVerbsAdapter() {
        return mVerbsAdapter;
    }

}
