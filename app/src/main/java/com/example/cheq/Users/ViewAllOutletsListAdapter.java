package com.example.cheq.Users;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cheq.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllOutletsListAdapter extends RecyclerView.Adapter<com.example.cheq.Users.ViewAllOutletsListAdapter.ViewHolder> {

    ArrayList<String> restaurantNames;
    ArrayList<String> categories;
    ArrayList<String> restaurantImages;
    ArrayList<String> restaurantWaitingTimes;
    static ArrayList<String> restaurantID;

    Context context;

    private static final String TAG = "test";

    private onRestaurantListener mOnRestaurantListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView categoryTextView;
        ImageView imageView;
        TextView waitingTimeTextView;

        onRestaurantListener onRestaurantListener;

        public ViewHolder(View itemView, onRestaurantListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restImage);
            nameTextView = itemView.findViewById(R.id.restName);
            categoryTextView = itemView.findViewById(R.id.restCategory);
            waitingTimeTextView = itemView.findViewById(R.id.waitingTime);
            this.onRestaurantListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int idx = getAdapterPosition();
            String restID = restaurantID.get(idx);
            onRestaurantListener.onRestaurantClick(restID);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getCategoryTextView() {
            return categoryTextView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getWaitingTimeTextView() {
            return waitingTimeTextView;
        }
    }

    // Constructor
    public ViewAllOutletsListAdapter(HashMap<String, HashMap<String, String>> info, onRestaurantListener onRestaurantListener, Context context) {
        this.restaurantNames = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.restaurantImages = new ArrayList<>();
        this.restaurantID = new ArrayList<>();
        this.restaurantWaitingTimes = new ArrayList<>();

        for (String id: info.keySet()) {
            this.restaurantID.add(id);
            this.restaurantNames.add(info.get(id).get("name"));
            this.categories.add(info.get(id).get("category"));
            this.restaurantWaitingTimes.add(info.get(id).get("waitingTime"));
            this.restaurantImages.add(info.get(id).get("image"));
        }
        this.mOnRestaurantListener = onRestaurantListener;

        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_outlets_list_details, parent, false);

        return new ViewAllOutletsListAdapter.ViewHolder(v, mOnRestaurantListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // retrieve the data for that position
        holder.getNameTextView().setText(restaurantNames.get(position));
        holder.getCategoryTextView().setText(categories.get(position));
        Glide.with(this.context).load(restaurantImages.get(position)).centerCrop().into(holder.getImageView());
        holder.getWaitingTimeTextView().setText(restaurantWaitingTimes.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return restaurantNames.size();
    }

    public interface onRestaurantListener {
        void onRestaurantClick(String id);
    }

    public ArrayList<String> getRestaurantID() {
        return restaurantID;
    }
}
