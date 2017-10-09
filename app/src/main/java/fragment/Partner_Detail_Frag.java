package fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gt.active_education.HttpFileUpload;
import com.gt.active_education.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import callbacks.Call_Dilaog_Listener;
import task.Async_Respoce;
import utilities.App_Raw_Data;
import utilities.App_Static_Method;
import utilities.FilePath;
import utilities.Image_picker;
import utilities.MyApplication;
import utilities.State_City_Search;
import utilities.UrlEndpoints;
import static android.content.Context.POWER_SERVICE;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_ADDRESS;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_CITY;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_COLLEGE_NAME;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_COLLGE_SHORT_NAME;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_EMAIL;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_ESTABLISHED;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_INFORMATION;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_PHONE;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_STATE;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_TYPE;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_WEBSITE;
import static utilities.App_Static_Method.permission_check;
import static utilities.UrlEndpoints.GET_CITY;
import static utilities.UrlEndpoints.PARTNER_DETAIL;
import static utilities.UrlEndpoints.UPLOAD_FILE;

/**
 * Created by GT on 9/27/2017.
 */

public class Partner_Detail_Frag extends Fragment implements Call_Dilaog_Listener, Image_picker.ImageAttachmentListener {

    private static final int PICK_FILE_REQUEST = 95;
    private EditText id_clg_name,id_short_clg_name,id_edt_address,id_state,id_city,id_type,id_email,id_phone,id_website,id_established_year;
    private Button btn_submit;
    private CheckBox id_term_con;
    private String str_clg_name,str_short_clg_name,str_edt_add,str_state,str_city,str_type,str_clg_email,str_clg_phone,str_clgestab_year;
    private ProgressDialog progressDialog;
    private Map<String, String> map;
    private String str_website,str_info;
    private EditText id_information;
    private State_City_Search state_city_search;
    private boolean open=false;
    private Image_picker imagePicker;
    private ImageView id_img_picker1,id_img_picker2,id_img_picker3;
    private Bitmap bitmap;
    private String file_name;
    private String str_profile_bitmap="";
    private ImageView id_img_picker4;
    private String str_file_name;
    PowerManager.WakeLock wakeLock;
    public static final String UPLOAD_URL = "http://internetfaqs.net/AndroidPdfUpload/upload.php";
    public static final String PDF_FETCH_URL = "http://internetfaqs.net/AndroidPdfUpload/getPdfs.php";
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

        View rootView = inflater.inflate(R.layout.frg_partner_detail, container, false);
        id_clg_name=(EditText)rootView.findViewById(R.id.id_clg_name);
        id_short_clg_name=(EditText)rootView.findViewById(R.id.id_short_clg_name);
        id_edt_address=(EditText)rootView.findViewById(R.id.id_edt_address);
        id_state=(EditText)rootView.findViewById(R.id.id_state);
        id_city=(EditText)rootView.findViewById(R.id.id_city);
        id_type=(EditText)rootView.findViewById(R.id.id_type);
        id_email=(EditText)rootView.findViewById(R.id.id_email);
        id_phone=(EditText)rootView.findViewById(R.id.id_phone);
        id_website=(EditText)rootView.findViewById(R.id.id_website);
        id_established_year=(EditText)rootView.findViewById(R.id.id_established_year);
        id_information=(EditText)rootView.findViewById(R.id.id_information);
        id_term_con=(CheckBox)rootView.findViewById(R.id.id_term_con);
        id_img_picker1=(ImageView)rootView.findViewById(R.id.id_images1);
        id_img_picker2=(ImageView)rootView.findViewById(R.id.id_images2);
        id_img_picker3=(ImageView)rootView.findViewById(R.id.id_images3);
        id_img_picker4=(ImageView)rootView.findViewById(R.id.id_images_brochre);
        btn_submit=(Button)rootView.findViewById(R.id.id_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("badfbba",""+"dgdgdg");
                if (id_term_con.isChecked())
                {
                    str_clg_name=id_clg_name.getText().toString();
                    str_short_clg_name=id_short_clg_name.getText().toString();
                    str_edt_add=id_edt_address.getText().toString();
                    str_state=id_state.getText().toString();
                    str_city=id_city.getText().toString();
                    str_type=id_type.getText().toString();
                    str_clg_email=id_email.getText().toString();
                    str_clg_phone=id_phone.getText().toString();
                    str_website=id_website.getText().toString();
                    str_info=id_information.getText().toString();
                    str_clgestab_year=id_established_year.getText().toString();

                    if (!App_Static_Method.isValidName(str_clg_name)) {     // fname
                        id_clg_name.setError("You must more characters");
                    } else {
                        if (!App_Static_Method.isValidName(str_short_clg_name)) {     // fname
                            id_short_clg_name.setError("You must more characters");
                        } else {
                            if (!App_Static_Method.isValidEmail(str_clg_email)) {                            // email
                                id_email.setError("Invalid Email");
                            } else {

                                if (!App_Static_Method.isValidName(str_state)) {
                                    id_state.setError("Please Enter a Valid State !!! ");
                                } else {
                                    if (!App_Static_Method.isValidName(str_city)) {
                                        id_city.setError("Please Enter a Valid State !!! ");
                                    } else {

                                        if (!App_Static_Method.isValidName(str_edt_add)) {
                                            id_edt_address.setError("Please Enter a Valid Address !!! ");
                                        } else {
                                            if (!App_Static_Method.isValidPhone(str_clg_phone)) {
                                                id_phone.setError("Please Enter a Valid Address !!! ");
                                            } else {
                                                if (!App_Static_Method.isValidName(str_clgestab_year)) {
                                                    id_established_year.setError("Please Enter a Valid Address !!! ");
                                                } else {

                                                    if (!App_Static_Method.isValidName(str_website)) {
                                                        id_website.setError("Please Enter a Valid Website !!! ");
                                                    } else {

                                                        if (!App_Static_Method.isValidName(str_info)) {
                                                            id_information.setError("Please Enter a Valid Website !!! ");
                                                        } else {
                                                            map = new HashMap<String, String>();
                                                            map.put(KEY_COLLEGE_NAME, str_clg_name);
                                                            map.put(KEY_COLLGE_SHORT_NAME, str_short_clg_name);
                                                            map.put(KEY_ADDRESS, str_edt_add);
                                                            map.put(KEY_STATE, str_state);
                                                            map.put(KEY_CITY, str_city);
                                                            map.put(KEY_TYPE, str_type);
                                                            map.put(KEY_EMAIL, str_clg_email);
                                                            map.put(KEY_PHONE, str_clg_phone);
                                                            map.put(KEY_WEBSITE, str_website);
                                                            map.put(KEY_ESTABLISHED, str_clgestab_year);
                                                            map.put(KEY_INFORMATION, str_info);

                                                         /* progressDialog = new ProgressDialog(Partner_Detail_Frag.this.getContext());
                                                            progressDialog.setCancelable(true);
                                                            progressDialog.show();
                                                            progressDialog.setMessage(getString(R.string.SignUp));*/

                                                            new Async_Respoce(new Async_Respoce.Responce_Obj_Lisatener() {
                                                                @Override
                                                                public void on_responce(JSONObject listMovies) {
                                                                    Log.d("objectggg", listMovies.toString());
                                                                }
                                                            }, PARTNER_DETAIL, map).execute();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else {

                }
            }

        });
        id_state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!open) {
                    open = true;


                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s) {
                            id_state.setText(s);id_city.setText("");
                            state_city_search.cancel();
                            open=false;
                        }
                    },Partner_Detail_Frag.this.getContext(), UrlEndpoints.get_filter_list + 1);
                    state_city_search.show();
                }
                return false;
            }
        });

        id_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    open = true;

                    if (id_state.getText().toString() != null) {
                        StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s) {
                                id_city.setText(s);
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Partner_Detail_Frag.this.getContext(), GET_CITY + stringBuilder.toString());
                        state_city_search.show();
                    } else {
                        Toast.makeText(Partner_Detail_Frag.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        permission_check(101,Partner_Detail_Frag.this);
        imagePicker =new Image_picker(Partner_Detail_Frag.this);


        id_img_picker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                    imagePicker.imagepicker(1, "10001");
                else
                    Partner_Detail_Frag.this.lower_camera_call(20001);
            }
        });

        id_img_picker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                    imagePicker.imagepicker(1,"10002");
                else
                    lower_camera_call(20002);
            }
        });

        id_img_picker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                    imagePicker.imagepicker(1, "10003");
                else
                    Partner_Detail_Frag.this.lower_camera_call(20003);
            }
        });
        id_img_picker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*if (Build.VERSION.SDK_INT >= 23)
                imagePicker.imagepicker(1,"10004");
            else
                lower_camera_call(20004);*/

                Partner_Detail_Frag.this.showFileChooser();
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void on_id_listener(String str_id) {

        open=false;
        id_state.setText(str_id);
        state_city_search.cancel();
    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("permiupsss","permission find"+requestCode+" "+permissions[0]+" "+grantResults[0]);
        if(requestCode==1) {
            Log.d("permiupsss","permission find"+requestCode+" "+permissions[0]+" "+grantResults[0]);
            imagePicker.request_permission_result(requestCode, permissions, grantResults);
        }else if(requestCode==101){
              Log.d("permisss","permission find"+requestCode+" "+permissions[0]+" "+grantResults[0]);
             *//*   //Log.d("permisss2","permission find2"+requestCode+" "+permissions[1]+" "+grantResults[1]);
                //Log.d("permisss3","permission find3"+requestCode+" "+permissions[2]+" "+grantResults[2]);*//*
            request_permission_result(requestCode, permissions, grantResults);
        }
    }

    public void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //Log.d("cou8nter","101");
        switch (requestCode)
        {
            case 101:
                if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED||grantResults[1] == PackageManager.PERMISSION_GRANTED||grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    // requestForSMS(st_mobile);
                    Log.d("sms_perm","permission granted");
                } else {
                   *//* Intent i = new Intent(getActivity(), User_Login_Activity.class);
                    i.putExtra("lg_call_page","sp_to_db");
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "sms Permission denied",Toast.LENGTH_LONG).show();
                    finish();*//*
                }
                break;
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("datapssack",data.toString()+"       bb"+requestCode );
        // programmitically set screen orientation of activity (landscape)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // programmitically set screen orientation of activity (portrait)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (oneOfEquals(20001,20002,20003,requestCode)&& resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d("datapack"," bb"+requestCode );
            switch (requestCode)
            {
                case 20001:
                    id_img_picker1.setImageBitmap(photo);

                    break;

                case 20002:
                    id_img_picker2.setImageBitmap(photo);

                    break;

                case 20003:
                    id_img_picker3.setImageBitmap(photo);

                    break;

                case 20004:
                    id_img_picker4.setImageBitmap(photo);

                    break;
            }


        }
        else if(!oneOfEquals(20001,20002,20003,requestCode) && !(requestCode == PICK_FILE_REQUEST)) {
            imagePicker.onActivityResult(requestCode, resultCode, data,""+requestCode);
        }
        else if (requestCode == PICK_FILE_REQUEST)
        {
            if (data == null) {
                //no data present
                return;
            }

         //   Bitmap pdf = (Bitmap) data.getExtras().get("data");
            PowerManager powerManager = (PowerManager) MyApplication.getAppContext(). getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "PowerLock");
            wakeLock.acquire();

            Uri selectedFileUri = data.getData();
            String selectedFilePath = FilePath.getPath(this.getContext(), selectedFileUri);
            Log.d("tedfrg", "Selected File Path:" + selectedFilePath);

            if (selectedFilePath != null && !selectedFilePath.equals("")) {
               // tvFileName.setText(selectedFilePath);
                Log.d("selected_path",selectedFilePath);
                Log.d("filename",imagePicker.getfilename_from_path(selectedFilePath));
                str_file_name=imagePicker.getfilename_from_path(selectedFilePath);
             //   Toast.makeText(Partner_Detail_Frag.this.getContext(), ""+pdf, Toast.LENGTH_SHORT).show();
              //  uploadFile(selectedFilePath);

                /* for reading pdf */
               /* Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(selectedFileUri,"application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }*/


                try {
                    String uploadId = UUID.randomUUID().toString();

                    //Creating a multi part request
                    new MultipartUploadRequest(this.getContext(), uploadId, UPLOAD_URL)
                            .addFileToUpload(selectedFilePath, "pdf") //Adding file
                            .addParameter("name", str_file_name) //Adding text parameter to the request
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload(); //Starting the upload

                } catch (Exception exc) {
                    Toast.makeText(this.getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this.getContext(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
            }

          //  File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ str_file_name);


        }
    }


    public boolean oneOfEquals(int a, int b,int c, int expected) {
        return (a == expected) || (b == expected) || (c == expected);
    }
    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String s, String str_status) {
        this.bitmap=file;
        this.file_name=filename; //Log.d("callll","call");

        //Log.d("bitmapp",file.toString());
        Log.d("urlir",uri.toString()+"  "+from+"  "+str_status);

        Log.d("rtrfrrt","dgeeedg"+filename);
        //str_profile_bitmap=s;
        String ImageAttach_folder_path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        Log.d("edegytrd","dgeeedg");
        /*BitmapProcessor bitmapProcessor = new BitmapProcessor(file, 1000, 1000, 90);
        id_image_profile_signup.setImageBitmap(bitmapProcessor.getBitmap());
        str_profile_bitmap=BitMapToString(bitmapProcessor.getBitmap());*/
        imagePicker.createImage(file,filename,ImageAttach_folder_path,false);

        switch (str_status)
        {
            case "10001":
                id_img_picker1.setImageBitmap(file);
                str_profile_bitmap=getStringImage(file);
                map.put("clg_crt1",getStringImage(file));
                break;

            case "10002":
                id_img_picker2.setImageBitmap(file);
                str_profile_bitmap=getStringImage(file);
                map.put("clg_crt2",getStringImage(file));
                break;

            case "10003":
                id_img_picker3.setImageBitmap(file);
                str_profile_bitmap=getStringImage(file);
                map.put("clg_crt3",getStringImage(file));
                break;
            case "10004":
                id_img_picker4.setImageBitmap(file);
                str_profile_bitmap=getStringImage(file);
                map.put("clg_crt3",getStringImage(file));
                break;
        }


    }
    private void lower_camera_call(int i) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, i);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("file/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }
    public void UploadFile(){
        try {
            // Set your file path here
            FileInputStream fstrm = new FileInputStream(Environment.getExternalStorageDirectory().toString()+"/DCIM/file.mp4");

            // Set your server page url (and the file title/description)
            HttpFileUpload hfu = new HttpFileUpload("http://www.myurl.com/fileup.aspx", "my file title","my file description");

            hfu.Send_Now(fstrm);

        } catch (FileNotFoundException e) {
            // Error: File not found
        }
    }
    public int uploadFile(final String selectedFilePath) {

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
          //  dialog.dismiss();

            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {*/
               /*     tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });*/

            Toast.makeText(Partner_Detail_Frag.this.getContext(), "Source File Doesn't Exist: ", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(UPLOAD_FILE);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty(
                        "Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; brochurename=\"uploaded_file\";brochure=\"" + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {

                    try {

                        //write the bytes read from inputstream
                        dataOutputStream.write(buffer, 0, bufferSize);
                    } catch (OutOfMemoryError e) {
                        Toast.makeText(Partner_Detail_Frag.this.getContext(), "Insufficient Memory!", Toast.LENGTH_SHORT).show();
                    }
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                try{
                    serverResponseCode = connection.getResponseCode();
                }catch (OutOfMemoryError e){
                    Toast.makeText(Partner_Detail_Frag.this.getContext(), "Memory Insufficient!", Toast.LENGTH_SHORT).show();
                }
                String serverResponseMessage = connection.getResponseMessage();

                Log.i("partener_server", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                   /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/" + fileName);
                      }
                    });*/

                   Log.d("serverresponce","File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/" + fileName);
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();

                if (wakeLock.isHeld()) {

                    wakeLock.release();
                }


            } catch (final FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Partner_Detail_Frag.this.getContext(), "File Not Found"+e.getMessage(), Toast.LENGTH_SHORT).show();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(Partner_Detail_Frag.this.getContext(), "URL Error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Toast.makeText(Partner_Detail_Frag.this.getContext(), "Cannot Read/Write File", Toast.LENGTH_SHORT).show();
            }
         //   dialog.dismiss();
            return serverResponseCode;
        }

    }

}
