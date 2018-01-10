package active;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gt.active_education.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import callbacks.Quiz_Opt_Choose_Event_Listener;
import callbacks.VP_PageChange_Listener;
import pojo.Quiz_Answer_Model;
import pojo.Quiz_Model;
import utilities.CircularToggle;
import utilities.SingleSelectToggleGroup;


/**
 * Created by GT on 6/9/2017.
 */

public class Sub_quiz_VP_Adapter extends PagerAdapter implements Quiz_Opt_Choose_Event_Listener {

    private Quiz_Opt_Choose_Event_Listener quizOptChooseEventListener;

    private static float MIN_SCALE = 0.75f;
    private Context context;
    ArrayList<HashMap<String, String>> Arrdata = null;
    private ArrayList<Quiz_Model> mlistDailyQuiz;
   // private CheckBox check_BBox1,check_BBox2,check_BBox3,check_BBox4;
    private LinearLayout lnr_opt1,lnr_opt2,lnr_opt3,lnr_opt4;
    private VP_PageChange_Listener vpPageChangeListener;
    private Quiz_Model quizModel;
    private SharedPreferences prefs,Shrd_prefs;
    private Quiz_Answer_Model quizAnswerModel,quizSharedAnswerModel;
    private ArrayList<Quiz_Answer_Model> Quiz_Ans_List;
    private TextView tv_opt_a,v,tv_opt_b,tv_opt_c,tv_opt_d;
    private TextView id_ques_no;
    private TextView id_daily_question;
    private SingleSelectToggleGroup single;
    private Boolean chk_status;
    private CircularToggle tgl_choice_a,tgl_choice_b,tgl_choice_c,tgl_choice_d;

    /*timer*/
    private Handler t_handler;
    private Timer swipeTimer;
    private Handler PQ_customHandler;
    private long PQ_startTime = 0L;
    long PQ_timeInMilliseconds = 0L;
    long PQ_timeSwapBuff = 0L;
    long PQ_updatedTime = 0L;

    private Handler  handler;
    private  Runnable Update;
    private Handler customHandler = new Handler();
    private int CRT_POS;

    public Sub_quiz_VP_Adapter(VP_PageChange_Listener vpPageChangeListener, Context context, ArrayList<Quiz_Model> mlistDailyQuiz, Quiz_Opt_Choose_Event_Listener quizOptChooseEventListener) {
        //super(context,0,Arrdata);
        this.quizOptChooseEventListener=quizOptChooseEventListener;
        this.vpPageChangeListener=vpPageChangeListener;
        this.context=context;
        this.mlistDailyQuiz=mlistDailyQuiz;

    }

    @Override
    public int getCount() {
        return mlistDailyQuiz.size();
    }


    public Object instantiateItem(View collection, final int position) {

        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.sub_vp_layout, null);

        //Log.d("listdfrf",mlistDailyQuiz.toString());
        // start_one_ques_Timer();
        single = (SingleSelectToggleGroup)layout.findViewById(R.id.group_choices);
        single.clearCheck();single.setTag(position);
        CRT_POS=position;
        quizAnswerModel=new Quiz_Answer_Model();
        quizSharedAnswerModel=new Quiz_Answer_Model();
        id_ques_no=(TextView)layout.findViewById(R.id.id_ques_no);
        id_ques_no.setText(String.valueOf(position+1));

        tv_opt_a=(TextView)layout.findViewById(R.id.opt_a);tv_opt_b=(TextView)layout.findViewById(R.id.opt_b);
        tv_opt_c=(TextView)layout.findViewById(R.id.opt_c);tv_opt_d=(TextView)layout.findViewById(R.id.opt_d);
        tgl_choice_a=(CircularToggle)layout.findViewById(R.id.choice_a);tgl_choice_b=(CircularToggle)layout.findViewById(R.id.choice_b);
        tgl_choice_c=(CircularToggle)layout.findViewById(R.id.choice_c);tgl_choice_d=(CircularToggle)layout.findViewById(R.id.choice_d);
          /*lnr_opt1=(LinearLayout)layout.findViewById(R.id.opt_linear1);lnr_opt2=(LinearLayout)layout.findViewById(R.id.opt_linear2);
            lnr_opt3=(LinearLayout)layout.findViewById(R.id.opt_linear3);lnr_opt4=(LinearLayout)layout.findViewById(R.id.opt_linear4);
         */
     //   question_timer.setTag(position);
        id_daily_question=(TextView)layout.findViewById(R.id.id_daily_question);
        // to be continiued
        id_daily_question.setText(mlistDailyQuiz.get(position).getQname());
        tv_opt_a.setText(mlistDailyQuiz.get(position).getOption1()); tv_opt_b.setText(mlistDailyQuiz.get(position).getOption2());
        tv_opt_c.setText(mlistDailyQuiz.get(position).getOption3()); tv_opt_d.setText(mlistDailyQuiz.get(position).getOption4());
        tgl_choice_a.setTag(position);tgl_choice_a.setText("a) "+mlistDailyQuiz.get(position).getOption1());
        tgl_choice_b.setTag(position);tgl_choice_b.setText("b) "+mlistDailyQuiz.get(position).getOption2());
        tgl_choice_c.setTag(position);tgl_choice_c.setText("c) "+mlistDailyQuiz.get(position).getOption3());
        tgl_choice_d.setTag(position);tgl_choice_d.setText("d) "+mlistDailyQuiz.get(position).getOption4());
        // lnr_opt1.setTag(position);lnr_opt2.setTag(position);lnr_opt3.setTag(position);lnr_opt4.setTag(position);

        Shrd_prefs =context.getApplicationContext().getSharedPreferences("Sub_Answer_Array_List", 0);
        String storedHashMapstr = Shrd_prefs.getString("SrdPrf_ArrayList", "");
        Gson gson = new Gson();
        java.lang.reflect.Type types;
        ArrayList<Quiz_Answer_Model> quiz_Answer_List = new ArrayList<Quiz_Answer_Model>();
        types = new TypeToken<ArrayList<Quiz_Answer_Model>>() {}.getType();
        quiz_Answer_List = (ArrayList<Quiz_Answer_Model>) gson.fromJson(storedHashMapstr, types);

        //Log.d("list_quiznbg",""+quiz_Answer_List);
        if (quiz_Answer_List!=null) {

            for(Quiz_Answer_Model quizAnswerModel:quiz_Answer_List)
            {
                if(Integer.parseInt(quizAnswerModel.getQ_number())==position)
                {
                    int opt_number= Integer.parseInt(quizAnswerModel.getChoose_Opt_Number());
                    //Log.d("cgcgcg",""+opt_number);

                    switch (opt_number)
                    {
                        case 1:
                            tgl_choice_a.setChecked(true);
                            break;
                        case 2:
                            tgl_choice_b.setChecked(true);
                            break;
                        case 3:
                            tgl_choice_c.setChecked(true);
                            break;
                        case 4:
                            tgl_choice_d.setChecked(true);
                            break;
                    }

                }

            }

        }
        //single.setTag(position);
        single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId, View toggle) {

                Log.d("drererre"," toggleel");


                int[] myList = {tgl_choice_a.getId(),tgl_choice_b.getId(), tgl_choice_c.getId(), tgl_choice_d.getId()};

                Log.d("idtgl",myList[0]+" "+myList[1]+" "+myList[2]+" "+myList[3]+" ");
                //Log.d("toggle_cked", "onCheckedChanged(): checkedId = " +  single.getCheckedId()+"possghj"+toggle.getTag()+"id  "+((SingleSelectToggleGroup)toggle).getCheckedId());

                int poss= (int) toggle.getTag();
                int tbgl_id=((SingleSelectToggleGroup)toggle).getCheckedId();
                int opt_choose_no=getChooseOpt_No(myList,tbgl_id);// change
                Quiz_Model quiz_model=mlistDailyQuiz.get(poss);
              /*  //Log.d("mnmnc",""+quiz_model);
                        *//* creating model liost for save in shared prefrences*//*
                //Log.d("fvff",""+mlistDailyQuiz);*/
                quizAnswerModel.setQ_id(quiz_model.getId());
                quizAnswerModel.setQname(quiz_model.getQname());
                quizAnswerModel.setOption1(quiz_model.getOption1());
                quizAnswerModel.setOption2(quiz_model.getOption2());
                quizAnswerModel.setOption3(quiz_model.getOption3());
                quizAnswerModel.setOption4(quiz_model.getOption4());
                quizAnswerModel.setQ_number(String.valueOf(poss));
                if(opt_choose_no!=-1) {
                  //  //Log.d("hgfds", String.valueOf(opt_choose_no));
                    quizAnswerModel.setChoose_Opt_Number(String.valueOf(opt_choose_no));
                    quizAnswerModel.setAns_Choosen(getChoos_ans(opt_choose_no, quiz_model));
                }
             //   //Log.d("hsggfdfcvd",quiz_model.getOpt_answe());
                quizAnswerModel.setOrig_Ans(quiz_model.getOpt_answe());
                Log.d("njdocjo",""+quizAnswerModel.getQ_id());
                update_shared_prefrences_quiz_data(quizAnswerModel);
                quizOptChooseEventListener.onOption_Choosen(poss,getChoos_ans(opt_choose_no, quiz_model),quiz_model.getId());

                vpPageChangeListener.on_opt_next_Ques();


            }
        },single);

        ((ViewPager) collection).addView(layout);

        return layout;
    }

    private String getChoos_ans(int chooseOpt_no, Quiz_Model quiz_model) {

        String str_ans="";

        switch (chooseOpt_no)
        {
            case 1:
                str_ans=quiz_model.getOption1();
                break;
            case 2:
                str_ans=quiz_model.getOption2();
                break;
            case 3:
                str_ans=quiz_model.getOption3();
                break;
            case 4:
                str_ans=quiz_model.getOption4();
                break;
        }
        //Log.d("fdsadc",""+str_ans);
        return str_ans;
    }

    private int getChooseOpt_No(int [] tgl_array,int tag) {

        for(int i=0; i<=tgl_array.length;i++)
        {
            if(tgl_array[i]==tag)
            {
                return (i+1);
            }
        }
        return -1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public void onOption_Choosen(Quiz_Answer_Model quiz_ans_model) {
        update_shared_prefrences_quiz_data(quiz_ans_model);

    }

    @Override
    public void onOption_Choosen(int postion,String str_ans,String str_Qid) {

    }

    private void update_shared_prefrences_quiz_data(Quiz_Answer_Model quizAnswerModel) {


        Gson gson = new Gson();
        java.lang.reflect.Type type;
        type = new TypeToken<ArrayList<Quiz_Answer_Model>>() {}.getType();

        prefs =context.getApplicationContext().getSharedPreferences("Sub_Answer_Array_List", 0);
        String storedHashMapString = prefs.getString("SrdPrf_ArrayList", "");

        Quiz_Ans_List = (ArrayList<Quiz_Answer_Model>) gson.fromJson(storedHashMapString, type);

        //Log.d("list_quisdecznbg",""+Quiz_Ans_List);
        // int i=Integer.parseInt(pos);

        if(contains(Quiz_Ans_List,quizAnswerModel.getQ_id()))
        {
            int ind_poss=getIndexByProperty(Quiz_Ans_List,quizAnswerModel);
            Quiz_Ans_List.remove(ind_poss);
        }
        //Log.d("njdocjo",""+quizAnswerModel);
        Quiz_Ans_List.add(quizAnswerModel);
        //Log.d("length",""+Quiz_Ans_List.size());
        //Log.d("new_list_remove",""+Quiz_Ans_List);
        SharedPreferences.Editor editor = prefs.edit();
        String str_SharedPrf = gson.toJson(Quiz_Ans_List);
        editor.putString("SrdPrf_ArrayList", str_SharedPrf);
        editor.commit();

    }
   public boolean contains(ArrayList<Quiz_Answer_Model> list, String q_id) {
        for (Quiz_Answer_Model item : list) {
            if (item.getQ_id().equals(q_id)) {

                return true;
            }
        }
        return false;
    }
    private int getIndexByProperty(ArrayList<Quiz_Answer_Model> objList, Quiz_Answer_Model quizAnswerModel) {

        for (int i = 0; i < objList.size(); i++) {
            if ((objList.get(i).getQ_id()).equals(quizAnswerModel.getQ_id())) {
                return i;
            }
        }
        return -1;// not there is list
    }
}
