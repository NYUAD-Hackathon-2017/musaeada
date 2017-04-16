package com.musaeda;

import android.Manifest;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMSSendingActivity extends AppCompatActivity {

  private Button mbtn1, mbtn2, mbtn3;
  private final static String NUMBER = "+15876002133";
  private final static String TAG = "SMSSendingActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_smssending);

    mbtn1 = (Button) findViewById(R.id.btn_1);
    mbtn2 = (Button) findViewById(R.id.btn_2);
    mbtn3 = (Button) findViewById(R.id.btn_3);

    ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.SEND_SMS},1);

    mbtn1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        SmsManager.getDefault().sendTextMessage(NUMBER,null,"SEND I need Help. \n My GPS location (Longitude: 24.522491900000002, \nLatitude: 54.4355024)",null, null);
        Log.d(TAG,"I need Help");

      }
    });

    mbtn2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        SmsManager.getDefault().sendTextMessage(NUMBER,null,"SEND I'm Safe",null, null);
        Log.d(TAG,"I am safe");
      }
    });

    mbtn3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        SmsManager.getDefault().sendTextMessage(NUMBER,null,"SEND Call me",null, null);
        Log.d(TAG,"Call me");
      }
    });
  }

  //public class onBtnClickHttpPost extends AsyncTask<String, String, String> {
  //
  //  @Override protected void onPreExecute() {
  //    super.onPreExecute();
  //  }
  //
  //  @Override protected String doInBackground(String... params) {
  //    String urlString = "";
  //
  //    InputStream in = null;
  //    try {
  //
  //      URL url = new URL(urlString);
  //
  //      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
  //
  //      in = new BufferedInputStream(urlConnection.getInputStream());
  //
  //    } catch (Exception e) {
  //
  //      e.printStackTrace();
  //
  //      return e.getMessage();
  //    }
  //
  //    return null;
  //  }
  //
  //
  //}
}
