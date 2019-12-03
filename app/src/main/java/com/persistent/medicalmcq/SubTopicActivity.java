package com.persistent.medicalmcq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.persistent.medicalmcq.adapter.SubTopicListAdapter;
import com.persistent.medicalmcq.data.impl.DatabaseHandler;
import com.persistent.medicalmcq.data.impl.DatabaseHelper;
import com.persistent.medicalmcq.model.Topic;
import com.persistent.medicalmcq.util.UiUtils;

import java.util.ArrayList;


public class SubTopicActivity extends ActionBarActivity {

    private static LayoutInflater inflater = null;
    int color;
    boolean onLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean fromResultDetails = getIntent().getBooleanExtra("FromResultDetails", false);
        if(fromResultDetails){
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_topic);


        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rootView;
        rootView = inflater.inflate(R.layout.layout_topic_grid_item, null);
        rootView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        Topic topic = (Topic) getIntent().getSerializableExtra("TOPIC");
        color = getIntent().getIntExtra("btnColor", 0);



        ((TextView) rootView.findViewById(R.id.tvTopicName)).setText(topic.getTopicName());
        ((TextView) rootView.findViewById(R.id.tvTopicName)).setTextSize(UiUtils.getSIP(this, 7));
        ((TextView) rootView.findViewById(R.id.tvSubTopicCount)).setText(DatabaseHandler.getInstance(this).getSubTopicListByTopicID(topic.getTopicID()).size() + "");
        rootView.setBackgroundColor(color);



        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayout llHeader = (LinearLayout) findViewById(R.id.llHeader);
        ListView lvSubtopics = (ListView) findViewById(R.id.lvSubTopics);

        llHeader.addView(rootView);

        if(fromResultDetails){
            llHeader.setVisibility(View.GONE);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
            getSupportActionBar().setTitle("Sub Topics");
            LinearLayout llListView = (LinearLayout) findViewById(R.id.llList);
            llListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            llListView.setBackgroundColor(color);
        }else {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.full_transparent));
            getSupportActionBar().setTitle("");
        }



        lvSubtopics.setAdapter(new SubTopicListAdapter(this, topic, color));

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_sub_topic, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mcqhome, container, false);
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
    }

}
