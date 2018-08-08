package com.bf.portugo.helper;

import android.content.Context;

import com.bf.portugo.R;
import com.bf.portugo.common.Enums;
import com.bf.portugo.model.Verb;

/*
 * @author frielb
 * Created on 08/08/2018
 */
public class VerbHelper {
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
}
