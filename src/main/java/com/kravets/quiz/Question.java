package com.kravets.quiz;

import java.util.ArrayList;

public class Question {
    private String question;
    private ArrayList<Answer> answers;

    public Question() {}

    public Question(String question, String ... answers) {
        this.question = question;
        this.answers = new ArrayList<>();
        for (String el: answers) {
            if (!el.equals(""))
                this.answers.add(new Answer(el, this.answers.isEmpty()));
            else
                this.answers.add(new Answer("", false));
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }
}
