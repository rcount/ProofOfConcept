package com.example.stephenvickers.proofofconcept;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


/**
 * Created by stephenvickers on 10/4/16.
 */
public class Questions implements Serializable{


    private String question;

    private String correctAnswer;

    List<String> answerChoice = new ArrayList<>();

    private int answerNumber = 0;


    public Questions(){
        this.question = "";
        this.correctAnswer = "";
    }



    public Questions(Questions question){
        this.setQuestion(question.getQuestion());

        for(int index = 0; index < question.getNumberOfAnswers(); index++){
            this.pushAnswer(question.getNextAnswer(index));
        }

        this.setCorrectAnswer(question.getCorrectAnswer());
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

    public String getNextAnswer(int index){
        return this.answerChoice.get(index);
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

    @Override
    public String toString() {
        return "Questions{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", answerChoice=" + answerChoice +
                ", answerNumber=" + answerNumber +
                '}';
    }
}
