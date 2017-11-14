package com.gt.active_education;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import adapter.Ans_Recycler_Adapter;
import pojo.Quiz_Answer_Model;
import pojo.Quiz_Model;

/**
 * Created by GT on 4/17/2017.
 */

public class Review_Answer_Activity extends AppCompatActivity {
    private RecyclerView id_review_recycler;
    private Ans_Recycler_Adapter ansRecyclerAdapter;
    private SharedPreferences prefs;
    private ArrayList<Quiz_Answer_Model> Quiz_Ans_List;
    private ArrayList<Quiz_Model> mDailyQuizList;
    private TextView id_back_quicz_Quick_btn;
    private MediaPlayer mPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_answer);
        id_back_quicz_Quick_btn=(TextView)findViewById(R.id.id_back_quicz_Quick_btn);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mDailyQuizList = this.getIntent().getParcelableArrayListExtra("quiz_ques_list");
        String storedHashMapString=this.getIntent().getStringExtra("str_anslist");


        //Log.d("ghty", String.valueOf(mDailyQuizList.size()));
        //Log.d("azxas",storedHashMapString);
        Gson gson = new Gson();java.lang.reflect.Type type;type = new TypeToken<ArrayList<Quiz_Answer_Model>>() {}.getType();

        Quiz_Ans_List = (ArrayList<Quiz_Answer_Model>) gson.fromJson(storedHashMapString, type);

        //Log.d("length_review", String.valueOf(Quiz_Ans_List.size()));
        id_review_recycler=(RecyclerView)findViewById(R.id.id_review_recycler);
        ansRecyclerAdapter=new Ans_Recycler_Adapter(getApplicationContext(),Quiz_Ans_List,mDailyQuizList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        id_review_recycler.setLayoutManager(mLayoutManager);
        //ansRecyclerAdapter=new Ans_Recycler_Adapter(getApplicationContext());
        id_review_recycler.setAdapter(ansRecyclerAdapter);
        id_back_quicz_Quick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   finish();
            }
        });
    }

    private void swap_page_audio_call() {
        mPlay = MediaPlayer.create(Review_Answer_Activity.this, R.raw.swap_page);
        mPlay.start();
    }
    private void common_click_sound() {

        mPlay = MediaPlayer.create(Review_Answer_Activity.this, R.raw.dialog_aud);
        mPlay.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        swap_page_audio_call();
    }
}
