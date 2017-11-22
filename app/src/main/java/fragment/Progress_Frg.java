package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GT on 11/20/2017.
 */

public class Progress_Frg extends Fragment {
    Picasso picasso;
    RecyclerView recyclerView;
    private PieChart pieChart;
    private PieDataSet dataSet;
    private ArrayList<String> xVals=new ArrayList<>();
    private ArrayList<Entry> yvalues=new ArrayList<>();
    private Bundle bundle;
    private int str_pie1,str_pie2,str_pie3,str_pie4,str_pie5;
    private String jsonObject;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_agent_progress_inner, container, false);
        try {
        /* if(getArguments()==null)
         {
             return null;

         }else {*/
             bundle=getArguments();


             JSONObject jsonObject = new JSONObject(bundle.getString("jobj"));

             JSONArray jsonArray=jsonObject.getJSONArray("data");
             Log.d("djjdhjhd",""+jsonArray.toString());
             str_pie1=Math.round(Float.parseFloat(jsonArray.getJSONObject(0).getString("remaining_per")));
             str_pie2=Math.round(Float.parseFloat(jsonArray.getJSONObject(0).getString("filled_per")));
             str_pie3=Math.round(Float.parseFloat(jsonArray.getJSONObject(0).getString("total_done")));
             str_pie4=Math.round(Float.parseFloat(jsonArray.getJSONObject(0).getString("total")));
        // }

        pieChart= (PieChart)rootView. findViewById(R.id.piechart);
        pieChart.setCenterTextSize(50);
        pieChart.setDrawCenterText(true);
        pieChart.setAnimationCacheEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.performClick();
        pieChart.setEnabled(false);
        pieChart.setUsePercentValues(true);

        yvalues = new ArrayList<Entry>();

        float p1=Float.parseFloat(String.valueOf(str_pie1)); float p2=Float.parseFloat (String.valueOf(str_pie2)); float p3=Float.parseFloat (String.valueOf(str_pie3));

        yvalues.add(new Entry(str_pie1,1));
        yvalues.add(new Entry(str_pie2, 2));
      //  yvalues.add(new Entry(str_pie3, 3));


        dataSet = new PieDataSet(yvalues, "");
        dataSet.setColors(new int[]{getResources().getColor(R.color.skip_color),getResources().getColor(R.color.wrong_color),getResources().getColor(R.color.right_color)});

        xVals = new ArrayList<String>();
        xVals.add("Remaining");
        xVals.add("Filled");
       // xVals.add("Right");
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        } catch (Exception e) {
            Log.d("bnbn",""+e.getMessage());
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
