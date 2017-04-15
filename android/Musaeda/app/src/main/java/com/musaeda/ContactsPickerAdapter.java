package com.musaeda;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactsPickerAdapter extends RecyclerView.Adapter<ContactsPickerAdapter.ViewHolder> {
  private String[] mDataset;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public ViewHolder(TextView v) {
      super(v);
      mTextView = v;
    }
  }

  public ContactsPickerAdapter(String[] myDataset) {
    mDataset = myDataset;
  }

  @Override
  public ContactsPickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {
    // // create a new view
    // TextView v = (TextView) LayoutInflater.from(parent.getContext())
    //     .inflate(R.layout.my_text_view, parent, false);
    // // set the view's size, margins, paddings and layout parameters
    //     ...
    // ViewHolder vh = new ViewHolder(v);
    return null;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.mTextView.setText(mDataset[position]);
  }

  @Override
  public int getItemCount() {
    return mDataset.length;
  }
}

