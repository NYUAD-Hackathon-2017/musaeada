package com.musaeda.presentation.di.modules;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module public class NetworkModule {
  @Provides @Singleton OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

  // @Provides @Singleton BitcoinService provideBitcoinService(OkHttpClient okHttpClient) {
  //   Retrofit retrofit = new Retrofit.Builder()
  //       .baseUrl("https://blockchain.info/")
  //       .addConverterFactory(GsonConverterFactory.create())
  //       .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
  //       .client(okHttpClient)
  //       .build();
  //   return retrofit.create(BitcoinService.class);
  // }
}
