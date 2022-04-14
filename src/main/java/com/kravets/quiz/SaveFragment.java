package com.kravets.quiz;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class SaveFragment extends Fragment {

    private TextView scoreCounter, correctOrNotMessage;
    private EditText enterNameField;
    private AppCompatButton buttonSubmit;

    private int questionsCorrect, questionsWrong;
    private boolean rightAnswer;

    Fragment SaveFragment = this;

    public static SaveFragment newInstance(int questionsCorrect, int questionsWrong, boolean rightAnswer) {
        SaveFragment saveFragment = new SaveFragment();
        Bundle args = new Bundle();
        args.putInt("questionsCorrect", questionsCorrect);
        args.putInt("questionsWrong", questionsWrong);
        args.putBoolean("rightAnswer", rightAnswer);
        saveFragment.setArguments(args);
        return saveFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionsCorrect = getArguments().getInt("questionsCorrect", 0);
        questionsWrong = getArguments().getInt("questionsWrong", 0);
        rightAnswer = getArguments().getBoolean("rightAnswer", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);

        scoreCounter = view.findViewById(R.id.scoreCounter);
        correctOrNotMessage = view.findViewById(R.id.correctOrNotMessage);
        enterNameField = view.findViewById(R.id.enterNameField);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        scoreCounter.setText(String.format("Правильных: %d, неправильных: %d", questionsCorrect, questionsWrong));
        if (rightAnswer) {
            correctOrNotMessage.setTextColor(getResources().getColor(R.color.text_color_correct));
            correctOrNotMessage.setText("Верно :)");
        } else {
            correctOrNotMessage.setTextColor(getResources().getColor(R.color.text_color_wrong));
            correctOrNotMessage.setText("Неверно :(");
        }

        View.OnClickListener onClickBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enterNameField.getText().length() > 0) {
                    Result result = new Result(enterNameField.getText().toString(), questionsCorrect);
                    QuizDBHelper dbHelper = new QuizDBHelper(getActivity());
                    dbHelper.addResult(result);
                    dbHelper.close();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    View focus = getActivity().getCurrentFocus();
                    if (focus == null)
                        focus = new View(getActivity());
                    imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);

                    getParentFragmentManager().popBackStack();
                }
            }
        };
        buttonSubmit.setOnClickListener(onClickBtn);

        return view;
    }
}