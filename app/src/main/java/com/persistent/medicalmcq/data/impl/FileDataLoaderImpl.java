package com.persistent.medicalmcq.data.impl;


import android.content.Context;

import com.persistent.medicalmcq.data.interfaces.DataLoader;
import com.persistent.medicalmcq.model.Answer;
import com.persistent.medicalmcq.model.Question;
import com.persistent.medicalmcq.model.SubTopic;
import com.persistent.medicalmcq.model.Topic;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by amandeep_singhbhatia on 5/4/2015.
 */
public class FileDataLoaderImpl implements DataLoader {
    public static final int NUMBER_OF_COLUMN = 7;
    Logger logger = null;
    public static ArrayList<Question> questions;
    public static ArrayList<Topic> topics;
    public static ArrayList<SubTopic> subTopics;
    String columnSplitter = "\\|";
    String optionSplitter = "\t";

    public FileDataLoaderImpl() {
        logger = Logger.getLogger("FileDataLoaderImpl");
        questions = new ArrayList<Question>();
        topics = new ArrayList<Topic>();
        subTopics=new ArrayList<SubTopic>();
    }

    @Override
    public List<Question> loadQuestionsByTopic(Topic topic) {
        return null;
    }

    @Override
    public boolean loadQuestionToDB(List<Question> questions) {
        return false;
    }

    @Override
    public boolean loadTopicToDB(List<Topic> topics) {
        return false;
    }
    //TopicName|ParentTopicName|Question|description|options|correctAnswers
    public void loadCSVToBeans(String filePath,Context context) {

        BufferedReader br = null;
        String line = "";

        int recordIndex = 0;
        HashMap<String,String> topicMap =new HashMap<String,String>();
        questions = new ArrayList<>();
        HashSet<Topic> topicSet = new HashSet<>();

        try {

            br = new BufferedReader(new InputStreamReader(context.getAssets().open("demo.csv")));
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(columnSplitter);
                ArrayList<Answer> answers = new ArrayList<Answer>();
                if (record.length == NUMBER_OF_COLUMN) {

                    Topic topic=new Topic();
                    topic.setTopicName(record[0]);
                    topic.setParentTopicName(record[1]);
                    topicSet.add(topic);
/*                    if (topicMap.containsKey(record[0]))
                    {
                        topicMap.get(record[0]).add(record[1]);
                    }*/
/*                    else
                    {
                        HashSet<String> tempSet=new HashSet<>();
                        tempSet.add(record[1]);
                        topicMap.put(record[0],tempSet);
                    }*/

                    answers = getAnswerList(record[5].split(optionSplitter));
                    markCorrectAnswers(answers, record[6]);
                    questions.add(new Question(-1, topic, answers, record[2], record[3],record[4]));
                    logger.info("Record"+ count++ + " : Details : " + record.toString());
                } else {
                    logger.info("Invalid record - need "+ NUMBER_OF_COLUMN+ " columns in record to load - Record Index " + recordIndex);
                }
                recordIndex++;
            }
            topics  = new ArrayList<Topic>(topicSet);
/*            for(String key: topicMap.keySet()){
                Topic t = new Topic();
                t.setTopicName(key);
                ArrayList<SubTopic> subTopics = new ArrayList<>();
                for (String subTopicName: topicMap.get(key)){
                    subTopics.add(new SubTopic(subTopicName));
                }
                t.setSubTopics(subTopics);
                topics.add(t);
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
    }

    private ArrayList<Answer> markCorrectAnswers(ArrayList<Answer> answers, String correctAnswers) {
        String[] correctAnsArray = correctAnswers.split(optionSplitter);

       for (Answer answer : answers) {
           for(String correctAnswer : correctAnsArray){
            if (answer.getAnswerText().equals(correctAnswer)) {
                answer.setCorrect(true);
            }
           }
        }
        return answers;
    }

    private ArrayList<Answer> getAnswerList(String[] options) {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        for (String option : options) {
            Answer ans = new Answer();
            ans.setAnswerText(option);
            answers.add(ans);
        }
        return answers;
    }
}
