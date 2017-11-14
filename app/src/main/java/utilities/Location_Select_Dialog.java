package utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import com.gt.active_education.R;

/**
 * Created by GT on 11/9/2017.
 */

public class Location_Select_Dialog extends Dialog  {

    private LinearLayout id_linear_HS, id_linear_intermediate, id_linear_graduate, id_linear_inter_graduate;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences,shrdf;

    public Location_Select_Dialog(Context activity) {
        super(activity);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.locat_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

}
