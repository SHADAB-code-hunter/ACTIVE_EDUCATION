package utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import callbacks.Gallery_list_listener;
import callbacks.Get_State_listener;
import pojo.Gallery_Model;


/**
 * Created by GT on 5/31/2017.
 */

public class Get_Gellary_List {
    public Gallery_list_listener get_gallery_listener;
    public String url;

    public Get_Gellary_List(Gallery_list_listener get_gallery_listener,Context context,String url) {
        this.get_gallery_listener=get_gallery_listener;
        this.url=url;
        get_galllery_list(context,url);
    }
    public interface Gallery_list_listener {
        public void onListLoaded(ArrayList<Gallery_Model> listgallery);

    }
    public  void get_galllery_list(final Context activity, String getList)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,getList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("quiz_banner_responce",""+str_response);
                            ArrayList<Gallery_Model> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("data"))
                            {
                                JSONArray array = response.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Gallery_Model sendDateModel = new Gallery_Model();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setImage_name(json.getString("image_name"));
                                    sendDateModel.setImage_flag(json.getString("image_flag"));
                                    sendDateModel.setAdded_date(json.getString("added_date"));
                                 //   sendDateModel.setEdit_date(json.getString("edit_date"));
                                    sendDateModel.setVideo_flag(json.getString("video_flag"));
                                    sendDateModel.setVideo_link(json.getString("video_link"));

                                    Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(sendDateModel);
                                }
                                get_gallery_listener.onListLoaded(arrayList);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                              //  ConnectionCheck.unAuth_prob(activity,response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);

    }

    public static void get_dialog_list(final Filter_Dialog dialog, String getList, final String array_type)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,getList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("quiz_banner_responce",""+str_response);
                            ArrayList<Common_Pojo> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has(array_type))
                            {
                                JSONArray array = response.getJSONArray(array_type);

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));

                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(sendDateModel);
                                }
                                ((Get_State_listener)dialog).onListLoaded(arrayList);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                //  ConnectionCheck.unAuth_prob(activity,response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(dialog.getContext());
        requestQueue.add(stringRequest);

    }
    public static void get_city_list(final Filter_Dialog dialog, String getList, final String array_type)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,getList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("city",""+str_response);
                            ArrayList<Common_Pojo> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has(array_type))
                            {
                                JSONArray array = response.getJSONArray(array_type);

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));

                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(sendDateModel);
                                }
                                ((Get_State_listener)dialog).onCity_ListLoaded(arrayList);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                //  ConnectionCheck.unAuth_prob(activity,response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(dialog.getContext());
        requestQueue.add(stringRequest);

    }

}
