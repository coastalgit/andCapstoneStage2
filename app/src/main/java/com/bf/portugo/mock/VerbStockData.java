package com.bf.portugo.mock;

import android.util.Log;

import com.bf.portugo.model.Verb;

import java.util.HashMap;

/*
 * @author frielb
 * Created on 31/07/2018
 */
public class VerbStockData {

    private String TAG = VerbStockData.class.getSimpleName();

    public static enum VerbType {
        REGULAR,
        IRREGULAR,
        ANY
    }

    public Verb[] VERBS_REG = {
            V_ARRIVE,
            V_ASK,
            V_EAT,
            V_BUY,
            V_ENTER};

    public Verb[] VERBS_IRREG = {
            V_DO,
            V_BE_TEMP,
            V_BE_PERM,
            V_SAY,
            V_GIVE,
            V_KNOW};

    public HashMap<String, Verb> VERB_MAP;

    public VerbStockData(VerbType verbType) {
        VERB_MAP = new HashMap<>();
        if (verbType == VerbType.REGULAR){
            for (Verb v : VERBS_REG) {
                VERB_MAP.put(String.valueOf(v.getWord_en()), v);
            }
            Log.d(TAG, "VerbStockData: REG COUNT="+String.valueOf(VERB_MAP.size()));
        }
        else if (verbType == VerbType.IRREGULAR){
            for (Verb v : VERBS_IRREG) {
                VERB_MAP.put(String.valueOf(v.getWord_en()), v);
            }
            Log.d(TAG, "VerbStockData: IRREG COUNT="+String.valueOf(VERB_MAP.size()));
        }
        else{
            for (Verb v : VERBS_REG) {
                VERB_MAP.put(String.valueOf(v.getWord_en()), v);
            }
            for (Verb v : VERBS_IRREG) {
                VERB_MAP.put(String.valueOf(v.getWord_en()), v);
            }

            Log.d(TAG, "VerbStockData: REG COUNT="+String.valueOf(VERB_MAP.size()));
        }
    }

    // REG
    public static final Verb V_ARRIVE = new Verb(
            "to arrive",
            "chegar",
            false,
            "ʃɨɡˈaɾ",
            0,
            50,
            "arriving",
            "chegando",
            "arrived",
            "chegado",
            "arrive",
            "arrives",
            "chego",
            "chegas",
            "chega",
            "chegamos",
            "chegam",
            "cheguei",
            "chegaste",
            "chegou",
            "chegámos",
            "chegaram",
            "will arrive",
            "chegarei",
            "chegarás",
            "chegará",
            "chegaremos",
            "chegarão",
            "TODO",
            "TODO",
            "TODO",
            "TODO",
            "TODO",
            "TODO"
            );


    public static final Verb V_BUY = new Verb(
            "to but",
            "comprar",
            false,
            "kõpɾˈaɾ",
            0,
            50,
            "buying",
            "comprando",
            "bought",
            "comprado",
            "buy",
            "buys",
            "compro",
            "compras",
            "compra",
            "compramos",
            "compram",
            "comprei",
            "compraste",
            "comprou",
            "comprámos",
            "compraram",
            "will buy",
            "comprarei",
            "comprarás",
            "comprará",
            "compraremos",
            "comprarão",
            "Comprámos uma casa no ano passado.",
            "We bought a house last year.",
            "Vocé compraria um carro usado deste homen?",
            "Would you buy a used car from this man?",
            "Comprarás um present para ele?",
            "Are you going to buy him a present?"
    );

    /*
    public static final Verb V_ARRIVE = new Verb(
            "to arrive",
            "chegar",
            false,
            "ʃɨɡˈaɾ",
            0,
            100,
            ""





            1, "Eu cheguei ontem");



    public static final WordSet WS_ASK = new WordSet("to ask", "pedir", 1, "Apenas um exemplo");
    public static final WordSet WS_EAT = new WordSet("to eat", "comer", 1, "Eu vou comer uma maçã");
    public static final WordSet WS_BUY = new WordSet("to buy", "comprar", 1, "Eu quero comprar um chapéu novo");
    public static final WordSet WS_ENTER = new WordSet("to enter", "entrar", 1, "Eu vou entrar nesse prédio");

    // IRREG
    public static final WordSet WS_DO = new WordSet("to do, to make", "fazer", 2, "Você fez o bolo?");
    public static final WordSet WS_BE_TEMP = new WordSet("to be (temp)", "estar", 2, "Apenas um exemplo");
    public static final WordSet WS_BE_PERM = new WordSet("to be (perm)", "ser", 2, "Apenas um exemplo");
    public static final WordSet WS_GIVE = new WordSet("to give", "dar", 2, "Apenas um exemplo");
    public static final WordSet WS_SAY = new WordSet("to say", "dizer", 2, "O que esses médicos dizem");
    public static final WordSet WS_KNOW = new WordSet("to know", "saber", 2, "Apenas um exemplo");
*/

}
