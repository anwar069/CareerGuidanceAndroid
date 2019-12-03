package com.persistent.medicalmcq.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by amandeep_singhbhatia on 5/4/2015.
 */
public class Topic implements Serializable {
    private int topicID;
    private String topicName;
    //private ArrayList<SubTopic> subTopics=new ArrayList<SubTopic>();
    private int parentTopicID = -1;
    private String parentTopicName = ""; // This gets sets initially when the topics are in process of saving

/*
    public ArrayList<SubTopic> getSubTopics() {
        return subTopics;
    }
*/

/*
    public void setSubTopics(ArrayList<SubTopic> subTopics) {
        this.subTopics = subTopics;
    }
*/

    public Topic(int topicID, String topicName, int parentTopicID) {
        this.topicID = topicID;
        this.topicName = topicName;
        this.parentTopicID = parentTopicID;
    }

    public Topic() {
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getParentTopicID() {
        return parentTopicID;
    }

    public void setParentTopicID(int parentTopicID) {
        this.parentTopicID = parentTopicID;
    }

    public String getParentTopicName() {
        return parentTopicName;
    }

    public void setParentTopicName(String parentTopicName) {
        this.parentTopicName = parentTopicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (parentTopicID != topic.parentTopicID) return false;
        if (topicID != topic.topicID) return false;
        if (parentTopicName != null ? !parentTopicName.equals(topic.parentTopicName) : topic.parentTopicName != null)
            return false;
        if (!topicName.equals(topic.topicName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = topicID;
        result = 31 * result + topicName.hashCode();
        result = 31 * result + parentTopicID;
        result = 31 * result + (parentTopicName != null ? parentTopicName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getTopicName();
    }
}
