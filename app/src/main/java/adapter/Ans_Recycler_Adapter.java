package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.active_education.R;

import java.util.ArrayList;

import pojo.Quiz_Answer_Model;
import pojo.Quiz_Model;

/**
 * Created by GT on 4/17/2017.
 */

public class Ans_Recycler_Adapter extends RecyclerView.Adapter<Ans_Recycler_Adapter.MyViewHolder> {

    public int i;
    private Context context;
    private ArrayList<Quiz_Answer_Model> quiz_Ans_List;
    private ArrayList<Quiz_Model> mDailyQuizList;
  //  private Quiz_Answer_Model answerQuizMdl;
    private Quiz_Answer_Model answerQuizMdl;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_ques, date,id_q_id;
        public ImageView audition_img;
        private Button btn_opt1,btn_opt2,btn_opt3,btn_opt4;
        private TextView opt_a,opt_b,opt_c,opt_d,id_attempt_status;

        public MyViewHolder(View view) {
            super(view);
            id_q_id=(TextView)view.findViewById(R.id.id_q_id);
            tv_ques=(TextView)view.findViewById(R.id.tv_ques);
            opt_a=(TextView)view.findViewById(R.id.opt_a);
            opt_b=(TextView)view.findViewById(R.id.opt_b);
            opt_c=(TextView)view.findViewById(R.id.opt_c);
            opt_d=(TextView)view.findViewById(R.id.opt_d);
            id_attempt_status=(TextView)view.findViewById(R.id.id_attempt_status);
            btn_opt1=(Button)view.findViewById(R.id.btn_opt1);btn_opt2=(Button)view.findViewById(R.id.btn_opt2);
            btn_opt3=(Button)view.findViewById(R.id.btn_opt3);btn_opt4=(Button)view.findViewById(R.id.btn_opt4);

        }
    }
    @Override
    public int getItemCount() {

        return quiz_Ans_List.size();
    }

    public Ans_Recycler_Adapter(Context context, ArrayList<Quiz_Answer_Model> quiz_Ans_List, ArrayList<Quiz_Model> mDailyQuizList) {
        this.mDailyQuizList=mDailyQuizList;
        this.quiz_Ans_List=quiz_Ans_List;
        this.context=context;
    }

    @Override
    public Ans_Recycler_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.ans_recyclr_adapter, parent, false);

        return new Ans_Recycler_Adapter.MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(Ans_Recycler_Adapter.MyViewHolder holder, int position) {
      //  Quiz_Model quiz_ans_Model = mDailyQuizList.get(position);
        Quiz_Answer_Model quiz_ans_Model = quiz_Ans_List.get(position);

        holder.tv_ques.setText(quiz_ans_Model.getQname());holder.id_q_id.setText("Q. "+(position+1)+" ");
        holder.opt_a.setText(quiz_ans_Model.getOption1()); holder.opt_b.setText(quiz_ans_Model.getOption2());
        holder.opt_c.setText(quiz_ans_Model.getOption3());holder.opt_d.setText(quiz_ans_Model.getOption4());

        int ans_list_size=quiz_Ans_List.size();
        //Log.d("dswe",""+ans_list_size);
       // set_ans_btn(ans_list_size,position,quiz_ans_Model,holder);
        set_ans_btn(quiz_ans_Model,holder);
    }

    private void set_ans_btn(Quiz_Answer_Model quiz_ans_model, MyViewHolder holder) {
        int ans_opt_number= Integer.parseInt(quiz_ans_model.getChoose_Opt_Number());
        int real_ans_number= Integer.parseInt(quiz_ans_model.getOrig_Ans());
        if(getcalc_opt(quiz_ans_model))
        {
            set_btn_switch_color(real_ans_number,holder,R.drawable.btn_color_right_ans);
        }
        else if(!getcalc_opt(quiz_ans_model))
        {
            set_btn_switch_color(ans_opt_number,holder,R.drawable.btn_color_ans);
            set_btn_switch_color(real_ans_number,holder,R.drawable.btn_color_right_ans);
        }
    }

    private boolean getcalc_opt(Quiz_Answer_Model quiz_ans_model) {

        return quiz_ans_model.getChoose_Opt_Number().equals(quiz_ans_model.getOrig_Ans());
    }


/*
    private void set_ans_btn(int ans_list_size, int position, Quiz_Model quiz_ans_Model, MyViewHolder holder) {
        boolean status=true;
        if(ans_list_size>position)
        {

          setdefault_btn_view(holder);
            //Log.d("azsxw",ans_list_size+" > "+position);
            answerQuizMdl=quiz_Ans_List.get(position);
            if(quiz_ans_Model.getId().equals(answerQuizMdl.getQ_id()))
            {
                //Log.d("yhuj",quiz_ans_Model.getId()+" = "+answerQuizMdl.getQ_id());
                if (!getOpt_Calculation(quiz_ans_Model,answerQuizMdl))// case: wrong answer
                {
                    //Log.d("iuygjk",""+false);
                    //Log.d("wrongquesid",""+answerQuizMdl.getQ_id());
                    int ans_opt_number= Integer.parseInt(answerQuizMdl.getChoose_Opt_Number());
                    int real_ans_number= Integer.parseInt(quiz_ans_Model.getOpt_answe());
                    holder.id_attempt_status.setText("Wrong Answer"); holder.id_attempt_status.setBackgroundColor(context.getResources().getColor(R.color.red));
                    set_btn_switch_color(ans_opt_number,holder,R.drawable.btn_color_ans);
                    set_btn_switch_color(real_ans_number,holder,R.drawable.btn_color_right_ans);
                }
                else if(getOpt_Calculation(quiz_ans_Model,answerQuizMdl))// case: right answer
                {
                    //Log.d("jhgfrtyiu",""+true);

                    int ans_opt_number= Integer.parseInt(answerQuizMdl.getChoose_Opt_Number());
                    //Log.d("rightquesid",""+answerQuizMdl.getQ_id());
                    holder.id_attempt_status.setText("Right Answer");holder.id_attempt_status.setBackgroundColor(context.getResources().getColor(R.color.quiz_ques_color));
                    set_btn_switch_color(ans_opt_number,holder,R.drawable.btn_color_right_ans);
                }
            }*/
/*else if(!(quiz_ans_Model.getId().equals(answerQuizMdl.getQ_id())))
            {

                //Log.d("iojkll",""+position);
                int right_opt_number= Integer.parseInt(quiz_ans_Model.getOpt_answe());
                //Log.d("jbhng",""+right_opt_number);
                holder.id_attempt_status.setText("Not Attempt ");holder.id_attempt_status.setBackgroundColor(context.getResources().getColor(R.color.PurplePrimary));
                setdefault_btn_view(holder);
                set_btn_switch_color(right_opt_number,holder,R.drawable.btn_color_right_ans);

                status=false;
            }
*//*


        }
      //  else if(status) {
*/
/*
          else if (ans_list_size <= position) {
                //Log.d("zxcvzx", "" + position);
                int right_opt_number = Integer.parseInt(quiz_ans_Model.getOpt_answe());
                //Log.d("sxczs", "" + right_opt_number);
                holder.id_attempt_status.setText("Not Attempt ");
                holder.id_attempt_status.setBackgroundColor(context.getResources().getColor(R.color.PurplePrimary));
                setdefault_btn_view(holder);
                set_btn_switch_color(right_opt_number, holder, R.drawable.btn_color_right_ans);
            }
*//*

      //  }

    }
*/

    private void set_btn_switch_color(int right_opt_number, MyViewHolder holder,int btn_color_ans) {

        switch (right_opt_number)
        {
            case 1:
                set_opt_btn(holder.btn_opt1,btn_color_ans);
                break;
            case 2:
                set_opt_btn(holder.btn_opt2, btn_color_ans);
                break;
            case 3:
                set_opt_btn(holder.btn_opt3,btn_color_ans);
                break;
            case 4:
                set_opt_btn(holder.btn_opt4,btn_color_ans);
                break;

        }

    }

    private void set_opt_btn(Button btn_opt, int btn_color_right_ans) {

        btn_opt.setBackground(context.getResources().getDrawable(btn_color_right_ans));
        btn_opt.setTextColor(context.getResources().getColor(R.color.white));
    }

/*
    private void setdefault_btn_view(MyViewHolder holder) {

        holder.btn_opt1.setBackground(context.getResources().getDrawable(R.drawable.click_color)); holder.btn_opt2.setBackground(context.getResources().getDrawable(R.drawable.click_color));
        holder.btn_opt3.setBackground(context.getResources().getDrawable(R.drawable.click_color)); holder.btn_opt4.setBackground(context.getResources().getDrawable(R.drawable.click_color));
        holder.btn_opt1.setTextColor(context.getResources().getColor(R.color.quiz_ques_color)); holder.btn_opt2.setTextColor(context.getResources().getColor(R.color.quiz_ques_color));
        holder.btn_opt3.setTextColor(context.getResources().getColor(R.color.quiz_ques_color)); holder.btn_opt4.setTextColor(context.getResources().getColor(R.color.quiz_ques_color));
    }
*/

    private boolean getOpt_Calculation(Quiz_Model quiz_ans_Model, Quiz_Answer_Model answerQuizMdl) {

        //Log.d("strefd",""+quiz_ans_Model.getOpt_answe());
        //Log.d("gvfdc",""+answerQuizMdl.getChoose_Opt_Number());

        return quiz_ans_Model.getOpt_answe().equals(answerQuizMdl.getChoose_Opt_Number());

    }


}
