package com.persistent.medicalmcq.data.interfaces;



import com.persistent.medicalmcq.model.Question;
import com.persistent.medicalmcq.model.Topic;

import java.util.List;

/**
 * Created by amandeep_singhbhatia on 5/4/2015.
 */
public interface DataLoader {

    List<Question> loadQuestionsByTopic(Topic topic);
    boolean loadQuestionToDB(List<Question> questions);
    boolean loadTopicToDB(List<Topic> topics);

}
