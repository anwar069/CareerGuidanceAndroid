package com.persistent.medicalmcq.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.hardware.usb.UsbInterface;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.TextView;

import com.persistent.medicalmcq.R;
import com.persistent.medicalmcq.SubTopicActivity;
import com.persistent.medicalmcq.data.impl.DatabaseHandler;
import com.persistent.medicalmcq.model.Topic;
import com.persistent.medicalmcq.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ahmed_anwar on 30-Apr-15.
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    TypedArray ta;
    ArrayList<Topic> topics= new ArrayList<Topic>();
    ArrayList<Topic> originalTopicsList= new ArrayList<Topic>();
    private static LayoutInflater inflater=null;
    int GRID_HEIGHT;
    private TopicFilter filter;
    private HashMap<Topic,Integer> topicColorHashMap=new HashMap<Topic,Integer>();

    public GridAdapter(Context c) {
        mContext = c;
        ta=  c.getResources().obtainTypedArray(R.array.metroColors);
        DatabaseHandler databaseHandler =  DatabaseHandler.getInstance(c);
        inflater = ( LayoutInflater )c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        topics = databaseHandler.getParentTopicList();
        originalTopicsList=topics;
        GRID_HEIGHT = UiUtils.getDIP(mContext, 150);


        int i=0;
        for (Topic topic: topics){
            topicColorHashMap.put(topic,ta.getColor(i++ % (ta.length()), 0));
        }
    }

    public Filter getFilter() {
        if (filter == null){
            filter  = new TopicFilter();
        }
        return filter;
    }

    public int getCount() {
        return topics.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
//        final Button button;
////        if (convertView == null) {
////            // if it's not recycled, initialize some attributes
//            button = new Button(mContext);
//            button.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UiUtils.getDIP(mContext,150)));
//
//            button.setHeight(button.getWidth());
//            button.setText(topics.get(position).getSubTopicName());
//            button.setTextColor(Color.WHITE);
//            button.setTextSize(UiUtils.getSIP(mContext,5));
//            button.setGravity(Gravity.BOTTOM);
////            button.setClickable(false);
//            button.setPadding(5, 5, 5, 5);
//        } else {
//            button = (Button) convertView;
//        }

        final Topic currentTopic=topics.get(position);
        final View gridItem;
            gridItem = inflater.inflate(R.layout.layout_topic_grid_item, null);
            gridItem.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GRID_HEIGHT));
            ((TextView) gridItem.findViewById(R.id.tvTopicName)).setText(currentTopic.getTopicName());
          ArrayList<Topic> subTopicList = DatabaseHandler.getInstance(mContext).getSubTopicListByTopicID(currentTopic.getTopicID());
        ((TextView) gridItem.findViewById(R.id.tvSubTopicCount)).setText(subTopicList.size() + " courses available");

            gridItem.setBackgroundColor(topicColorHashMap.get(currentTopic));

        gridItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Animation
                Animation animFadeout;
//                Toast.makeText(mContext, "Topic " + button.getText(), Toast.LENGTH_LONG).show();
//
                animFadeout = AnimationUtils.loadAnimation(mContext,
                        R.anim.abc_fade_out);
//                int[] locations= new int[4];
//                gridItem.getLocationOnScreen(locations);
//                int deltaX = locations[0];
//                int deltaY = locations[1]- ((ActionBarActivity) mContext).getSupportActionBar().getHeight();

//                Toast.makeText(mContext,locations[0] +" "+locations[1],Toast.LENGTH_LONG).show();
//
//                TranslateAnimation anim = new TranslateAnimation(
//                        TranslateAnimation.ABSOLUTE, 0.0f,
//                        TranslateAnimation.ABSOLUTE, -deltaX,
//                        TranslateAnimation.ABSOLUTE, 0.0f,
//                        TranslateAnimation.ABSOLUTE, -deltaY
//                );
//                anim.setFillAfter(false);
//                anim.setFillBefore(false);
//                anim.setFillEnabled(false);
//                anim.setDuration(1000);

                animFadeout.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent iSubtopics=new Intent(mContext,SubTopicActivity.class);
//                        iSubtopics.putExtra("topicName", topics.get(position).getTopicName());
                        iSubtopics.putExtra("btnColor", topicColorHashMap.get(currentTopic));
                        iSubtopics.putExtra("TOPIC", currentTopic);
                        iSubtopics.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        mContext.getApplicationContext().startActivity(iSubtopics);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                gridItem.startAnimation(animFadeout);
//                Intent iLevel=new Intent(mContext,LevelActivity.class);
//                iLevel.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



            }
        });
        return gridItem;
    }

//    // references to our images
//    private Integer[] mThumbIds = {
//
//    };





//    static final String[] letters = new String[] {
//          };


    private class TopicFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Topic> filteredItems = new ArrayList<Topic>();

                for(int i = 0, l = originalTopicsList.size(); i < l; i++)
                {
                    Topic topic = originalTopicsList.get(i);
                    if(topic.toString().toLowerCase().contains(constraint))
                        filteredItems.add(topic);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = originalTopicsList;
                    result.count = originalTopicsList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            topics = (ArrayList<Topic>)results.values;
            notifyDataSetChanged();
//            topics.clear();
//            for(int i = 0, l = countryList.size(); i < l; i++)
//                add(countryList.get(i));
//            notifyDataSetInvalidated();
        }
    }


}