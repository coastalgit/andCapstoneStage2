package com.bf.portugo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.fragment.LearnMainVerbsFragment;


import java.util.List;

import static com.bf.portugo.common.Constants.Fonts.FONT_ITIM_REGULAR;

public class LearnMainVerbsRecyclerViewAdapter extends RecyclerView.Adapter<LearnMainVerbsRecyclerViewAdapter.VerbViewHolder> {

    private final Context mContext;
    private List<Verb> mVerbs;
    private final LearnMainVerbsFragment.OnLearnMainVerbFragmentInteractionListener mListener;
    private Typeface mFont;

    //public LearnMainVerbsRecyclerViewAdapter(Context mContext, List<Verb> verbs, OnListFragmentInteractionListener listener) {
    public LearnMainVerbsRecyclerViewAdapter(Context mContext, LearnMainVerbsFragment.OnLearnMainVerbFragmentInteractionListener listener) {
        this.mContext = mContext;
        //mVerbs = verbs;
        mListener = listener;
        mFont = Typeface.createFromAsset(mContext.getAssets(), FONT_ITIM_REGULAR);
    }

    public void reloadAdapter(List<Verb> verbs){
        this.mVerbs = verbs;
        notifyDataSetChanged();
    }

    @Override
    public VerbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mainverbitem, parent, false);
        return new VerbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VerbViewHolder holder, int position) {
        holder.mVerb = mVerbs.get(position);
        holder.mTvVerb_PT.setText(mVerbs.get(position).getWord_pt());
        holder.mTvVerb_EN.setText(mVerbs.get(position).getWord_en());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onVerbItemClick(holder.mVerb);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mVerbs == null)
            return 0;
        return mVerbs.size();
    }

    public class VerbViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTvVerb_EN;
        public final TextView mTvVerb_PT;
        //public final TextView mContentView;
        public Verb mVerb;

        public VerbViewHolder(View view) {
            super(view);
            mView = view;
            mTvVerb_PT = (TextView) view.findViewById(R.id.tv_mainverb_pt);
            mTvVerb_PT.setTypeface(mFont);
            mTvVerb_EN = (TextView) view.findViewById(R.id.tv_mainverb_en);
            mTvVerb_EN.setTypeface(mFont);

//            itemView.setOnClickListener(view -> {
//            int pos = getAdapterPosition();
//            WordSet ws = mWordSetList.get(pos);
//            mListener.onClick(ws);

/*
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
*/
        }
    }
}
