package com.musaeda.presentation;

import android.app.Application;
import com.musaeda.presentation.di.components.ApplicationComponent;
import com.musaeda.presentation.di.components.DaggerApplicationComponent;
import com.musaeda.presentation.di.modules.ApplicationModule;

public class MusaedaApplication extends Application {
  private static MusaedaApplication application;
  private ApplicationComponent applicationComponent;

  public static MusaedaApplication getInstance() {
    return application;
  }

  @Override public void onCreate() {
    super.onCreate();
    application = this;
    initializeInjector();
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }
}
