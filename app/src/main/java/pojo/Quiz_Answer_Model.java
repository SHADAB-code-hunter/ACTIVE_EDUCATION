package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 4/7/2017.
 */

public class Quiz_Answer_Model implements Parcelable
{

    public String getQ_id() {
        return Q_id;
    }

    public void setQ_id(String q_id) {
        Q_id = q_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQ_number() {
        return Q_number;
    }

    public void setQ_number(String q_number) {
        Q_number = q_number;
    }

    public String getAns_Choosen() {
        return Ans_Choosen;
    }

    public void setAns_Choosen(String ans_Choosen) {
        Ans_Choosen = ans_Choosen;
    }

    public String getOrig_Ans() {
        return Orig_Ans;
    }

    public void setOrig_Ans(String orig_Ans) {
        Orig_Ans = orig_Ans;
    }

    public String getCurr_Q_Status() {
        return Curr_Q_Status;
    }

    public void setCurr_Q_Status(String curr_Q_Status) {
        Curr_Q_Status = curr_Q_Status;
    }

    public String getSrvr_Svd_Status() {
        return Srvr_Svd_Status;
    }

    public void setSrvr_Svd_Status(String srvr_Svd_Status) {
        Srvr_Svd_Status = srvr_Svd_Status;
    }

    public String getQuiz_Status_Nav() {
        return Quiz_Status_Nav;
    }

    public void setQuiz_Status_Nav(String quiz_Status_Nav) {
        Quiz_Status_Nav = quiz_Status_Nav;
    }

    public String getChoose_Opt_Number() {
        return Choose_Opt_Number;
    }

    public void setChoose_Opt_Number(String choose_Opt_Number) {
        Choose_Opt_Number = choose_Opt_Number;
    }


    public static final Creator<Quiz_Answer_Model> CREATOR = new Creator<Quiz_Answer_Model>() {
        public Quiz_Answer_Model createFromParcel(Parcel in) {
           // L.m("create from parcel :Movie");
            return new Quiz_Answer_Model(in);
        }

        public Quiz_Answer_Model[] newArray(int size) {
            return new Quiz_Answer_Model[size];
        }
    };



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
    private String Q_id;
    private String subject;
    private String qname;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String Q_number;
    private String Ans_Choosen;
    private String Orig_Ans;
    private String Curr_Q_Status;
    private String Srvr_Svd_Status;
    private String Quiz_Status_Nav;
    private String Choose_Opt_Number;

    public Quiz_Answer_Model(Parcel input) {
        Q_id = input.readString();
        subject = input.readString();
        qname = input.readString();
        option1 = input.readString();
        option2 = input.readString();
        option3 = input.readString();
        option4 = input.readString();
        Q_number = input.readString();
        Ans_Choosen = input.readString();
        Orig_Ans = input.readString();
        Curr_Q_Status = input.readString();
        Srvr_Svd_Status = input.readString();
        Quiz_Status_Nav = input.readString();
        Choose_Opt_Number =input.readString();
    }

    public Quiz_Answer_Model() {

    }
    public Quiz_Answer_Model(String Q_id, String subject, String qname, String option1, String option2, String option3, String option4, String Q_number, String Ans_Choosen, String Orig_Ans, String Curr_Q_Status, String Srvr_Svd_Status, String Quiz_Status_Nav, String Choose_Ans_Number) {
        this.Q_id=Q_id;
        this.subject = subject;
        this.qname=qname;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;
        this.Q_number = Q_number;
        this.Ans_Choosen = Ans_Choosen;
        this.Orig_Ans = Orig_Ans;
        this.Curr_Q_Status = Curr_Q_Status;
        this.Srvr_Svd_Status = Srvr_Svd_Status;
        this.Quiz_Status_Nav = Quiz_Status_Nav;
        this.Choose_Opt_Number =Choose_Ans_Number;

    }

    @Override
    public String toString() {
        return "\nQ_Id: " + Q_id +
                "\nSUbject " + subject +
                "\nQname " + qname +
                "\nOption1 " + option1 +
                "\nOption2 " + option2 +
                "\nOption3 " + option3 +
                "\nOption4 " + option4 +
                "\nQ_Number " + Q_number +
                "\nAns_CHoosen " + Ans_Choosen +
                "\nOrig_Answer " + Orig_Ans +
                "\nCurr_QStatus " + Curr_Q_Status +
                "\nSrvr_SvdStatus " + Srvr_Svd_Status +
                "\nQuiz_StatusNav " + Quiz_Status_Nav +
                "\nChoose_OptNumber"+ Choose_Opt_Number +
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
        dest.writeString(Q_id);
        dest.writeString(subject);
        dest.writeString(qname);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeString(Q_number);
        dest.writeString(Ans_Choosen);
        dest.writeString(Orig_Ans);
        dest.writeString(Curr_Q_Status);
        dest.writeString(Srvr_Svd_Status);
        dest.writeString(Quiz_Status_Nav);
        dest.writeString(Choose_Opt_Number);

    }
}
