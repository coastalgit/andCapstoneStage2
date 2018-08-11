package com.bf.portugo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.model.QuestionCard;
import com.bf.portugo.model.Verb;

import org.w3c.dom.ls.LSException;

import java.util.List;


/*
 * @author frielb
 * Created on 10/08/2018
 */
public class QuestionCardPagerAdapter extends PagerAdapter {

    private static final String TAG = QuestionCardPagerAdapter.class.getSimpleName();

    private static int QUESTION_TYPE_1 = 1; // EN phrase with selectable PT answers
    private static int QUESTION_TYPE_2 = 2; // Listen to PT phrase and type in (TTS)
    private static int QUESTION_TYPE_3 = 3; // PT phrase with selectable EN answers

    //private List<CardView> mViews;
    Context mContext;
    private List<QuestionCard> mCards;
    //private float mBaseElevation;

    private IPagerAdapterAction mListener;

    public interface IPagerAdapterAction{
        void onQuestionCardChanged(int pos);
    }
    //public QuestionCardPagerAdapter(List<CardView> mViews, List<QuestionCard> mCards) {
    //public QuestionCardPagerAdapter(Context context, List<QuestionCard> cards, IPagerAdapterAction listener) {
    public QuestionCardPagerAdapter(Context context, List<QuestionCard> cards) {
        this.mContext = context;
        //this.mListener = listener;
        //this.mViews = mViews;
        this.mCards = cards;
        if (mCards != null) {
            for (QuestionCard qc :mCards) {
                String answers = "Answers:";
                for (Verb answer: qc.getWrongAnswers()) {
                    answers = answers + " " +answer.getWord_pt();
                }
                Log.d(TAG, "QuestionCardPagerAdapter: Card:["+qc.getVerb().getWord_en()+"] "+answers);
            }

        }
    }

    public QuestionCard getQuestionCardAtPosition(int position){
        if (mCards == null)
            return null;
        return mCards.get(position);
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        //mViews.set(position, null);
    }

    @Override
    public int getCount() {
        if (mCards == null)
            return 0;
        return mCards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.d(TAG, "instantiateItem: Pos="+String.valueOf(position));

        QuestionCard qc = mCards.get(position);
        // TODO: 11/08/2018 Determine best means of generating a quiz type

        LayoutInflater inflater = LayoutInflater.from(mContext);

        // TODO: 11/08/2018 support for landscape layout
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.layout_questioncard_type1, container, false);

        final TextView tvQuestion = view.findViewById(R.id.tv_quiz_question);

        tvQuestion.setText(qc.getVerb().getWord_en());

//        Button button = view.findViewById(R.id.button);
//        button.setText(mListData.get(position));
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText(mListData.get(position));
//            }
//        });

        container.addView(view);
        return view;
    }
}
