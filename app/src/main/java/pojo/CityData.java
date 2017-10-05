package pojo;

/**
 * Created by GT on 7/27/2017.
 */

public class CityData
{
    private String id;

    private String cityid;

    private String city_name;

    public String getCity_state_id() {
        return city_state_id;
    }

    public void setCity_state_id(String city_state_id) {
        this.city_state_id = city_state_id;
    }

    private String city_state_id;

    private String is_active;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setCityid(String cityid){
        this.cityid = cityid;
    }
    public String getCityid(){
        return this.cityid;
    }
    public void setCity_name(String city_name){
        this.city_name = city_name;
    }
    public String getCity_name(){
        return this.city_name;
    }

    public void setIs_active(String is_active){
        this.is_active = is_active;
    }
    public String getIs_active(){
        return this.is_active;
    }
}
