package com.example.stephenvickers.proofofconcept;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by stephenvickers on 10/4/16.
 */
public class Questions {

    private String question;


    private String correctAnswer;

    List<String> answerChoice = new ArrayList<>();

    private int answerNumber = 0;

    public Questions(){

    }

    public Questions(String question){
        this.setQuestion(question);
    }

    public void pushAnswer(String answer){
        answerChoice.add(answer);
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getNextAnswer(){
        return this.answerChoice.remove(0);
    }

    public int getNumberOfAnswers(){
        return answerChoice.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Questions)) return false;

        Questions questions = (Questions) o;

        if (getCorrectAnswer() != questions.getCorrectAnswer()) return false;
        if (getQuestion() != null ? !getQuestion().equals(questions.getQuestion()) : questions.getQuestion() != null)
            return false;
        return answerChoice != null ? answerChoice.equals(questions.answerChoice) : questions.answerChoice == null;

    }

    @Override
    public int hashCode() {
        int result = getQuestion() != null ? getQuestion().hashCode() : 0;
        result = 31 * result + this.getCorrectAnswer().hashCode();
        result = 31 * result + (this.answerChoice != null ? this.answerChoice.hashCode() : 0);
        return result;
    }
}
