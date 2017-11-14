package pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GT on 6/7/2017.
 */

public class ADV_RANK_MODEL implements Parcelable {

    public ADV_RANK_MODEL() {
    }

    protected ADV_RANK_MODEL(Parcel in) {
        SP_NAME = in.readString();
        SP_IMG_FLAG = in.readString();
        SP_IMAGE = in.readString();
        SP_IMG_SIZE = in.readString();
        SP_VEDIO_FLAG = in.readString();
        SP_VEDIO = in.readString();
        RK_NAME = in.readString();
        RK_STATE = in.readString();
        RK_IMAGE = in.readString();
    }

    public static final Creator<ADV_RANK_MODEL> CREATOR = new Creator<ADV_RANK_MODEL>() {
        @Override
        public ADV_RANK_MODEL createFromParcel(Parcel in) {
            return new ADV_RANK_MODEL(in);
        }

        @Override
        public ADV_RANK_MODEL[] newArray(int size) {
            return new ADV_RANK_MODEL[size];
        }
    };

    public String getSP_NAME() {
        return SP_NAME;
    }

    public void setSP_NAME(String SP_NAME) {
        this.SP_NAME = SP_NAME;
    }

    public String getSP_IMG_FLAG() {
        return SP_IMG_FLAG;
    }

    public void setSP_IMG_FLAG(String SP_IMG_FLAG) {
        this.SP_IMG_FLAG = SP_IMG_FLAG;
    }

    public String getSP_IMAGE() {
        return SP_IMAGE;
    }

    public void setSP_IMAGE(String SP_IMAGE) {
        this.SP_IMAGE = SP_IMAGE;
    }

    public String getSP_VEDIO_FLAG() {
        return SP_VEDIO_FLAG;
    }

    public void setSP_VEDIO_FLAG(String SP_VEDIO_FLAG) {
        this.SP_VEDIO_FLAG = SP_VEDIO_FLAG;
    }

    public String getSP_VEDIO() {
        return SP_VEDIO;
    }

    public void setSP_VEDIO(String SP_VEDIO) {
        this.SP_VEDIO = SP_VEDIO;
    }

    public String getRK_NAME() {
        return RK_NAME;
    }

    public void setRK_NAME(String RK_NAME) {
        this.RK_NAME = RK_NAME;
    }

    public String getSP_IMG_SIZE() {
        return SP_IMG_SIZE;
    }

    public void setSP_IMG_SIZE(String SP_IMG_SIZE) {
        this.SP_IMG_SIZE = SP_IMG_SIZE;
    }

    public String getRK_STATE() {
        return RK_STATE;
    }

    public void setRK_STATE(String RK_STATE) {
        this.RK_STATE = RK_STATE;
    }

    public String getRK_IMAGE() {
        return RK_IMAGE;
    }

    public void setRK_IMAGE(String RK_IMAGE) {
        this.RK_IMAGE = RK_IMAGE;
    }
    public String SP_NAME;
    public String SP_IMG_FLAG;
    public String SP_IMAGE;
    public String SP_IMG_SIZE;
    public String SP_VEDIO_FLAG;
    public String SP_VEDIO;
    public String RK_NAME;
    public String RK_STATE;
    public String RK_IMAGE;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SP_NAME);
        dest.writeString(SP_IMG_FLAG);
        dest.writeString(SP_IMAGE);
        dest.writeString(SP_IMG_SIZE);
        dest.writeString(SP_VEDIO_FLAG);
        dest.writeString(SP_VEDIO);
        dest.writeString(RK_NAME);
        dest.writeString(RK_STATE);
        dest.writeString(RK_IMAGE);
    }
}
