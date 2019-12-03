package com.persistent.medicalmcq.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amandeep_singhbhatia on 5/7/2015.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_QUESTIONS = "question";
    public static final String TABLE_ANSWERS = "answer";
    public static final String TABLE_TOPIC = "topic";
    public static final String TABLE_SUB_TOPIC = "sub_topic";
    public static final String TABLE_RESULT = "result";
    public static final String TABLE_RESULT_DETAILS = "result_details";

    public static final String COLUMN_ID = "id";
    public static final String MARKS_OBTAINED = "marks";
    public static final String MARKS_OUT_OFF = "out_off";
    public static final String QUESTION_TEXT = "question_text";
    public static final String ADDITIONAL_INFO = "additional_info";
    public static final String IMG_PATH = "img_path";
    public static final String TOPIC_ID = "topic_id";
    public static final String SUB_TOPIC_ID = "sub_topic_id";
    public static final String QUESTION_ID = "question_id";
    public static final String ANSWER_ID = "answer_id";
    public static final String ANSWER_TEXT = "answer_text";
    public static final String IS_CORRECT = "is_correct";
    public static final String TOPIC_NAME = "topic_name";
    public static final String SUB_TOPIC_NAME = "sub_topic_name";
    public static final String PARENT_TOPIC_ID = "p_topic_id";

    private static final String QUESTION_CREATE = "create table "
            + TABLE_QUESTIONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + TOPIC_ID + " int not null, " + QUESTION_TEXT
            + " text , " + ADDITIONAL_INFO + " text, " + IMG_PATH + " text);";
    private static final String ANSWER_CREATE = "create table "
            + TABLE_ANSWERS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + QUESTION_ID + " int not null, "
            + ANSWER_TEXT + " text ,  " + IS_CORRECT + " int , " + ADDITIONAL_INFO + " text);";

    // Database creation sql statement
    private static final String RESULT_CREATE = "create table "
            + TABLE_RESULT + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + TOPIC_ID + " int not null unique, "
            + MARKS_OBTAINED + " int, " + MARKS_OUT_OFF + " int );";

    private static final String RESULT_DETAILS_CREATE = "create table "
            + TABLE_RESULT_DETAILS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + QUESTION_ID + " int not null unique, "
            + ANSWER_ID + " int not null);";

    private static final String TOPIC_CREATE = "create table "
            + TABLE_TOPIC + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + TOPIC_NAME + " text , "+ PARENT_TOPIC_ID + " integer);";

    private static final String SUB_TOPIC_CREATE = "create table "
            + TABLE_SUB_TOPIC + "(" + COLUMN_ID
            + " integer primary key autoincrement, "+ TOPIC_ID + " int not null, "  + SUB_TOPIC_NAME + " text );";

    private static final String DATABASE_NAME = "mcq.db";
    private static final int DATABASE_VERSION =1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TOPIC_CREATE);
       // database.execSQL(SUB_TOPIC_CREATE);
        database.execSQL(QUESTION_CREATE);
        database.execSQL(ANSWER_CREATE);
        database.execSQL(RESULT_CREATE);
        database.execSQL(RESULT_DETAILS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUB_TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULT_DETAILS);

        onCreate(db);
    }

}
