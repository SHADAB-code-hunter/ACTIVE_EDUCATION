package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 5/20/2017.
 */

public class State_MODEL implements Parcelable
{
    public State_MODEL() {
    }

    public State_MODEL(Parcel in) {
        is_active = in.readString();
        state = in.readString();
        id = in.readString();
    }

    public static final Creator<State_MODEL> CREATOR = new Creator<State_MODEL>() {
        @Override
        public State_MODEL createFromParcel(Parcel in) {
            return new State_MODEL(in);
        }

        @Override
        public State_MODEL[] newArray(int size) {
            return new State_MODEL[size];
        }
    };

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String is_active;
    private String state;
    private String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(is_active);
        dest.writeString(state);
        dest.writeString(id);
    }
}
