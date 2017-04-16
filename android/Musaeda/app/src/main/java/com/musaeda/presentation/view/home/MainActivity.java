package com.musaeda.presentation.view.home;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.*;
import android.view.View;
import android.widget.Button;
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

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View bottomSheet = findViewById(R.id.bottom_sheet1);
    behavior = BottomSheetBehavior.from(bottomSheet);

    Button mButton1 = (Button) findViewById(R.id.button_1);
    mButton1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
          behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
          behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
      }
    });
    // actionButton.setOnClickListener(v -> {
    //   if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
    //     behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    //   } else {
    //     behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    //   }
    // });
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