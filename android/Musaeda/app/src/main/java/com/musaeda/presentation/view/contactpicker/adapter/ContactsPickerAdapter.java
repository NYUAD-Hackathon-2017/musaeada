package com.musaeda.presentation.view.contactpicker.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.musaeda.R;
import com.musaeda.domain.entity.ContactEntity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ContactsPickerAdapter extends RecyclerView.Adapter<ContactsPickerAdapter.ViewHolder> {
  private List<ContactEntity> data;

  public ContactsPickerAdapter(List<ContactEntity> data) {
    this.data = data;
  }

  @Override
  public ContactsPickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {
    Context context = parent.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.row_contacts_picker, parent, false);
    ViewHolder vh = new ViewHolder(view);
    return vh;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    ContactEntity entity = data.get(position);
    holder.name.setText(entity.getName());
    Context context = holder.itemView.getContext();
    if (entity.getPhotoUri() != null) {
      Picasso.with(context).load(entity.getPhotoUri()).into(holder.photo);
    } else {
      holder.photo.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));
    }
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView photo;

    public ViewHolder(View v) {
      super(v);
      name = (TextView) v.findViewById(R.id.contact_name);
      photo = (ImageView) v.findViewById(R.id.contact_image);
    }
  }
}

