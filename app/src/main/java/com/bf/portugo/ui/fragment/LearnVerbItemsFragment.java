package com.bf.portugo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bf.portugo.R;
import com.bf.portugo.adapter.LearnMainVerbsRecyclerViewAdapter;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.dummy.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnVerbItemsFragment extends Fragment{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

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
    public static LearnVerbItemsFragment newInstance(int columnCount) {
        LearnVerbItemsFragment fragment = new LearnVerbItemsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
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
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLearnVerbFragmentInteractionListener) {
            mListener = (OnLearnVerbFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
