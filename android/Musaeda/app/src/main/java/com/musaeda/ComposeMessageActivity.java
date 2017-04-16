package com.musaeda;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMSSendingActivity extends AppCompatActivity {

  private Button mbtn1, mbtn2, mbtn3;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_smssending);

    mbtn1 = (Button) findViewById(R.id.btn_1);
    mbtn2 = (Button) findViewById(R.id.btn_2);
    mbtn3 = (Button) findViewById(R.id.btn_3);

    mbtn1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });

    mbtn2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {


      }
    });

    mbtn3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });
  }

  public class onBtnClickHttpPost extends AsyncTask<String, String, String> {

    @Override protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override protected String doInBackground(String... params) {
      String urlString = "";

      InputStream in = null;
      try {

        URL url = new URL(urlString);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        in = new BufferedInputStream(urlConnection.getInputStream());

      } catch (Exception e) {

        e.printStackTrace();

        return e.getMessage();
      }

      return null;
    }


  }
}
