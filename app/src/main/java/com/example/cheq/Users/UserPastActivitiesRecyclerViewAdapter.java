package com.example.cheq.Users;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cheq.R;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UserPastActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<UserPastActivitiesRecyclerViewAdapter.ViewHolder> {

    String names[], dates[], groupsizes[];
    int images[];
//    Context context;



    public UserPastActivitiesRecyclerViewAdapter(String[] names, String[] dates, String[] groupsizes, int[] images) {
        this.names = names;
        this.dates = dates;
        this.groupsizes = groupsizes;
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_past_activities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(names[position]);
        holder.date.setText(dates[position]);
        holder.groupsize.setText(groupsizes[position]);
        holder.image.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
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
    }
}