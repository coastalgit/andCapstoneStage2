package com.bf.portugo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.model.QuestionCardData;
import com.bf.portugo.model.Verb;

import java.util.ArrayList;
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
    private List<QuestionCardData> mQuestionCardData;
    private List<CardView> mCards;
    //private float mBaseElevation;

    private IPagerAdapterAction mListener;

    // TODO: 11/08/2018 remove
    public interface IPagerAdapterAction{
        void onQuestionCardChanged(int pos);
    }

    //public QuestionCardPagerAdapter(List<CardView> mViews, List<QuestionCardData> mCards) {
    //public QuestionCardPagerAdapter(Context context, List<QuestionCardData> cards, IPagerAdapterAction listener) {
    public QuestionCardPagerAdapter(Context context, List<QuestionCardData> cards) {
        this.mContext = context;
        this.mCards = new ArrayList<>();
        //this.mListener = listener;
        //this.mViews = mViews;
        this.mQuestionCardData = cards;
        if (mQuestionCardData != null) {
            for (QuestionCardData qc :mQuestionCardData) {
                String answers = "Answers:";
                for (Verb answer: qc.getWrongAnswers()) {
                    answers = answers + " " +answer.getWord_pt();
                }
                Log.d(TAG, "QuestionCardPagerAdapter: Card:["+qc.getVerb().getWord_en()+"] "+answers);
                mCards.add(null);
            }

        }
    }

    //public void addQuestionCard()

/*
    public CardView getCardAtPosition(int position){
//        if (mCards == null)
//            return null;
        return mCards.get(position);
    };
*/

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mCards.set(position, null);
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

        // TODO: 11/08/2018 Determine best means of generating a quiz type

        //View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
        //LayoutInflater inflater = LayoutInflater.from(mContext);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        // TODO: 11/08/2018 support for landscape layout
        View view = (View) inflater.inflate(R.layout.layout_qcard_type1, container, false);
        container.addView(view);


        // Bind content
        QuestionCardData qc = mQuestionCardData.get(position);
        final TextView tvQuestion = view.findViewById(R.id.tv_quiz_question);
        tvQuestion.setText(qc.getVerb().getWord_en());

        final CardView cardView = view.findViewById(R.id.layout_quizcard);

//        Button button = view.findViewById(R.id.button);
//        button.setText(mListData.get(position));
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText(mListData.get(position));
//            }
//        });

        mCards.set(position,cardView);
        return view;
    }
}
