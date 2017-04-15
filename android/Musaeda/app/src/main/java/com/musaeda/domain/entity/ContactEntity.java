package com.musaeda.domain.entity;

import android.net.Uri;

public class ContactEntity {

  private int id;
  private String name;
  private String number;
  private Uri photoUri;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Uri getPhotoUri() {
    return photoUri;
  }

  public void setPhotoUri(Uri photoUri) {
    this.photoUri = photoUri;
  }
}
