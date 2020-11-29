package com.example.cheq.Users;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cheq.R;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UserPastActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<UserPastActivitiesRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> names, dates, groupsizes;
    ArrayList<Uri> images;
    Context context;



    public UserPastActivitiesRecyclerViewAdapter(ArrayList<String> names, ArrayList<String> dates, ArrayList<String> groupsizes, ArrayList<Uri> images, Context context) {
        this.names = names;
        this.dates = dates;
        this.groupsizes = groupsizes;
        this.images = images;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_past_activities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(names.get(position));
        holder.date.setText(dates.get(position));
        holder.groupsize.setText(groupsizes.get(position));
        holder.image.setImageURI(images.get(position));
        Glide.with(this.context).load(images.get(position)).centerCrop().into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        if (names == null) {
            return 0;
        }

        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name, date, groupsize;
        public final ImageView image;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.past_activity_name);
            date = (TextView) view.findViewById(R.id.past_activity_date);
            groupsize = (TextView) view.findViewById(R.id.past_activity_groupsize);
            image = view.findViewById(R.id.past_activity_imageView);
        }

        public ImageView getImage() {
            return image;
        }
    }
}