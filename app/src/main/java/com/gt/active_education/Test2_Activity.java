package com.gt.active_education;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by GT on 8/24/2017.
 */

public class Test2_Activity extends AppCompatActivity {


    Button mbtn1,mbtn2,mbtn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       /* TextView textView=(TextView)findViewById(R.id.id_tv);
        textView.setText(local_parseJson("DELHI"));*/

        ExpandableRelativeLayout expandableLayout= (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);

// toggle expand, collapse
        expandableLayout.toggle();
// expand
        expandableLayout.expand();
// collapse
        expandableLayout.collapse();

// move position of child view
        expandableLayout.moveChild(0);
// move optional position
        expandableLayout.move(500);

// set base position which is close position
        expandableLayout.setClosePosition(500);
    }

    private StringBuilder local_parseJson(String check_json) {

        Resources res = getResources();
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


}

