package pojo;

import android.os.Parcel;
import android.os.Parcelable;

import extras.Constants;


/**
 * Created by akashdeep on 16-Apr-17.
 */

public class Quiz_Subject_Ques_Model implements Parcelable
{

    public static final Creator<Quiz_Subject_Ques_Model> CREATOR = new Creator<Quiz_Subject_Ques_Model>() {
        public Quiz_Subject_Ques_Model createFromParcel(Parcel in) {
          //  L.m("create from parcel :Movie");
            return new Quiz_Subject_Ques_Model(in);
        }

        public Quiz_Subject_Ques_Model[] newArray(int size) {
            return new Quiz_Subject_Ques_Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getDaily_flag() {
        return daily_flag;
    }

    public void setDaily_flag(String daily_flag) {
        this.daily_flag = daily_flag;
    }


    private String id= Constants.NA;
    private String subject=Constants.NA;
    private String qname= Constants.NA;
    private String option1= Constants.NA;
    private String option2= Constants.NA;
    private String option3= Constants.NA;
    private String option4= Constants.NA;
    private String answer= Constants.NA;
    private String is_active= Constants.NA;
    private String daily_flag= Constants.NA;

    public String getOption_answer() {
        return option_answer;
    }

    public void setOption_answer(String option_answer) {
        this.option_answer = option_answer;
    }

    private String option_answer= Constants.NA;

    public Quiz_Subject_Ques_Model(Parcel input) {
        id = input.readString();
        subject = input.readString();
        qname = input.readString();
        option1 = input.readString();
        option2 = input.readString();
        option3 = input.readString();
        option4 = input.readString();
        answer = input.readString();
        is_active = input.readString();
        daily_flag = input.readString();
        option_answer=input.readString();
    }

    public Quiz_Subject_Ques_Model() {

    }
    public Quiz_Subject_Ques_Model(String id, String subject, String qname, String option1, String option2, String option3, String option4, String answer, String is_active, String daily_flag, String option_answer) {
        this.id=id;
        this.subject = subject;
        this.qname = qname;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer=answer;
        this.is_active=is_active;
        this.daily_flag=daily_flag;
        this.option_answer=option_answer;
    }

    @Override
    public String toString() {
        return "\nSub_Id: " + id +
                "\nSUbject " + subject +
                "\nQname " + qname +
                "\nOption1 " + option1 +
                "\nOption2 " + option2 +
                "\nOption3 " + option3 +
                "\nOption4 " + option4 +
                "\nAnswer " + answer +
                "\nIs_Active " + is_active +
                "\nDaily_Flag " + daily_flag +
                "\nopt_Ans " + option_answer +
                "\n";
    }

    @Override
    public int describeContents() {
//        L.m("describe Contents Movie");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//      L.m("writeToParcel Movie");
        dest.writeString(id);
        dest.writeString(subject);
        dest.writeString(qname);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeString(answer);
        dest.writeString(is_active);
        dest.writeString(daily_flag);
        dest.writeString(option_answer);
    }

}
