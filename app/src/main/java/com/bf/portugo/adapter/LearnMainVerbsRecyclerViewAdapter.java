package com.bf.portugo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.fragment.LearnMainVerbsFragment;


import java.util.List;

import static com.bf.portugo.common.Constants.Fonts.FONT_ITIM_REGULAR;

@SuppressWarnings("ALL")
public class LearnMainVerbsRecyclerViewAdapter extends RecyclerView.Adapter<LearnMainVerbsRecyclerViewAdapter.VerbViewHolder> {

    private static final String TAG = LearnMainVerbsRecyclerViewAdapter.class.getSimpleName();

    private final Context mContext;
    private List<Verb> mVerbs;
    private final LearnMainVerbsFragment.OnLearnMainVerbFragmentInteractionListener mListener;
    private final Typeface mFont;

    public LearnMainVerbsRecyclerViewAdapter(Context mContext, LearnMainVerbsFragment.OnLearnMainVerbFragmentInteractionListener listener) {
        this.mContext = mContext;
        mListener = listener;
        mFont = Typeface.createFromAsset(mContext.getAssets(), FONT_ITIM_REGULAR);
    }

    public void reloadAdapter(List<Verb> verbs){
        this.mVerbs = verbs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VerbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mainverbitem, parent, false);
        return new VerbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerbViewHolder holder, int position) {
        holder.mVerb = mVerbs.get(position);
        holder.mTvVerb_PT.setText(mVerbs.get(position).getWord_pt());
        holder.mTvVerb_EN.setText(mVerbs.get(position).getWord_en());

        //ripple support
        holder.mContainer.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onVerbItemClick(holder.mVerb);
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
        final TextView mTvVerb_EN;
        final TextView mTvVerb_PT;
        final LinearLayout mContainer;//for ripple click events

        Verb mVerb;

        @SuppressWarnings("RedundantCast")
        VerbViewHolder(View view) {
            super(view);
            mContainer = (LinearLayout) view.findViewById(R.id.layout_card_container);
            mTvVerb_PT = (TextView) view.findViewById(R.id.tv_mainverb_pt);
            mTvVerb_PT.setTypeface(mFont);
            mTvVerb_EN = (TextView) view.findViewById(R.id.tv_mainverb_en);
            mTvVerb_EN.setTypeface(mFont);
        }
    }
}
