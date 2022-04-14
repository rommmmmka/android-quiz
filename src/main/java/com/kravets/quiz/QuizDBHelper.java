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
        for (int i = 0; i < QuestionsTable.OPTIONS.length; i++)
            cv.put(QuestionsTable.OPTIONS[i], question.getAnswers().get(i).getAnswer());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public void addResult(Result result) {
        ContentValues cv = new ContentValues();
        cv.put(ResultsTable.COLUMN_NAME, result.getName());
        cv.put(ResultsTable.COLUMN_CORRECT_ANSWERS, result.getCorrectAnswers());
        db.insert(ResultsTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Что из этого верно для списков в Python?", "Не существует ограничения на размер списка", "Список может содержать объекты любого типа, кроме другого списка", "Все элементы в списке должны быть одного типа", "Объекты в списке уникальны и не могут повторяться");
        addQuestion(q1);
        Question q2 = new Question("a = ['a', 'b', 'c', 'd']\nЧто выведет команда print(a[-1])?", "d", "a", "Появиться ошибка выхода за пределы массива", "c");
        addQuestion(q2);
        Question q3 = new Question("Что выведет команда print(9 / 2)", "4.5", "4", "5", "0");
        addQuestion(q3);
        Question q4 = new Question("Какой из перечисленных операторов имеет наименьший приоритет?", "and", "%", "+", "**");
        addQuestion(q4);
        Question q5 = new Question("Чему равно значение выражения 1 + 2 ** 3 * 4?", "33", "4097", "36", "25");
        addQuestion(q5);
        Question q6 = new Question("Что делает команда map()?", "Обрабатывает элементы в итерируемом объекте с использованием заданной функции", "Создаёт пустой ассоциативный массив (словарь)", "Разбивает строку на подстроки", "Генерирует ряд последовательных чисел в заданном диапазоне");
        addQuestion(q6);
        Question q7 = new Question("s1 = 'a'\ns2 = 'b'\ns3 = 'c'\nprint(s1.join([s2, s3]))\nЧто будет выведено в консоль?", "bac", "Сообщение об ошибке", "abc", "['a', 'b', 'c']");
        addQuestion(q7);
        Question q8 = new Question("Какой из следующих стилей рекомендуется использовать, чтобы давать переменным имена, состоящие из нескольких слов?", "multi_word_variable_name\n(Snake Case)", "MultiWordVariableName\n(Pascal Case)", "multiWordVariableName\n(Camel Case)", "");
        addQuestion(q8);
        Question q9 = new Question("print({1, 3, 2} & set('qux'))\nЧто будет выведено в консоль?", "set()", "{}", "{1, 2, 3, 'q', 'x', 'u'}", "Сообщение об ошибке");
        addQuestion(q9);
        Question q10 = new Question("Что из этого неверно?", "s[::-1][::-1] is s", "s[::-1][::-1] == s", "s[:] == s", "s[:] is s");
        addQuestion(q10);
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
                Question question = new Question(
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)),
                    c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
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
