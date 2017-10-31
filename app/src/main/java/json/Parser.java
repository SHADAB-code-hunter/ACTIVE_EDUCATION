package json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import extras.Constants;
import pojo.Agent_Deal_Pojo;
import pojo.Get_Course_desc;
import pojo.Cat_Model;
import pojo.SEARCH_MODEL;
import utilities.Common_Pojo;

import static extras.Keys.EndpointBoxOffice.KEY_BNAME;
import static extras.Keys.EndpointBoxOffice.KEY_BRANCH_FEES;
import static extras.Keys.EndpointBoxOffice.KEY_BRN_ID;
import static extras.Keys.EndpointBoxOffice.KEY_BROUCHER;
import static extras.Keys.EndpointBoxOffice.KEY_B_INFO;
import static extras.Keys.EndpointBoxOffice.KEY_B_NAME;
import static extras.Keys.EndpointBoxOffice.KEY_B_SHORT_NAME;
import static extras.Keys.EndpointBoxOffice.KEY_CATEGORY;
import static extras.Keys.EndpointBoxOffice.KEY_CID;
import static extras.Keys.EndpointBoxOffice.KEY_CITY;
import static extras.Keys.EndpointBoxOffice.KEY_CNAME;
import static extras.Keys.EndpointBoxOffice.KEY_COMNID;
import static extras.Keys.EndpointBoxOffice.KEY_COMNNAME;
import static extras.Keys.EndpointBoxOffice.KEY_COM_COURSENNAME;
import static extras.Keys.EndpointBoxOffice.KEY_COURSE;
import static extras.Keys.EndpointBoxOffice.KEY_COURSE_ID;
import static extras.Keys.EndpointBoxOffice.KEY_CRS_ID;
import static extras.Keys.EndpointBoxOffice.KEY_C_BRANCH;
import static extras.Keys.EndpointBoxOffice.KEY_C_INFO;
import static extras.Keys.EndpointBoxOffice.KEY_C_NAME;
import static extras.Keys.EndpointBoxOffice.KEY_C_PHONE1;
import static extras.Keys.EndpointBoxOffice.KEY_C_SHORT_NAME;
import static extras.Keys.EndpointBoxOffice.KEY_C_WEBSITE;
import static extras.Keys.EndpointBoxOffice.KEY_DATA_ARRAY;
import static extras.Keys.EndpointBoxOffice.KEY_DISCOUNT;
import static extras.Keys.EndpointBoxOffice.KEY_IMAGE;
import static extras.Keys.EndpointBoxOffice.KEY_RATING;
import static extras.Keys.EndpointBoxOffice.KEY_R_SEAT;
import static extras.Keys.EndpointBoxOffice.KEY_STATE;
import static extras.Keys.EndpointBoxOffice.KEY_STATUS;
import static extras.Keys.EndpointBoxOffice.KEY_T_SEAT;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_BRANCH_ID;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_BRANCH_NAME;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_CATEGORY;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_CATEGORY_NAME;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_CLG_ID;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_CLG_NAME;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_COURSE_ID;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_COURSE_NAME;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_DEAL_ID;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_DEAL_MONEY;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_END_ADTE;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_ID;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_NAME;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_PROGRESS;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_SRT_DATE;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_TOTAL_ADM_DONE;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_TOTAL_SEAT;
import static extras.Keys.KEY_AGENT_NOTES.KEY_A_TOTAl_ADMISSION;

/**
 * Created by Windows on 02-03-2015.
 */
public class Parser {
    public static ArrayList<Cat_Model> parse_List_JSON(JSONObject response) {

        ArrayList<Cat_Model> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try
            {
                JSONArray arrayMovies = response.getJSONArray(KEY_DATA_ARRAY);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    //long id = -1;

                    String added_date = Constants.NA;

                    String c_address = Constants.NA;

                    String c_city = Constants.NA;


                    String c_image = Constants.NA;

                    String c_info = Constants.NA;

                    String c_name = Constants.NA;

                    String c_phone1 = Constants.NA;

                    String c_phone2 = Constants.NA;

                    String c_state = Constants.NA;

                    String course_id = Constants.NA;

                    String branch_id = Constants.NA;

                    String c_course = Constants.NA;

                    String c_branch = Constants.NA;

                    String edit_date = Constants.NA;

                    String c_website=Constants.NA;

                    String c_rating = Constants.NA;

                    String c_broucher = Constants.NA;

                    String discounted_fee = Constants.NA;

                    String c_id = "-1";

                    String c_branch_fees=Constants.NA;
                    String is_active = Constants.NA;
                    String category=Constants.NA;
                    String user_id;

                    JSONObject current_obj = arrayMovies.getJSONObject(i);
                    //get the id of the current movie

                    if (Utils.contains(current_obj, KEY_CATEGORY)) {
                        Log.d("gdfr",""+current_obj.getString(KEY_CATEGORY));
                        category = current_obj.getString(KEY_CATEGORY);
                    }

                    if (Utils.contains(current_obj, KEY_CID)) {
                        Log.d("gdfr",""+current_obj.getString(KEY_CID));
                        c_id = current_obj.getString(KEY_CID);
                    }
                    //get the title of the current movie
                    if (Utils.contains(current_obj, KEY_C_NAME)) {
                        c_name = current_obj.getString(KEY_C_NAME);
                    }

                    //get the date in theaters for the current movie
                    if (Utils.contains(current_obj, KEY_RATING)) {

                        c_rating = current_obj.getString(KEY_RATING);
                    }

                    //get the date in theaters for the current movie
                    if (Utils.contains(current_obj, KEY_IMAGE)) {

                        Log.d("imahr",""+current_obj.getString(KEY_IMAGE));
                        c_image = current_obj.getString(KEY_IMAGE);
                    }

                    //get the audience score for the current movie

                    if (Utils.contains(current_obj, KEY_CITY)) {
                        c_city = current_obj.getString(KEY_CITY);
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(current_obj, KEY_STATE)) {
                        c_state = current_obj.getString(KEY_STATE);
                    }

                    //get the url for the thumbnail to be displayed inside the current movie result
                    if (Utils.contains(current_obj, KEY_BROUCHER)) {
                        c_broucher = current_obj.getString(KEY_BROUCHER);
                    }
                    if (Utils.contains(current_obj, KEY_BRANCH_FEES)) {
                        c_branch_fees = current_obj.getString(KEY_BRANCH_FEES);
                    }
                    if (Utils.contains(current_obj, KEY_C_INFO)) {
                        c_info = current_obj.getString(KEY_C_INFO);
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(current_obj, KEY_CRS_ID)) {
                        course_id = current_obj.getString(KEY_CRS_ID);
                    }

                    //get the url for the thumbnail to be displayed inside the current movie result
                    if (Utils.contains(current_obj, KEY_BRN_ID)) {
                        branch_id = current_obj.getString(KEY_BRN_ID);
                    }
                    if (Utils.contains(current_obj, KEY_COURSE)) {
                        c_course = current_obj.getString(KEY_COURSE);
                    }
                    if (Utils.contains(current_obj, KEY_C_BRANCH)) {
                        c_branch = current_obj.getString(KEY_C_BRANCH);
                    }
                    if (Utils.contains(current_obj, KEY_C_WEBSITE)) {
                        c_website = current_obj.getString(KEY_C_WEBSITE);
                    }
                    if (Utils.contains(current_obj, KEY_C_PHONE1)) {
                        c_phone1 = current_obj.getString(KEY_C_PHONE1);
                    }
                    if (Utils.contains(current_obj, KEY_DISCOUNT)) {
                        discounted_fee = current_obj.getString(KEY_DISCOUNT);
                    }

                    //get the url of the related links

                    Cat_Model catModel = new Cat_Model();
                    Log.d("gdfrfr",""+current_obj.getString(KEY_CID));
                    catModel.setCategory(category);
                    catModel.setC_id(c_id);
                    catModel.setC_name(c_name);
                    catModel.setC_image(c_image);
                    catModel.setRating(c_rating);
                    catModel.setC_city(c_city);
                    catModel.setC_state(c_state);
                    catModel.setC_website(c_website);
                    catModel.setBroucher(c_broucher);
                    catModel.setBranch_fees(c_branch_fees);
                    catModel.setC_info(c_info);
                    catModel.setC_phone1(c_phone1);
                    catModel.setCourse_id(course_id);
                    catModel.setBranch_id(branch_id);
                    catModel.setC_course(c_course);
                    catModel.setC_branch(c_branch);
                    catModel.setDiscount_fee(discounted_fee);

//                    L.t(getActivity(), movie + "");
                    if (!c_id.equals(-1) && !c_name.equals(Constants.NA)) {
                        listMovies.add(catModel);
                    }
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return listMovies;
    }


    /*   search */



    /*   ------------------------ Common   for all array  ----------------------    */
    public static ArrayList<Common_Pojo> parse_common_list(JSONObject response, String str_key) {

        ArrayList<Common_Pojo> list_Sate = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray jsonArray = response.getJSONArray(str_key);
                for (int i = 0; i < jsonArray.length(); i++) {

                    String id = Constants.NA;
                    String name = Constants.NA;
                    String course_id = "-1";

                    JSONObject current_obj = jsonArray.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(current_obj, KEY_COMNID)) {
                        id = current_obj.getString(KEY_COMNID);
                    }
                    //get the title of the current movie
                    if (Utils.contains(current_obj, KEY_COMNNAME)) {
                        name = current_obj.getString(KEY_COMNNAME);
                    }

                    //get the title of the current movie
                    if (Utils.contains(current_obj, KEY_COMNNAME)) {
                        name = current_obj.getString(KEY_COMNNAME);
                    }

                    //get the url of the related links
                    Common_Pojo commonPojo = new Common_Pojo();
                    commonPojo.setId(id);
                    commonPojo.setName(name);

                    // L.t(getActivity(), movie + "");
                    if (!course_id.equals(-1) && !name.equals(Constants.NA)) {
                        list_Sate.add(commonPojo);
                    }
                }
            } catch (JSONException e) {

            }
        }
        return list_Sate;
    }
    public static ArrayList<Common_Pojo> parse_coursecommon_list(JSONObject response, String str_key) {

        ArrayList<Common_Pojo> list_Sate = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray jsonArray = response.getJSONArray(str_key);
                for (int i = 0; i < jsonArray.length(); i++) {

                    String id = Constants.NA;
                    String name = Constants.NA;
                    String course_id = "-1";

                    JSONObject current_obj = jsonArray.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(current_obj, KEY_COMNID)) {
                        id = current_obj.getString(KEY_COMNID);
                    }
                    //get the title of the current movie
                  /*  if (Utils.contains(current_obj, KEY_COMNNAME)) {
                        name = current_obj.getString(KEY_COMNNAME);
                    }*/

                    //get the title of the current movie
                    if (Utils.contains(current_obj, KEY_COM_COURSENNAME)) {
                        name = current_obj.getString(KEY_COM_COURSENNAME);
                    }

                    //get the url of the related links
                    Common_Pojo commonPojo = new Common_Pojo();
                    commonPojo.setId(id);
                    commonPojo.setName(name);

                    // L.t(getActivity(), movie + "");
                    if (!course_id.equals(-1) && !name.equals(Constants.NA)) {
                        list_Sate.add(commonPojo);
                    }
                }
            } catch (JSONException e) {

            }
        }
        return list_Sate;
    }

    public static ArrayList<String> parse_Status_List_JSON(JSONObject response) {

        ArrayList<String> status_list = new ArrayList<>();
        if (response != null && response.length() > 0) {
            Log.d("dfedgr",""+response.length());
            try
            {
                JSONArray arrayMovies = response.getJSONArray(KEY_STATUS);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    Log.d("dfgr",""+i);
                    JSONObject jsonObject = arrayMovies.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(jsonObject, ""+(i+1))) {
                        // c_id = current_obj.getString(""+i);
                        Log.d("sderd",jsonObject.getString(""+(i+1)));
                        status_list.add(jsonObject.getString(""+(i+1)));
                    }
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return status_list;
    }


    public static ArrayList<Get_Course_desc> parse_Course_JSON(JSONObject response) {

        ArrayList<Get_Course_desc> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try
            {
                JSONArray arrayMovies = response.getJSONArray(KEY_DATA_ARRAY);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    //long id = -1;

                    String added_date = Constants.NA;

                    String c_address = Constants.NA;

                    String c_city = Constants.NA;

                    String c_country = Constants.NA;

                    String c_email = Constants.NA;

                    String c_established = Constants.NA;

                    String c_image = Constants.NA;

                    String c_info = Constants.NA;

                    String c_name = Constants.NA;

                    String c_short_name = Constants.NA;
                    String b_name = Constants.NA;
                    String b_short_anme = Constants.NA;
                    String b_info = Constants.NA;
                    String branch = Constants.NA;
                    String c_branch_fees = Constants.NA;
                    String t_seat = Constants.NA;

                    String r_seat = Constants.NA;
                    String bname = Constants.NA;
                    String cname = Constants.NA;

                    String c_id = "-1";
                    JSONObject current_obj = arrayMovies.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(current_obj, KEY_CID)) {
                        c_id = current_obj.getString(KEY_CID);
                    }
                    //get the title of the current movie
                    if (Utils.contains(current_obj, KEY_C_NAME)) {
                        c_name = current_obj.getString(KEY_C_NAME);
                    }

                    //get the date in theaters for the current movie
                    if (Utils.contains(current_obj, KEY_C_SHORT_NAME)) {

                        c_short_name = current_obj.getString(KEY_C_SHORT_NAME);
                    }

                    //get the date in theaters for the current movie
                    if (Utils.contains(current_obj, KEY_C_INFO)) {

                        Log.d("imahr",""+current_obj.getString(KEY_C_INFO));
                        c_info = current_obj.getString(KEY_C_INFO);
                    }

                    //get the audience score for the current movie

                    if (Utils.contains(current_obj, KEY_B_NAME)) {
                        b_name = current_obj.getString(KEY_B_NAME);
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(current_obj, KEY_B_SHORT_NAME)) {
                        b_short_anme = current_obj.getString(KEY_B_SHORT_NAME);
                    }

                    //get the url for the thumbnail to be displayed inside the current movie result
                    if (Utils.contains(current_obj, KEY_B_INFO)) {
                        b_info = current_obj.getString(KEY_B_INFO);
                    }
                    if (Utils.contains(current_obj, KEY_BRANCH_FEES)) {
                        c_branch_fees = current_obj.getString(KEY_BRANCH_FEES);
                    }

                    if (Utils.contains(current_obj, KEY_T_SEAT)) {
                        b_info = current_obj.getString(KEY_T_SEAT);
                    }

                    if (Utils.contains(current_obj, KEY_R_SEAT)) {
                        r_seat = current_obj.getString(KEY_R_SEAT);
                    }
                    if (Utils.contains(current_obj, KEY_BNAME)) {
                        bname = current_obj.getString(KEY_BNAME);
                    }

                    if (Utils.contains(current_obj, KEY_CNAME)) {
                        cname = current_obj.getString(KEY_CNAME);
                    }

                    //get the url of the related links

                    Get_Course_desc catModel = new Get_Course_desc();

                    catModel.setId(c_id);

                    catModel.setC_name(c_name);

                    catModel.setC_short_name(c_short_name);

                    catModel.setC_info(c_info);

                    catModel.setBranch_name(b_name);

                    catModel.setBranch_short_name(b_short_anme);

                    catModel.setBranch_info(b_info);

                    catModel.setBranch_fee(c_branch_fees);

                    catModel.setTotal_seats(t_seat);

                    catModel.setRemaining_seats(r_seat);

                    catModel.setBname(bname);

                    catModel.setCname(cname);

//                    L.t(getActivity(), movie + "");
                    if (!c_id.equals(-1) && !c_name.equals(Constants.NA)) {
                        listMovies.add(catModel);
                    }
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return listMovies;
    }
    public static ArrayList<Agent_Deal_Pojo> parse_List_agent(JSONObject response) {

        ArrayList<Agent_Deal_Pojo> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try
            {
                JSONArray arrayMovies = response.getJSONArray(KEY_DATA_ARRAY);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    //long id = -1;

                     String agent_id= "-1";

                     String branch_id= Constants.NA;

                     String branch_name= Constants.NA;

                     String clg_id= Constants.NA;

                     String clg_name= Constants.NA;

                     String course_id= Constants.NA;

                     String course_name= Constants.NA;

                     String deal_id= Constants.NA;

                     String deal_money= Constants.NA;

                     String total_seats= Constants.NA;

                     String agent_name= Constants.NA;

                     String category= Constants.NA;

                     String category_name= Constants.NA;

                     String end_date= Constants.NA;

                     String progress= Constants.NA;

                     String start_date= Constants.NA;

                     String total_admission= Constants.NA;

                     String total_admission_done= Constants.NA;

                    JSONObject current_obj = arrayMovies.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(current_obj, KEY_A_ID)) {
                        Log.d("gdfr",""+current_obj.getString(KEY_A_ID));
                        agent_id = current_obj.getString(KEY_A_ID);
                    }
                    //get the title of the current movie
                    if (Utils.contains(current_obj, KEY_A_NAME)) {
                        agent_name = current_obj.getString(KEY_A_NAME);
                    }

                    //get the date in theaters for the current movie
                    if (Utils.contains(current_obj, KEY_A_CATEGORY)) {

                        category = current_obj.getString(KEY_A_CATEGORY);
                    }
                    if (Utils.contains(current_obj, KEY_A_CATEGORY_NAME)) {

                        category_name = current_obj.getString(KEY_A_CATEGORY_NAME);
                    }
                    if (Utils.contains(current_obj, KEY_A_END_ADTE)) {

                        end_date = current_obj.getString(KEY_A_END_ADTE);
                    }
                    /**/
                    if (Utils.contains(current_obj, KEY_A_PROGRESS)) {

                        progress = current_obj.getString(KEY_A_PROGRESS);
                    }
                    if (Utils.contains(current_obj, KEY_A_SRT_DATE)) {

                        start_date = current_obj.getString(KEY_A_SRT_DATE);
                    }
                    if (Utils.contains(current_obj, KEY_A_TOTAl_ADMISSION)) {

                        total_admission = current_obj.getString(KEY_A_TOTAl_ADMISSION);
                    }
                    if (Utils.contains(current_obj, KEY_A_TOTAL_ADM_DONE)) {

                        total_admission_done = current_obj.getString(KEY_A_TOTAL_ADM_DONE);
                    }
                    /**/
                    if (Utils.contains(current_obj, KEY_A_BRANCH_ID)) {

                        branch_id = current_obj.getString(KEY_A_BRANCH_ID);
                    }
                    if (Utils.contains(current_obj, KEY_A_BRANCH_NAME)) {

                        branch_name = current_obj.getString(KEY_A_BRANCH_NAME);
                    }
                    if (Utils.contains(current_obj, KEY_A_CLG_ID)) {

                        clg_id = current_obj.getString(KEY_A_CLG_ID);
                    }
                    if (Utils.contains(current_obj, KEY_A_CLG_NAME)) {

                        clg_name = current_obj.getString(KEY_A_CLG_NAME);
                    }

                    /**/
                    if (Utils.contains(current_obj, KEY_A_COURSE_ID)) {

                        course_id = current_obj.getString(KEY_A_COURSE_ID);
                    }
                    if (Utils.contains(current_obj, KEY_A_COURSE_NAME)) {

                        course_name = current_obj.getString(KEY_A_COURSE_NAME);
                    }
                    if (Utils.contains(current_obj, KEY_A_DEAL_ID)) {

                        deal_id = current_obj.getString(KEY_A_DEAL_ID);
                    }

                     /**/
                    if (Utils.contains(current_obj, KEY_A_DEAL_MONEY)) {

                        deal_money = current_obj.getString(KEY_A_DEAL_MONEY);
                    }
                    if (Utils.contains(current_obj, KEY_A_TOTAL_SEAT)) {

                        total_seats = current_obj.getString(KEY_A_TOTAL_SEAT);
                    }

                    //get the url of the related links

                    Agent_Deal_Pojo catModel = new Agent_Deal_Pojo();

                    catModel.setAgent_id(agent_id);
                    catModel.setAgent_name(agent_name);
                    Log.d("dfgr",""+catModel.getAgent_name());
                    catModel.setCategory(category);
                    catModel.setCategory_name(category_name);
                    catModel.setEnd_date(end_date);
                    catModel.setProgress(progress);
                    catModel.setStart_date(start_date);
                    catModel.setTotal_admission(total_admission);
                    catModel.setTotal_admission_done(total_admission_done);
                    catModel.setCourse_id(course_id);
                    catModel.setBranch_name(branch_name);
                    catModel.setClg_id(clg_id);
                    catModel.setClg_name(clg_name);
                    catModel.setCourse_id(course_id);
                    catModel.setCourse_name(course_name);
                    catModel.setBranch_id(branch_id);
                    catModel.setDeal_money(deal_money);
                    catModel.setTotal_seats(total_seats);

                    //  L.t(getActivity(), movie + "");
                    if (!agent_id.equals("-1")) {

                        listMovies.add(catModel);
                    }
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return listMovies;
    }


}
