package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 5/30/2017.
 */

public class Gallery_Model implements Parcelable{
    public Gallery_Model() {
    }

    protected Gallery_Model(Parcel in) {
        id = in.readString();
        image_flag = in.readString();
        image_name = in.readString();
        video_flag = in.readString();
        video_link = in.readString();
        added_date = in.readString();
        edit_date = in.readString();
        is_active = in.readString();
    }

    public static final Creator<Gallery_Model> CREATOR = new Creator<Gallery_Model>() {
        @Override
        public Gallery_Model createFromParcel(Parcel in) {
            return new Gallery_Model(in);
        }

        @Override
        public Gallery_Model[] newArray(int size) {
            return new Gallery_Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_flag() {
        return image_flag;
    }

    public void setImage_flag(String image_flag) {
        this.image_flag = image_flag;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getVideo_flag() {
        return video_flag;
    }

    public void setVideo_flag(String video_flag) {
        this.video_flag = video_flag;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(String edit_date) {
        this.edit_date = edit_date;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String id;
    public String image_flag;
    public String image_name;
    public String video_flag;
    public String video_link;
    public String added_date;
    public String edit_date;
    public String is_active;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(image_flag);
        dest.writeString(image_name);
        dest.writeString(video_flag);
        dest.writeString(video_link);
        dest.writeString(added_date);
        dest.writeString(edit_date);
        dest.writeString(is_active);
    }
}
