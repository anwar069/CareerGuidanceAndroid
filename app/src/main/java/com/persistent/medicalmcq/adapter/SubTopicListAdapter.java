package com.persistent.medicalmcq.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.persistent.medicalmcq.QuizActivity;
import com.persistent.medicalmcq.R;
import com.persistent.medicalmcq.SubTopicActivity;
import com.persistent.medicalmcq.data.impl.DatabaseHandler;
import com.persistent.medicalmcq.model.SubTopic;
import com.persistent.medicalmcq.model.Topic;

import java.util.ArrayList;

/**
 * Created by ahmed_anwar on 20-May-15.
 */
public class SubTopicListAdapter extends BaseAdapter {
    /**
     * ******** Declare Used Variables ********
     */
    private Context mContext;
    Topic topic=null;
    int color=-1;
    private static LayoutInflater inflater = null;
    public Resources res;
    int i = 0;
    ArrayList<Topic> subTopicList = null;
    /**
     * **********  CustomAdapter Constructor ****************
     */
    public SubTopicListAdapter(Context c) {
    }

    public SubTopicListAdapter(Context c, Topic topic,int color) {
        /********** Take passed values **********/
        mContext = c;
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(c);
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.topic=topic;
        this.color=color;
        subTopicList = databaseHandler.getSubTopicListByTopicID(topic.getTopicID());
    }

    /**
     * ***** What is the size of Passed Arraylist Size ***********
     */
    public int getCount() {

        if (subTopicList.size() <= 0)
            return 1;
        return subTopicList.size();
    }

    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }


    /**
     * *** Depends upon data size called for each row , Create each ListView row ****
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
        vi = inflater.inflate(R.layout.layout_listitem_sub_topic, null);

        ((TextView) vi.findViewById(R.id.tvSubTopicName)).setText(subTopicList.get(position).getTopicName());
//        ((TextView) vi.findViewById(R.id.tvQuestionCount)).setText((DatabaseHandler.getInstance(mContext).getQuestionsByTopicID(subTopicList.get(position).getTopicID())).size() + " questions");

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iQuestions = new Intent(mContext, QuizActivity.class);
                iQuestions.putExtra("TopicID", subTopicList.get(position).getTopicID() );
                iQuestions.putExtra("subTopicName", subTopicList.get(position).getTopicName());
                iQuestions.putExtra("Color", color);
                iQuestions.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.getApplicationContext().startActivity(iQuestions);
            }
        });


        return vi;
    }

}
