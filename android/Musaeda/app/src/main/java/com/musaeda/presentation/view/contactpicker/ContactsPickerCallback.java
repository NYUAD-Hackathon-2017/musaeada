package com.musaeda.presentation.view.contactpicker;

import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.presentation.view.ViewCallback;
import java.util.List;

public interface ContactsPickerCallback extends ViewCallback {
  void setUpRecyclerView();

  void setAdapter(List<ContactEntity> entities);
}
