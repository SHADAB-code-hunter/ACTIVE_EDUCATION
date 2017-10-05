package com.gt.active_education;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by GT on 9/23/2017.
 */

public class PDFFromServerActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_download);
        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "pdf");
        folder.mkdir();
        File file = new File(folder, "pdf-sample.pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        Downloader.DownloadFile("http://activeeduindia.com/admin/media/college/brochure/pdf-sample.pdf", file);

      //  showPdf();
    }
    public void showPdf()
    {
        File file = new File(Environment.getExternalStorageDirectory()+"/pdf/pdf-sample.pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }

    public static class Downloader {


        public static void DownloadFile(String fileURL, File directory) {
            try {

                FileOutputStream f = new FileOutputStream(directory);
                URL u = new URL(fileURL);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                InputStream in = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                    Log.d("hdhdh","gfgfg");
                }
                f.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}