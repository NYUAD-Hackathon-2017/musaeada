package com.musaeda.presentation.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.musaeda.R;
import com.musaeda.presentation.view.contactpicker.ContactsPickerActivity;

public class MainActivity extends AppCompatActivity {

  private Toolbar mToolbar;
  private ActionBar mActionBar;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);

    mActionBar = getSupportActionBar();
    if (mActionBar != null){
      mActionBar.setDisplayShowHomeEnabled(true);
      mActionBar.setDisplayHomeAsUpEnabled(true);
      mActionBar.setDisplayShowTitleEnabled(false);
    }

    startActivity(new Intent(this, ContactsPickerActivity.class));
  }
}