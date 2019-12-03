package com.persistent.medicalmcq.model;

/**
 * Created by amandeep_singhbhatia on 5/4/2015.
 */
public class Answer {

    private int answerID;
    private String answerText;
    private boolean isCorrect;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        if (getAnswerID() != answer.getAnswerID()) return false;
        return getAnswerText().equals(answer.getAnswerText());

    }

    @Override
    public int hashCode() {
        int result = getAnswerID();
        result = 31 * result + getAnswerText().hashCode();
        return result;
    }

    public Answer( int answerID, boolean isCorrect, String answerText) {
        this.isCorrect = isCorrect;
        this.answerText = answerText;
        this.answerID = answerID;
    }

    public Answer() {
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
