package pojo;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by GT on 4/15/2017.
 */

public class Quiz_Subject_Model implements Parcelable
{

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Creator<Quiz_Subject_Model> CREATOR = new Creator<Quiz_Subject_Model>() {
        public Quiz_Subject_Model createFromParcel(Parcel in) {
          //  L.m("create from parcel :Movie");
            return new Quiz_Subject_Model(in);
        }

        public Quiz_Subject_Model[] newArray(int size) {
            return new Quiz_Subject_Model[size];
        }
    };


    private String id;
    private String images;
    private String is_active;
    private String sname;

    public Quiz_Subject_Model(Parcel input) {
        id = input.readString();
        images = input.readString();
        sname = input.readString();
        is_active = input.readString();
    }

    public Quiz_Subject_Model() {

    }
    public Quiz_Subject_Model(String id, String images, String is_active, String sname) {
        this.id=id;
        this.images = images;
        this.is_active = is_active;
        this.sname = sname;

    }

    @Override
    public String toString() {
        return "\nSub_Id: " + id +
                "\nSUbject_images " + images +
                "\nIs_Active " + is_active +
                "\nSname " + sname +
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
        dest.writeString(images);
        dest.writeString(is_active);
        dest.writeString(sname);
    }
}
