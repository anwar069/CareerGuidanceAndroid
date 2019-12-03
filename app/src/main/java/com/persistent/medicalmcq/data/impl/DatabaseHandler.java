package com.persistent.medicalmcq.data.impl;

import com.persistent.medicalmcq.model.Answer;
import com.persistent.medicalmcq.model.Question;
import com.persistent.medicalmcq.model.SubTopic;
import com.persistent.medicalmcq.model.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by amandeep_singhbhatia on 5/7/2015.
 */
public class DatabaseHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private static DatabaseHandler dbHandler;

    private DatabaseHandler(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.open();
    }

    public static DatabaseHandler getInstance(Context context) {
        if (dbHandler == null)
            return new DatabaseHandler(context);
        return dbHandler;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void saveQuestions(List<Question> questions) {

        ContentValues values = null;

        HashMap<String, Topic> topicMap = getTopicMap();
        for (Question question : questions) {
            values = new ContentValues();
            int tempTopicID = topicMap.get(question.getTopic().getTopicName() + question.getTopic().getParentTopicName()).getTopicID();
            values.put(DatabaseHelper.TOPIC_ID, tempTopicID);
            values.put(DatabaseHelper.QUESTION_TEXT, question.getQuestionText());
            values.put(DatabaseHelper.ADDITIONAL_INFO, question.getAdditionalInfo());
            values.put(DatabaseHelper.IMG_PATH, question.getImgPath());
/*            HashMap<String, Integer> subTopicMap = getSubTopicMapByTopicID(tempTopicID);
            values.put(DatabaseHelper.SUB_TOPIC_ID, subTopicMap.get(question.getTopic().getSubTopics().get(0).getSubTopicName()));*/
            long questionID = database.insert(DatabaseHelper.TABLE_QUESTIONS, null,
                    values);
            if (questionID > 0) {
                for (Answer answer : question.getAnswerList()) {
                    values = new ContentValues();
                    values.put(DatabaseHelper.QUESTION_ID, questionID);
                    values.put(DatabaseHelper.ANSWER_TEXT, answer.getAnswerText());
                    values.put(DatabaseHelper.IS_CORRECT, answer.isCorrect());
                    database.insert(DatabaseHelper.TABLE_ANSWERS, null, values);
                }
            } else {
                throw new SQLException("Unable to insert question " + question.getQuestionText());
            }

        }
//        this.close();
    }

    public HashMap<String, Topic> getTopicMap() {

        HashMap<String, Topic> topicMap = new HashMap<String, Topic>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_NAME, DatabaseHelper.PARENT_TOPIC_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TOPIC,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int parebtTopicID = cursor.getInt(2);
            if (parebtTopicID == -1) {
                topicMap.put(cursor.getString(1), new Topic(cursor.getInt(0), cursor.getString(1), parebtTopicID));
            } else {
                Topic parentTopic = getTopicByID(parebtTopicID);
                topicMap.put(cursor.getString(1) + parentTopic.getTopicName(), new Topic(cursor.getInt(0), cursor.getString(1), parebtTopicID));
            }

            cursor.moveToNext();
        }
        cursor.close();
//        this.close();
        return topicMap;
    }


    public HashMap<String, Integer> getSubTopicMapByTopicID(int topicID) {

        HashMap<String, Integer> subTopicMap = new HashMap<String, Integer>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TOPIC,
                allColumns, DatabaseHelper.PARENT_TOPIC_ID + " = " + topicID, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            subTopicMap.put(cursor.getString(1), cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();
//        this.close();
        return subTopicMap;
    }

    public ArrayList<Topic> getTopicList() {
//        this.open();
        ArrayList<Topic> topicList = new ArrayList<Topic>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_NAME, DatabaseHelper.PARENT_TOPIC_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TOPIC,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int tempID = cursor.getInt(0);
            topicList.add(new Topic(tempID, cursor.getString(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        //this.close();
        return topicList;
    }

    public ArrayList<Topic> getParentTopicList() {
//        this.open();
        ArrayList<Topic> topicList = new ArrayList<Topic>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_NAME, DatabaseHelper.PARENT_TOPIC_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TOPIC,
                allColumns, DatabaseHelper.PARENT_TOPIC_ID + " = " + -1, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int tempID = cursor.getInt(0);
            topicList.add(new Topic(tempID, cursor.getString(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        //this.close();
        return topicList;
    }

    public ArrayList<Topic> getSubTopicListByTopicID(int topicID) {
//        this.open();
        ArrayList<Topic> subTopicList = new ArrayList<Topic>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_NAME, DatabaseHelper.PARENT_TOPIC_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TOPIC,
                allColumns, DatabaseHelper.PARENT_TOPIC_ID + " = " + topicID, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            subTopicList.add(new Topic(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        //this.close();
        return subTopicList;
    }


    public void saveTopics(List<Topic> topics) {

        ContentValues values = null;
        for (Topic topic : topics) {
            values = new ContentValues();
            long parentID = getOrSaveParentTopic(topic.getParentTopicName());
            values.put(DatabaseHelper.TOPIC_NAME, topic.getTopicName());
           /* HashMap<String, Topic> allTopics = getTopicMap();
            if (allTopics.containsKey(topic.getParentTopicName())) {
                parentID = allTopics.get(topic.getParentTopicName()).getParentTopicID();
            }*/
            values.put(DatabaseHelper.PARENT_TOPIC_ID, (int) parentID);
            database.insert(DatabaseHelper.TABLE_TOPIC, null,
                    values);

        }
    }

    ;


    //Returns the topicID of ParentTopic Passed
    private long getOrSaveParentTopic(String topicName) {
        long parentID = -1;
        String[] allColumns = {DatabaseHelper.COLUMN_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TOPIC,
                allColumns, DatabaseHelper.TOPIC_NAME + " = '" + topicName + "'", null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            parentID = cursor.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.TOPIC_NAME, topicName);
            values.put(DatabaseHelper.PARENT_TOPIC_ID, -1);
            parentID = database.insert(DatabaseHelper.TABLE_TOPIC, null,
                    values);
        }
        cursor.close();
        return parentID;
    }


/*    public void saveSubTopics(List<SubTopic> subTopics, long topicID) {

        ContentValues values = null;
        for (SubTopic subTopic : subTopics) {
            values = new ContentValues();
            values.put(DatabaseHelper.TOPIC_ID, topicID);
            values.put(DatabaseHelper.SUB_TOPIC_NAME, subTopic.getSubTopicName());
            long id = database.insert(DatabaseHelper.TABLE_SUB_TOPIC, null,
                    values);
            Log.w(DatabaseHelper.class.getName(),
                    "Upgrading " + id);
        }
//        this.close();
    }*/

    public ArrayList<Question> getQuestionsByTopicID(int topicID) {
        ArrayList<Question> questions = new ArrayList<Question>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.TOPIC_ID
                , DatabaseHelper.QUESTION_TEXT, DatabaseHelper.ADDITIONAL_INFO,DatabaseHelper.IMG_PATH};
        Cursor cursor = database.query(DatabaseHelper.TABLE_QUESTIONS,
                allColumns, DatabaseHelper.TOPIC_ID + " = " + topicID, null, null, null, null);
        Topic topic = getTopicByID(topicID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ArrayList<Answer> answers = getAnwersByQuestionID(cursor.getInt(0));
            questions.add(new Question(cursor.getInt(0), topic, answers, cursor.getString(2), cursor.getString(3),cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();
        //this.close();
        return questions;
    }

    public HashMap<Question, ArrayList<Answer>> getResultDetailsByTopicID(int topicID) {
        HashMap<Question, ArrayList<Answer>> resultMap = new HashMap<Question, ArrayList<Answer>>();

        ArrayList<Question> questionsByTopicID = getQuestionsByTopicID(topicID);
        for (Question ques : questionsByTopicID) {
            ArrayList<Answer> answers = new ArrayList<Answer>();


            String[] allColumns = {DatabaseHelper.COLUMN_ID,
                    DatabaseHelper.QUESTION_ID, DatabaseHelper.ANSWER_ID};
            Cursor cursor = database.query(DatabaseHelper.TABLE_RESULT_DETAILS,
                    allColumns, DatabaseHelper.QUESTION_ID + " = " + ques.getQuestionID(), null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                int attemptedAnswerId = cursor.getInt(2);
                answers.add(getAnswerByAnswerID(attemptedAnswerId));
                cursor.moveToNext();
            }

            if (answers.size() > 0) {
                resultMap.put(ques, answers);
            } else {
                resultMap.put(ques, null);
            }

        }

        return resultMap;
    }

    private Answer getAnswerByAnswerID(int answerID) {
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.ANSWER_TEXT, DatabaseHelper.IS_CORRECT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_ANSWERS,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + answerID, null, null, null, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            boolean isCorrect = cursor.getInt(2) == 1 ? true : false;
            return new Answer(cursor.getInt(0), isCorrect, cursor.getString(1));
        }

        return null;
    }


    private ArrayList<Answer> getAnwersByQuestionID(int questionID) {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.ANSWER_TEXT, DatabaseHelper.IS_CORRECT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_ANSWERS,
                allColumns, DatabaseHelper.QUESTION_ID + " = " + questionID, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            boolean isCorrect = cursor.getInt(2) == 1 ? true : false;
            answers.add(new Answer(cursor.getInt(0), isCorrect, cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        //this.close();
        return answers;
    }

    public boolean truncateAndRecreateDB(int dbVersion) {
        dbHelper.onUpgrade(database, dbVersion - 1, dbVersion);
        return true;
    }

    public Topic getTopicByID(int topicID) {
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_NAME, DatabaseHelper.PARENT_TOPIC_ID};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TOPIC,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + topicID, null, null, null, null);
        cursor.moveToFirst();
        Topic topic = null;
        if (!cursor.isAfterLast()) {
            topic = new Topic(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
        }
        cursor.close();
        //this.close();
        return topic;
    }

/*    public SubTopic getSubTopicByID(int subTopicID) {
        String[] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.TOPIC_ID,
                DatabaseHelper.SUB_TOPIC_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_SUB_TOPIC,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + subTopicID, null, null, null, null);
        cursor.moveToFirst();
        SubTopic subTtopic = null;
        if (!cursor.isAfterLast()) {
            subTtopic = new SubTopic(cursor.getInt(0), cursor.getString(2));
        }
        cursor.close();
        //this.close();
        return subTtopic;
    }*/

    public void saveResult(int topicID, int marksObtained, int marksOutOff) {
        String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_RESULT +
                " (" + DatabaseHelper.TOPIC_ID + " , "
                + DatabaseHelper.MARKS_OBTAINED + ","
                + DatabaseHelper.MARKS_OUT_OFF + " ) VALUES ("
                + topicID + " , "
                + marksObtained + " , "
                + marksOutOff + " ) ";
        database.execSQL(sql);
    }


    public void saveResultDetails(HashMap<Question, ArrayList<Answer>> userAttempt, ArrayList<Question> questions) {
        for (Question ques : questions) {
            int attemptedAnsId = 0;
            if (userAttempt.get(ques) == null) {
                attemptedAnsId = -1;
            } else {
                attemptedAnsId = userAttempt.get(ques).get(0).getAnswerID();
            }
            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_RESULT_DETAILS +
                    " (" + DatabaseHelper.QUESTION_ID + " , "
                    + DatabaseHelper.ANSWER_ID + " ) VALUES ("
                    + ques.getQuestionID() + " , "
                    + attemptedAnsId + " ) ";
            database.execSQL(sql);
        }
    }

    public HashMap<Integer, ArrayList<ArrayList<Integer>>> getAllResults() {
        HashMap<Integer, ArrayList<ArrayList<Integer>>> resultMap = new HashMap<>();
        ArrayList<ArrayList<Integer>> resultArray = new ArrayList<ArrayList<Integer>>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_ID, DatabaseHelper.MARKS_OBTAINED, DatabaseHelper.MARKS_OUT_OFF};
        Cursor cursor = database.query(DatabaseHelper.TABLE_RESULT,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ArrayList<Integer> singleResult = new ArrayList<Integer>();
            singleResult.add(cursor.getInt(0));
            int topicID = cursor.getInt(1);
            singleResult.add(topicID);
            singleResult.add(cursor.getInt(2));
            singleResult.add(cursor.getInt(3));
            Topic topic = getTopicByID(topicID);
            ArrayList<ArrayList<Integer>> topicArrayList = new ArrayList<>();
            if (resultMap.containsKey(topic.getParentTopicID())) {
                topicArrayList = resultMap.get(topic.getParentTopicID());
            }
            topicArrayList.add(singleResult);
            resultMap.put(topic.getParentTopicID(), topicArrayList);
            cursor.moveToNext();
        }
        cursor.close();
        //this.close();
        return resultMap;
    }

    public ArrayList<Integer> getResultsByTopicID(int topicID) {
        ArrayList<Integer> resultArray = new ArrayList<Integer>();
        String[] allColumns = {DatabaseHelper.COLUMN_ID,
                DatabaseHelper.TOPIC_ID, DatabaseHelper.MARKS_OBTAINED, DatabaseHelper.MARKS_OUT_OFF};
        Cursor cursor = database.query(DatabaseHelper.TABLE_RESULT,
                allColumns, DatabaseHelper.TOPIC_ID + " = " + topicID, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            resultArray.add(cursor.getInt(0));
            resultArray.add(cursor.getInt(1));
            resultArray.add(cursor.getInt(2));
            resultArray.add(cursor.getInt(3));
        }
        cursor.close();
        //this.close();
        return resultArray;
    }


}

