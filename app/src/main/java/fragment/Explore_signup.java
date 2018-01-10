package fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gt.active_education.Agent_login_Activity;
import com.gt.active_education.R;
import com.gt.active_education.Signup_Guest_Activity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import callbacks.JsonRes_Listener;
import task.Asynch_Responce_OBJ;
import utilities.App_Raw_Data;
import utilities.App_Static_Method;
import utilities.Image_picker;
import utilities.State_City_Search;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.permission_check;
import static utilities.App_Static_Method.request_permission_result;
import static utilities.UrlEndpoints.GET_CITY;
import static utilities.UrlEndpoints.SIGN_UP_API;

/**
 * Created by GT on 11/26/2017.
 */

public class Explore_signup extends Fragment implements View.OnClickListener, Image_picker.ImageAttachmentListener, JsonRes_Listener {

    private String str_type;
    private  EditText id_clg_name
            ,id_short_clg_name
        ,id_password
             ,   id_phone
        ,id_landline
             ,   id_email
       , id_address;
    private String[] str_SOCIAL_server_key;
    private String[] str_SOCIAL_hint_key;
    private Map<String, String> map_add=new HashMap<>();
    private ImageView id_edit_camera_iv,id_image_profile_signup;
    private Image_picker imagePicker;
    private Bitmap bitmap;
    private String file_name;
    private String str_profile_bitmap;
    private Button id_btn;
    private FrameLayout id_state_frm,id_frm_city;
    private boolean open=false;
    private State_City_Search state_city_search;
    private TextView id_text_tv_state,id_text_tv_city;
    private TextView id_tv_state,id_tv_city;
    private Activity context;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.explore_signup, container, false);
        id_edit_camera_iv=(ImageView)rootView.findViewById(R.id.id_edit_camera_iv);id_edit_camera_iv.setOnClickListener(this);
         str_type=getArguments().getString("type");
        map_add.put("type",str_type);
        id_image_profile_signup=(ImageView)rootView.findViewById(R.id.id_image_profile_signup);
        id_btn=(Button)rootView.findViewById(R.id.id_btn);id_btn.setOnClickListener(this);
        set_clg_social(rootView);
//   new Login_Asynch(Gust_Regis_Fragment.this,map,UrlEndpoints.GUSET_REGISTER,"Guset_Register").execute();
        id_state_frm=(FrameLayout)rootView.findViewById(R.id.id_frm_state);
        id_frm_city=(FrameLayout)rootView.findViewById(R.id.id_frm_city);
        id_tv_state=(TextView)rootView.findViewById(R.id.id_text_tv_state);
        id_tv_city=(TextView)rootView.findViewById(R.id.id_text_tv_city);


        id_tv_state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d("gdgdgg","click");

                if(!open) {
                    open = true;

                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s,String s_id) {
                            // id_tv_state.setText(s);id_tv_city.setText("Select City");
                            Log.d("kkjkjkjkj",""+s_id);
                            if(!s.equals("na")){
                                id_tv_state.setText(s);
                                id_tv_city.setText("Select City");
                                map_add.put("state", ""+ App_Raw_Data.local_parseJson(s));
                                Log.d("sgsdfe",""+App_Raw_Data.local_parseJson(s))  ;                                                      }
                            else {
                                id_tv_state.setText("Select State"); id_tv_city.setText("Select City");
                            }
                            state_city_search.cancel();
                            open=false;
                        }
                    }, Explore_signup.this.getContext(), UrlEndpoints.get_filter_list+1,"state");
                    state_city_search.show();
                }
                return false;
            }
        });

        id_tv_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("gdgdgg","click");
                if (!open) {
                    open = true;

                    if (id_tv_state.getText().toString() != null) {
                        StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {

                                //  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_tv_city.setText(s);

                                    map_add.put("city",s_id);
                                }else {
                                    id_tv_city.setText("Select City");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Explore_signup.this.getContext(),GET_CITY +map_add.get("state"),"city");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Explore_signup.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        return rootView;

    }
    
    
    private void set_clg_social(View rootView) {

        id_clg_name=(EditText)rootView.findViewById(R.id.id_clg_name);
        id_short_clg_name=(EditText)rootView.findViewById(R.id.id_short_clg_name);
        id_password=(EditText)rootView.findViewById(R.id.id_password);
        id_phone=(EditText)rootView.findViewById(R.id.id_phone);
        id_landline=(EditText)rootView.findViewById(R.id.id_landline);
        id_email=(EditText)rootView.findViewById(R.id.id_email);
        id_address=(EditText)rootView.findViewById(R.id.id_address);

        //id_detail_save_btn=(Button)rootView.findViewById(R.id.id_detail_save_btn);id_detail_save_btn.setOnClickListener(this);

        Log.d("hintstring", id_clg_name.getHint().toString());

        str_SOCIAL_server_key=new String[]{"name" ,"sname","mobile","landline","pwd","email","address"};
        str_SOCIAL_hint_key=new String[]{"Full Name" ,"Short Name","Mobile Number","LandLine Number","Password","Address"};

        set_SOCIAL_TextWatcher(id_clg_name, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_short_clg_name, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_password, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_phone, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_landline, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_email, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_address, str_SOCIAL_hint_key);

    }
    private void set_SOCIAL_TextWatcher(final EditText id_edt_a,final String[] str_partner_hint_key) {

        id_edt_a.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Log.d("editabale","g   "+s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("dcccdc","djhj   "+s);
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if(id_edt_a.getHint().toString()!=null) {
                    //  Log.d("str_key", "1");
                    for (int i = 0; i < str_partner_hint_key.length; i++) {
                        String str_server = str_partner_hint_key[i];
                        //  Log.d("str_server", "2" + str_server);
                        //  Log.d("id_edt", "3" + id_edt_a.getHint().toString());
                        if (id_edt_a.getHint().toString().equals(str_server)) {
                            String str_key = str_SOCIAL_server_key[i];
                            map_add.put(str_key, query.toString());
                            Log.d("str_key", str_key);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.id_edit_camera_iv:
                permission_check(101, Explore_signup.this);
                imagePicker = new Image_picker(Explore_signup.this);

                if (Build.VERSION.SDK_INT >= 23) {
                    imagePicker.imagepicker(1, "1111");
                } else {
                    App_Static_Method.lower_camera_call(Explore_signup.this);
                }
                break;

            case R.id.id_btn:
                if(str_profile_bitmap!=null) {
                    map_add.put("filename", "" + map_add.get("name") + ".png");
                    map_add.put("image", str_profile_bitmap);
                }
              //  new Login_Asynch(Explore_signup.this,map_add, SIGN_UP_API,"Guset_Register").execute();

                new Asynch_Responce_OBJ(new Asynch_Responce_OBJ.OBJ_LISTENER() {
                    @Override
                    public void obn_obj_find(JSONObject jsonObject) {
                        try {

                            Log.d("rdfesruts", jsonObject.toString());

                            if (jsonObject.getInt("msg") == 1) {
                                Intent i = new Intent(getContext(), Agent_login_Activity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);
                                ((Signup_Guest_Activity) context).finish();
                            }else if(jsonObject.getInt("exist") == 1) {
                                Toast.makeText(context, "Already Exist !!!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e)
                        {
                            Log.d("ecept",e.getMessage());
                        }
                    }
                },SIGN_UP_API,map_add).execute();
                break;
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1) {
            imagePicker.request_permission_result(requestCode, permissions, grantResults);
        }else if(requestCode==101){

            request_permission_result(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("dataedpack",data.toString());

        if (requestCode == App_Static_Method.lower_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            id_image_profile_signup.setImageBitmap(photo);
        }else if(requestCode!= App_Static_Method.lower_CAMERA_REQUEST) {
            imagePicker.onActivityResult(requestCode, resultCode, data,"10001");
        }

    }
    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String s, String str_img) {
        this.bitmap=file;
        this.file_name=filename; //Log.d("callll","call");
        id_image_profile_signup.setImageBitmap(file);
        str_profile_bitmap=getStringImage(file);
        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imagePicker.createImage(file,filename,path,false);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void on_res_litsner(JSONObject response, String restLocatDash) {

    }
}
