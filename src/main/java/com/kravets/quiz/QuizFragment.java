package com.kravets.quiz;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class QuizFragment extends Fragment {
    private TextView questionNumber, scoreCounter, questionText, correctOrNotMessage;
    private AppCompatButton button1, button2, button3, button4;

    private ArrayList<Question> questionsList;
    private int currentQuestionNumber, totalQuestionsNumber, questionsCorrect, questionsWrong;
    private Question currentQuestion;

    private boolean rightAnswer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        questionNumber = view.findViewById(R.id.questionNumber);
        scoreCounter = view.findViewById(R.id.scoreCounter);
        questionText = view.findViewById(R.id.questionText);
        correctOrNotMessage = view.findViewById(R.id.correctOrNotMessage);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);

        QuizDBHelper dbHelper = new QuizDBHelper(getActivity());
        questionsList = dbHelper.getAllQuestions();
        dbHelper.close();

        startQuiz();

        return view;
    }

    private void startQuiz() {
        currentQuestionNumber = 0;
        questionsCorrect = 0;
        questionsWrong = 0;
        totalQuestionsNumber = questionsList.size();
        Collections.shuffle(questionsList);

        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionNumber < totalQuestionsNumber) {
            currentQuestion = questionsList.get(currentQuestionNumber);
            questionText.setText(currentQuestion.getQuestion());
            button1.setText(currentQuestion.getAnswer1());
            button2.setText(currentQuestion.getAnswer2());
            button3.setText(currentQuestion.getAnswer3());
            button4.setText(currentQuestion.getAnswer4());
            rightAnswer = false;

            View.OnClickListener onClickBtn = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int correctAnswerButton = new int[] {R.id.button1, R.id.button2, R.id.button3, R.id.button4}[currentQuestion.getCorrectAnswerId() - 1];
                    if (view.getId() == correctAnswerButton) {
                        questionsCorrect++;
                        rightAnswer = true;
                        correctOrNotMessage.setTextColor(getResources().getColor(R.color.text_color_correct));
                        correctOrNotMessage.setText("Верно :)");
                    } else {
                        questionsWrong++;
                        correctOrNotMessage.setTextColor(getResources().getColor(R.color.text_color_wrong));
                        correctOrNotMessage.setText("Неверно :(");
                    }
                    showNextQuestion();
                }
            };
            button1.setOnClickListener(onClickBtn);
            button2.setOnClickListener(onClickBtn);
            button3.setOnClickListener(onClickBtn);
            button4.setOnClickListener(onClickBtn);

            currentQuestionNumber++;

            questionNumber.setText(String.format("Вопрос: %d/%d", currentQuestionNumber, totalQuestionsNumber));
            scoreCounter.setText(String.format("Правильных: %d, неправильных: %d", questionsCorrect, questionsWrong));
        } else {
            SaveFragment saveFragment = SaveFragment.newInstance(questionsCorrect, questionsWrong, rightAnswer);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.mainActivity, saveFragment, null)
                    .commit();
        }
    }
}