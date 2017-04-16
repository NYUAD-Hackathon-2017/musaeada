package com.musaeda.presentation.view.home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.*;
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

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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