package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 6/20/2017.
 */

public class Notif implements Parcelable {

    protected Notif(Parcel in) {
        id = in.readString();
        address = in.readString();
    }
    public  Notif() {
    }

    public static final Creator<Notif> CREATOR = new Creator<Notif>() {
        @Override
        public Notif createFromParcel(Parcel in) {
            return new Notif(in);
        }

        @Override
        public Notif[] newArray(int size) {
            return new Notif[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String id;
    private String address;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(address);
    }
}
