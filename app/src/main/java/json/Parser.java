package json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import extras.Constants;
import pojo.Agent_Deal_Pojo;
import pojo.Get_Course_desc;
import pojo.Cat_Model;
import pojo.Quiz_Model;
import pojo.Quiz_Pending_Model;
import pojo.Quiz_Subject_Model;
import pojo.Quiz_Subject_Ques_Model;
import pojo.SEARCH_MODEL;
import utilities.Common_Pojo;
import utilities.Movie;

import static extras.Keys.EndpointBoxOffice.KEYD;
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
import static extras.Keys.EndpointBoxOffice.KEY_DEALID;
import static extras.Keys.EndpointBoxOffice.KEY_DISCOUNT;
import static extras.Keys.EndpointBoxOffice.KEY_DURATION;
import static extras.Keys.EndpointBoxOffice.KEY_ID;
import static extras.Keys.EndpointBoxOffice.KEY_IMAGE;
import static extras.Keys.EndpointBoxOffice.KEY_RATING;
import static extras.Keys.EndpointBoxOffice.KEY_R_SEAT;
import static extras.Keys.EndpointBoxOffice.KEY_STATE;
import static extras.Keys.EndpointBoxOffice.KEY_STATUS;
import static extras.Keys.EndpointBoxOffice.KEY_TYPE;
import static extras.Keys.EndpointBoxOffice.KEY_T_SEAT;
import static extras.Keys.EndpointBox_Office.KEY_ADDRESS;
import static extras.Keys.EndpointBox_Office.KEY_AMOUNT;
import static extras.Keys.EndpointBox_Office.KEY_ANAME;
import static extras.Keys.EndpointBox_Office.KEY_AUD_ID;
import static extras.Keys.EndpointBox_Office.KEY_CELEB;
import static extras.Keys.EndpointBox_Office.KEY_DATE_TIME;
import static extras.Keys.EndpointBox_Office.KEY_IMAGES;
import static extras.Keys.EndpointBox_Office.KEY_MOVIES;
import static extras.Keys.EndpointBox_Office.KEY_RELEASE_DATES;
import static extras.Keys.EndpointBox_Office.KEY_SPONSERS;
import static extras.Keys.EndpointBox_Office.KEY_THEATER;
import static extras.Keys.EndpointBox_Office.KEY_VANUE;
import static extras.Keys.EndpointBox_Office.KEY_VIDEO;
import static extras.Keys.EndpointDailyQuiz.KEY_ANSWER;
import static extras.Keys.EndpointDailyQuiz.KEY_IS_ACTIVE;
import static extras.Keys.EndpointDailyQuiz.KEY_OPTIION1;
import static extras.Keys.EndpointDailyQuiz.KEY_OPTIION2;
import static extras.Keys.EndpointDailyQuiz.KEY_OPTIION3;
import static extras.Keys.EndpointDailyQuiz.KEY_OPTIION4;
import static extras.Keys.EndpointDailyQuiz.KEY_OPT_ANSWER;
import static extras.Keys.EndpointDailyQuiz.KEY_QNAME;
import static extras.Keys.EndpointDailyQuiz.KEY_SUBJECT;
import static extras.Keys.EndpointDailyQuiz.KEY_S_No;
import static extras.Keys.EndpointDailyQuizPending.KEY_CR_HOUR;
import static extras.Keys.EndpointDailyQuizPending.KEY_CR_MIN;
import static extras.Keys.EndpointDailyQuizPending.KEY_CR_SEC;
import static extras.Keys.EndpointDailyQuizPending.KEY_ST_DATE;
import static extras.Keys.EndpointDailyQuizPending.KEY_ST_HOUR;
import static extras.Keys.EndpointDailyQuizPending.KEY_ST_MIN;
import static extras.Keys.EndpointDailyQuizPending.KEY_ST_MON;
import static extras.Keys.EndpointDailyQuizPending.KEY_ST_SEC;
import static extras.Keys.EndpointDailyQuizPending.KEY_ST_YEAR;
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
import static extras.Keys.KEY_AGENT_NOTES.KEY_BRANCH_FEE;
import static extras.Keys.KEY_AGENT_NOTES.KEY_CAT_ID;
import static extras.Keys.KEY_AGENT_NOTES.KEY_DIS_OFFER;
import static extras.Keys.KEY_AGENT_NOTES.KEY__IMAGE;

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

                    String dealid = Constants.NA;

                    String type = Constants.NA;

                    String c_website=Constants.NA;

                    String c_rating = Constants.NA;

                    String c_broucher = Constants.NA;

                    String discounted_fee = Constants.NA;

                    String c_id = "-1";
                    String id = "-1";

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
                    if (Utils.contains(current_obj, KEY_ID)) {
                        Log.d("gdfr",""+current_obj.getString(KEY_ID));
                        id = current_obj.getString(KEY_ID);
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
                    if (Utils.contains(current_obj, KEY_TYPE)) {
                         type = current_obj.getString(KEY_TYPE);
                        }
                    if (Utils.contains(current_obj, KEY_DEALID)) {
                         dealid = current_obj.getString(KEY_DEALID);
                        }

                    //get the url of the related links

                    Cat_Model catModel = new Cat_Model();
                    Log.d("gdfrfr",""+current_obj.getString(KEY_CID));
                    catModel.setCategory(category);
                    catModel.setId(id);
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
                    catModel.setDealid(dealid);
                    catModel.setC_course(c_course);
                    catModel.setC_branch(c_branch);
                    catModel.setDiscount_fee(discounted_fee);
                    catModel.setType(type);

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
                    String cduration = Constants.NA;

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
                    if (Utils.contains(current_obj, KEY_DURATION)) {
                        cduration = current_obj.getString(KEY_DURATION);
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

                    catModel.setC_duration(cduration);
                    Log.d("lklkl",""+cduration);

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

                     String cat_id= Constants.NA;

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

                     String display_offer= Constants.NA;

                     String branch_fee= Constants.NA;

                     String image= Constants.NA;

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
                    if (Utils.contains(current_obj, KEY_DIS_OFFER)) {

                        display_offer = current_obj.getString(KEY_DIS_OFFER);
                    }
                    if (Utils.contains(current_obj, KEY_BRANCH_FEE)) {

                        branch_fee = current_obj.getString(KEY_BRANCH_FEE);
                    }
                    if (Utils.contains(current_obj, KEY_CAT_ID)) {

                        cat_id = current_obj.getString(KEY_CAT_ID);
                    }
                    if (Utils.contains(current_obj, KEY__IMAGE)) {

                        image = current_obj.getString(KEY__IMAGE);
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
                    catModel.setDis_offer(display_offer);
                    catModel.setCat_id(cat_id);
                    catModel.setDeal_id(deal_id);
                    catModel.setImage(image);
                    catModel.setBranch_fee(branch_fee);

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
    public static ArrayList<Movie> parseMoviesJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Movie> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    String aud_id= Constants.NA;
                    String aname= Constants.NA;
                    String releaseDate = Constants.NA;
                    String date_time= Constants.NA;
                    String venue= Constants.NA;
                    String address= Constants.NA;
                    String sponsers_name= Constants.NA;
                    String video= Constants.NA;
                    String celeb_name= Constants.NA;
                    String str_images= Constants.NA;
                    String amount= Constants.NA;

                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(currentMovie, KEY_AUD_ID)) {
                        aud_id = currentMovie.getString(KEY_AUD_ID);
                        // L.m("audid" + currentMovie.getString(KEY_AUD_ID));
                    }
                    if (Utils.contains(currentMovie, KEY_ANAME)) {
                        aname = currentMovie.getString(KEY_ANAME);
                        //  L.m("aname" + currentMovie.getString(KEY_ANAME));
                    }
                    //get the title of the current movie
                    if (Utils.contains(currentMovie, KEY_DATE_TIME)) {
                        date_time = currentMovie.getString(KEY_DATE_TIME);
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(currentMovie, KEY_ADDRESS)) {
                        address = currentMovie.getString(KEY_ADDRESS);
                    }
                    if (Utils.contains(currentMovie, KEY_IMAGES)) {
                        str_images = currentMovie.getString(KEY_IMAGES);
                    }

                    if (Utils.contains(currentMovie, KEY_VANUE)) {
                        venue = currentMovie.getString(KEY_VANUE);
                    }
                    if (Utils.contains(currentMovie, KEY_SPONSERS)) {
                        sponsers_name = currentMovie.getString(KEY_SPONSERS);
                    }
                    if (Utils.contains(currentMovie, KEY_VIDEO)) {
                        video = currentMovie.getString(KEY_VIDEO);
                    }
                    if (Utils.contains(currentMovie, KEY_VANUE)) {
                        venue = currentMovie.getString(KEY_VANUE);
                    }
                    if (Utils.contains(currentMovie, KEY_CELEB)) {
                        celeb_name = currentMovie.getString(KEY_CELEB);
                    }
                    if (Utils.contains(currentMovie, KEY_AMOUNT)) {
                        amount = currentMovie.getString(KEY_AMOUNT);
                    }

                    if (Utils.contains(currentMovie, KEY_RELEASE_DATES)) {
                        JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);

                        if (Utils.contains(objectReleaseDates, KEY_THEATER)) {
                            releaseDate = objectReleaseDates.getString(KEY_THEATER);
                        }
                    }

                    Date date = null;
                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException e) {
                    }
                    Movie movie = new Movie();
                    movie.setAud_id(aud_id);
                    movie.setAname(aname);
                    movie.setReleaseDateTheater(date);
                    movie.setDate_time(date_time);
                    movie.setVenue(venue);
                    movie.setAddress(address);
                    movie.setSponsers_name(sponsers_name);
                    movie.setImages(str_images);
                    movie.setVideo(video);
                    movie.setCeleb_name(celeb_name);
                    movie.setAmount(amount);

                    if (!aname.equals(Constants.NA)) {
                        listMovies.add(movie);
                    }
                }

            } catch (JSONException e) {

            }
        }
        return listMovies;
    }


    public static ArrayList<Quiz_Model> parseQuizJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Quiz_Model> quiz_listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {

            if(response.has("status")) {
                try {
                    if (response.getString("status").equals("1")) {

                        JSONArray arrayQuiz = response.getJSONArray(KEY_MOVIES);
                        for (int i = 0; i < arrayQuiz.length(); i++) {

                            String id = Constants.NA;
                            String is_active = Constants.NA;
                            String option1 = Constants.NA;
                            String option2 = Constants.NA;
                            String option3 = Constants.NA;
                            String option4 = Constants.NA;
                            String answer = Constants.NA;
                            String q_name = Constants.NA;
                            String subject = Constants.NA;
                            String opt_ans = Constants.NA;
                            String s_no=Constants.NA;

                            JSONObject currentQuiz = arrayQuiz.getJSONObject(i);
                            //get the id of the current movie
                            if (Utils.contains(currentQuiz, KEYD)) {
                                id = currentQuiz.getString(KEYD);
                                Log.d("aname", currentQuiz.getString(KEYD));
                            }
                            //get the title of the current movie
                            if (Utils.contains(currentQuiz, KEY_IS_ACTIVE)) {
                                is_active = currentQuiz.getString(KEY_IS_ACTIVE);
                            }

                            // get the synopsis of the current movie
                            if (Utils.contains(currentQuiz, KEY_OPTIION1)) {
                                option1 = currentQuiz.getString(KEY_OPTIION1);
                            }
                            if (Utils.contains(currentQuiz, KEY_OPTIION2)) {
                                option2 = currentQuiz.getString(KEY_OPTIION2);
                            }

                            if (Utils.contains(currentQuiz, KEY_OPTIION3)) {
                                option3 = currentQuiz.getString(KEY_OPTIION3);
                            }
                            if (Utils.contains(currentQuiz, KEY_OPTIION4)) {
                                option4 = currentQuiz.getString(KEY_OPTIION4);
                            }
                            if (Utils.contains(currentQuiz, KEY_ANSWER)) {
                                answer = currentQuiz.getString(KEY_ANSWER);
                            }
                            if (Utils.contains(currentQuiz, KEY_QNAME)) {
                                q_name = currentQuiz.getString(KEY_QNAME);
                            }
                            if (Utils.contains(currentQuiz, KEY_SUBJECT)) {
                                subject = currentQuiz.getString(KEY_SUBJECT);
                            }

                            if (Utils.contains(currentQuiz, KEY_OPT_ANSWER)) {
                                opt_ans = currentQuiz.getString(KEY_OPT_ANSWER);
                            }
                            if (Utils.contains(currentQuiz, KEY_S_No)) {
                                s_no = currentQuiz.getString(KEY_S_No);
                            }
                            Quiz_Model quiz_model = new Quiz_Model();
                            quiz_model.setId(id);
                            quiz_model.setQname(q_name);
                            quiz_model.setIs_active(is_active);
                            quiz_model.setSubject(subject);
                            quiz_model.setAnswer(answer);
                            quiz_model.setOption1(option1);
                            quiz_model.setOption2(option2);
                            quiz_model.setOption3(option3);
                            quiz_model.setOption4(option4);
                            quiz_model.setOpt_answe(opt_ans);
                            quiz_model.setS_no(s_no);
                            if (!id.equals(Constants.NA)) {
                                quiz_listMovies.add(quiz_model);
                            }

                        }

                    }
                }catch (Exception e) {
                    Log.d("gdgd___g",e.getMessage());

                }

            }
        }
        return quiz_listMovies;
    }

    public static ArrayList<Quiz_Pending_Model> parseQuizPendingJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Quiz_Pending_Model> quiz_pend_data = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONArray arrayQuiz = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayQuiz.length(); i++) {

                    String st_Hour= Constants.NA;
                    String st_Min= Constants.NA;
                    String st_Sec= Constants.NA;
                    String st_Date= Constants.NA;
                    String st_Mon= Constants.NA;
                    String st_Year= Constants.NA;
                    String cr_Hour= Constants.NA;
                    String cr_Min= Constants.NA;
                    String cr_Sec= Constants.NA;

                    JSONObject currentQuiz = arrayQuiz.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(currentQuiz, KEY_ST_HOUR)) {
                        st_Hour = currentQuiz.getString(KEY_ST_HOUR);
                        Log.d("aname",currentQuiz.getString(KEY_ST_HOUR));
                    }
                    //get the title of the current movie
                    if (Utils.contains(currentQuiz, KEY_ST_MIN)) {
                        st_Min = currentQuiz.getString(KEY_ST_MIN);
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(currentQuiz, KEY_ST_SEC)) {
                        st_Sec = currentQuiz.getString(KEY_ST_SEC);
                    }
                    if (Utils.contains(currentQuiz, KEY_ST_DATE)) {
                        st_Date = currentQuiz.getString(KEY_ST_DATE);
                    }

                    if (Utils.contains(currentQuiz, KEY_ST_MON)) {
                        st_Mon = currentQuiz.getString(KEY_ST_MON);
                    }
                    if (Utils.contains(currentQuiz, KEY_ST_YEAR)) {
                        st_Year = currentQuiz.getString(KEY_ST_YEAR);
                    }
                    if (Utils.contains(currentQuiz, KEY_CR_HOUR)) {
                        cr_Hour = currentQuiz.getString(KEY_CR_HOUR);
                    }
                    if (Utils.contains(currentQuiz, KEY_CR_MIN)) {
                        cr_Min = currentQuiz.getString(KEY_CR_MIN);
                    }
                    if (Utils.contains(currentQuiz, KEY_CR_SEC)) {
                        cr_Sec = currentQuiz.getString(KEY_CR_SEC);
                    }


                    Quiz_Pending_Model quiz_pending_model = new Quiz_Pending_Model();
                    quiz_pending_model.setSt_Hour(st_Hour);
                    quiz_pending_model.setSt_Min(st_Min);
                    quiz_pending_model.setSt_Sec(st_Sec);
                    quiz_pending_model.setSt_Date(st_Date);
                    quiz_pending_model.setSt_Mon(st_Mon);
                    quiz_pending_model.setSt_Year(st_Year);
                    quiz_pending_model.setCr_Hour(cr_Hour);
                    quiz_pending_model.setCr_Min(cr_Min);
                    quiz_pending_model.setCr_Sec(cr_Sec);

                    if (!st_Hour.equals(Constants.NA)) {
                        quiz_pend_data.add(quiz_pending_model);
                    }

                }

            } catch (JSONException e) {

            }
        }
        return quiz_pend_data;
    }

    public static ArrayList<Quiz_Subject_Model> parseQuizSubjectJson(JSONObject response) {

        ArrayList<Quiz_Subject_Model> quiz_pend_data = new ArrayList<>();
        if (response != null && response.length() > 0) {
            Log.d("url_size1", String.valueOf(response.length()));
            try {
                JSONArray arrayQuiz = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayQuiz.length(); i++) {

                    String st_id= Constants.NA;
                    String st_name= Constants.NA;
                    String st_images= Constants.NA;
                    String st_isActive= Constants.NA;

                    JSONObject currentQuiz = arrayQuiz.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(currentQuiz, "id")) {
                        st_id = currentQuiz.getString("id");
                        Log.d("id_sname",currentQuiz.getString("id"));
                    }
                    //get the title of the current movie
                    if (Utils.contains(currentQuiz, "sname")) {
                        st_name = currentQuiz.getString("sname");
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(currentQuiz, "images")) {
                        st_images = currentQuiz.getString("images");
                    }
                    if (Utils.contains(currentQuiz, "is_active")) {
                        st_isActive = currentQuiz.getString("is_active");
                    }

                    Quiz_Subject_Model quiz_pending_model = new Quiz_Subject_Model();
                    quiz_pending_model.setId(st_id);
                    quiz_pending_model.setImages(st_images);
                    quiz_pending_model.setSname(st_name);
                    quiz_pending_model.setIs_active(st_isActive);
                    if (!st_id.equals(Constants.NA)) {
                        quiz_pend_data.add(quiz_pending_model);
                    }

                }

            } catch (JSONException e) {

            }
        }
        return quiz_pend_data;
    }
    public static ArrayList<Quiz_Subject_Ques_Model> parseQuizSubject_Question_Json(JSONObject response) {

        ArrayList<Quiz_Subject_Ques_Model> quiz_pend_data = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONArray arrayQuiz = response.getJSONArray(KEY_MOVIES);
                Log.d("url_size", ""+arrayQuiz);
                for (int i = 0; i < arrayQuiz.length(); i++) {

                    String st_id= Constants.NA;
                    String st_subect= Constants.NA;
                    String st_qname= Constants.NA;
                    String st_option1=Constants.NA;
                    String st_option2=Constants.NA;
                    String st_option3=Constants.NA;
                    String st_option4=Constants.NA;
                    String st_answer=Constants.NA;
                    String st_opt_ans_number=Constants.NA;
                    String st_is_active=Constants.NA;
                    String st_daily_flag=Constants.NA;

                    JSONObject currentQuiz = arrayQuiz.getJSONObject(i);
                    //get the id of the current movie
                    if (Utils.contains(currentQuiz, "id")) {
                        st_id = currentQuiz.getString("id");


                    }
                    //get the title of the current movie
                    if (Utils.contains(currentQuiz, "subject")) {
                        st_subect = currentQuiz.getString("subject");
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(currentQuiz, "qname")) {
                        st_qname = currentQuiz.getString("qname");
                    }
                    if (Utils.contains(currentQuiz, "option1")) {
                        st_option1 = currentQuiz.getString("option1");
                    }

                    if (Utils.contains(currentQuiz, "option2")) {
                        st_option2 = currentQuiz.getString("option2");
                    }
                    if (Utils.contains(currentQuiz, "option3")) {
                        st_option3 = currentQuiz.getString("option3");
                    }
                    if (Utils.contains(currentQuiz, "option4")) {
                        st_option4 = currentQuiz.getString("option4");
                    }
                    if (Utils.contains(currentQuiz, "answer")) {
                        st_answer = currentQuiz.getString("answer");
                    }
                    if (Utils.contains(currentQuiz, "is_active")) {
                        st_is_active = currentQuiz.getString("is_active");
                    }
                    if (Utils.contains(currentQuiz, "daily_flag")) {
                        st_daily_flag = currentQuiz.getString("daily_flag");
                    }
                    if (Utils.contains(currentQuiz, "option_answer")) {
                        st_opt_ans_number = currentQuiz.getString("option_answer");
                    }

                    Quiz_Subject_Ques_Model quiz_pending_model = new Quiz_Subject_Ques_Model();

                    quiz_pending_model.setId(st_id);
                    Log.d("id_sname",quiz_pending_model.getId());
                    quiz_pending_model.setSubject(st_subect);
                    quiz_pending_model.setQname(st_qname);
                    quiz_pending_model.setOption1(st_option1);
                    quiz_pending_model.setOption2(st_option2);
                    quiz_pending_model.setOption3(st_option3);
                    quiz_pending_model.setOption4(st_option4);
                    quiz_pending_model.setAnswer(st_answer);
                    quiz_pending_model.setOption_answer(st_opt_ans_number);
                    quiz_pending_model.setIs_active(st_is_active);
                    quiz_pending_model.setDaily_flag(st_daily_flag);
                    if (!st_id.equals(Constants.NA)) {
                        quiz_pend_data.add(quiz_pending_model);
                        Log.d("id_sname",quiz_pending_model.getId());
                    }
                }
                Log.d("siff",""+quiz_pend_data);
            } catch (JSONException e) {

            }
        }
        return quiz_pend_data;
    }

}
