package com.musaeda.presentation.view.home;

import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.presentation.view.ViewCallback;
import java.util.List;

public interface HomeCallback extends ViewCallback {
  void setUpRecyclerView();

  void setAdapter(List<ContactEntity> entities);
}
