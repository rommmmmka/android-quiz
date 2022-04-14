package com.kravets.quiz;

public class Result implements Comparable<Result> {
    private String name;
    private int correctAnswers;

    public Result() {}

    public Result(String name, int correctAnswers) {
        this.name = name;
        this.correctAnswers = correctAnswers;
    }

    @Override
    public int compareTo(Result o) {
        return o.getCorrectAnswers() - this.getCorrectAnswers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
