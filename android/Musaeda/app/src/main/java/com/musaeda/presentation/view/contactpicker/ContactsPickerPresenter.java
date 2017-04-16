package com.musaeda.presentation.view.contactpicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.musaeda.domain.database.ContactsHolder;
import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.domain.interactor.GetAllContactsUseCase;
import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;
import java.util.List;
import javax.inject.Inject;

public class ContactsPickerPresenter extends ActivityLightCycleDispatcher<ContactsPickerCallback> {

  private ContactsPickerCallback callback;
  private GetAllContactsUseCase getAllContactsUseCase;
  private ContactsHolder contactsHolder;

  @Inject ContactsPickerPresenter(GetAllContactsUseCase getAllContactsUseCase, ContactsHolder contactsHolder) {
    this.getAllContactsUseCase = getAllContactsUseCase;
    this.contactsHolder = contactsHolder;
  }

  @Override public void onCreate(ContactsPickerCallback callback, @Nullable Bundle bundle) {
    super.onCreate(callback, bundle);
    this.callback = callback;
    contactsHolder.clear();
    callback.setUpRecyclerView();
    loadAllContacts();
  }

  private void loadAllContacts() {
    getAllContactsUseCase.execute(
        o -> callback.setAdapter((List<ContactEntity>) o),
        throwable -> {
          callback.showError(throwable.getMessage());
          callback.hideLoading();
        },
        () -> callback.hideLoading());
  }
}
