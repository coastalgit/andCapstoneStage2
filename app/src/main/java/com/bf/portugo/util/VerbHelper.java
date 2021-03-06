package com.bf.portugo.util;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bf.portugo.R;
import com.bf.portugo.common.Enums;
import com.bf.portugo.model.Verb;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * @author frielb
 * Created on 08/08/2018
 */
@SuppressWarnings({"Convert2Diamond", "JavaDoc"})
public class VerbHelper {

    private static final String TAG = VerbHelper.class.getSimpleName();

    public static <T> int getLiveListRecordCount(LiveData<List<T>> liveList){
        if ((liveList != null) && liveList.getValue() != null && (liveList.getValue().size() > 0))
            return liveList.getValue().size();
        return 0;
    }

    public static <T> int getListRecordCount(List<T> list){
        if ((list != null) && (list.size() > 0))
            return list.size();
        return 0;
    }

    public static String buildEnglishString(Context context, Enums.VerbTense tense, Enums.VerbPrefix_EN prefix, Verb verb){

        String sI = context.getResources().getString(R.string.en_i);
        String sYou = context.getResources().getString(R.string.en_you);
        String sHeShe = context.getResources().getString(R.string.en_heshe);
        String sWe = context.getResources().getString(R.string.en_we);
        String sThey = context.getResources().getString(R.string.en_they);

        switch (tense){
            case PRESENT:
                switch (prefix){
                    case I:
                        return sI + " " + verb.getTense_pres_en_i();
                    case YOU:
                        return sYou + " " + verb.getTense_pres_en_youwethey();
                    case HESHE:
                        return sHeShe + " " + verb.getTense_pres_en_heshe();
                    case WE:
                        return sWe + " " + verb.getTense_pres_en_youwethey();
                    case THEY:
                        return sThey + " " + verb.getTense_pres_en_youwethey();
                    default:
                        return "-";
                }
            case FUTURE:
                switch (prefix){
                    case I:
                        return sI + " " + verb.getTense_fut_en_all();
                    case YOU:
                        return sYou + " " + verb.getTense_fut_en_all();
                    case HESHE:
                        return sHeShe + " " + verb.getTense_fut_en_all();
                    case WE:
                        return sWe + " " + verb.getTense_fut_en_all();
                    case THEY:
                        return sThey + " " + verb.getTense_fut_en_all();
                    default:
                        return "-";
                }
            case PAST:
                switch (prefix){
                    case I:
                        return sI + " " + verb.getPast_part_en();
                    case YOU:
                        return sYou + " " + verb.getPast_part_en();
                    case HESHE:
                        return sHeShe + " " + verb.getPast_part_en();
                    case WE:
                        return sWe + " " + verb.getPast_part_en();
                    case THEY:
                        return sThey + " " + verb.getPast_part_en();
                    default:
                        return "-";
                }
            default:
                return "-";
        }
    }

    public static Verb getRandomVerb(List<Verb> allverbs){

        if (allverbs != null && allverbs.size() < 1)
            return null;

        Random random = new Random();
        assert allverbs != null;
        int indexer = random.nextInt(allverbs.size());
        Verb v = allverbs.get(indexer);
        Log.d(TAG, "getRandomVerb: ["+v.getWord_en()+"]");

        return v;
    }

    public static List<Verb> generateRandomVerbList(int requiredCount, List<Verb> allVerbs){

        if ((allVerbs == null))
            return null;

        if (allVerbs.size() < 1)
            return null;

        if (requiredCount > allVerbs.size())
            requiredCount = allVerbs.size()-1;

        Log.d(TAG, "generateRandomVerbList: requiredAnswerCount="+String.valueOf(requiredCount));
        if (requiredCount == 0)
            return null;

        List<Verb> selectedVerbs = new ArrayList<Verb>();
        int counter = 0;

        do{
            Verb randomVerb = getRandomVerb(allVerbs);
            if (randomVerb != null){
                boolean alreadyAdded = false;
                for (Verb v: selectedVerbs){
                    if (randomVerb.getWord_en().equalsIgnoreCase(v.getWord_en())){
                        Log.d(TAG, "generateRandomVerbList: Already added");
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    selectedVerbs.add(randomVerb);
                    Log.d(TAG, "generateRandomVerbList: Added[" + randomVerb.getWord_en() + "]");
                    counter++;
                }
            }
            else
                break;
        }
        while (counter < requiredCount);

        return selectedVerbs;
    }

    public static List<Verb> generateQuizAnswersForVerb(Verb quizVerb, int requiredAnswerCount, List<Verb> allVerbs){

        if ((allVerbs == null) || (quizVerb == null))
            return null;

        if (allVerbs.size() < 1)
            return null;

        if (requiredAnswerCount > allVerbs.size())
            requiredAnswerCount = allVerbs.size()-1;

        Log.d(TAG, "generateQuizAnswersForVerb: requiredAnswerCount="+String.valueOf(requiredAnswerCount));
        if (requiredAnswerCount == 0)
            return null;

        List<Verb> wrongAnswers = new ArrayList<Verb>();
        int counter = 0;

        do{
            Verb randomVerb = getRandomVerb(allVerbs);
            if (randomVerb != null){
                // check random is not our answer
                if (!randomVerb.getWord_en().equalsIgnoreCase(quizVerb.getWord_en())){
                    boolean alreadyAdded = false;
                    for (Verb v: wrongAnswers){
                        if (randomVerb.getWord_en().equalsIgnoreCase(v.getWord_en())){
                            Log.d(TAG, "generateQuizAnswersForVerb: Already added");
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if (!alreadyAdded) {
                        wrongAnswers.add(randomVerb);
                        Log.d(TAG, "generateQuizAnswersForVerb: Added[" + randomVerb.getWord_en() + "]");
                        counter++;
                    }
                }
            }
            else
                break;
        }
        while (counter < requiredAnswerCount);

        return wrongAnswers;
    }

    /**
     * Compare two strings regardless of accented characters
     *
     * @param originalString
     * @param userInput
     * @return
     */
    public static boolean isMatch(Boolean ignoreAccents, String originalString, String userInput){
        Log.d(TAG, "isMatch: Orig:["+originalString+"] Input:["+userInput+"]");
        final Collator instance = Collator.getInstance();

        instance.setStrength(ignoreAccents?Collator.NO_DECOMPOSITION:Collator.IDENTICAL);
        // Zero is a match
        int comp = instance.compare(originalString.toLowerCase(), userInput.toLowerCase());
        return (comp==0);
    }

    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean hasSample(Verb verb){
        if (verb == null)
            return false;

        return !TextUtils.isEmpty(verb.getSample_1_pt()) ||
                (!TextUtils.isEmpty(verb.getSample_2_pt())) ||
                (!TextUtils.isEmpty(verb.getSample_3_pt()));
    }

    public static String buildEndCardMessage(Context context, int newScore, int previousScore, int bestScore) {
        Log.d(TAG, "buildEndCardMessage: N="+String.valueOf(newScore)+" P="+String.valueOf(previousScore)+" B="+String.valueOf(bestScore));

        if (newScore>bestScore)
            return context.getString(R.string.quizresult_bestscore);

        if (newScore>previousScore)
            return context.getString(R.string.quizresult_improved);

        if (newScore == 0)
            return context.getString(R.string.quizresult_zero);

        return context.getString(R.string.quizresult_gwork);
    }
}
