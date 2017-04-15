package com.musaeda.presentation.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.musaeda.R;
import com.musaeda.presentation.view.contactpicker.ContactsPickerActivity;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    startActivity(new Intent(this, ContactsPickerActivity.class));
    finish();
  }
}