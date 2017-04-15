package com.musaeda.presentation.view.contactpicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.domain.interactor.GetAllContactsUseCase;
import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;
import java.util.List;
import javax.inject.Inject;

public class ContactsPickerPresenter extends ActivityLightCycleDispatcher<ContactsPickerCallback> {

  private ContactsPickerCallback host;
  private GetAllContactsUseCase getAllContactsUseCase;

  @Inject ContactsPickerPresenter(GetAllContactsUseCase getAllContactsUseCase) {
    this.getAllContactsUseCase = getAllContactsUseCase;
  }

  @Override public void onCreate(ContactsPickerCallback host, @Nullable Bundle bundle) {
    super.onCreate(host, bundle);
    this.host = host;
    getAllContactsUseCase.execute(o -> {
      List<ContactEntity> entities = (List<ContactEntity>) o;
      Log.e("APPPP", String.valueOf(entities));
    });
  }
}
