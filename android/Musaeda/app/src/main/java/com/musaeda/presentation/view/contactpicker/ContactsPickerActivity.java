package com.musaeda.presentation.view.contactpicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.musaeda.R;
import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.presentation.di.components.ApplicationComponent;
import com.musaeda.presentation.view.BaseActivity;
import com.musaeda.presentation.view.contactpicker.adapter.ContactsPickerAdapter;
import com.soundcloud.lightcycle.*;
import java.util.List;
import javax.inject.Inject;

public class ContactsPickerActivity extends BaseActivity<ContactsPickerActivity>
    implements LightCycleDispatcher<ActivityLightCycle<ContactsPickerActivity>>,
    ContactsPickerCallback {

  private Toolbar mToolbar;
  private ActionBar mActionBar;

  @Inject @LightCycle ContactsPickerPresenter presenter;

  @BindView(R.id.contacts_picker_list) RecyclerView recyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // recyclerView.setAdapter(new ContactsPickerAdapter(myDataset));

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);

    mActionBar = getSupportActionBar();
    if (mActionBar != null){
      mActionBar.setDisplayShowHomeEnabled(true);
      mActionBar.setDisplayHomeAsUpEnabled(true);
      mActionBar.setDisplayShowTitleEnabled(false);
    }
  }

  @Override protected void setContentView() {
    setContentView(R.layout.activity_contacts_picker);
    ButterKnife.bind(this);
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[] {
          Manifest.permission.READ_CONTACTS
      }, 0);
    }
  }

  @Override protected void setupDagger(ApplicationComponent appComponent) {
    appComponent.inject(this);
  }

  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }

  @Override public void showError(String message) {

  }

  @Override public void setUpRecyclerView() {
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override public void setAdapter(List<ContactEntity> entities) {
    recyclerView.setAdapter(new ContactsPickerAdapter(entities));
  }
}
