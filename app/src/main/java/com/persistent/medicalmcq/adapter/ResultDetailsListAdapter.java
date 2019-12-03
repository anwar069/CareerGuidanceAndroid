package com.persistent.medicalmcq.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.persistent.medicalmcq.R;
import com.persistent.medicalmcq.model.Answer;
import com.persistent.medicalmcq.model.Question;
import com.persistent.medicalmcq.model.Topic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ahmed_anwar on 20-May-15.
 */
public class ResultDetailsListAdapter extends BaseAdapter {
    /**
     * ******** Declare Used Variables ********
     */
    private Context mContext;
    Topic topic=null;
    private static LayoutInflater inflater = null;
    public Resources res;
    int i = 0;
    ArrayList<Question> questionList = new ArrayList<Question>();
    HashMap<Question, ArrayList<Answer>> attemptedQuestions= null;
    /**
     * **********  CustomAdapter Constructor ****************
     */
    public ResultDetailsListAdapter(Context c) {
    }

    public ResultDetailsListAdapter(Context c,HashMap<Question, ArrayList<Answer>> attemptedQuestions) {
        /********** Take passed values **********/
        mContext = c;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.attemptedQuestions=attemptedQuestions;
        this.questionList.addAll(attemptedQuestions.keySet());
    }

    /**
     * ***** What is the size of Passed Arraylist Size ***********
     */
    public int getCount() {
        if (questionList.size() <= 0)
            return 1;
        return questionList.size();
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
        vi = inflater.inflate(R.layout.layout_listitem_result_details, null);

        Question question = questionList.get(position);
        ((TextView)vi.findViewById(R.id.questionText)).setText(question.getQuestionText());
        for(Answer ans: question.getAnswerList()){
            if (ans.isCorrect()){
                ((TextView) vi.findViewById(R.id.correctAnswer)).setText(ans.getAnswerText());
            }
        }
        TextView yourAnswer = (TextView) vi.findViewById(R.id.yourAnswer);
        Answer attemptedAnswer = attemptedQuestions.get(question).get(0);
        if(attemptedAnswer != null) {
            yourAnswer.setText(attemptedAnswer.getAnswerText());
            if (attemptedAnswer.isCorrect()) {
                yourAnswer.setTextColor(Color.parseColor("#00aa00"));
            } else {
                yourAnswer.setTextColor(Color.RED);
            }
        }
        return vi;
    }

}
