package com.persistent.medicalmcq.util;



import com.persistent.medicalmcq.model.Answer;
import com.persistent.medicalmcq.model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by amandeep_singhbhatia on 5/4/2015.
 */
public class ScoreManager {
    final int WEIGHTAGE = 10;
    private HashMap<Question, ArrayList<Answer>> userAttempt;
    private ArrayList<Question> questions;

    public ScoreManager(HashMap<Question, ArrayList<Answer>> userAttempt , ArrayList<Question> questions) {
        this.userAttempt = userAttempt;
        this.questions = questions;
    }

    public int calculateScore() {
        Iterator<Question> iterator = userAttempt.keySet().iterator();
        int correctCount = 0;

        while (iterator.hasNext()) {
            Question question = iterator.next();
            ArrayList<Answer> attemptedAnswers = userAttempt.get(question);
            if(question.validateAnswer(attemptedAnswers)){
                correctCount++;
            }

        }

        return correctCount * WEIGHTAGE;
    }

    public int getOutOfScore() {
        return questions.size() * WEIGHTAGE;
    }
    public static int getCategoryTotal(ArrayList<ArrayList<Integer>> categoryList){
        int total = 0;
        for(ArrayList <Integer> singleResult : categoryList){
        total += singleResult.get(2);
        }
        return total;
    }

    public static int getCategoryOutOf(ArrayList<ArrayList<Integer>> categoryList){
        int total = 0;
        for(ArrayList <Integer> singleResult : categoryList){
            total += singleResult.get(3);
        }
        return total;
    }
}
