package com.kravets.quiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kravets.quiz.QuestionsContract.*;
import com.kravets.quiz.ResultsContract.*;

import java.util.ArrayList;
import java.util.Collections;

public class QuizDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "quiz";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
//        onUpgrade(this.getWritableDatabase(), 0, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NUMBER + " INTEGER )";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        final String SQL_CREATE_RESULTS_TABLE = "CREATE TABLE " +
                ResultsTable.TABLE_NAME + " ( " +
                ResultsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ResultsTable.COLUMN_NAME + " TEXT, " +
                ResultsTable.COLUMN_CORRECT_ANSWERS + " INTEGER )";

        db.execSQL(SQL_CREATE_RESULTS_TABLE);

        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ResultsTable.TABLE_NAME);
        onCreate(db);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getAnswer1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getAnswer2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getAnswer3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getAnswer4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NUMBER, question.getCorrectAnswerId());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public void addResult(Result result) {
        ContentValues cv = new ContentValues();
        cv.put(ResultsTable.COLUMN_NAME, result.getName());
        cv.put(ResultsTable.COLUMN_CORRECT_ANSWERS, result.getCorrectAnswers());
        db.insert(ResultsTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("1", "2", "3", "4", "5", 1);
        addQuestion(q1);
        Question q2 = new Question("2", "9", "8", "7", "6", 1);
        addQuestion(q2);
        Question q3 = new Question("7", "343", "25325", "1364", "3426326", 1);
        addQuestion(q3);
    }

    @SuppressLint("Range")
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionsList = new ArrayList<>();
        String[] Projection = {
                QuestionsTable._ID,
                QuestionsTable.COLUMN_QUESTION,
                QuestionsTable.COLUMN_OPTION1,
                QuestionsTable.COLUMN_OPTION2,
                QuestionsTable.COLUMN_OPTION3,
                QuestionsTable.COLUMN_OPTION4,
                QuestionsTable.COLUMN_ANSWER_NUMBER,
        };
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAnswer1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setAnswer2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setAnswer3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswer4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setCorrectAnswerId(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUMBER)));
                questionsList.add(question);
            } while (c.moveToNext());
        }
        c.close();

        return questionsList;
    }

    @SuppressLint("Range")
    public ArrayList<Result> getAllResults() {
        ArrayList<Result> resultsList = new ArrayList<>();
        String[] Projection = {
                ResultsTable._ID,
                ResultsTable.COLUMN_NAME,
                ResultsTable.COLUMN_CORRECT_ANSWERS,
        };
        Cursor c = db.query(
                ResultsTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Result result = new Result();
                result.setName(c.getString(c.getColumnIndex(ResultsTable.COLUMN_NAME)));
                result.setCorrectAnswers(c.getInt(c.getColumnIndex(ResultsTable.COLUMN_CORRECT_ANSWERS)));
                resultsList.add(result);
            } while (c.moveToNext());
        }
        c.close();

        Collections.sort(resultsList);

        return resultsList;
    }
}
