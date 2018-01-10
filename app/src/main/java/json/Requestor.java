package json;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import extras.Keys;
import logging.L;

import static utilities.UrlEndpoints.PARTNER_DETAIL;


/**
 * Created by Windows on 02-03-2015.
 */
public class Requestor {
    public static JSONObject requestMovies_oldJSON(RequestQueue requestQueue, String url) {

       // String fresh_string=url.replace(" \ ", "");
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        Log.d("urlbg",url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                (String)null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(50000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            L.m(e + "");
        } catch (ExecutionException e) {
            L.m(e + "");
        } catch (TimeoutException e) {
            L.m(e + "");
        }
        return response;
    }
    public static JSONObject requestMoviesJSON(RequestQueue requestQueue, String url) {
        String response = null;
        JSONObject jObj=null;
        RequestFuture<String> requestFuture = RequestFuture.newFuture();
        Log.d("urlbg",""+url);
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                requestFuture, requestFuture);
        requestQueue.add(request);
        try {
            response = requestFuture.get(5000, TimeUnit.SECONDS);
            jObj = new JSONObject(response);
        } catch (InterruptedException e) {
            L.m(e + "");
        } catch (ExecutionException e) {
            L.m(e + "");
        } catch (TimeoutException e) {
            L.m(e + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    public static JSONObject requestJSON(RequestQueue requestQueue, String url,Map<String,String> map) {
        String response = null;
        JSONObject jObj=null;
        RequestFuture<String> requestFuture = RequestFuture.newFuture();
        Log.d("urlbg",""+url);
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                requestFuture, requestFuture);
        requestQueue.add(request);
        try {
            response = requestFuture.get(5000, TimeUnit.SECONDS);
            jObj = new JSONObject(response);
        } catch (InterruptedException e) {
            L.m(e + "");
        } catch (ExecutionException e) {
            L.m(e + "");
        } catch (TimeoutException e) {
            L.m(e + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }

    public static JSONObject requestJSON_MAP(RequestQueue requestQueue, String url, final Map<String, String> map) {
        String response = null;
        JSONObject jObj=null;
        RequestFuture<String> requestFuture = RequestFuture.newFuture();
       // Log.d("urlbg",""+PARTNER_DETAIL+"   "+map.get("remaining_seats")  );
        StringRequest request = new StringRequest(Request.Method.POST, url, requestFuture, requestFuture){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return map;
            }
        };
        requestQueue.add(request);
        try {
            response = requestFuture.get(5000, TimeUnit.MILLISECONDS);
            jObj = new JSONObject(response);
        } catch (InterruptedException e) {
          //  L.m(e + "");
        } catch (ExecutionException e) {
          //  L.m(e + "");
        } catch (TimeoutException e) {
           // L.m(e + "");
        } catch (JSONException e) {
          //  e.printStackTrace();
        }
        return jObj;
    }
/*
  public static JSONObject requestJSON_MAP_header(RequestQueue requestQueue, String url, final Map<String, String> map) {
        //String response = null;
        JSONObject jObj=null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
       // Log.d("urlbg",""+PARTNER_DETAIL+"   "+map.get("remaining_seats")  );

     */
/*  request = new JsonObjectRequest(Request.Method.POST,
              url,
              (String)null, requestFuture, requestFuture);

      requestQueue.add(request);*//*


      JsonObjectRequest request = new JsonObjectRequest(url,null, requestFuture, requestFuture){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
             */
/*   String credentials = "username:password";
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);*//*

                headers.put("Content-Type", "application/json");
                headers.putAll(map);
                return headers;
            }
        };
        requestQueue.add(request);
        try {
            response = requestFuture.get(5000, TimeUnit.MILLISECONDS);
          //  jObj = new JSONObject(response);
        } catch (InterruptedException e) {
          //  L.m(e + "");
        } catch (ExecutionException e) {
          //  L.m(e + "");
        } catch (TimeoutException e) {
           // L.m(e + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jObj;
    }
*/

}
