package com.gt.active_education;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;

import callbacks.Attach_Listener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utilities.MyApplication;

public class SampleFragment extends Fragment implements View.OnClickListener {

    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    private Context context;
    private Button btnNegative;
    private String str_poss;
    private String str_mobile;
    private SharedPreferences sharedPreferences;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;}

    public SampleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);

      //  linearLayout = (LinearLayout) view.findViewById(R.id.id_linear_success);
     /*   btnNegative = (Button) view.findViewById(R.id.btnNegative);
        btnNegative.setOnClickListener(this);*/

        sharedPreferences= MyApplication.getAppContext().getSharedPreferences("Dialog_Callback",0);
        str_poss=sharedPreferences.getString("Position_School","NA");
        str_mobile=sharedPreferences.getString("mobile","NA");

        checkPermissionsAndOpenFilePicker();

        return view;
    }

    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }

    private void showError() {
        Toast.makeText(getContext(), "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker();
                } else {
                    showError();
                }
            }
        }
    }

    private void openFilePicker() {
        new MaterialFilePicker()
                .withSupportFragment(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            if (path != null) {
                Log.d("Path (fragment): ", ""+path);
                Log.d("string_path : ", "name_file");
                Toast.makeText(getContext(), "Picked file in fragment: " + path, Toast.LENGTH_LONG).show();
             /* progressDialog = new ProgressDialog(SampleFragment.this.getContext());
                progressDialog.setCancelable(true);
                progressDialog.show();
                progressDialog.setMessage("Linking ....");*/
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                        File f=new File(path);
                        String content_type=getMimeType(path);

                        String file_path=f.getAbsolutePath();
                        OkHttpClient client=new OkHttpClient();
                        RequestBody file_body=RequestBody.create(MediaType.parse(content_type),f);

                        Log.d("stage","stage 1");
                        Log.d("stage_image",str_poss);
                        RequestBody requestBody=new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                              //  .addFormDataPart("type",content_type)
                                .addFormDataPart("User_Key",str_mobile)
                                .addFormDataPart("File_Key",str_poss)
                                .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1),file_body)
                                .build();
                        Log.d("stage","stage 2");

                        Request request=new Request.Builder()
                                .url("http://activeeduindia.com/admin/webservices/checkSingleFileUpload.php")
                                .post(requestBody)
                                .build();

                            Log.d("stage","stage 3");
                          //  linearLayout.setVisibility(View.VISIBLE);

                            Response response=client.newCall(request).execute();
                            Log.d("stage","stage 3.5 : "+response.toString() );
                            if(!response.isSuccessful())
                            { Log.d("stage","stage 4");

                                throw new IOException("Error :  "+response);
                            }else {
                               // progressDialog.cancel();
                            }

                        }catch (Exception e)
                        {
                            Log.d("stage","stage 5"+"  "+ e.getMessage()+"  "+ e.toString());

                            Log.d("excedeption"," cdc"+e.toString());
                        }
                        Log.d("ferdrd"," cbbbbbbbbbbndc");
                        }


                });
                t.start();

            }
        }
    }

    private String getMimeType(String path) {

        String extention= MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extention);
    }

    @Override
    public void onClick(View v) {
       // ((Attach_Listener)context).onAttach();
    }
}
