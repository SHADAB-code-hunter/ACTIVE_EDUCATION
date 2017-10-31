package com.gt.active_education;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by GT on 6/8/2017.
 */

public class Website_Activity extends AppCompatActivity {

    private String str_intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        WebView webView = (WebView) findViewById(R.id.id_webview_review);

        if(getIntent()!=null)
        {
            str_intent=getIntent().getStringExtra("Url_Web");
        }

       // Toast.makeText(this, ""+str_intent, Toast.LENGTH_SHORT).show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(str_intent);

        if(isSubstring(".pdf",str_intent)) {

            finish();
        }



/*
        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new webViewClient());
        //webView.loadUrl("http://www.cleankutz.appointy.com");
        webView.loadUrl("https://cleankutz.appointy.com/");*/

    }
    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }
}
