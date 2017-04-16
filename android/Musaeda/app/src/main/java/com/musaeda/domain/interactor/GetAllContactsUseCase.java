package com.musaeda.domain.interactor;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import com.musaeda.domain.entity.ContactEntity;
import java.util.List;
import javax.inject.Inject;
import rx.AsyncEmitter;
import rx.Observable;
import rx.functions.Action1;

import static android.provider.ContactsContract.CommonDataKinds.Phone;
import static android.provider.ContactsContract.Contacts;

public class GetAllContactsUseCase extends UseCase {

  private ContentResolver resolver;

  @Inject
  public GetAllContactsUseCase(ContentResolver resolver) {
    this.resolver = resolver;
  }

  @Override protected Observable buildUseCase() {
    return getContactsList().flatMap(Observable::from).distinct(ContactEntity::getName).toList();
  }

  private Observable<List<ContactEntity>> getContactsList() {
    return Observable.fromAsync((Action1<AsyncEmitter<ContactEntity>>) emitter -> {
      Uri uri = Phone.CONTENT_URI;
      String selection = Contacts.HAS_PHONE_NUMBER;
      Cursor cursor = resolver.query(uri, new String[] {
          Phone.NUMBER,
          Phone.DISPLAY_NAME,
          Contacts.LOOKUP_KEY,
          Contacts.PHOTO_THUMBNAIL_URI,
          Contacts._ID
      }, selection, null, Phone.DISPLAY_NAME + " ASC");

      try {
        while (cursor.moveToNext()) {
          ContactEntity entity = new ContactEntity();
          int id = cursor.getColumnIndex(Contacts._ID);
          entity.setId(id);
          entity.setNumber(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
          entity.setName(cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME)));
          String imageUri = cursor.getString(cursor.getColumnIndex(Contacts.PHOTO_THUMBNAIL_URI));
          if (imageUri != null) entity.setPhotoUri(Uri.parse(imageUri));
          emitter.onNext(entity);
        }
      } catch (Exception e) {
        emitter.onError(e);
      } finally {
        if (cursor != null) cursor.close();
        emitter.onCompleted();
      }
      if (cursor != null) cursor.close();
    }, AsyncEmitter.BackpressureMode.BUFFER).toList();
  }
}
