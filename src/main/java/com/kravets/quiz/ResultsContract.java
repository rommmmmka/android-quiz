package com.kravets.quiz;

import android.provider.BaseColumns;

public final class ResultsContract {

    public ResultsContract() {}

    public static class ResultsTable implements BaseColumns {
        public static final String TABLE_NAME = "results";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CORRECT_ANSWERS = "correct_answers";
    }
}
