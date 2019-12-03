package com.persistent.medicalmcq;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.persistent.medicalmcq.data.impl.DatabaseHandler;
import com.persistent.medicalmcq.model.Answer;
import com.persistent.medicalmcq.model.Question;
import com.persistent.medicalmcq.util.ScoreManager;
import com.persistent.medicalmcq.util.SystemUiHider;
import com.persistent.medicalmcq.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class QuizActivity extends ActionBarActivity {

    LinearLayout selectorLayout;
    private Button prevButton;
    boolean isFirst = true;
    ArrayList<Question> questionList = new ArrayList<Question>();
    TextView tvQuestion, tvDescription;
    ImageView imgIcon;
    RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    Button btnNext, btnPrev, btnCheck,btnSubmit;
    int CurrentQuestionIndex = 0;
    ArrayList<Button> buttonList = null;
    HashMap<Question, ArrayList<Answer>> attemptedQuestions= new HashMap<Question,ArrayList<Answer>>();
    static boolean isClearCalled=false;
    RadioGroup rgOptions;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
//        final int subTopicID = getIntent().getIntExtra("subTopicID", -1);
        final int topicID = getIntent().getIntExtra("TopicID", -1);
        final String subTopicName = getIntent().getStringExtra("subTopicName");

        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();

        supportActionBar.setTitle(subTopicName);
        final int color = getIntent().getIntExtra("Color", 0);
        supportActionBar.setBackgroundDrawable(new ColorDrawable(color));
        questionList = DatabaseHandler.getInstance(getBaseContext()).getQuestionsByTopicID(topicID);
        setUpVariables();
        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        displayQuestion(0);





    }


    private void displayQuestion(int finalI) {

        Log.d("QUIZ", questionList.get(finalI).getAdditionalInfo() + "- " + questionList.get(finalI).getImgPath());
        String description = questionList.get(finalI).getAdditionalInfo().replace("\\n", System.getProperty("line.separator"));
        tvDescription.setText(description);
        Context context = getApplicationContext();
        int id = context.getResources().getIdentifier(questionList.get(finalI).getImgPath(), "drawable",context.getPackageName());
        imgIcon.setBackgroundResource(id);

    }

    private void displayCheckBoxOptions(List<Answer> answerList) {
       // TODO: Display Checkboxes for multiple contact

    }


    private void setUpVariables() {
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        imgIcon= (ImageView) findViewById(R.id.imgicon);
//        btnCheck = (Button) findViewById(R.id.btnCheck);
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_quiz, menu);
//        return true;
//    }

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
}
