package com.musaeda.presentation.view.contactpicker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.musaeda.R;
import com.musaeda.domain.entity.ContactEntity;
import com.musaeda.presentation.view.utils.CircleTransform;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Random;

public class ContactsPickerAdapter extends RecyclerView.Adapter<ContactsPickerAdapter.ViewHolder> {
  private List<ContactEntity> data;

  private static final int[] COLORS = {
      Color.parseColor("#F44336"),
      Color.parseColor("#E91E63"),
      Color.parseColor("#9C27B0"),
      Color.parseColor("#673AB7"),
      Color.parseColor("#3F51B5"),
      Color.parseColor("#2196F3"),
  };

  private static final Random RANDOM = new Random();

  private CheckBoxCheckedListener listener;

  public void setListener(CheckBoxCheckedListener listener) {
    this.listener = listener;
  }

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
    holder.itemView.setClickable(true);
    ContactEntity entity = data.get(position);
    holder.name.setText(entity.getName());
    Context context = holder.itemView.getContext();

    TextDrawable defaultPhoto = getDefaultDrawable(entity);

    if (entity.getPhotoUri() != null) {
      Picasso.with(context)
          .load(entity.getPhotoUri())
          .transform(new CircleTransform())
          .into(holder.photo);
    } else {
      holder.photo.setImageDrawable(defaultPhoto);
    }

    holder.checkbox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.anim_task_completion));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          ((AnimatedVectorDrawable) holder.checkbox.getDrawable().mutate()).start();
        }
        listener.onCheckBoxChecked(position);
      }
    });
  }

  private TextDrawable getDefaultDrawable(ContactEntity entity) {
    String iconText = String.valueOf(entity.getName().charAt(0));
    int color = COLORS[RANDOM.nextInt(COLORS.length)];
    return TextDrawable.builder().buildRound(iconText, color);
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView photo;
    public ImageView checkbox;

    public ViewHolder(View v) {
      super(v);
      name = (TextView) v.findViewById(R.id.contact_name);
      photo = (ImageView) v.findViewById(R.id.contact_image);
      checkbox = (ImageView) v.findViewById(R.id.pick_checkbox);
    }
  }

  public interface CheckBoxCheckedListener {
    void onCheckBoxChecked(int position);
  }
}

