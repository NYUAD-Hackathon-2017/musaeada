package com.musaeda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class PickActivity extends AppCompatActivity {

  private ListView mListView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pick);

    mListView = (ListView) findViewById(R.id.list_view_pick);
  }
}
