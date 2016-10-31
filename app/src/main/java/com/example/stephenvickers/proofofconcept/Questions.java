package com.example.stephenvickers.proofofconcept;

import java.util.*;

/**
 * Created by stephenvickers on 10/4/16.
 */
public class Questions {

//    private String question;
//    private Map<String, String> answers;
//    private String correctAnswer;
//
//    public Questions(){}
//
//    public String getQuestion (){
//        return this.question;
//    }
//
//    public String getCorrectAnswer(){
//        return this.correctAnswer;
//    }
//
//    public Map<String, String> getAnswers() {
//        return answers;
//    }
//
//    public int getNumberOfAnswers(){
//        return this.answers.size();
//    }


    private String question;


    private String correctAnswer;

    List<String> answerChoice = new ArrayList<>();

    private int answerNumber = 0;

    public Questions(){

    }

    public Questions(Questions question){
        this.setQuestion(question.getQuestion());

        for(int index = 0; index < question.getNumberOfAnswers(); index++){
            this.pushAnswer(question.getNextAnswer());
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
