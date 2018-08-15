package com.bf.portugo.data;

import android.util.Log;

import com.bf.portugo.model.Verb;

import java.util.HashMap;

/*
 * @author frielb
 * Created on 31/07/2018
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess", "CanBeFinal"})
public class VerbStockData {

    private String TAG = VerbStockData.class.getSimpleName();

    public enum VerbType {
        REGULAR,
        IRREGULAR,
        ANY
    }

    public Verb[] VERBS_REG = {
            V_ARRIVE,
            //V_ASK,
            V_BUY,
            V_EAT,
            //V_ENTER
            V_CLOSE,
            V_LEND,
            V_FLY
            };

    public Verb[] VERBS_IRREG = {
            V_BE_TEMP,
            V_BE_PERM,
            V_DO,
            V_BRING
            //V_SAY,
            //V_GIVE,
            //V_KNOW
            };

    public HashMap<String, Verb> VERB_MAP;

    @SuppressWarnings("IfCanBeSwitch")
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

            Log.d(TAG, "VerbStockData: ALL COUNT="+String.valueOf(VERB_MAP.size()));
        }
    }

    //region REGULAR
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
            "to buy",
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

    public static final Verb V_EAT = new Verb(
            "to eat",
            "comer",
            false,
            "kumˈeɾ",
            0,
            50,
            "eating",
            "comendo",
            "ate",
            "comido",
            "eat",
            "eat",
            "eats",
            "como",
            "comes",
            "come",
            "comemos",
            "comem",
            "comi",
            "comeste",
            "comeu",
            "comemos",
            "comeram",
            "will eat",
            "comerei",
            "comerás",
            "comerá",
            "comêramos",
            "comerão",
            "Sempre comemos o jantar às sete.",
            "We always eat dinner at seven.",
            "",
            "",
            "",
            ""
    );

    public static final Verb V_CLOSE = new Verb(
            "to close",
            "fechar",
            false,
            "ʃɨɡˈaɾ",
            0,
            50,
            "closing",
            "fechando",
            "closed",
            "fechado",
            "close",
            "close",
            "closes",
            "fecho",
            "fechas",
            "fecha",
            "fechamos",
            "fecham",
            "fechei",
            "fechaste",
            "fechou",
            "fechámos",
            "fecharam",
            "will close",
            "fecharei",
            "fecharás",
            "fechará",
            "fecharemos",
            "fecharão",
            "Vocé fechou a janela?",
            "Did you close the window?",
            "",
            "",
            "",
            ""
    );

    public static final Verb V_LEND = new Verb(
            "to lend/loan",
            "emprestar",
            false,
            "ẽpɾɨʃtˈaɾ",
            0,
            50,
            "lending",
            "emprestando",
            "loaned",
            "emprestado",
            "loan",
            "loan",
            "loans",
            "empresto",
            "emprestas",
            "empresta",
            "emprestamos",
            "emprestam",
            "emprestei",
            "emprestaste",
            "emprestou",
            "emprestámos",
            "emprestaram",
            "will loan",
            "emprestarei",
            "emprestarás",
            "empresterá",
            "emprestaremos",
            "emprestarão",
            "Emprestei tudo o que tinha a uma amiga.",
            "I lent everything I had to a friend.",
            "Emprestámos-lhe muitos livros.",
            "We lent you a lot of books.",
            "",
            ""
    );

    public static final Verb V_FLY = new Verb(
            "to fly",
            "voar",
            false,
            "vuˈaɾ",
            0,
            51,
            "flying",
            "voando",
            "flew",
            "voado",
            "fly",
            "fly",
            "flies",
            "voo",
            "voas",
            "voa",
            "voamos",
            "voam",
            "voei",
            "voaste",
            "voou",
            "voámos",
            "voaram",
            "will fly",
            "voarei",
            "voarás",
            "voará",
            "voaremos",
            "voarão",
            "Ela voou aos Estados Unidos ontem.",
            "She flew to the United States yesterday.",
            "",
            "",
            "",
            ""
    );

/*
    public static final Verb V_BLAH = new Verb(
            "to blah",
            "blah",
            false,
            "blah",
            0,
            50,
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blahblah",
            "blahblah",
            "blahblah",
            "blahblah",
            "blahblah",
            "blahblah"
    );
*/

    //endregion REGULAR

    //region IRREGULAR

    public static final Verb V_BE_TEMP = new Verb(
            "to be (temp)",
            "estar",
            true,
            "ʃtˈa",
            0,
            50,
            "being",
            "estando",
            "have been",
            "estado",
            "am",
            "are",
            "is",
            "estou",
            "estás",
            "está",
            "estamos",
            "estão",
            "estive",
            "estiveste",
            "esteve",
            "estivemos",
            "estiveram",
            "will be",
            "estarei",
            "estiveras",
            "estivera",
            "estivéramos",
            "estiveram",
            "pXXX",
            "eXXX",
            "",
            "",
            "",
            ""
    );

    public static final Verb V_BE_PERM = new Verb(
            "to be (perm)",
            "ser",
            true,
            "sˈeɾ",
            0,
            50,
            "being",
            "sendo",
            "have been",
            "sido",
            "am",
            "are",
            "is",
            "sou",
            "és",
            "é",
            "somos",
            "são",
            "fui",
            "foste",
            "foi",
            "fomos",
            "foram",
            "will be",
            "serei",
            "serás",
            "será",
            "seremos",
            "serão",
            "pXXX",
            "eXXX",
            "",
            "",
            "",
            ""
    );

    public static final Verb V_DO = new Verb(
            "to do, to make",
            "fazer",
            true,
            "fɐzˈeɾ",
            0,
            50,
            "doing,making",
            "fazendo",
            "did, made",
            "feito",
            "do/make",
            "do/make",
            "does/makes",
            "faço",
            "fazes",
            "faz",
            "fazemos",
            "fazem",
            "fiz",
            "fizeste",
            "fez",
            "fizemos",
            "fizeram",
            "will do/make",
            "farei",
            "farás",
            "fará",
            "faremos",
            "farão",
            "Eu já tinha feito o trabalho.",
            "I had already done the work.",
            "",
            "",
            "",
            ""
    );

    public static final Verb V_BRING = new Verb(
            "to bring",
            "trazer",
            true,
            "tɾɐzˈeɾ",
            0,
            50,
            "bringing",
            "trazendo",
            "brought",
            "trazido",
            "bring",
            "bring",
            "brings",
            "trago",
            "trazes",
            "traz",
            "trazemos",
            "trazem",
            "trouxe",
            "trouxeste",
            "trouxe",
            "trouxemos",
            "trouxeram",
            "will bring",
            "trarei",
            "trarás",
            "trará",
            "traremos",
            "traráo",
            "pXXX",
            "eXXX",
            "",
            "",
            "",
            ""
    );

    public static final Verb V_BLAH = new Verb(
            "blah",
            "blah",
            true,
            "blah",
            0,
            50,
            "blah",
            "blah",
            "have blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "will blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "blah",
            "pXXX",
            "eXXX",
            "",
            "",
            "",
            ""
    );

    //endregion IRREGULAR

    /*
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
