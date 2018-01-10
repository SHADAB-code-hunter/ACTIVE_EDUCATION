package fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Step_Form_Validator.BackConfirmationFragment;
import Step_Form_Validator.VerticalStepperForm;
import Step_Form_Validator.VerticalStepperFormLayout;
import callbacks.Form_Responce_Listener;
import callbacks.Pager_Change_listener;
import callbacks.Postion_Listener_Education_Form;
/*
import droidninja.filepicker.FilePickerBuilder;
import permissions.dispatcher.NeedsPermission;*/
import utilities.App_Static_Method;
import utilities.Attach_Dialog;
import utilities.Image_picker;
import utilities.MyApplication;
import utilities.UpdateValues;

import static utilities.App_Static_Method.get_session_type;
import static utilities.App_Static_Method.lower_CAMERA_REQUEST;
import static utilities.App_Static_Method.request_permission_result;
import static utilities.App_Static_Method.toMERGE_JSON;
import static utilities.UpdateValues.ADDMISSION;

/**
 * Created by GT on 8/25/2017.
 */

public class Education_Form_Fragment extends Fragment implements VerticalStepperForm , Postion_Listener_Education_Form
, Form_Responce_Listener,Image_picker.ImageAttachmentListener{
    public static final String NEW_ALARM_ADDED = "new_alarm_added";

    // Information about the steps/fields of the form
    private static final int TITLE_STEP_NUM = 0;
    private static final int DESCRIPTION_STEP_NUM = 1;
    private static final int TIME_STEP_NUM = 2;
    private static final int DAYS_STEP_NUM = 3;

    // Title step
    private EditText titleEditText;
    private static final int MIN_CHARACTERS_TITLE = 3;
    public static final String STATE_TITLE = "title";

    // Description step
    private EditText descriptionEditText;
    public static final String STATE_DESCRIPTION = "description";

    // Time step
    private TextView timeTextView;
    private TimePickerDialog timePicker;
    private Pair<Integer, Integer> time;
    public static final String STATE_TIME_HOUR = "time_hour";
    public static final String STATE_TIME_MINUTES = "time_minutes";

    // Week days step
    private boolean[] weekDays;
    private LinearLayout daysStepContent;
    public static final String STATE_WEEK_DAYS = "week_days";

    private boolean confirmBack = true;
    private ProgressDialog progressDialog;
    private VerticalStepperFormLayout verticalStepperForm;

    public static final String STATE_H_School= "High School (10) Detail";
    public static final String STATE_I_School= "Intermediate (12) Detail";
    public static final String STATE_DIPLOMA= "Diploma/ITI Detail";
    public static final String STATE_GRADUATE= "Graduate Detail";
    public static final String STATE_POST_GRADUATE= "Post Graduate Detail";
    private Map<String, HashMap<String,String>> gens;
    private int poss;
    private EditText id_mark_percent,id_edt_duration,id_edt_pass_year,id_edt_b_name,id_edt_s_name;
    private HashMap<String,String> hashMap=new HashMap<String,String>();
    private Bundle bundle;
    private Image_picker imagePicker;
    private  String str_final_hashmap;
    private Bitmap bitmap;
    private String file_name;
    private ImageView id_img_picker_h;
    private String str_profile_bitmap="";
    private int MAX_ATTACHMENT_COUNT = 10;
    private View inflatedLayout;
    private View rootView;
    private LinearLayout id_linear_attach;
    private FrameLayout id_frm;
    private Activity context;
    private FrameLayout id_done;
    private JSONObject jsonObject_admission;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_vertical_stepper_form, container, false);


       /* context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/

        SharedPreferences sharedPreferences =context.getSharedPreferences(UpdateValues.FORM_ID, 0);
        Log.d("hgdhgh",""+sharedPreferences.getString("Form_ID","NA"));
        if(sharedPreferences.getString("Form_ID","NA").equalsIgnoreCase("NA"))
        {

        }

        id_done=(FrameLayout)rootView.findViewById(R.id.id_done);
        id_frm=(FrameLayout)rootView.findViewById(R.id.id_frm);
        id_frm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attach_Dialog attach_dialog=new Attach_Dialog(context);
                attach_dialog.show();
            }
        });

        id_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Pager_Change_listener pager_change_listener=(Pager_Change_listener)context;
                pager_change_listener.on_pager_change(1, response);*/

            }
        });
        if(getArguments()!=null)
        {
            Log.d("bundrrle","null");
            bundle=getArguments();
            try {
                sharedPreferences =context.getSharedPreferences(UpdateValues.FORM_ID, 0);
                Log.d("hgdhgh",""+sharedPreferences.getString("Form_ID","NA"));
                jsonObject_admission=new JSONObject(bundle.getString(ADDMISSION));
                if(!sharedPreferences.getString("Form_ID","NA").equalsIgnoreCase("NA")) {
                    jsonObject_admission.put("formid", "" + sharedPreferences.getString("Form_ID", "NA"));
                }else {
                    Toast.makeText(context, "Please Fill The Personal Detail first !!!", Toast.LENGTH_SHORT).show();
                }
                jsonObject_admission=toMERGE_JSON(jsonObject_admission,new JSONObject(get_session_type()));
                Log.d("hg_dhgh",""+jsonObject_admission.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        imagePicker=new Image_picker(Education_Form_Fragment.this);
        gens= new HashMap<String,HashMap<String,String>>();
        initializeActivity(rootView,jsonObject_admission);
        //set_form_oprn_status(rootView,jsonObject_admission);
        return rootView;
    }

    private void set_form_oprn_status(View rootView, JSONObject jsonObject_admission) {
        try {
            String str_category= this.jsonObject_admission.getString("category");
            String str_class= this.jsonObject_admission.getString("class_name");

            switch (str_category)
            {
                case "1":

                  //  get_form_staep(rootView,);
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void get_form_staep() {


    }


    private void initializeActivity(View rootView, JSONObject jsonObject_admission) {

        this.rootView=rootView;
        // Time step vars
        time = new Pair<>(8, 30);
        setTimePicker(8, 30);

        // Week days step vars
        weekDays = new boolean[7];

        // Vertical Stepper form vars
        int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);


        String[] stepsTitles = getResources().getStringArray(R.array.steps_titles);
        //switch ()
        String[] stepsTitles2 = Arrays.copyOfRange(stepsTitles, 0, 3);
        //String[] stepsSubtitles = getResources().getStringArray(R.array.steps_subtitles);

        // Here we find and initialize the form
        verticalStepperForm = (VerticalStepperFormLayout) rootView.findViewById(R.id.vertical_stepper_form);
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, stepsTitles2, (VerticalStepperForm)this, (Activity) getContext(),
                (Postion_Listener_Education_Form)this)
                //.stepsSubtitles(stepsSubtitles)
                .materialDesignInDisabledSteps(true) // false by default
                //.showVerticalLineWhenStepsAreCollapsed(true) // false by default
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true)
                .init();

    }
    // METHODS THAT HAVE TO BE IMPLEMENTED TO MAKE THE LIBRARY WORK
    // (Implementation of the interface "VerticalStepperForm")

    @Override
    public View createStepContentView(int stepNumber) {
        // Here we generate the content view of the correspondent step and we return it so it gets
        // automatically added to the step layout (AKA stepContent)
        View view = null;
        switch (stepNumber) {
            case TITLE_STEP_NUM:
                view = create_H_School_Step(TITLE_STEP_NUM);
                break;
            case DESCRIPTION_STEP_NUM:
               // view = createAlarmDescriptionStep();
                view = create_H_School_Step(DESCRIPTION_STEP_NUM);
                break;
            case TIME_STEP_NUM:
              //  view = createAlarmTimeStep();
                view = create_H_School_Step(TIME_STEP_NUM);
                break;
            case DAYS_STEP_NUM:
               // view = createAlarmDaysStep();
                view = create_H_School_Step(DAYS_STEP_NUM);
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case TITLE_STEP_NUM:
                // When getContext() step is open, we check that the title is correct
              //  checkTitleStep(titleEditText.getText().toString());
                checkTitleStep();
                break;
            case DESCRIPTION_STEP_NUM:

            case TIME_STEP_NUM:
                // As soon as they are open, these two steps are marked as completed because they
                // have default values
                verticalStepperForm.setStepAsCompleted(stepNumber);
                // In getContext() case, the instruction above is equivalent to:
                // verticalStepperForm.setActiveStepAsCompleted();
                break;
            case DAYS_STEP_NUM:
                // When getContext() step is open, we check the days to verify that at least one is selected
                verticalStepperForm.setStepAsCompleted(stepNumber);
              //  checkDays();
                break;
        }
    }

    @Override
    public void sendData() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.vertical_form_stepper_form_sending_data_message));
        executeDataSending(progressDialog);
        Log.d("djhjfh","pogress add");
    }

    // OTHER METHODS USED TO MAKE getContext() EXAMPLE WORK

    private void executeDataSending(final ProgressDialog progressDialog) {

        // TODO Use here the data of the form as you wish

        // Fake data sending effect
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    App_Static_Method.send_Toserver(Education_Form_Fragment.this,jsonObject_admission);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start(); // You should delete getContext() code and add yours
    }

    private View create_H_School_Step(final int titleStepNum) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflatedLayout= inflater.inflate(R.layout.form_feild_layout, null, false);
        id_linear_attach=(LinearLayout)inflatedLayout.findViewById(R.id.id_linear_attach);
        id_mark_percent=(EditText)inflatedLayout.findViewById(R.id.id_mark_percent);
        id_edt_duration=(EditText)inflatedLayout.findViewById(R.id.id_edt_duration);
        id_edt_pass_year=(EditText)inflatedLayout.findViewById(R.id.id_edt_pass_year);
        id_edt_b_name=(EditText)inflatedLayout.findViewById(R.id.id_edt_b_name);
        id_edt_s_name=(EditText)inflatedLayout.findViewById(R.id.id_edt_s_name);

        id_linear_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Custom_Dialog_FTP custom_dialog_ftp=new Custom_Dialog_FTP(Education_Form_Fragment.this.getContext(),titleStepNum);
                custom_dialog_ftp.show();*/
            }
        });
        id_edt_s_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              //  checkTitleStep(s.toString());
             //   Toast.makeText(Education_Form_Fragment.this.getContext(), ""+s, Toast.LENGTH_SHORT).show();
                hashMap.put("str_sname",""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        id_edt_b_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  checkTitleStep(s.toString());
               // Toast.makeText(Education_Form_Fragment.this.getContext(), ""+s, Toast.LENGTH_SHORT).show();
                hashMap.put("str_b_name",""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        id_edt_pass_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  checkTitleStep(s.toString());
           //     Toast.makeText(Education_Form_Fragment.this.getContext(), ""+s, Toast.LENGTH_SHORT).show();
                hashMap.put("str_mark",""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        id_edt_duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  checkTitleStep(s.toString());
            //    Toast.makeText(Education_Form_Fragment.this.getContext(), ""+s, Toast.LENGTH_SHORT).show();
                hashMap.put("str_duration",""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        id_edt_pass_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  checkTitleStep(s.toString());
            //    Toast.makeText(Education_Form_Fragment.this.getContext(), ""+s, Toast.LENGTH_SHORT).show();
                hashMap.put("str_pass_year",""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return inflatedLayout;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1) {
            imagePicker.request_permission_result(requestCode, permissions, grantResults);
        }else if(requestCode==101){
            /*  //Log.d("permisss","permission find"+requestCode+" "+permissions[0]+" "+grantResults[0]);
                //Log.d("permisss2","permission find2"+requestCode+" "+permissions[1]+" "+grantResults[1]);
                //Log.d("permisss3","permission find3"+requestCode+" "+permissions[2]+" "+grantResults[2]);*/
            request_permission_result(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // programmitically set screen orientation of activity (landscape)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        // programmitically set screen orientation of activity (portrait)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (requestCode == lower_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d("datapack",data.toString());
           /* View inflatedLayout=(View)verticalStepperForm.findViewById(R.layout.form_feild_layout);
            ((ImageView)inflatedLayout.findViewById(R.id.id_doc_a)).setImageBitmap(photo);*/
        }else if(requestCode!= lower_CAMERA_REQUEST) {
            imagePicker.onActivityResult(requestCode, resultCode, data,"10001");
        }
    }


    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String s,String str_img) {
        this.bitmap=file;
        this.file_name=filename; //Log.d("callll","call");

      //  Log.d("bitmapp",file.toString());
        //Log.d("urlir",uri.toString()+"  "+from);
        id_img_picker_h.setImageBitmap(file);
      //  image_Attach_Run(file);
        str_profile_bitmap=getStringImage(file);
        Log.d("bitmaddpp",str_profile_bitmap);
        //str_profile_bitmap=s;
        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        /*BitmapProcessor bitmapProcessor = new BitmapProcessor(file, 1000, 1000, 90);
        id_image_profile_signup.setImageBitmap(bitmapProcessor.getBitmap());
        str_profile_bitmap=BitMapToString(bitmapProcessor.getBitmap());*/
        imagePicker.createImage(file,filename,path,false);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void lower_camera_call() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, lower_CAMERA_REQUEST);
    }
    private View createAlarmDescriptionStep() {
        descriptionEditText = new EditText(getContext());
        descriptionEditText.setHint(R.string.form_hint_description);
        descriptionEditText.setSingleLine(true);
        descriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                verticalStepperForm.goToNextStep();
                return false;
            }
        });
        return descriptionEditText;
    }

    private View createAlarmTimeStep() {
        // getContext() step view is generated by inflating a layout XML file
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout timeStepContent =
                (LinearLayout) inflater.inflate(R.layout.step_time_layout, null, false);
        timeTextView = (TextView) timeStepContent.findViewById(R.id.time);
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show();
            }
        });
        return timeStepContent;
    }

    private View createAlarmDaysStep() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        daysStepContent = (LinearLayout) inflater.inflate(
                R.layout.step_days_of_week_layout, null, false);

        String[] weekDays = getResources().getStringArray(R.array.week_days);
        for(int i = 0; i < weekDays.length; i++) {
            final int index = i;
            final LinearLayout dayLayout = getDayLayout(index);
            if(index < 5) {
                activateDay(index, dayLayout, false);
            } else {
                deactivateDay(index, dayLayout, false);
            }

            dayLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((boolean)v.getTag()) {
                        deactivateDay(index, dayLayout, true);
                    } else {
                        activateDay(index, dayLayout, true);
                    }
                }
            });

            final TextView dayText = (TextView) dayLayout.findViewById(R.id.day);
            dayText.setText(weekDays[index]);
        }
        return daysStepContent;
    }

    private void setTimePicker(int hour, int minutes) {
        timePicker = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTime(hourOfDay, minute);
                    }
                }, hour, minutes, true);
    }

    private boolean checkTitleStep() {
        boolean titleIsCorrect = false;

      /*  if(title.length() >= MIN_CHARACTERS_TITLE) {

        */
            verticalStepperForm.setActiveStepAsCompleted();
            titleIsCorrect = true;

            // Equivalent to: verticalStepperForm.setStepAsCompleted(TITLE_STEP_NUM);

     //   } else {
           /* String titleErrorString = getResources().getString(R.string.error_title_min_characters);
            String titleError = String.format(titleErrorString, MIN_CHARACTERS_TITLE);

            verticalStepperForm.setActiveStepAsUncompleted(titleError);*/
            // Equivalent to: verticalStepperForm.setStepAsUncompleted(TITLE_STEP_NUM, titleError);

      //  }

        return titleIsCorrect;
    }

    private void setTime(int hour, int minutes) {
        time = new Pair<>(hour, minutes);
        String hourString = ((time.first > 9) ?
                String.valueOf(time.first) : ("0" + time.first));
        String minutesString = ((time.second > 9) ?
                String.valueOf(time.second) : ("0" + time.second));
        String time = hourString + ":" + minutesString;
        timeTextView.setText(time);
    }

    private void activateDay(int index, LinearLayout dayLayout, boolean check) {
        weekDays[index] = true;

        dayLayout.setTag(true);

        Drawable bg = ContextCompat.getDrawable(getContext(),R.drawable.circle_step_done);
        int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        bg.setColorFilter(new PorterDuffColorFilter(colorPrimary, PorterDuff.Mode.SRC_IN));
        dayLayout.setBackground(bg);

        TextView dayText = (TextView) dayLayout.findViewById(R.id.day);
        dayText.setTextColor(Color.rgb(255, 255, 255));

        if(check) {
            checkDays();
        }
    }

    private void deactivateDay(int index, LinearLayout dayLayout, boolean check) {
        weekDays[index] = false;

        dayLayout.setTag(false);

        dayLayout.setBackgroundResource(0);

        TextView dayText = (TextView) dayLayout.findViewById(R.id.day);
        int colour = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        dayText.setTextColor(colour);

        if(check) {
            checkDays();
        }
    }

    private boolean checkDays() {
        boolean thereIsAtLeastOneDaySelected = false;
        for(int i = 0; i < weekDays.length && !thereIsAtLeastOneDaySelected; i++) {
            if(weekDays[i]) {
                verticalStepperForm.setStepAsCompleted(DAYS_STEP_NUM);
                thereIsAtLeastOneDaySelected = true;
            }
        }
        if(!thereIsAtLeastOneDaySelected) {
            verticalStepperForm.setStepAsUncompleted(DAYS_STEP_NUM, null);
        }

        return thereIsAtLeastOneDaySelected;
    }

    private LinearLayout getDayLayout(int i) {
        int id = daysStepContent.getResources().getIdentifier(
                "day_" + i, "id", getContext().getPackageName());
        return (LinearLayout) daysStepContent.findViewById(id);
    }

    // CONFIRMATION DIALOG WHEN USER TRIES TO LEAVE WITHOUT SUBMITTING

    private void confirmBack() {
        if(confirmBack && verticalStepperForm.isAnyStepCompleted()) {
            BackConfirmationFragment backConfirmation = new BackConfirmationFragment();
            backConfirmation.setOnConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = true;
                }
            });
            backConfirmation.setOnNotConfirmBack(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmBack = false;
                    getActivity().finish();
                }
            });
            backConfirmation.show(getActivity().getSupportFragmentManager(), null);
        } else {
            confirmBack = false;
            getActivity().finish();
        }
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home && confirmBack) {
            confirmBack();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences =context.getSharedPreferences(UpdateValues.FORM_ID, 0);
        Log.d("hgdhgh",""+sharedPreferences.getString("Form_ID","NA"));
        if(sharedPreferences.getString("Form_ID","NA").equalsIgnoreCase("NA"))
        {
            Toast.makeText(context, "Please Fill The Personal detail Form First !!!", Toast.LENGTH_SHORT).show();
            return ;
        }
    }

    /*@Override
        public void onBackPressed(){

            confirmBack();
        }
    */
    @Override
    public void onPause() {
        super.onPause();
        dismissDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        dismissDialog();
    }

    // SAVING AND RESTORING THE STATE

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Saving title field
        if(titleEditText != null) {
            savedInstanceState.putString(STATE_TITLE, titleEditText.getText().toString());
        }

        // Saving description field
        if(descriptionEditText != null) {
            savedInstanceState.putString(STATE_DESCRIPTION, descriptionEditText.getText().toString());
        }

        // Saving time field
        if(time != null) {
            savedInstanceState.putInt(STATE_TIME_HOUR, time.first);
            savedInstanceState.putInt(STATE_TIME_MINUTES, time.second);
        }

        // Saving week days field
        if(weekDays != null) {
            savedInstanceState.putBooleanArray(STATE_WEEK_DAYS, weekDays);
        }

        // The call to super method must be at the end here
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void on_postion_completed(int poss) {
        Log.d("poss",""+poss);

        this.poss=poss;
/*
        String str_sname,str_b_name,str_mark,str_duration,str_pass_year;
        str_sname=id_edt_s_name.getText().toString();str_b_name=id_edt_b_name.getText().toString();str_mark=id_mark_percent.getText().toString();
        str_duration=id_edt_duration.getText().toString();str_pass_year=id_edt_pass_year.getText().toString();

        HashMap<String,String> hashMap=new HashMap<String,String>();
        hashMap.put("str_sname",str_sname);  hashMap.put("str_b_name",str_b_name);  hashMap.put("str_mark",str_mark);
        hashMap.put("str_duration",str_duration);  hashMap.put("str_pass_year",str_pass_year);
        String str_pos= String.valueOf(this.poss);
        Log.d("podssss",""+str_sname+"  "+str_b_name);*/
        //   gens.put(str_pos,hashMap);

        setAll_Form(poss);
        Log.d("podssss",""+hashMap.get("str_sname")+"  "+poss);
    }

    private void setAll_Form(int poss) {
        Gson gson = new Gson();
        java.lang.reflect.Type types;
        types = new TypeToken<HashMap<String,String>>() {}.getType();
        Map<String,HashMap<String,String>> f_map=new HashMap<String,HashMap<String,String>>();
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.EDU_FORM,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String str_hs_map_list,str_Inter_map_list,str_dip_map_list,str_grad_map_list;
        switch (poss)
        {
            case 0:

                break;
            case 1:
              //  gens.put("High_School",hashMap);
                Log.d("High_School",""+hashMap.get("str_b_name"));

                str_hs_map_list = gson.toJson(hashMap, types);
                editor.putString("High_School",str_hs_map_list);
                editor.commit();
                break;
            case 2:
             //   gens.put("Inter",hashMap);
                Log.d("Inter",""+hashMap.get("str_b_name"));// it will give o position element if reverse it will give current postion value

                str_Inter_map_list = gson.toJson(hashMap, types);
                editor.putString("Inter",str_Inter_map_list);
                editor.commit();
                break;
            case 3:
                gens.put("ITI/Diploma",hashMap);
                Log.d("ITI/Diploma",""+hashMap.get("str_b_name"));

                str_dip_map_list = gson.toJson(hashMap, types);
                editor.putString("ITI/Diploma",str_dip_map_list);
                editor.commit();
                break;
            case 4:
                gens.put("Graduate",hashMap);
                Log.d("Graduate",""+hashMap.get("str_b_name"));

                str_grad_map_list = gson.toJson(hashMap, types);
                editor.putString("Graduate",str_grad_map_list);
                editor.commit();
                break;
        }
    }

    @Override
    public void on_form() {
        dismissDialog();
        JSONObject jsonObject=new JSONObject();
        Log.d("form_success","success");
        Pager_Change_listener pager_change_listener=(Pager_Change_listener)Education_Form_Fragment.this.getActivity();
        pager_change_listener.on_pager_change(1, jsonObject.toString());
    }

}
