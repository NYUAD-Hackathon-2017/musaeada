package com.musaeda.domain.entity;

import android.graphics.Bitmap;

public class ContactEntity {

  private int id;
  private String name;
  private String number;
  private String photoUri;
  private Bitmap photo;

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

  public String getPhotoUri() {
    return photoUri;
  }

  public void setPhotoUri(String photoUri) {
    this.photoUri = photoUri;
  }

  public Bitmap getPhoto() {
    return photo;
  }

  public void setPhoto(Bitmap photo) {
    this.photo = photo;
  }
}