package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 5/28/2017.
 */

public class Winner_Model implements Parcelable {

    public String uid;
    public String uname;
    public String image;
    public String total_marks;
    public String user_marks;
    public String per;
    public String total_attempted;

    protected Winner_Model(Parcel in) {
        uid=in.readString();
        uname = in.readString();
        total_marks = in.readString();
        user_marks = in.readString();
        per = in.readString();
        total_attempted = in.readString();
        image=in.readString();
    }

    public static final Creator<Winner_Model> CREATOR = new Creator<Winner_Model>() {
        @Override
        public Winner_Model createFromParcel(Parcel in) {
            return new Winner_Model(in);
        }

        @Override
        public Winner_Model[] newArray(int size) {
            return new Winner_Model[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTotal_attempted() {
        return total_attempted;
    }

    public void setTotal_attempted(String total_attempted) {
        this.total_attempted = total_attempted;
    }


    public Winner_Model() {
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }

    public String getUser_marks() {
        return user_marks;
    }

    public void setUser_marks(String user_marks) {
        this.user_marks = user_marks;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(uname);
        dest.writeString(total_marks);
        dest.writeString(user_marks);
        dest.writeString(per);
        dest.writeString(total_attempted);
        dest.writeString(image);
    }
}
