package utilities;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import json.Parser;
import json.Requestor;
import pojo.Agent_Deal_Pojo;
import pojo.Cat_Model;
import pojo.Get_Course_desc;
import pojo.SEARCH_MODEL;

/**
 * Created by GT on 7/25/2017.
 */

public class List_Utils {


    public static JSONObject load_JSONOBJECT(RequestQueue requestQueue, String URL,Map<String,String> map) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        Log.d("responce_pojojuk", "" + URL);
        JSONObject response = Requestor.requestJSON_MAP(requestQueue, URL,map);
        return response;
    }
    public static JSONObject load_JSONOBJECT(RequestQueue requestQueue, String URL) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        Log.d("responce_pojojuk", "" + URL);
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, URL);
        return response;
    }

    public static ArrayList<Cat_Model> load_Cat_List(RequestQueue requestQueue, String URL) {
    //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        Log.d("responce_pojojuk",""+URL);
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, URL);
        ArrayList<Cat_Model> list_default = Parser.parse_List_JSON(response);
        /*  Log.d("responce_pojgggojuk",""+list_default.size());
        Log.d("responce_pojdddgggojuk",""+response.toString());*/


        ArrayList<String> status_list=Parser.parse_Status_List_JSON(response);
    //    Log.d("stsdrter", ""+status_list.size());
        for (int i=0; i<status_list.size();i++)
        {
         /*   Log.d("stsdggrter", ""+status_list.get(i));
            Log.d("stsder", String.valueOf(status_list.size()));*/
            save_shared_prefrece(Parser.parse_common_list(response,status_list.get(i)),status_list.get(i),status_list);
        }

      //  Log.d("listt",""+str_list);

        return list_default;
    }


    public static ArrayList<Cat_Model> load_search(RequestQueue requestQueue, String URL) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
      //  Log.d("responce_pojojukdd",""+URL);
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, URL);
        ArrayList<Cat_Model> list_default = Parser.parse_List_JSON(response);

        return list_default;
    }
    public static ArrayList<Common_Pojo> load_pager_filter(RequestQueue requestQueue,String filter_url,String filter_key) {
      //  Log.d("responce_pojojuk",""+filter_url+"   "+filter_key);
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, filter_url);

        return Parser.parse_common_list(response,filter_key);
    }
    public static ArrayList<Agent_Deal_Pojo> load_agent_deal_list(RequestQueue requestQueue, String URL, Map<String, String> map) {
        JSONObject response = Requestor.requestJSON_MAP(requestQueue, URL,map);
        ArrayList<Agent_Deal_Pojo> list_default = Parser.parse_List_agent(response);
        return list_default;
    }
    public static ArrayList<Agent_Deal_Pojo> load_agent_deal_list(RequestQueue requestQueue, String URL) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, URL);
        ArrayList<Agent_Deal_Pojo> list_default = Parser.parse_List_agent(response);
        return list_default;
    }

    public static ArrayList<Cat_Model> load_Clg_Detail(RequestQueue requestQueue, String URL,String key) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        Log.d("gfgrtd",""+URL);
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, URL);
        Log.d("gfgrtd",""+response);
        ArrayList<Cat_Model> list_default = Parser.parse_List_JSON(response);
        Log.d("gfgrtd",""+response);

        return list_default;
    }


    public static JSONObject load_json(RequestQueue requestQueue, String URL, Map<String, String> map) {

        JSONObject response = Requestor.requestJSON_MAP(requestQueue, URL,map);

        return response;
    }

    private static void save_shared_prefrece(ArrayList<Common_Pojo> common_pojos, String str_listname, ArrayList<String> status_list) {

        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences("Temp_List",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TAB_LIST",status_list.toString());

        java.lang.reflect.Type type;

        /* cat_model_list*/
        type = new TypeToken<List<Common_Pojo>>() {}.getType();
        Gson gson = new Gson();
        String str_list=gson.toJson(common_pojos,type);
        editor.putString(str_listname,str_list);
        editor.apply();
    }
    public static ArrayList<Common_Pojo> load_common_pojo(RequestQueue requestQueue,String url,String arr_name) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        Log.d("responce_url_vfg",""+url);
        JSONObject response = Requestor.requestMoviesJSON(requestQueue,url);
        ArrayList<Common_Pojo> list_default = Parser.parse_common_list(response,arr_name);
        Log.d("responce_pojo",""+response);

        ArrayList<String> status_list=Parser.parse_Status_List_JSON(response);
        Log.d("stsdrter", ""+status_list.size());
        for (int i=0; i<status_list.size();i++)
        {
            Log.d("stsder", String.valueOf(status_list.size()));
            save_shared_prefrece(Parser.parse_common_list(response,status_list.get(i)),status_list.get(i),status_list);
        }

        //  Log.d("listt",""+str_list);
        return list_default;
    }

    public static ArrayList<Common_Pojo> load_common_course_pojo(RequestQueue requestQueue,String url,String arr_name) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        Log.d("responce_url_vfg",""+url);
        JSONObject response = Requestor.requestMoviesJSON(requestQueue,url);
        ArrayList<Common_Pojo> list_default = Parser.parse_coursecommon_list(response,arr_name);
        Log.d("responce_pojo",""+response);

        ArrayList<String> status_list=Parser.parse_Status_List_JSON(response);
        Log.d("stsdrter", ""+status_list.size());
        for (int i=0; i<status_list.size();i++)
        {
            Log.d("stsder", String.valueOf(status_list.size()));
            save_shared_prefrece(Parser.parse_common_list(response,status_list.get(i)),status_list.get(i),status_list);
        }

        //  Log.d("listt",""+str_list);
        return list_default;
    }

    public static ArrayList<Cat_Model> filter_load_Cat_List(RequestQueue requestQueue,String str_url) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, str_url);

        Log.d("responce_pojo_filter",""+response);
        ArrayList<Cat_Model> list_default = Parser.parse_List_JSON(response);//for parsing json data

        Log.d("filter_request",""+list_default.size());

        return list_default;
    }
    public static ArrayList<Cat_Model> filter_load_Cat_List(RequestQueue requestQueue,String str_url,Map<String ,String> map) {
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        JSONObject response = Requestor.requestJSON_MAP(requestQueue, str_url,map);

        Log.d("responce_pojo_filter",""+response);
        ArrayList<Cat_Model> list_default = Parser.parse_List_JSON(response);//for parsing json data

        Log.d("filter_request",""+list_default.size());

        return list_default;
    }


    public static ArrayList<Get_Course_desc> get_course_descs(RequestQueue requestQueue, String str_url) {
        Log.d("jdhjhcjhch",str_url);
        //////////  UrlEndpoints.URL_DEAL_CAT_MAIN   should be dynamic according to click listener............
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, str_url);

        Log.d("responce_filter",""+response);
        ArrayList<Get_Course_desc> list_default = Parser.parse_Course_JSON(response);//for parsing json data

      //  Log.d("filter_request",""+list_default.size());

        return list_default;
      // return null;
    }
}
