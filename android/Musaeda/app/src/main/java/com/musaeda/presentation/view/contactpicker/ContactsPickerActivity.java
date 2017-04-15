package com.musaeda.presentation.view.contactpicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import com.musaeda.R;
import com.musaeda.presentation.di.components.ApplicationComponent;
import com.musaeda.presentation.view.BaseActivity;
import com.soundcloud.lightcycle.*;
import javax.inject.Inject;

public class ContactsPickerActivity extends BaseActivity<ContactsPickerActivity>
    implements LightCycleDispatcher<ActivityLightCycle<ContactsPickerActivity>>,
    ContactsPickerCallback {

  @Inject @LightCycle ContactsPickerPresenter presenter;

  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    //
    // // use this setting to improve performance if you know that changes
    // // in content do not change the layout size of the RecyclerView
    // mRecyclerView.setHasFixedSize(true);
    //
    // // use a linear layout manager
    // mLayoutManager = new LinearLayoutManager(this);
    // mRecyclerView.setLayoutManager(mLayoutManager);
    //
    // // specify an adapter (see also next example)
    // // mAdapter = new ContactsPickerAdapter(myDataset);
    // mRecyclerView.setAdapter(mAdapter);
  }

  @Override protected void setContentView() {
    setContentView(R.layout.activity_contacts_picker);
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
}
