package com.musaeda.presentation.view;

public interface ViewCallback {
  void showLoading();

  void hideLoading();

  void showError(String message);
}
