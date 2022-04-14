package com.kravets.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {
    private LinearLayout linearLayout;

    private ArrayList<Result> resultsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        linearLayout = view.findViewById(R.id.linear_layout);

        QuizDBHelper dbHelper = new QuizDBHelper(getActivity());
        resultsList = dbHelper.getAllResults();
        dbHelper.close();

        for (Result el : resultsList) {
            TableRow layout = new TableRow(getActivity());
            TextView textViewLeft = new TextView(getActivity());
            TextView textViewRight = new TextView(getActivity());

            textViewLeft.setText(Integer.toString(el.getCorrectAnswers()));
            textViewLeft.setTextSize(18);
            textViewLeft.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewLeft.setWidth(0);
            textViewLeft.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 3));
            textViewRight.setText(el.getName());
            textViewRight.setTextSize(18);
            textViewRight.setWidth(0);
            textViewRight.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 15));

            layout.addView(textViewLeft);
            layout.addView(textViewRight);
            linearLayout.addView(layout);
        }

        return view;
    }
}