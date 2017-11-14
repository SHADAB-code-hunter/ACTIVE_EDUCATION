package utilities;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pojo.*;

/**
 * Created by GT on 9/28/2017.
 */

public class App_Raw_Data {

    public static  StringBuilder local_parseJson(String check_json) {

        Resources res = MyApplication.getAppContext().getResources();
        InputStream is=res.openRawResource(R.raw.state);
        Scanner scanner=new Scanner(is);
        StringBuilder builder_json=new StringBuilder();

        while(scanner.hasNextLine()){
            builder_json.append(scanner.nextLine());
        }
        StringBuilder builder2=new StringBuilder();
        try {
            JSONObject root= new JSONObject(builder_json.toString());
            JSONArray jsonArray=root.getJSONArray("state");

            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                jsonObject.getString("name");
                if(check_json.equals(jsonObject.getString("name")))
                {
                    builder2.append(jsonObject.getString("id"));
                    return builder2;
                }
            }

        }catch (JSONException e){
            Log.d("jsonexception",e.getMessage());
        }

        return builder2;
    }

    public static  String[] local_state_parseJson() {

        Resources res = MyApplication.getAppContext().getResources();
        InputStream is=res.openRawResource(R.raw.state);
        Scanner scanner=new Scanner(is);
        StringBuilder builder_json=new StringBuilder();

        while(scanner.hasNextLine()){
            builder_json.append(scanner.nextLine());
        }

        StringBuilder builder2=new StringBuilder();
        try {
            JSONObject root= new JSONObject(builder_json.toString());
            JSONArray jsonArray=root.getJSONArray("state");
             String [] str_state_name=null;
            for (int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                jsonObject.getString("name");

                str_state_name[i]=jsonObject.getString("name");

            }
            return str_state_name;

        }catch (JSONException e){
            Log.d("jsonexception",e.getMessage());
        }

        return null;
    }

    public static int getMatColor(int poss,String Arr_name)
    {
        int returnColor = Color.WHITE;
        int arrayId = MyApplication.getAppContext().getResources().getIdentifier("mdcolor_A100" , "array", MyApplication.getAppContext().getPackageName());

        if (arrayId != 0)
        {
            TypedArray colors = MyApplication.getAppContext().getResources().obtainTypedArray(arrayId);
            // int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(poss, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }
}
