package utilities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


/**
 * Created by Windows on 09-02-2015.
 */
public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
         //   L.m("create from parcel :Movie");
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSponsers_name() {
        return sponsers_name;
    }

    public void setSponsers_name(String sponsers_name) {
        this.sponsers_name = sponsers_name;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCeleb_name() {
        return celeb_name;
    }

    public void setCeleb_name(String celeb_name) {
        this.celeb_name = celeb_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public String getAud_id() {
        return aud_id;
    }

    public void setAud_id(String aud_id) {
        this.aud_id = aud_id;
    }

    private String aud_id;
    private String aname;
    private String date_time;
    private String venue;
    private String address;
    private String sponsers_name;
    private String images;
    private String video;
    private String celeb_name;
    private String amount;

   // private Date date_Audition;
    public Date releaseDateTheater;

    public Movie() {

    }

    public Movie(Parcel input) {
        aud_id=input.readString();
        aname = input.readString();
       // date_time = input.readString();
        long dateMillis=input.readLong();
        releaseDateTheater = (dateMillis == -1 ? null : new Date(dateMillis));
        venue = input.readString();
        address = input.readString();
        sponsers_name = input.readString();
        images=input.readString();
        video = input.readString();
        celeb_name = input.readString();
        amount = input.readString();


    }

    public Movie(String aud_id, String aname, Date releaseDateTheater, String venue, String address, String sponsers_name, String images, String video, String celeb_name, String amount) {
        this.aud_id=aud_id;
        this.aname=aname;
        this.releaseDateTheater = releaseDateTheater;
        this.venue = venue;
        this.address = address;
        this.sponsers_name = sponsers_name;
        this.images=images;
        this.video = video;
        this.celeb_name = celeb_name;
        this.amount = amount;
     //   this.releaseDateTheater = releaseDateTheater;

    }

    @Override
    public String toString() {
        return  "\nAud_id"+aud_id+
                "\nAname: " + aname +
                "\nDate " + releaseDateTheater +
                "\nVenue " + venue +
                "\nAddress " + address +
                "\nSponsers_name " + sponsers_name +
                "\nImages " + images +
                "\nVideo " + video +
                "\nCeleb_name " + celeb_name +
                "\nAmount " + amount +
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
        dest.writeString(aud_id);
        dest.writeString(aname);
        dest.writeLong(releaseDateTheater == null ? -1 : releaseDateTheater.getTime());
        dest.writeString(venue);
        dest.writeString(address);
        dest.writeString(sponsers_name);
        dest.writeString(images);
        dest.writeString(video);
        dest.writeString(celeb_name);
        dest.writeString(amount);

    }
}
