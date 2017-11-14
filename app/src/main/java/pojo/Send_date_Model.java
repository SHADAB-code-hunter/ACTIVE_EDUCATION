package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 5/27/2017.
 */

public class Send_date_Model implements Parcelable
{
    private String id;
    private String name;
    private String image;
    private String rank;
    private String rdate;
    private String added_date;
    private String is_active;

    public Send_date_Model() {
    }


    public Send_date_Model(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        rank = in.readString();
        rdate = in.readString();
        added_date = in.readString();
        is_active = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(rank);
        dest.writeString(rdate);
        dest.writeString(added_date);
        dest.writeString(is_active);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Send_date_Model> CREATOR = new Creator<Send_date_Model>() {
        @Override
        public Send_date_Model createFromParcel(Parcel in) {
            return new Send_date_Model(in);
        }

        @Override
        public Send_date_Model[] newArray(int size) {
            return new Send_date_Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }




}
