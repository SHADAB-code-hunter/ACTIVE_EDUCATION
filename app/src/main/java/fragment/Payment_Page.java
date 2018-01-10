package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.Agent_login_Activity;
import com.gt.active_education.Login_Fior_Guest_Activity;
import com.gt.active_education.PayU_Money_Activity;
import com.gt.active_education.R;
import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.payUMoney.sdk.SdkConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import task.Asynch_Obj;
import utilities.NEW_ASYNCH;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static utilities.App_Static_Method.get_session_type;
import static utilities.App_Static_Method.is_session_exist;
import static utilities.App_Static_Method.progressDialog;
import static utilities.App_Static_Method.session_type;
import static utilities.App_Static_Method.toMap;
import static utilities.UpdateValues.ADDMISSION;

/**
 * Created by GT on 8/25/2017.
 */

public class Payment_Page extends Fragment implements View.OnClickListener {

    EditText amt = null;
    Button pay = null;
    Activity context;
    public static final String TAG = "PayUMoneySDK Sample";
    private Bundle bundle;
    private JSONObject set_obj;
    private TextView id_term_c;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_my, container, false);
        id_term_c=(TextView)rootView.findViewById(R.id.id_term_c);
        amt = (EditText)rootView. findViewById(R.id.amount);
        pay = (Button) rootView.findViewById(R.id.pay_btn);pay.setOnClickListener(this);


        try {
            amt.setText((new JSONObject(getArguments().getString(ADDMISSION)).getString("branch_fee")));
            if(getArguments()!=null) {

                new NEW_ASYNCH(new NEW_ASYNCH.JOBJ_LISTENER() {
                    @Override
                    public void on_listener(JSONObject jsonobject, String loginApi) {
                        //  userBokingListener.on_dialog__listener(jsonobject);
                        try {
                            id_term_c.setText(jsonobject.getJSONArray("data").getJSONObject(0).getString("name"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, toMap(new JSONObject(getArguments().getString(ADDMISSION))), UrlEndpoints.MAIN_URL_school_TERM, "TERM").execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private double getAmount() {


        Double amount = 10.0;

        if (isDouble(amt.getText().toString())) {
            amount = Double.parseDouble(amt.getText().toString());
            return amount;
        } else {
            Toast.makeText(getContext(), "Paying Default Amount ₹10", Toast.LENGTH_LONG).show();
            return amount;
        }
    }
    public void makePayment() {

        String phone = "8882434664";
        String productName = "product_name";
        String firstName = "piyush";
        String txnId = "0nf7" + System.currentTimeMillis();
        String email="piyush.jain@payu.in";
        String sUrl = "https://test.payumoney.com/mobileapp/payumoney/success.php";
        String fUrl = "https://test.payumoney.com/mobileapp/payumoney/failure.php";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        boolean isDebug = true;
        String key = "dRQuiA";
        String merchantId = "4928174" ;

        PayUmoneySdkInitilizer.PaymentParam.Builder builder = new PayUmoneySdkInitilizer.PaymentParam.Builder();


        builder.setAmount(getAmount())
                .setTnxId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(sUrl)
                .setfUrl(fUrl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(isDebug)
                .setKey(key)
                .setMerchantId(merchantId);

        PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();

//             server side call required to calculate hash with the help of <salt>
//             <salt> is already shared along with merchant <key>
     /*        serverCalculatedHash =sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|<salt>)

             (e.g.)

             sha512(FCstqb|0nf7|10.0|product_name|piyush|piyush.jain@payu.in||||||MBgjYaFG)

             9f1ce50ba8995e970a23c33e665a990e648df8de3baf64a33e19815acd402275617a16041e421cfa10b7532369f5f12725c7fcf69e8d10da64c59087008590fc
*/

        // Recommended
        calculateServerSideHashAndInitiatePayment(paymentParam);

//        testing purpose

       /* String salt = "";
        String serverCalculatedHash=hashCal(key+"|"+txnId+"|"+getAmount()+"|"+productName+"|"
                +firstName+"|"+email+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"|"+salt);

        paymentParam.setMerchantHash(serverCalculatedHash);

        PayUmoneySdkInitilizer.startPaymentActivityForResult(MyActivity.this, paymentParam);*/

    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    private void calculateServerSideHashAndInitiatePayment(final PayUmoneySdkInitilizer.PaymentParam paymentParam) {

        // Replace your server side hash generator API URL
        String url = "https://test.payumoney.com/payment/op/calculateHashForTest";

        Toast.makeText(getContext(), "Please wait... Generating hash from server ... ", Toast.LENGTH_LONG).show();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has(SdkConstants.STATUS)) {
                        String status = jsonObject.optString(SdkConstants.STATUS);
                        if (status != null || status.equals("1")) {

                            String hash = jsonObject.getString(SdkConstants.RESULT);
                            Log.i("app_activity", "Server calculated Hash :  " + hash);

                            paymentParam.setMerchantHash(hash);

                            PayUmoneySdkInitilizer.startPaymentActivityForResult(Payment_Page.this.getActivity(), paymentParam);
                        } else {
                            Toast.makeText(Payment_Page.this.getActivity(),
                                    jsonObject.getString(SdkConstants.RESULT),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    Toast.makeText(Payment_Page.this.getActivity(),
                            Payment_Page.this.getActivity().getString(R.string.connect_to_internet),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Payment_Page.this.getActivity(),
                            error.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paymentParam.getParams();
            }
        };
        Volley.newRequestQueue(Payment_Page.this.getActivity()).add(jsonObjectRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {

            /*if(data != null && data.hasExtra("result")){
              String responsePayUmoney = data.getStringExtra("result");
                if(SdkHelper.checkForValidString(responsePayUmoney))
                    showDialogMessage(responsePayUmoney);
            } else {
                showDialogMessage("Unable to get Status of Payment");
            }*/


            if (resultCode == RESULT_OK) {
                Log.i(TAG, "Success - Payment ID : " + data.getStringExtra(SdkConstants.PAYMENT_ID));
                String paymentId = data.getStringExtra(SdkConstants.PAYMENT_ID);
                showDialogMessage("Payment Success Id : " + paymentId);
            } else if (resultCode == RESULT_CANCELED) {
                Log.i(TAG, "failure");
                showDialogMessage("cancelled");
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
                Log.i("app_activity", "failure");

                if (data != null) {
                    if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {

                    } else {
                        showDialogMessage("failure");
                    }
                }
                //Write your code if there's no result
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
                Log.i(TAG, "User returned without login");
                showDialogMessage("User returned without login");
            }
        }
    }

    private void showDialogMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Payment_Page.this.getActivity());
        builder.setTitle(TAG);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.pay_btn:
            // if(get_session_type().equals())
                JSONObject jsonObject=null;
                try {
                   jsonObject= new JSONObject(get_session_type());
                 //   Toast.makeText(context, ""+jsonObject.getString("type"), Toast.LENGTH_SHORT).show();
                    switch (jsonObject.getString("type"))
                    {

                        case "guest":
                            fake_apyment(jsonObject);
                          //  startActivity(new Intent(Payment_Page.this.getContext(), Login_Fior_Guest_Activity.class));
                            break;
                         case "user":
                             fake_apyment(jsonObject);
                           //  startActivity(new Intent(Payment_Page.this.getContext(), Login_Fior_Guest_Activity.class));
                            break;
                         case "agent":
                             fake_apyment(jsonObject);
                           //  startActivity(new Intent(Payment_Page.this.getContext(), Login_Fior_Guest_Activity.class));
                            break;

                    }


               Log.d("sessionrtpe",""+get_session_type());
                } catch (Exception e) {
                    e.printStackTrace();
                }
             break;
        }
    }

    private void fake_apyment(JSONObject jsonObject) {

        SharedPreferences sharedPreferences =context.getSharedPreferences(UpdateValues.FORM_ID, 0);
        Log.d("ojf_jn",""+jsonObject);
        try {

            jsonObject.put("formid",""+sharedPreferences.getString("Form_ID","NA"));
            jsonObject.put("price",(new JSONObject(getArguments().getString(ADDMISSION)).getString("branch_fee")));
            Log.d("ojf_jn",""+jsonObject);
            if(!jsonObject.getString("formid").equalsIgnoreCase("NA"))
            {
                new Asynch_Obj(new Asynch_Obj.OBJ_Lister() {
                    @Override
                    public void on_lis_obj(JSONObject jsonObject, String str_key) {
                        Log.d("jsonObject", "" + jsonObject.toString());
                       /* price:100*/
                        try {
                            if(jsonObject.getInt("msg")==1) {
                                SharedPreferences sharedPreferences = context.getSharedPreferences(UpdateValues.FORM_ID, 0);
                                SharedPreferences.Editor editor= sharedPreferences.edit();
                                editor.putString("Form_ID","NA");
                                editor.commit();

                                context.finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, UrlEndpoints.SEAT_USER_PAYEMNT, toMap(jsonObject), "payment").execute();
            }else {
                context.finish();
               // Toast.makeText(context, "Please Fill The Admission Detail First ", Toast.LENGTH_SHORT).show();
            }
            context.finish();
        } catch (Exception e) {
            Log.d("Eceio",""+e.getMessage());
        }

    }
}
