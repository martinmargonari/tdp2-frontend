package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.QuestionAdapter;
import com.example.margonari.tdp2_frontend.domain.Question;
import com.example.margonari.tdp2_frontend.services.ExamResultServices;

import java.util.ArrayList;

public class EvaluationActivity extends AppCompatActivity {

    private RecyclerView questionsRecyclerView;
    private RecyclerView.Adapter questionsAdapter;
    private RecyclerView.LayoutManager questionsLayoutManager;

    private static String LOG_TAG = "EvaluationActivity";
    private ArrayList<Question> questionsList;
    private String api_token;
    private String session_id;
    private ArrayList<Integer> answers;
    private String unit_id;
    private int correct_answers=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        Intent intent = getIntent();
        api_token = getIntent().getStringExtra("API_TOKEN");
        questionsList = (ArrayList<Question>) intent.getSerializableExtra("QUESTIONS");
        session_id = getIntent().getStringExtra("SESSION_ID");
        unit_id = getIntent().getStringExtra("UNITY_ID");




        questionsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_questions);
        questionsRecyclerView.setHasFixedSize(true);
        questionsLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(questionsLayoutManager);
        questionsRecyclerView.setFocusable(false);
        questionsAdapter = new QuestionAdapter(questionsList);
        questionsRecyclerView.setAdapter(questionsAdapter);

        final Button button = (Button) findViewById(R.id.button_send_answers);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String mySessionId=  session_id;
                String myApiToken =  api_token;
                String myUnit_id=  unit_id;
                int questionAmount=  questionsList.size();
                correct_answers   =  getNumberOfCorrectAnswers();

                HttpRequestTaskExamResult httpRequestTaskExamResult= new HttpRequestTaskExamResult();
                httpRequestTaskExamResult.execute(myApiToken,mySessionId,myUnit_id,String.valueOf(questionAmount),String.valueOf(correct_answers));


            }
        });
    }

    private int getNumberOfCorrectAnswers() {
        ArrayList<Integer> answers = ((QuestionAdapter)questionsAdapter).getAnswers();
        int correct_answers=0;
        for (int i = 0; i < answers.size();i++) {
          int correctAnswerNumber= questionsList.get(i).getCorrectAnswerNumber();
            if(answers.get(i)==correctAnswerNumber) correct_answers++;


        }
        Log.d("CorrectAnswers", String.valueOf(correct_answers));
        return correct_answers;

    }

    private ArrayList<Question> getDataSetQuestions() {
        //TODO
        ArrayList results = new ArrayList<Question>();

        return results;
    }


    private class HttpRequestTaskExamResult extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {

                String myApiToken 		=  params[0];
                String mySessionId		=  params[1];
                String unit_id          =  params[2];
                String correct_answers   =  params[3];
                String questions_amount  =  params[4];



                ExamResultServices examResultServices=  new ExamResultServices();
                        examResultServices.make(myApiToken, mySessionId,unit_id,correct_answers,questions_amount);

            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }
}
