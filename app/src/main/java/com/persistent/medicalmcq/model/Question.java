package com.persistent.medicalmcq.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amandeep_singhbhatia on 5/4/2015.
 */
public class Question {

    private int questionID;
    private Topic topic;
    private List<Answer> answerList;
    private String questionText;
    private String additionalInfo;
    private String imgPath;



    private  boolean isAnswered;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Question(int questionID, Topic topic, List<Answer> answerList, String questionText, String additionalInfo,String imgPath) {
        this.questionID = questionID;
        this.topic = topic;
        this.answerList = answerList;
        this.questionText = questionText;
        this.additionalInfo = additionalInfo;
        this.imgPath=imgPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionID != question.questionID) return false;
        if (!questionText.equals(question.questionText)) return false;
        if (!topic.equals(question.topic)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionID;
        result = 31 * result + topic.hashCode();
        result = 31 * result + questionText.hashCode();
        return result;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public boolean isAnswered() {

        return isAnswered;
    }
    public ArrayList<Answer> getCorrectAnswers() {
        ArrayList<Answer> correctAnswers = new ArrayList<Answer>();
        for (Answer answer : this.answerList) {
            if (answer.isCorrect())
                correctAnswers.add(answer);
        }
        return correctAnswers;
    }

    /*
    Call this method for multiple choice question with list of attempted answers.
     */
    public boolean validateAnswer(ArrayList<Answer> attemptedAnswers) {
        ArrayList<Answer> correctAnswers = getCorrectAnswers();

        if (attemptedAnswers.size() != correctAnswers.size())
            return false;

        int correctCount = 0;
        for (Answer attemptedAnswer : attemptedAnswers) {

            for (Answer correctAnswer : correctAnswers) {
                if (attemptedAnswer.getAnswerText().equals(correctAnswer.getAnswerText())) {
                    correctCount++;
                    break;
                }
            }
        }
        if (correctAnswers.size() == correctCount) {
            return true;
        }
        return false;
    }

    /*
    Call this method for single choice question.
    */
    public boolean validateAnswer(Answer attemptedAnswer) {
        ArrayList<Answer> correctAnswers = getCorrectAnswers();

        if (correctAnswers.size() != 1)
            return false;

        for (Answer correctAnswer : correctAnswers) {
            if (attemptedAnswer.getAnswerText().equals(correctAnswer.getAnswerText())) {
                return true;
            }
        }
        return false;
    }

}