package active;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gt.active_education.R;
import java.util.ArrayList;
import callbacks.Quiz_Sub_uestion_Listener;
import callbacks.Quiz_Subject_listener;
import pojo.Quiz_Model;
import pojo.Quiz_Subject_Model;
import pojo.Quiz_Subject_Ques_Model;
import quiz_adapter.Subject_list_Adapter;
import task.TaskLoad_Sub_Quiz;
import task.TaskQuizSubject_Async;
import utilities.App_Static_Method;
import utilities.ConnectionCheck;
import utilities.MyApplication;
import utilities.RecyclerTouchListener;

import static utilities.App_Static_Method.session_type;

/**
 * Created by GT on 4/11/2017.
 */

public class Quiz_SubList_Activity extends AppCompatActivity implements Quiz_Subject_listener, Quiz_Sub_uestion_Listener, View.OnClickListener {

    private ImageView id_back_btn;
    private RecyclerView subject_recyclerView;
    private Subject_list_Adapter subjectListAdapter;
    private ArrayList<Quiz_Subject_Model> listSubQuiz;
    private GridLayoutManager verticleLayoutManager;
    private ArrayList<Quiz_Model> mDailyQuizList;
    private MyApplication myApplication=new MyApplication();
    private SharedPreferences shrd_prf_login;
    private ConnectionCheck cCheck;
    private boolean Conn_Status=false;
    private MediaPlayer mPlay =null;
    private TextView id_back_quicz_Quick_btn;
    private SharedPreferences sharedPreferences_theme;
    private LinearLayout id_id_sub_list;
    private Toolbar toolbar2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_subject_layout);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        subject_recyclerView=(RecyclerView)findViewById(R.id.subject_recyclerView);
        id_back_quicz_Quick_btn=(TextView)findViewById(R.id.id_back_quicz_Quick_btn);
        id_id_sub_list=(LinearLayout)findViewById(R.id.id_id_sub_list);
        toolbar2=(Toolbar)findViewById(R.id.toolbar2);
        mDailyQuizList= new ArrayList<Quiz_Model>();
        // shrd_prf_login = myApplication.get_shrd_prf_login(getBaseContext()); //1
        /*  cCheck=new ConnectionCheck(getApplicationContext());
        if(cCheck.checkConnection())
        {
            Conn_Status=true;
            if (myApplication.is_User_Login(getBaseContext())) {
              //  new TaskQuizSubject_Async(Quiz_SubList_Activity.this, shrd_prf_login.getString("mobile", ""), shrd_prf_login.getString("Login_Token", "")).execute();
            }else{
                common_click_sound();
              //  ConnectionCheck.login_Dialog("null",Quiz_SubList_Activity.this,true);
            }
        } else {
            common_click_sound();
            ConnectionCheck.openDialog(Conn_Status,Quiz_SubList_Activity.this);
        }*/

        App_Static_Method.get_session_data("token");

        new TaskQuizSubject_Async(Quiz_SubList_Activity.this,session_type().get("mobile"), session_type().get("token")).execute();
        subject_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), subject_recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
              common_click_sound();
              TextView tv=(TextView)view.findViewById(R.id.tv_url);
              String str_sub_id=  tv.getText().toString();

                if(!str_sub_id.equals("")) {
                    //Log.d("dede",str_sub_id);

                    new TaskLoad_Sub_Quiz(Quiz_SubList_Activity.this,session_type().get("mobile"), session_type().get("token"),"5").execute();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        id_back_quicz_Quick_btn.setOnClickListener(this);


        //Theme_Change.Toolbar_Change_Prefrense((Toolbar) findViewById(R.id.toolbar2));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        swap_page_audio_call();
        finish();
    }

    @Override
    public void onQuiz_Subject_Loaded(ArrayList<Quiz_Subject_Model> listSubQuiz) {
        this.listSubQuiz=listSubQuiz;
        //Log.d("gdgd",""+listSubQuiz.size());
        if(listSubQuiz.size()==0) {
            common_click_sound();
//            ConnectionCheck.list_not_get(Quiz_SubList_Activity.this, "List Not Get !!!!");
        }else {
            subjectListAdapter = new Subject_list_Adapter(getApplicationContext(), listSubQuiz);
            verticleLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
            subject_recyclerView.setLayoutManager(verticleLayoutManager);
            subject_recyclerView.setAdapter(subjectListAdapter);
        }
    }

    @Override
    public void onQuiz_Sub_Ques_Loaded(ArrayList<Quiz_Subject_Ques_Model> listMovies, String str_sub_id)
    {
        if (listMovies.size()>0)
        {
            swap_page_audio_call();
              for(int i=0; i<listMovies.size();i++)
                {
                    Quiz_Model quizModel=new Quiz_Model();
                    Quiz_Subject_Ques_Model quizSubjectQuesModel=listMovies.get(i);
                    quizModel.setId(quizSubjectQuesModel.getId());quizModel.setOpt_answe(quizSubjectQuesModel.getOption_answer());
                    quizModel.setOption1(quizSubjectQuesModel.getOption1());quizModel.setOption2(quizSubjectQuesModel.getOption2());
                    quizModel.setOption3(quizSubjectQuesModel.getOption3());quizModel.setOption4(quizSubjectQuesModel.getOption4());
                    quizModel.setQname(quizSubjectQuesModel.getQname());
                    mDailyQuizList.add(quizModel);
                }
            //Log.d("fccxcv",""+mDailyQuizList);
            //Log.d("szxcvb",""+str_sub_id);
            Intent i = new Intent(getApplicationContext(), Sub_Quiz_Activity.class);
            i.putParcelableArrayListExtra("list", mDailyQuizList);
            startActivity(i);finish();
        }else if((listMovies.size()==0))
        {
            common_click_sound();
            ConnectionCheck.list_not_get(Quiz_SubList_Activity.this,"List Not Get");
        }
    }

    private void swap_page_audio_call() {
        mPlay = MediaPlayer.create(Quiz_SubList_Activity.this, R.raw.swap_page);
        mPlay.start();
    }
    private void common_click_sound() {
        mPlay = MediaPlayer.create(Quiz_SubList_Activity.this, R.raw.dialog_aud);
        mPlay.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_back_quicz_Quick_btn:
                swap_page_audio_call();
                finish();
                break;

        }
    }

}
