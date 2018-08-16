package com.bf.portugo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bf.portugo.R;
import com.bf.portugo.model.QuestionCardData;
import com.bf.portugo.model.QuestionCardData_End;
import com.bf.portugo.model.QuestionCardData_Type1;
import com.bf.portugo.model.QuestionCardData_Type2;
import com.bf.portugo.model.Verb;
import com.bf.portugo.ui.activity.QuizMainActivity;
import com.bf.portugo.util.VerbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bf.portugo.common.Constants.Fonts.FONT_ITIM_REGULAR;
import static com.bf.portugo.common.Constants.WRONG_ANSWER_COUNT;

/*
 * @author frielb
 * Created on 10/08/2018
 */
@SuppressWarnings("CanBeFinal")
public class QuestionCardPagerAdapter extends PagerAdapter {

    private static final String TAG = QuestionCardPagerAdapter.class.getSimpleName();
    private final Typeface mFont;

    private Context mContext;
    private List<QuestionCardData> mQuestionCardData;
    private List<CardView> mCards;
    private IPagerAdapterAction mListener;

    public interface IPagerAdapterAction{
        void setFABEnabled(boolean enabled);
        void setSkipEnabled(boolean enabled);
        void adjustScore(boolean answeredCorrect);
        void speakWord(String wordText);
        void displayMessage(String message);
        void endQuiz();
    }

    //public QuestionCardPagerAdapter(List<CardView> mViews, List<QuestionCardData> mCards) {
    public QuestionCardPagerAdapter(Context context, List<QuestionCardData> cards, IPagerAdapterAction listener) {
        Log.d(TAG, "QuestionCardPagerAdapter: ");
        this.mContext = context;
        this.mFont = Typeface.createFromAsset(mContext.getAssets(), FONT_ITIM_REGULAR);
        this.mCards = new ArrayList<>();
        this.mListener = listener;
        this.mQuestionCardData = cards;
        if (mQuestionCardData != null) {
            for (QuestionCardData qc :mQuestionCardData) {
                if (qc.getQuestionType().equals(QuestionCardData.QuestionType.TYPE1)) {
                    StringBuilder answers = new StringBuilder("Answers:");
                    for (Verb answer : qc.getWrongAnswers()) {
                        answers.append(" ").append(answer.getWord_pt());
                    }
                    Log.d(TAG, "QuestionCardPagerAdapter: Card:[" + qc.getVerb().getWord_en() + "] " + answers);
                }
                mCards.add(null);
            }

        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
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

    @NonNull
    @SuppressWarnings({"IfCanBeSwitch", "ConstantConditions", "RedundantCast"})
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        QuestionCardData qc = mQuestionCardData.get(position);

        if (qc.getQuestionType().equals(QuestionCardData.QuestionType.TYPE1))
            Log.i(TAG, "instantiateItem: >>> TYPE1 Card at pos="+String.valueOf(position)+" is of type:"+qc.getQuestionType().toString()+" with selectedindex=["+String.valueOf(((QuestionCardData_Type1)qc).getChosenAnswer())+"]");
        else if (qc.getQuestionType().equals(QuestionCardData.QuestionType.TYPE2))
            Log.i(TAG, "instantiateItem: >>> OTHER Card at pos="+String.valueOf(position)+" is of type:"+qc.getQuestionType().toString());

        StringBuilder answers1 = new StringBuilder("Answers:");
        for (Verb answer: qc.getWrongAnswers()) {
            answers1.append(" ").append(answer.getWord_pt());
        }
        Log.i(TAG, "instantiateItem: >>> LOADED AT ("+String.valueOf(position)+") WITH :["+qc.getVerb().getWord_en()+"] "+answers1);
        Log.i(TAG, "instantiateItem: >>> ACTIVE (VM) INDEX ="+((QuizMainActivity)mContext).getViewModel().getActiveCardIndex()+"");

        Log.d(TAG, "instantiateItem: *** CARDTYPE["+qc.getQuestionType().toString()+"]");
        Log.d(TAG, "instantiateItem: *** LASTPAGE["+((QuizMainActivity)mContext).getViewModel().getLastPageReached()+"]");
        Log.d(TAG, "instantiateItem: *** FINALSHOWN["+((QuizMainActivity)mContext).getViewModel().getFinalCardShown()+"]");

        //if (!(qc.getQuestionType().equals(QuestionCardData.QuestionType.TYPEEND)) && (!((QuizMainActivity)mContext).getViewModel().getLastPageReached()) && (!((QuizMainActivity)mContext).getViewModel().getFinalCardShown())){
        if (!(qc.getQuestionType().equals(QuestionCardData.QuestionType.TYPEEND)) && (!((QuizMainActivity)mContext).getViewModel().getFinalCardShown())){
            // Ignore previously instantiated cards as we do not allow backward option
            if (isDiscardedCard(position)) {
                Log.i(TAG, "instantiateItem: DISCARDED AT POS="+String.valueOf(position));
                return null;
            }
        }

        int resourceId;
        switch (qc.getQuestionType()){
            case TYPE1: resourceId=R.layout.layout_qcard_type1;
                break;
            case TYPE2: resourceId=R.layout.layout_qcard_type2;
                        break;
            case TYPEEND:
            default:
                resourceId=R.layout.layout_qcard_typeend;
        }

        View view = (View) inflater.inflate(resourceId, container, false);
        container.addView(view);

        final CardView cardView = view.findViewById(R.id.layout_quizcard);

        // Bind content
        if (qc.getQuestionType() == QuestionCardData.QuestionType.TYPE1) {
            //region TYPE 1
            Log.d(TAG, "instantiateItem: CARD TYPE 1 at pos=" + String.valueOf(position) + " selectedIndex=" + String.valueOf(((QuestionCardData_Type1)qc).getChosenAnswer() + " correct=" + String.valueOf(qc.getChosenAnswerCorrect())));
            final TextView tvQuestion = view.findViewById(R.id.tv_quiz_question);
            tvQuestion.setTypeface(mFont);
            tvQuestion.setText(qc.getVerb().getWord_en());

            FrameLayout frameAnswer0 = (FrameLayout) cardView.findViewById(R.id.answer_0);
            TextView tvAnswer0 = (TextView) frameAnswer0.findViewById(R.id.tv_quiz_answer);
            tvAnswer0.setTypeface(mFont);

            FrameLayout frameAnswer1 = (FrameLayout) cardView.findViewById(R.id.answer_1);
            TextView tvAnswer1 = (TextView) frameAnswer1.findViewById(R.id.tv_quiz_answer);
            tvAnswer1.setTypeface(mFont);

            FrameLayout frameAnswer2 = (FrameLayout) cardView.findViewById(R.id.answer_2);
            TextView tvAnswer2 = (TextView) frameAnswer2.findViewById(R.id.tv_quiz_answer);
            tvAnswer2.setTypeface(mFont);

            FrameLayout frameAnswer3 = (FrameLayout) cardView.findViewById(R.id.answer_3);
            TextView tvAnswer3 = (TextView) frameAnswer3.findViewById(R.id.tv_quiz_answer);
            tvAnswer3.setTypeface(mFont);

            int correctAnswerIndex = generateRandomAnswerIndex();
            if (((QuestionCardData_Type1)qc).getAnswerPosition() != -1) {
                correctAnswerIndex = ((QuestionCardData_Type1)qc).getAnswerPosition();
                Log.d(TAG, "instantiateItem: Correct answer SET at " + String.valueOf(correctAnswerIndex));
            }
            else
                Log.d(TAG, "instantiateItem: Correct answer ALREADY at " + String.valueOf(correctAnswerIndex));

            if (isCurrentCard(position)) {
                ((QuestionCardData_Type1) qc).setAnswerPosition(correctAnswerIndex);

                StringBuilder answers = new StringBuilder("Answers:");
                for (Verb answer: qc.getWrongAnswers()) {
                    answers.append(" ").append(answer.getWord_pt());
                }
                Log.i(TAG, "instantiateItem: CURRENT, POPULATE ("+String.valueOf(position)+") with:["+qc.getVerb().getWord_en()+"] "+answers);
                mQuestionCardData.set(position, qc);
            }
            else
                Log.i(TAG, "instantiateItem: NOT CURRENT (POS="+String.valueOf(position)+")");

            assignType1AnswerButton(cardView, correctAnswerIndex, qc, 0, frameAnswer0, tvAnswer0);
            assignType1AnswerButton(cardView, correctAnswerIndex, qc, 1, frameAnswer1, tvAnswer1);
            assignType1AnswerButton(cardView, correctAnswerIndex, qc, 2, frameAnswer2, tvAnswer2);
            assignType1AnswerButton(cardView, correctAnswerIndex, qc, 3, frameAnswer3, tvAnswer3);

            //Log.d(TAG, "instantiateItem: POS="+String.valueOf(position)+" CURRENT="+String.valueOf(((QuizMainActivity)mContext).getViewModel().getActiveCardIndex()));
            // in case of rotation, reshow previous selection
            if (((QuestionCardData_Type1) qc).getChosenAnswer() != -1) {
                Log.d(TAG, "instantiateItem: Reselecting=" + String.valueOf(((QuestionCardData_Type1) qc).getChosenAnswer() == 0));
                if (((QuestionCardData_Type1) qc).getChosenAnswer() == 0)
                    frameAnswer0.performClick();
                else if (((QuestionCardData_Type1) qc).getChosenAnswer() == 1)
                    frameAnswer1.performClick();
                else if (((QuestionCardData_Type1) qc).getChosenAnswer() == 2)
                    frameAnswer2.performClick();
                else if (((QuestionCardData_Type1) qc).getChosenAnswer() == 3)
                    frameAnswer3.performClick();
            } else {
                //if (((QuizMainActivity)mContext).getViewModel().getActiveCardIndex() == position) {
                if (isCurrentCard(position)) {
                    if (mListener != null) {
                        mListener.setFABEnabled(false);
                        mListener.setSkipEnabled(true);
                    }
                }
            }

            //endregion TYPE 1
        }
        else if (qc.getQuestionType() == QuestionCardData.QuestionType.TYPE2) {
            //region TYPE 2
            Log.d(TAG, "instantiateItem: CARD TYPE 2 at pos=" + String.valueOf(position) + " answerInput=" + ((QuestionCardData_Type2)qc).getAnswerInput() + " correct=" + String.valueOf(qc.getChosenAnswerCorrect()));

            TextView tvQuestion = (TextView) cardView.findViewById(R.id.tv_quiz_question);
            tvQuestion.setTypeface(mFont);
            TextView tvResultMessage = (TextView) cardView.findViewById(R.id.tv_quiz_resultmsg);
            tvResultMessage.setTypeface(mFont);
            TextView tvCorrectAnswer = (TextView) cardView.findViewById(R.id.tv_quiz_correctanswer);
            tvCorrectAnswer.setTypeface(mFont);

            EditText etAnswerInput = (EditText) cardView.findViewById(R.id.etAnswerInput);
            String answerInput = ((QuestionCardData_Type2)qc).getAnswerInput();
            if (answerInput.length() > 0)
                etAnswerInput.setText(answerInput);

            ImageButton btnListen = (ImageButton) cardView.findViewById(R.id.ib_listen);
            btnListen.setOnClickListener(view1 -> {
                if (mListener != null)
                    mListener.speakWord(qc.getVerb().getWord_pt());
            });

            Button btnReturn = (Button) cardView.findViewById(R.id.btn_quiz_return);
            btnReturn.setOnClickListener(view12 -> {
                if (etAnswerInput.getText().length() < 1) {
                    etAnswerInput.requestFocus();
                    if (mListener != null)
                        mListener.displayMessage("Answer required, or skip");
                }
                else {
                    if ((mListener != null) && (!((QuestionCardData_Type2) qc).getAnswerAlreadyChecked())) {
                        mListener.adjustScore(qc.getChosenAnswerCorrect());
                    }

                    ((QuestionCardData_Type2) qc).setAnswerAlreadyChecked(true);

                    if (VerbHelper.isMatch(true, qc.getVerb().getWord_pt(), etAnswerInput.getText().toString().trim())) {
                        Log.d(TAG, "onClick: CORRECT");
                        qc.setChosenAnswerCorrect(true);
                    } else {
                        Log.d(TAG, "onClick: INCORRECT");
                        qc.setChosenAnswerCorrect(false);
                    }
                    handleType2Controls((QuestionCardData_Type2) qc, btnReturn, btnListen, etAnswerInput, tvResultMessage, tvCorrectAnswer);
                }
            });

            if (isCurrentCard(position)){
                handleType2Controls((QuestionCardData_Type2) qc, btnReturn, btnListen, etAnswerInput, tvResultMessage, tvCorrectAnswer);
            }

            //endregion TYPE 2
        }
        else if (qc.getQuestionType() == QuestionCardData.QuestionType.TYPEEND) {
            Log.d(TAG, "instantiateItem: CARD TYPE END at pos=" + String.valueOf(position));

            TextView tvTitle = (TextView) cardView.findViewById(R.id.tv_quiz_title);
            tvTitle.setTypeface(mFont);

            TextView tvScore = (TextView) cardView.findViewById(R.id.tv_quiz_score);
            tvScore.setTypeface(mFont);
            TextView tvMessage = (TextView) cardView.findViewById(R.id.tv_quiz_message);
            tvMessage.setTypeface(mFont);

            tvScore.setText(((QuestionCardData_End)qc).getLabelScore());
            tvMessage.setText(((QuestionCardData_End)qc).getLabelMessage());

            Button btnReturn = (Button) cardView.findViewById(R.id.btn_quiz_return);
            btnReturn.setOnClickListener(view13 -> {
                if (mListener != null){
                    mListener.endQuiz();
                }
            });

        }

        Log.i(TAG, "instantiateItem: SET CARDVIEW AT POS:["+String.valueOf(position)+"]");
        mCards.set(position,cardView);
        Log.i(TAG, "instantiateItem: END <<<<<<<<<<<<<<<<<<<<<<");
        return view;
    }

    private boolean isCurrentCard(int pos){
        Log.d(TAG, "isCurrentCard: POS="+String.valueOf(pos)+" CURRENT="+String.valueOf(((QuizMainActivity)mContext).getViewModel().getActiveCardIndex()));
        return (pos == ((QuizMainActivity)mContext).getViewModel().getActiveCardIndex());
    }

    private boolean isDiscardedCard(int pos){
        Log.i(TAG, "isDiscardedCard: POS="+String.valueOf(pos)+" CURRENT="+String.valueOf(((QuizMainActivity)mContext).getViewModel().getActiveCardIndex()));
        return (pos < ((QuizMainActivity)mContext).getViewModel().getActiveCardIndex());
    }

    @SuppressLint("SetTextI18n")
    private void handleType2Controls(QuestionCardData_Type2 qcd, Button btnReturn, ImageButton btnListen, EditText etAnswerText, TextView resultMessage, TextView correctAnswer){
        if (qcd.getAnswerAlreadyChecked()){
            // TODO: 12/08/2018 Indicator (Colouring)?
            btnReturn.setVisibility(View.INVISIBLE);
            btnListen.setVisibility(View.INVISIBLE);
            etAnswerText.setEnabled(false);

            resultMessage.setVisibility(View.VISIBLE);
            if (qcd.getChosenAnswerCorrect()){
                resultMessage.setText((mContext.getResources().getString(R.string.correct)).toUpperCase());
                correctAnswer.setVisibility(View.INVISIBLE);
            }
            else{
                resultMessage.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                resultMessage.setText((mContext.getResources().getString(R.string.wrong)).toUpperCase());
                etAnswerText.setVisibility(View.INVISIBLE);
                correctAnswer.setText(qcd.getVerb().getWord_pt());
                correctAnswer.setVisibility(View.VISIBLE);
            }

            if (mListener != null) {
                mListener.setFABEnabled(true);
                mListener.setSkipEnabled(false);
            }
        }
        else{
            //btnReturn.setEnabled(true);
            btnReturn.setVisibility(View.VISIBLE);
            btnListen.setEnabled(true);
            etAnswerText.setEnabled(true);
            etAnswerText.requestFocus();
            correctAnswer.setVisibility(View.INVISIBLE);
            resultMessage.setVisibility(View.INVISIBLE);

            if (mListener != null) {
                mListener.setFABEnabled(false);
                mListener.setSkipEnabled(true);
            }
        }

    }

    //region Multiple Choice (Type1) Question Handlers
    private void handleType1AnswerSelection(CardView cardView, int correctAnswerIndex, int selectedIndex){

        Log.d(TAG, "handleType1AnswerSelection: >>> SELECTED INDEX:["+String.valueOf(selectedIndex)+"]");
        int pos = ((QuizMainActivity)mContext).getViewModel().getActiveCardIndex();
        Log.d(TAG, "handleType1AnswerSelection: >>> ACTIVE INDEX:["+String.valueOf(pos)+"]");
        QuestionCardData qcd = mQuestionCardData.get(pos);

        StringBuilder answers1 = new StringBuilder("Answers:");
        for (Verb answer: qcd.getWrongAnswers()) {
            answers1.append(" ").append(answer.getWord_pt());
        }
        Log.i(TAG, "instantiateItem: >>> CARD OFF STACK AT ("+String.valueOf(pos)+") WITH :["+qcd.getVerb().getWord_en()+"] "+answers1);

        ((QuestionCardData_Type1)qcd).setChosenAnswer(selectedIndex);
        Log.d(TAG, "handleType1AnswerSelection: >>> CORRECT ANS INDEX:["+String.valueOf(correctAnswerIndex)+"]");
        boolean correct = correctAnswerIndex==selectedIndex;
        Log.d(TAG, "handleType1AnswerSelection: >>> CORRECT? :["+String.valueOf(correct)+"]");
        qcd.setChosenAnswerCorrect(correct);
        if (correct) {
            ((QuestionCardData_Type1) qcd).setAnswerPosition(correctAnswerIndex);
            Log.d(TAG, "handleType1AnswerSelection: >>> SAVED: CORRECT ANS INDEX:["+String.valueOf(correctAnswerIndex)+"]");
        }
        Log.d(TAG, "handleType1AnswerSelection: Pos="+String.valueOf(pos)+"("+qcd.getVerb().getWord_pt()+") Selected="+String.valueOf(selectedIndex)+" Correct="+String.valueOf(correct));
        mQuestionCardData.set(pos,qcd);

        FrameLayout frameAnswer0 = cardView.findViewById(R.id.answer_0);
        TextView tvAnswer0 = frameAnswer0.findViewById(R.id.tv_quiz_answer);
        FrameLayout frameAnswer1 = cardView.findViewById(R.id.answer_1);
        TextView tvAnswer1 = frameAnswer1.findViewById(R.id.tv_quiz_answer);
        FrameLayout frameAnswer2 = cardView.findViewById(R.id.answer_2);
        TextView tvAnswer2 = frameAnswer2.findViewById(R.id.tv_quiz_answer);
        FrameLayout frameAnswer3 = cardView.findViewById(R.id.answer_3);
        TextView tvAnswer3 = frameAnswer3.findViewById(R.id.tv_quiz_answer);

        configureType1AnswerButtonAfterSelection(selectedIndex==0,correctAnswerIndex==0,frameAnswer0,tvAnswer0);
        configureType1AnswerButtonAfterSelection(selectedIndex==1,correctAnswerIndex==1,frameAnswer1,tvAnswer1);
        configureType1AnswerButtonAfterSelection(selectedIndex==2,correctAnswerIndex==2,frameAnswer2,tvAnswer2);
        configureType1AnswerButtonAfterSelection(selectedIndex==3,correctAnswerIndex==3,frameAnswer3,tvAnswer3);

        if (mListener != null) {
            mListener.setFABEnabled(true);
            mListener.setSkipEnabled(false);
        }
    }

    private void configureType1AnswerButtonAfterSelection(boolean wasSelected, boolean isCorrectAnswer, final FrameLayout answerFrame, final TextView answerTextView){
        answerFrame.setEnabled(false);
        CardView card = answerFrame.findViewById(R.id.layout_card_answer);
        if (wasSelected){
            answerTextView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            card.setCardBackgroundColor(mContext.getResources().getColor(isCorrectAnswer?R.color.colorAnswerOk:R.color.colorAnswerWrong));
        }
        else{
            answerTextView.setTextColor(mContext.getResources().getColor(isCorrectAnswer?R.color.colorAnswerOk:R.color.colorLGray));
            if (isCorrectAnswer)
                answerTextView.setTypeface(answerTextView.getTypeface(), Typeface.BOLD);
        }
    }

    private void assignType1AnswerButton(final CardView cardView, final int correctAnswerIndex, QuestionCardData qcd, int answerIndex, final FrameLayout answerFrame, final TextView answerTextView){

/*
        StringBuilder answers = new StringBuilder("Answers:");
        for (Verb answer: qcd.getWrongAnswers()) {
            answers.append(" ").append(answer.getWord_pt());
        }
        Log.d(TAG, "assignType1AnswerButton: Card:["+qcd.getVerb().getWord_en()+"] "+answers);
*/

        boolean isCorrect = (correctAnswerIndex == answerIndex);
        String answerTxt = qcd.getVerb().getWord_pt();

        if (!isCorrect){
            Verb v = qcd.getWrongAnswers().get(answerIndex);
            answerTxt = v.getWord_pt();
        }
        answerTextView.setText(answerTxt);

        String finalAnswerTxt = answerTxt;
        answerFrame.setOnClickListener(view -> {
            Log.d(TAG, "onClick: "+(isCorrect?"CORRECT":"WRONG")+" answer selected for index "+ String.valueOf(answerIndex) + ":["+ finalAnswerTxt +"]");
            if ((mListener != null) && ((QuestionCardData_Type1)qcd).getChosenAnswer()<0) {
                mListener.adjustScore(isCorrect);
            }
            handleType1AnswerSelection(cardView, correctAnswerIndex, answerIndex);
        });
    }
    //endregion Multiple Choice (Type1) Question Handlers
}
