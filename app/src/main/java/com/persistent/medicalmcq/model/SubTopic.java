package com.persistent.medicalmcq.model;

import java.io.Serializable;

/**
 * Created by ahmed_anwar on 21-May-15.
 */
public class SubTopic implements Serializable{
    private int subTopicID;
    private String subTopicName;

    public SubTopic(int subTopicID, String subTopicName) {
        this.subTopicID = subTopicID;
        this.subTopicName = subTopicName;
    }

    public SubTopic() {
    }

    public SubTopic(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    public int getSubTopicID() {
        return subTopicID;
    }

    public void setSubTopicID(int subTopicID) {
        this.subTopicID = subTopicID;
    }

    public String getSubTopicName() {
        return subTopicName;
    }

    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }
}
