package com.musaeda.domain.interactor;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import com.musaeda.domain.entity.ContactEntity;
import java.io.*;
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
    return getContactsList().flatMap(Observable::from).map(this::loadContactImage).toList();
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
          int lookup = cursor.getColumnIndex(Contacts.LOOKUP_KEY);
          Uri lookupUri = Contacts.getLookupUri(
              cursor.getLong(id),
              cursor.getString(lookup)
          );
          entity.setId(id);
          entity.setNumber(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
          entity.setName(cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME)));
          entity.setPhotoUri(cursor.getString(cursor.getColumnIndex(Contacts.PHOTO_THUMBNAIL_URI)));
          entity.setPhoto(MediaStore.Images.Media.getBitmap(resolver, lookupUri));
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

  private ContactEntity loadContactImage(ContactEntity entity) {
    if (entity.getName().contains("Naomi")) {
      Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, entity.getId());
      InputStream stream = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
      entity.setPhoto(BitmapFactory.decodeStream(stream));
    }
    return entity;
  }

  private Bitmap loadContactPhotoThumbnail(String photoData) {
    // Creates an asset file descriptor for the thumbnail file.
    AssetFileDescriptor afd = null;
    // try-catch block for file not found
    try {
      // Creates a holder for the URI.
      Uri thumbUri;
      // If Android 3.0 or later
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        // Sets the URI from the incoming PHOTO_THUMBNAIL_URI
        thumbUri = Uri.parse(photoData);
      } else {
        // Prior to Android 3.0, constructs a photo Uri using _ID
                /*
                 * Creates a contact URI from the Contacts content URI
                 * incoming photoData (_ID)
                 */
        final Uri contactUri = Uri.withAppendedPath(
            Contacts.CONTENT_URI, photoData);
                /*
                 * Creates a photo URI by appending the content URI of
                 * Contacts.Photo.
                 */
        thumbUri =
            Uri.withAppendedPath(
                contactUri, Contacts.Photo.CONTENT_DIRECTORY);
      }

        /*
         * Retrieves an AssetFileDescriptor object for the thumbnail
         * URI
         * using ContentResolver.openAssetFileDescriptor
         */
      afd = resolver.openAssetFileDescriptor(thumbUri, "r");
        /*
         * Gets a file descriptor from the asset file descriptor.
         * This object can be used across processes.
         */
      FileDescriptor fileDescriptor = afd.getFileDescriptor();
      // Decode the photo file and return the result as a Bitmap
      // If the file descriptor is valid
      if (fileDescriptor != null) {
        // Decodes the bitmap
        return BitmapFactory.decodeFileDescriptor(
            fileDescriptor, null, null);
      }
      // If the file isn't found
    } catch (FileNotFoundException e) {
            /*
             * Handle file not found errors
             */
      // In all cases, close the asset file descriptor
    } finally {
      if (afd != null) {
        try {
          afd.close();
        } catch (IOException e) {}
      }
    }
    return null;
  }
}
