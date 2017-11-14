package utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 8/16/2017.
 */

public class Common_Pojo implements Parcelable{

    public String id;
    public String name;
    public String desc;


    protected Common_Pojo(Parcel in) {
        id = in.readString();
        name = in.readString();
        desc = in.readString();
    }
    public  Common_Pojo() {

    }

    public static final Creator<Common_Pojo> CREATOR = new Creator<Common_Pojo>() {
        @Override
        public Common_Pojo createFromParcel(Parcel in) {
            return new Common_Pojo(in);
        }

        @Override
        public Common_Pojo[] newArray(int size) {
            return new Common_Pojo[size];
        }
    };

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(desc);
    }
}
