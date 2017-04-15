package com.musaeda.presentation.view.contactpicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.domain.interactor.GetAllContactsUseCase;
import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;
import java.util.List;
import javax.inject.Inject;

public class ContactsPickerPresenter extends ActivityLightCycleDispatcher<ContactsPickerCallback> {

  private ContactsPickerCallback callback;
  private GetAllContactsUseCase getAllContactsUseCase;

  @Inject ContactsPickerPresenter(GetAllContactsUseCase getAllContactsUseCase) {
    this.getAllContactsUseCase = getAllContactsUseCase;
  }

  @Override public void onCreate(ContactsPickerCallback callback, @Nullable Bundle bundle) {
    super.onCreate(callback, bundle);
    this.callback = callback;
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
