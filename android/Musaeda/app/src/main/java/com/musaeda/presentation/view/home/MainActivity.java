package com.musaeda.presentation.view.home;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.*;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.musaeda.R;
import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.presentation.di.components.ApplicationComponent;
import com.musaeda.presentation.view.BaseActivity;
import com.musaeda.presentation.view.home.adapter.MyContactsAdapter;
import com.soundcloud.lightcycle.*;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainActivity>
    implements LightCycleDispatcher<ActivityLightCycle<MainActivity>>,
    HomeCallback {

  @Inject @LightCycle HomePresenter presenter;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.my_contacts_list) RecyclerView recyclerView;
  private BottomSheetBehavior behavior;
  private Button mbtn1, mbtn2, mbtn3;
  private final static String NUMBER = "+15876002133";
  private final static String TAG = "SMSSendingActivity";
  private Context mContext = this;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View bottomSheet = findViewById(R.id.bottom_sheet);
    behavior = BottomSheetBehavior.from(bottomSheet);

    Button button = (Button) findViewById(R.id.broadcast_button);
    button.setOnClickListener(view -> {
      if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      } else {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
      }
    });
    mbtn1 = (Button) findViewById(R.id.btn_1);
    mbtn2 = (Button) findViewById(R.id.btn_2);
    mbtn3 = (Button) findViewById(R.id.btn_3);

    ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.SEND_SMS},1);

    mbtn1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        SmsManager.getDefault().sendTextMessage(NUMBER,null,"SEND I need Help. \n My GPS location (Longitude: 24.522491900000002, \nLatitude: 54.4355024)",null, null);
        Log.d(TAG,"I need Help");
        Toast.makeText(mContext, "Sending the Message",Toast.LENGTH_SHORT).show();

      }
    });

    mbtn2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        SmsManager.getDefault().sendTextMessage(NUMBER,null,"SEND I'm Safe",null, null);
        Log.d(TAG,"I am safe");
        Toast.makeText(mContext, "Sending the Message",Toast.LENGTH_SHORT).show();
      }
    });

    mbtn3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        SmsManager.getDefault().sendTextMessage(NUMBER,null,"SEND Call me",null, null);
        Log.d(TAG,"Call me");
        Toast.makeText(mContext, "Sending the Message",Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override protected void setContentView() {
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(false);
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
    MyContactsAdapter adapter = new MyContactsAdapter(entities);
    adapter.setListener(position -> {

    });
    recyclerView.setAdapter(adapter);
  }
}