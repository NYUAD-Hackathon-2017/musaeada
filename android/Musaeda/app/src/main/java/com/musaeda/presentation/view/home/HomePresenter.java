package com.musaeda.presentation.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.musaeda.domain.database.ContactsHolder;
import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;
import javax.inject.Inject;

public class HomePresenter extends ActivityLightCycleDispatcher<HomeCallback> {

  private HomeCallback callback;
  private ContactsHolder contactsHolder;

  @Inject HomePresenter(ContactsHolder contactsHolder) {
    this.contactsHolder = contactsHolder;
  }

  @Override public void onCreate(HomeCallback callback, @Nullable Bundle bundle) {
    super.onCreate(callback, bundle);
    this.callback = callback;
    callback.setUpRecyclerView();
    loadMyContacts();
  }

  private void loadMyContacts() {
    callback.setAdapter(contactsHolder.getContacts());
  }
}
