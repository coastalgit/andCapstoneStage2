package com.bf.portugo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.model.QuestionCardData;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.QuizMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bf.portugo.common.Constants.Fonts.FONT_ITIM_REGULAR;
import static com.bf.portugo.common.Constants.WRONG_ANSWER_COUNT;


/*
 * @author frielb
 * Created on 10/08/2018
 */
public class QuestionCardPagerAdapter extends PagerAdapter {

    private static final String TAG = QuestionCardPagerAdapter.class.getSimpleName();

    private static int QUESTION_TYPE_1 = 1; // EN phrase with selectable PT answers
    private static int QUESTION_TYPE_2 = 2; // Listen to PT phrase and type in (TTS)
    private static int QUESTION_TYPE_3 = 3; // PT phrase with selectable EN answers

    private Typeface mFont;

    Context mContext;
    private List<QuestionCardData> mQuestionCardData;
    private List<CardView> mCards;

    private IPagerAdapterAction mListener;

    // TODO: 11/08/2018 remove
    public interface IPagerAdapterAction{
        //void onQuestionCardChanged(int pos);
        void setFABEnabled(boolean enabled);
        void setSkipEnabled(boolean enabled);
        void adjustScore(boolean answeredCorrect);
    }

    //public QuestionCardPagerAdapter(List<CardView> mViews, List<QuestionCardData> mCards) {
    public QuestionCardPagerAdapter(Context context, List<QuestionCardData> cards, IPagerAdapterAction listener) {
        Log.d(TAG, "QuestionCardPagerAdapter: ");
    //public QuestionCardPagerAdapter(Context context, List<QuestionCardData> cards) {
        this.mContext = context;
        this.mFont = Typeface.createFromAsset(mContext.getAssets(), FONT_ITIM_REGULAR);
        this.mCards = new ArrayList<>();
        this.mListener = listener;
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

    private int generateRandomAnswerIndex(){
        int maxVal = WRONG_ANSWER_COUNT-1;
        int minval = 0;
        return new Random().nextInt(maxVal-minval+1) + minval;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.d(TAG, "instantiateItem: Pos="+String.valueOf(position));

        // TODO: 11/08/2018 Determine best means of generating a quiz type

//        if (mListener != null) {
//            mListener.setFABEnabled(false);
//            mListener.setSkipEnabled(true);
//        }
        //View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
        //LayoutInflater inflater = LayoutInflater.from(mContext);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        // TODO: 11/08/2018 support for landscape layout
        View view = (View) inflater.inflate(R.layout.layout_qcard_type1, container, false);
        container.addView(view);


        // Bind content
        QuestionCardData qc = mQuestionCardData.get(position);
        Log.d(TAG, "instantiateItem: CARD at pos="+String.valueOf(position)+" selectedIndex="+String.valueOf(qc.getChosenAnswer()+" correct="+String.valueOf(qc.getChosenAnswerCorrect())));
        final TextView tvQuestion = view.findViewById(R.id.tv_quiz_question);
        tvQuestion.setTypeface(mFont);
        tvQuestion.setText(qc.getVerb().getWord_en());

        final CardView cardView = view.findViewById(R.id.layout_quizcard);

        FrameLayout mFrameAnswer0 = (FrameLayout) cardView.findViewById(R.id.answer_0);
        TextView mTvAnswer0 = (TextView) mFrameAnswer0.findViewById(R.id.tv_quiz_answer);
        mTvAnswer0.setTypeface(mFont);

        FrameLayout mFrameAnswer1 = (FrameLayout) cardView.findViewById(R.id.answer_1);
        TextView mTvAnswer1 = (TextView) mFrameAnswer1.findViewById(R.id.tv_quiz_answer);
        mTvAnswer1.setTypeface(mFont);

        FrameLayout mFrameAnswer2 = (FrameLayout) cardView.findViewById(R.id.answer_2);
        TextView mTvAnswer2 = (TextView) mFrameAnswer2.findViewById(R.id.tv_quiz_answer);
        mTvAnswer2.setTypeface(mFont);

        FrameLayout mFrameAnswer3 = (FrameLayout) cardView.findViewById(R.id.answer_3);
        TextView mTvAnswer3 = (TextView) mFrameAnswer3.findViewById(R.id.tv_quiz_answer);
        mTvAnswer3.setTypeface(mFont);
//
        int correctAnswerIndex = generateRandomAnswerIndex();
        if (qc.getAnswerPosition() != -1) {
            //existing
            correctAnswerIndex = qc.getAnswerPosition();
        }
        Log.d(TAG, "instantiateItem: Correct answer at "+String.valueOf(correctAnswerIndex));

        qc.setAnswerPosition(correctAnswerIndex);
        mQuestionCardData.set(position,qc);

        //if (correctAnswerIndex == 0) {
//        assignAnswerButton(cardView, correctAnswerIndex==0, qc, 0, mFrameAnswer0, mTvAnswer0);
//        assignAnswerButton(cardView, correctAnswerIndex==1, qc, 1, mFrameAnswer1, mTvAnswer1);
//        assignAnswerButton(cardView, correctAnswerIndex==2, qc, 2, mFrameAnswer2, mTvAnswer2);
//        assignAnswerButton(cardView, correctAnswerIndex==3, qc, 3, mFrameAnswer3, mTvAnswer3);
        assignAnswerButton(cardView, correctAnswerIndex, qc, 0, mFrameAnswer0, mTvAnswer0);
        assignAnswerButton(cardView, correctAnswerIndex, qc, 1, mFrameAnswer1, mTvAnswer1);
        assignAnswerButton(cardView, correctAnswerIndex, qc, 2, mFrameAnswer2, mTvAnswer2);
        assignAnswerButton(cardView, correctAnswerIndex, qc, 3, mFrameAnswer3, mTvAnswer3);


/*
        if (correctAnswerIndex == 0)
            answerRefs.set(0, ref3);
            assignCorrectAnswerButton(qc.getVerb().getWord_pt(),0, mFrameAnswer0, mTvAnswer0);
        else if (correctAnswerIndex == 1)
            assignCorrectAnswerButton(qc.getVerb().getWord_pt(),1, mFrameAnswer1, mTvAnswer1);
        else if (correctAnswerIndex == 2)
            assignCorrectAnswerButton(qc.getVerb().getWord_pt(),2, mFrameAnswer2, mTvAnswer2);
        else if (correctAnswerIndex == 3)
            assignCorrectAnswerButton(qc.getVerb().getWord_pt(),3, mFrameAnswer3, mTvAnswer3);


        assignAnswerButton(answerRefs, qc, 0, mFrameAnswer0, mTvAnswer0);
        assignAnswerButton(answerRefs, qc, 1, mFrameAnswer1, mTvAnswer1);
        assignAnswerButton(answerRefs, qc, 2, mFrameAnswer2, mTvAnswer2);
        assignAnswerButton(answerRefs, qc, 3, mFrameAnswer3, mTvAnswer3);

*/

        // in case of rotation, reshow previous selection
        if (qc.getChosenAnswer() != -1){
            Log.d(TAG, "instantiateItem: reselecting="+String.valueOf(qc.getChosenAnswer() == 0));
            if (qc.getChosenAnswer() == 0) mFrameAnswer0.performClick();
            else if (qc.getChosenAnswer() == 1) mFrameAnswer1.performClick();
            else if (qc.getChosenAnswer() == 2) mFrameAnswer2.performClick();
            else if (qc.getChosenAnswer() == 3) mFrameAnswer3.performClick();
        }

        mCards.set(position,cardView);
        return view;
    }

    //private void handleAnswerSelection(CardView cardView, int correctAnswerIndex, int selectedIndex, boolean isCorrect){
    private void handleAnswerSelection(CardView cardView, int correctAnswerIndex, int selectedIndex){

        int pos = ((QuizMainActivity)mContext).getViewModel().getActiveCardIndex();
        QuestionCardData qcd = mQuestionCardData.get(pos);
        qcd.setChosenAnswer(selectedIndex);
        boolean correct = correctAnswerIndex==selectedIndex;
        qcd.setChosenAnswerCorrect(correct);
        Log.d(TAG, "handleAnswerSelection: Pos="+String.valueOf(pos)+"("+qcd.getVerb().getWord_pt()+") Selected="+String.valueOf(selectedIndex)+" Correct="+String.valueOf(correct));
        mQuestionCardData.set(pos,qcd);


        //0
        FrameLayout frameAnswer0 = cardView.findViewById(R.id.answer_0);
        TextView tvAnswer0 = frameAnswer0.findViewById(R.id.tv_quiz_answer);
        FrameLayout frameAnswer1 = cardView.findViewById(R.id.answer_1);
        TextView tvAnswer1 = frameAnswer1.findViewById(R.id.tv_quiz_answer);
        FrameLayout frameAnswer2 = cardView.findViewById(R.id.answer_2);
        TextView tvAnswer2 = frameAnswer2.findViewById(R.id.tv_quiz_answer);
        FrameLayout frameAnswer3 = cardView.findViewById(R.id.answer_3);;
        TextView tvAnswer3 = frameAnswer3.findViewById(R.id.tv_quiz_answer);

//        int correctIndex = -1;
//        if (isCorrect)
//            correctIndex = selectedIndex;

/*
        configureAnswerButtonAfterSelection(selectedIndex==0,correctAnswerIndex==0, isCorrect,frameAnswer0,tvAnswer0);
        configureAnswerButtonAfterSelection(selectedIndex==1,correctAnswerIndex==1, isCorrect,frameAnswer1,tvAnswer1);
        configureAnswerButtonAfterSelection(selectedIndex==2,correctAnswerIndex==2, isCorrect,frameAnswer2,tvAnswer2);
        configureAnswerButtonAfterSelection(selectedIndex==3,correctAnswerIndex==3, isCorrect,frameAnswer3,tvAnswer3);
*/

        configureAnswerButtonAfterSelection(selectedIndex==0,correctAnswerIndex==0,frameAnswer0,tvAnswer0);
        configureAnswerButtonAfterSelection(selectedIndex==1,correctAnswerIndex==1,frameAnswer1,tvAnswer1);
        configureAnswerButtonAfterSelection(selectedIndex==2,correctAnswerIndex==2,frameAnswer2,tvAnswer2);
        configureAnswerButtonAfterSelection(selectedIndex==3,correctAnswerIndex==3,frameAnswer3,tvAnswer3);

//        configureAnswerButtonAfterSelection(selectedIndex==0,isCorrect,mFrameAnswer0,mTvAnswer0);
//        configureAnswerButtonAfterSelection(selectedIndex==1,isCorrect,mFrameAnswer1,mTvAnswer1);
//        configureAnswerButtonAfterSelection(selectedIndex==2,isCorrect,mFrameAnswer2,mTvAnswer2);
//        configureAnswerButtonAfterSelection(selectedIndex==3,isCorrect,mFrameAnswer3,mTvAnswer3);

//    private void handleAnswerSelection(List<QuestionCardAnswerRef> refs, int selectedIndex, boolean isCorrect){
//        configureAnswerButtonAfterSelection(selectedIndex==0,isCorrect,refs.get(0).getCardFrame(),refs.get(0).getCardText());
//        configureAnswerButtonAfterSelection(selectedIndex==1,isCorrect,refs.get(1).getCardFrame(),refs.get(1).getCardText());
//        configureAnswerButtonAfterSelection(selectedIndex==2,isCorrect,refs.get(2).getCardFrame(),refs.get(2).getCardText());
//        configureAnswerButtonAfterSelection(selectedIndex==3,isCorrect,refs.get(3).getCardFrame(),refs.get(3).getCardText());

        if (mListener != null) {
            mListener.setFABEnabled(true);
            mListener.setSkipEnabled(false);
        }
    }

    private void configureAnswerButtonAfterSelection(boolean wasSelected, boolean isCorrectAnswer, final FrameLayout answerFrame, final TextView answerTextView){
        //answerTextView.setEnabled(false);
        answerFrame.setEnabled(false);
        CardView card = answerFrame.findViewById(R.id.layout_card_answer);
        if (wasSelected){
            answerTextView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            //answerFrame.setBackgroundColor(mContext.getResources().getColor(isCorrect?R.color.colorAnswerOk:R.color.colorAnswerWrong));
            card.setCardBackgroundColor(mContext.getResources().getColor(isCorrectAnswer?R.color.colorAnswerOk:R.color.colorAnswerWrong));
            //answerTextView.setText("GOT ME");
        }
        else{
            answerTextView.setTextColor(mContext.getResources().getColor(isCorrectAnswer?R.color.colorAnswerOk:R.color.colorLGray));
            if (isCorrectAnswer)
                answerTextView.setTypeface(answerTextView.getTypeface(), Typeface.BOLD);
            //card.setCardBackgroundColor(mContext.getResources().getColor(isCorrect?R.color.colorAnswerOk:R.color.colorAnswerWrong));
//            if (isCorrect)
//                card.setCardBackgroundColor(mContext.getResources().getColor(isCorrect?R.color.colorAnswerOk:R.color.colorAnswerWrong));
            //answerTextView.setText("NOT ME");
        }
    }

/*
    private void configureAnswerButtonAfterSelection(boolean wasSelected, boolean isCorrectAnswer, boolean isCorrect, final FrameLayout answerFrame, final TextView answerTextView){
        //answerTextView.setEnabled(false);
        answerFrame.setEnabled(false);
        CardView card = answerFrame.findViewById(R.id.layout_card_answer);
        if (wasSelected){
            answerTextView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            //answerFrame.setBackgroundColor(mContext.getResources().getColor(isCorrect?R.color.colorAnswerOk:R.color.colorAnswerWrong));
            card.setCardBackgroundColor(mContext.getResources().getColor(isCorrect?R.color.colorAnswerOk:R.color.colorAnswerWrong));
            //answerTextView.setText("GOT ME");
        }
        else{
            answerTextView.setTextColor(mContext.getResources().getColor(isCorrectAnswer?R.color.colorAnswerOk:R.color.colorLGray));
            //card.setCardBackgroundColor(mContext.getResources().getColor(isCorrect?R.color.colorAnswerOk:R.color.colorAnswerWrong));
//            if (isCorrect)
//                card.setCardBackgroundColor(mContext.getResources().getColor(isCorrect?R.color.colorAnswerOk:R.color.colorAnswerWrong));
            //answerTextView.setText("NOT ME");
        }
    }
*/

    private void assignAnswerButton(final CardView cardView, final int correctAnswerIndex, QuestionCardData qcd, int answerIndex, final FrameLayout answerFrame, final TextView answerTextView){

        String answers = "Answers:";
        for (Verb answer: qcd.getWrongAnswers()) {
            answers = answers + " " +answer.getWord_pt();
        }
        Log.d(TAG, "assignAnswerButton: Card:["+qcd.getVerb().getWord_en()+"] "+answers);


        boolean isCorrect = (correctAnswerIndex == answerIndex);
        String answerTxt = qcd.getVerb().getWord_pt();

        if (!isCorrect){
            Verb v = qcd.getWrongAnswers().get(answerIndex);
            answerTxt = v.getWord_pt();
        }
        answerTextView.setText(answerTxt);

        String finalAnswerTxt = answerTxt;
        answerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+(isCorrect?"CORRECT":"WRONG")+" answer selected for index "+ String.valueOf(answerIndex) + ":["+ finalAnswerTxt +"]");
                if ((mListener != null) && qcd.getChosenAnswer()<0) {
                    mListener.adjustScore(isCorrect);
                }

                handleAnswerSelection(cardView, correctAnswerIndex, answerIndex);
            }
        });
    }

/*
    private void assignAnswerButton(final CardView cardView, final boolean isCorrect, QuestionCardData qcd, int answerIndex, final FrameLayout answerFrame, final TextView answerTextView){

        String answerTxt = qcd.getVerb().getWord_pt();
        if (!isCorrect){
            Verb v = qcd.getWrongAnswers().get(answerIndex);
            answerTxt = v.getWord_pt();
        }
        answerTextView.setText(answerTxt);

        String finalAnswerTxt = answerTxt;
        answerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+(isCorrect?"CORRECT":"WRONG")+" answer selected for index "+ String.valueOf(answerIndex) + ":["+ finalAnswerTxt +"]");
                handleAnswerSelection(cardView, answerIndex, isCorrect);
            }
        });
    }
*/

/*
    private void assignAnswerButton(CardView cardView, QuestionCardData qcd, int answerIndex, final FrameLayout answerFrame, final TextView answerText){
        answerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: WRONG answer selected for index "+ String.valueOf(answerIndex) + ":["+answerText+"]");
                handleAnswerSelection(cardView, answerIndex,false);
            }
        });
        Verb v = qcd.getWrongAnswers().get(answerIndex);
        answerText.setText(v.getWord_pt());
    }
*/

/*
    private void assignAnswerButton(List<QuestionCardAnswerRef> refs, QuestionCardData qcd, int answerIndex, final FrameLayout answerFrame, final TextView answerText){
        answerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: WRONG answer selected for index "+ String.valueOf(answerIndex) + ":["+answerText+"]");
                handleAnswerSelection(refs, answerIndex,false);
            }
        });
        Verb v = qcd.getWrongAnswers().get(answerIndex);
        answerText.setText(v.getWord_pt());
    }
*/

/*
    private void assignAnswerButton(QuestionCardData qcd, int answerIndex, final FrameLayout answerFrame, final TextView answerText){
        answerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: WRONG answer selected for index "+ String.valueOf(answerIndex) + ":["+answerText+"]");
                handleAnswerSelection(answerIndex,false);
            }
        });
        Verb v = qcd.getWrongAnswers().get(answerIndex);
        answerText.setText(v.getWord_pt());
    }
*/

/*
    private void assignCorrectAnswerButton(CardView cardView, String correctAnswer, int answerIndex, final FrameLayout answerFrame, final TextView answerText){
        answerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: CORRECT answer selected for index "+ String.valueOf(answerIndex) + ":["+answerText+"]");
                handleAnswerSelection(cardView, answerIndex,true);
            }
        });
        answerText.setText(correctAnswer);
    }
*/

/*
    private void assignCorrectAnswerButton(String correctAnswer, int answerIndex, final FrameLayout answerFrame, final TextView answerText){
        answerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: CORRECT answer selected for index "+ String.valueOf(answerIndex) + ":["+answerText+"]");
                handleAnswerSelection(answerIndex,true);
            }
        });
        answerText.setText(correctAnswer);
    }
*/

}
