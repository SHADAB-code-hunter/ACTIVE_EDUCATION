package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 4/4/2017.
 */

public class Quiz_Model implements Parcelable
{
    private String id;//
    private String subject;//
    private String qname;//
    private String option1;//
    private String option2;//
    private String option3;//
    private String option4;//
    private String answer;//
    private String is_active;//
    private String opt_answe;//
    private String s_no;//

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public static final Creator<Quiz_Model> CREATOR = new Creator<Quiz_Model>() {
        public Quiz_Model createFromParcel(Parcel in) {
          //  L.m("create from parcel :Movie");
            return new Quiz_Model(in);
        }

        public Quiz_Model[] newArray(int size) {
            return new Quiz_Model[size];
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
    public String getOpt_answe() {
        return opt_answe;
    }

    public void setOpt_answe(String opt_answe) {
        this.opt_answe = opt_answe;
    }


    public Quiz_Model() {

    }


    public Quiz_Model(Parcel input) {
        id = input.readString();
        subject = input.readString();
        qname = input.readString();
        option1 = input.readString();
        option2 = input.readString();
        option3 = input.readString();
        option4 = input.readString();
        answer = input.readString();
        is_active = input.readString();
        opt_answe=input.readString();
        s_no=input.readString();
    }

    public Quiz_Model(String id, String subject, String qname, String option1, String option2, String option3, String option4, String answer, String is_active, String opt_answe, String s_no) {
        this.id=id;
        this.subject = subject;
        this.qname = qname;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.is_active = is_active;
        this.opt_answe=opt_answe;
        this.s_no=s_no;

    }

    @Override
    public String toString() {
        return "\nId: " + id +
                "\nSubject " + subject +
                "\nQname " + qname +
                "\nOption1 " + option1 +
                "\nOption2 " + option2 +
                "\nOption3 " + option3 +
                "\nOption4 " + option4 +
                "\nAnswer " + answer +
                "\nIs_active " + is_active +
                "\nOpt_Answer " + opt_answe +
                "\nS_No " + s_no +
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
        dest.writeString(opt_answe);
        dest.writeString(s_no);

    }
}
