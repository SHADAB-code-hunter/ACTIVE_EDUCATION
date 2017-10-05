package com.gt.active_education;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmPGService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by GT on 10/2/2017.
 */

public class NewMarchant_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchantapp);
        initOrderId();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    // This is to refresh the order id: Only for the Sample App’s purpose.
    @Override
    protected void onStart() {
        super.onStart();
        initOrderId();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initOrderId() {
        Random r = new Random(System.currentTimeMillis());
        String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
                + r.nextInt(10000);
       /* EditText orderIdEditText = (EditText) findViewById(R.id.order_id);
        orderIdEditText.setText(orderId);*/

    }

/*
    public void onStartTransaction(View view) throws InterruptedException, ExecutionException {
        PaytmPGService Service = PaytmPGService.getStagingService();

        Map paramMap = new HashMap();
        String mid=””,order_id="",cust_id="",callback=””,industry_type=””,txn_amount=””,checksum=””;

        Log.d("before request","some");
        JSONObject mJsonObject = null;
        String url = "http://paytmtest.azurewebsites.net/APP/generateChecksum.php";

// paramMap = AsyncRequest.getParamFromUrl(url);
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        String json =	myAsyncTask.execute(url).get();

        try {
            mJsonObject=new JSONObject(json);
        } catch (JSONException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            mid = mJsonObject.getString("MID");

            order_id=mJsonObject.getString("ORDER_ID");

            cust_id = mJsonObject.getString(“CUST_ID”);
            callback = mJsonObject.getString(“CALLBACK_URL”);
            industry_type = mJsonObject.getString(“INDUSTRY_TYPE_ID”);
            txn_amount = mJsonObject.getString(“TXN_AMOUNT”);
            checksum = mJsonObject.getString(“CHECKSUMHASH”);

        } catch (JSONException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d(“after request”, “some”);

// {
// “MID”:”AlertT58780996152701″,
// “ORDER_ID”:”ORDER0000001″,
// “CUST_ID”:”CUST00001″,
// “INDUSTRY_TYPE_ID”:”Retail”,
// “CHANNEL_ID”:”WAP”,
// “TXN_AMOUNT”:”1.00″,
// “WEBSITE”:”APP_PRODUCTION”,
// “CALLBACK_URL”:”https:\/\/pguat.paytm.com\/paytmchecksum\/paytmCallback.jsp”,
// “EMAIL”:”abc@gmail.com”,
// “MOBILE_NO”:”9999999999″,
// “CHECKSUMHASH”:”z0R4jt\/j8nnnp7JiFZSTyi+4LrG5XJTAw04sW6UDi1usV3D759YE1F5d4U7OlLf+ut0KcuNRd5KG9Deqwdrbzip9pTM\/MsxqEkNlfalmmzk=”
// }

        paramMap.put(“MID”, mid);
        paramMap.put(“ORDER_ID”, order_id);
        paramMap.put(“CUST_ID”, cust_id);
        paramMap.put(“INDUSTRY_TYPE_ID”,industry_type);
        paramMap.put(“CHANNEL_ID”, “WAP”);
        paramMap.put(“TXN_AMOUNT”,txn_amount);
        paramMap.put(“WEBSITE”, “APP_STAGING”);
        paramMap.put(“CALLBACK_URL”,callback);
        paramMap.put(“CHECKSUMHASH”,checksum);

        PaytmOrder Order = new PaytmOrder(paramMap);

*/
/*
* PaytmMerchant Merchant = new PaytmMerchant(
* “https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp”,
* “https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp”);
*//*


        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
// Some UI Error Occurred in Payment Gateway Activity.
// // This may be due to initialization of views in
// Payment Gateway Activity or may be due to //
// initialization of webview. // Error Message details
// the error occurred.
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d(“LOG”, “Payment Transaction : ” + inResponse);
                        Toast.makeText(
                                getApplicationContext(),
                                “Payment Transaction response ”
                                + inResponse.toString(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() {
// If network is not
// available, then this
// method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
// This method gets called if client authentication
// failed. // Failure may be due to following reasons //
// 1. Server error or downtime. // 2. Server unable to
// generate checksum or checksum response is not in
// proper format. // 3. Server failed to authenticate
// that client. That is value of payt_STATUS is 2. //
// Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
// TODO Auto-generated method stub
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage,
                                                    Bundle inResponse) {
                        Log.d(“LOG”, “Payment Transaction Failed ”
                                + inErrorMessage);
                        Toast.makeText(getBaseContext(),
                                “Payment Transaction Failed “,
                                Toast.LENGTH_LONG).show();
                    }

                });
    }
*/

/*
    class MyAsyncTask extends AsyncTask {

        @Override
        protected String doInBackground(String… params) {

            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpConn = null;
            try {
                httpConn = (HttpURLConnection) url.openConnection();

                int responseCode = httpConn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream is = httpConn.getInputStream();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return sb.toString();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

            } catch (Exception e) {
// TODO: handle exception
            }

            Log.d(“Response”, result);
        }

    }
*/
}
