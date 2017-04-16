package com.musaeda.domain.database;

import com.musaeda.domain.entity.ContactEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

// Just cached. There is no time for databases in hackathons.
@Singleton
public class ContactsHolder {

  private List<ContactEntity> contacts = new ArrayList<>();

  @Inject
  public ContactsHolder() {
  }

  public void addContact(ContactEntity entity) {
    contacts.add(entity);
  }

  public List<ContactEntity> getContacts() {
    return contacts;
  }

  public void setContacts(List<ContactEntity> contacts) {
    this.contacts = contacts;
  }

  public void clear() {
    contacts.clear();
  }
}
