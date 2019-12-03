package com.persistent.medicalmcq.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.persistent.medicalmcq.R;
import com.persistent.medicalmcq.data.impl.DatabaseHandler;
import com.persistent.medicalmcq.model.Topic;

import java.util.ArrayList;

/**
 * Created by ahmed_anwar on 20-May-15.
 */
public class SubTopicResultListAdapter extends BaseAdapter {
    /**
     * ******** Declare Used Variables ********
     */
    private Context mContext;
    Topic topic=null;
    int color=-1;
    private static LayoutInflater inflater = null;
    public Resources res;
    int i = 0;
    ArrayList<ArrayList<Integer>> resultList = null;
    /**
     * **********  CustomAdapter Constructor ****************
     */
    public SubTopicResultListAdapter(Context c) {
    }

    public SubTopicResultListAdapter(Context c, ArrayList<ArrayList<Integer>> topic,int color) {
        /********** Take passed values **********/
        mContext = c;
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(c);
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.color=color;
        this.resultList=topic;
    }

    /**
     * ***** What is the size of Passed Arraylist Size ***********
     */
    public int getCount() {

        if (resultList.size() <= 0)
            return 1;
        return resultList.size();
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

        ArrayList<Integer> result = resultList.get(position);
        final Integer topicID = result.get(1);
        final String topicName = DatabaseHandler.getInstance(mContext).getTopicByID(topicID).getTopicName();
        ((TextView) vi.findViewById(R.id.tvSubTopicName)).setText(topicName);
        ((TextView) vi.findViewById(R.id.tvQuestionCount)).setText("Score: "+result.get(2)+" out of "+result.get(3));
        ((TextView) vi.findViewById(R.id.tvQuestionCount)).setTextColor(Color.parseColor("#4e9a30"));
        ((TextView) vi.findViewById(R.id.tvQuestionCount)).setTypeface(null, Typeface.BOLD);


        return vi;
    }

}
