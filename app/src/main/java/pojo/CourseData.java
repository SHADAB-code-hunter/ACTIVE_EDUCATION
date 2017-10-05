package pojo;

/**
 * Created by GT on 7/27/2017.
 */

public class CourseData {
    private String id;

    private String course_name;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_short_name() {
        return course_short_name;
    }

    public void setCourse_short_name(String course_short_name) {
        this.course_short_name = course_short_name;
    }

    private String course_short_name;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

}
