package com.musaeda.presentation.di.components;

import com.musaeda.presentation.di.modules.ApplicationModule;
import com.musaeda.presentation.di.modules.NetworkModule;
import com.musaeda.presentation.view.contactpicker.ContactsPickerActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    NetworkModule.class,
})
public interface ApplicationComponent {
  void inject(ContactsPickerActivity contactsPickerActivity);
}
