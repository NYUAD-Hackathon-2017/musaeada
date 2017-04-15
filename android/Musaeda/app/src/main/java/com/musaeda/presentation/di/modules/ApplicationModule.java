package com.musaeda.presentation.di.modules;

import android.content.ContentResolver;
import android.content.Context;
import com.musaeda.presentation.MusaedaApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {
  private final MusaedaApplication application;

  public ApplicationModule(MusaedaApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ContentResolver provideContentResolver() {
    return this.application.getContentResolver();
  }
}
