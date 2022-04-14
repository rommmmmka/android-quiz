package com.kravets.quiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kravets.quiz.QuizContract.*;

import java.util.ArrayList;

public class QuizDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "quiz";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
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

    private void fillQuestionsTable() {
        Question q1 = new Question("1", "2", "3", "4", "5", 1);
        addQuestion(q1);
        Question q2 = new Question("2", "9", "8", "7", "6", 1);
        addQuestion(q2);
        Question q3 = new Question("7", "343", "25325", "1364", "3426326", 1);
        addQuestion(q3);
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionsList = new ArrayList<>();
        db = getReadableDatabase();
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
}
