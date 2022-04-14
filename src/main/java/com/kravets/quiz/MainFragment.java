package com.kravets.quiz;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    private AppCompatButton buttonStart, buttonStatistics, buttonExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);


        buttonStart = view.findViewById(R.id.buttonStart);
        buttonStatistics = view.findViewById(R.id.buttonStatistics);
        buttonExit = view.findViewById(R.id.buttonExit);

        View.OnClickListener onClickBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.buttonStart:
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.mainActivity, QuizFragment.class, null)
                                .addToBackStack("2")
                                .commit();
                        break;
                    case R.id.buttonStatistics:
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.mainActivity, StatisticsFragment.class, null)
                                .addToBackStack("2")
                                .commit();
                        break;
                    case R.id.buttonExit:
                        getActivity().finish();
                        break;
                }
            }
        };

        buttonStart.setOnClickListener(onClickBtn);
        buttonStatistics.setOnClickListener(onClickBtn);
        buttonExit.setOnClickListener(onClickBtn);

        return view;

    }
}