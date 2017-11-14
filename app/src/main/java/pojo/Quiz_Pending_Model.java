package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 4/12/2017.
 */

public class Quiz_Pending_Model implements Parcelable {
    public Quiz_Pending_Model() {
    }

    private String st_Hour;
    private String st_Min;
    private String st_Sec;
    private String st_Date;
    private String st_Mon;
    private String st_Year;
    private String cr_Hour;
    private String cr_Min;
    private String cr_Sec;

    public Quiz_Pending_Model(Parcel in) {
        st_Hour = in.readString();
        st_Min = in.readString();
        st_Sec = in.readString();
        st_Date = in.readString();
        st_Mon = in.readString();
        st_Year = in.readString();
        cr_Hour = in.readString();
        cr_Min = in.readString();
        cr_Sec = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(st_Hour);
        dest.writeString(st_Min);
        dest.writeString(st_Sec);
        dest.writeString(st_Date);
        dest.writeString(st_Mon);
        dest.writeString(st_Year);
        dest.writeString(cr_Hour);
        dest.writeString(cr_Min);
        dest.writeString(cr_Sec);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Quiz_Pending_Model> CREATOR = new Creator<Quiz_Pending_Model>() {
        @Override
        public Quiz_Pending_Model createFromParcel(Parcel in) {
            return new Quiz_Pending_Model(in);
        }

        @Override
        public Quiz_Pending_Model[] newArray(int size) {
            return new Quiz_Pending_Model[size];
        }
    };

    public String getSt_Hour() {
        return st_Hour;
    }

    public void setSt_Hour(String st_Hour) {
        this.st_Hour = st_Hour;
    }

    public String getSt_Min() {
        return st_Min;
    }

    public void setSt_Min(String st_Min) {
        this.st_Min = st_Min;
    }

    public String getSt_Sec() {
        return st_Sec;
    }

    public void setSt_Sec(String st_Sec) {
        this.st_Sec = st_Sec;
    }

    public String getSt_Date() {
        return st_Date;
    }

    public void setSt_Date(String st_Date) {
        this.st_Date = st_Date;
    }

    public String getSt_Mon() {
        return st_Mon;
    }

    public void setSt_Mon(String st_Mon) {
        this.st_Mon = st_Mon;
    }

    public String getSt_Year() {
        return st_Year;
    }

    public void setSt_Year(String st_Year) {
        this.st_Year = st_Year;
    }

    public String getCr_Hour() {
        return cr_Hour;
    }

    public void setCr_Hour(String cr_Hour) {
        this.cr_Hour = cr_Hour;
    }

    public String getCr_Min() {
        return cr_Min;
    }

    public void setCr_Min(String cr_Min) {
        this.cr_Min = cr_Min;
    }

    public String getCr_Sec() {
        return cr_Sec;
    }

    public void setCr_Sec(String cr_Sec) {
        this.cr_Sec = cr_Sec;
    }


}
