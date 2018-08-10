package com.bf.portugo.util;

import com.bf.portugo.model.Verb;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/*
 * @author frielb
 * Created on 10/08/2018
 */
public class VerbHelperTest {

    List<Verb> verbs;
    Verb v1;

    @Before
    public void setUp() throws Exception {
        verbs = new ArrayList<>();

        //region Test verbs
        v1 = new Verb();
        v1.setWord_en("to learn");
        v1.setWord_pt("aprender");
        verbs.add(v1);

        Verb v2 = new Verb();
        v2.setWord_en("to eat");
        v2.setWord_pt("comer");
        verbs.add(v2);

        Verb v3 = new Verb();
        v3.setWord_en("to sleep");
        v3.setWord_pt("dormir");
        verbs.add(v3);

        Verb v4 = new Verb();
        v4.setWord_en("to drink");
        v4.setWord_pt("beber");
        verbs.add(v4);

        Verb v5 = new Verb();
        v5.setWord_en("to bring");
        v5.setWord_pt("trazer");
        verbs.add(v5);

        Verb v6 = new Verb();
        v6.setWord_en("to buy");
        v6.setWord_pt("comprar");
        verbs.add(v6);
        //endregion Test verbs
    }

    @Test
    public void getRandomVerb() {
        Verb rv = VerbHelper.getRandomVerb(verbs);
        System.out.println("Random:["+rv.getWord_en()+"]");
        assertNotNull("Null random verb",rv);
    }

    @Test
    public void generateQuizAnswersForVerb() {
        List<Verb> answers = VerbHelper.generateQuizAnswersForVerb(v1, 4, verbs);
        assertNotNull("No answers",answers);
        assertEquals("Count mismatch",4,answers.size());

        // test randomly picked duplicate functionality
        List<Verb> stuffedVerbList = new ArrayList<>(verbs);
        for (int i = 0; i < 50; i++) {
            Verb v = new Verb();
            v.setWord_en("dummy");
            stuffedVerbList.add(v);
        }
        assertEquals("Count mismatch with duplicates",4,answers.size());
    }

    @Test
    public void isMatch() {
        String sAccented = "ãáàâçéêíõóôúü";
        String sAccented_upper = "ÃÁÀÂÇÉÊÍÕÓÔÚÜ";
        String sPlain = "aaaaceeiooouu";

        assertTrue("Accent mismatch", VerbHelper.isMatch(true,sPlain,sAccented));
        assertTrue("Accent mismatch", VerbHelper.isMatch(true,sPlain,sAccented_upper));
        assertFalse("Accent mismatch", VerbHelper.isMatch(false,sPlain,sAccented));
    }
}