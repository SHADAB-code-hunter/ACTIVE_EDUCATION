package pojo;

/**
 * Created by GT on 7/27/2017.
 */

public class BranchData {

    private String id;

    private String branch_name;

    private String branch_short_name;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setBranch_name(String branch_name){
        this.branch_name = branch_name;
    }
    public String getBranch_name(){
        return this.branch_name;
    }
    public void setBranch_short_name(String branch_short_name){
        this.branch_short_name = branch_short_name;
    }
    public String getBranch_short_name(){
        return this.branch_short_name;
    }
}