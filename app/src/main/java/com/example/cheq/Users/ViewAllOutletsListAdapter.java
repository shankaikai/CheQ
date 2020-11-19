package com.example.cheq.Users;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;
import com.example.cheq.Utils.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllOutletsListAdapter extends RecyclerView.Adapter<com.example.cheq.Users.ViewAllOutletsListAdapter.ViewHolder> {

    private static final String TAG = "retrieve id";
    ArrayList<String> restaurantNames;
    ArrayList<String> categories;
    ArrayList<String> restaurantImages;
    ArrayList<String> restaurantWaitingTimes;
    static ArrayList<String> restaurantID;

    private ViewAllOutletsListAdapter.onRestaurantListener mOnRestaurantListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView categoryTextView;
        ImageView imageView;
        TextView waitingTimeTextView;

        ViewAllOutletsListAdapter.onRestaurantListener onRestaurantListener;

        public ViewHolder(View itemView, ViewAllOutletsListAdapter.onRestaurantListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurantImage);
            nameTextView = itemView.findViewById(R.id.restaurantName);
            categoryTextView = itemView.findViewById(R.id.restaurantCategory);
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
    public ViewAllOutletsListAdapter(HashMap<String, HashMap<String, String>> info, ViewAllOutletsListAdapter.onRestaurantListener onRestaurantListener) {
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
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewAllOutletsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.outlets_list_details, parent, false);

        return new ViewAllOutletsListAdapter.ViewHolder(v, mOnRestaurantListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewAllOutletsListAdapter.ViewHolder holder, int position) {

        // retrieve the data for that position
        holder.getNameTextView().setText(restaurantNames.get(position));
        holder.getCategoryTextView().setText(categories.get(position));

        // Convert the URL string to Bitmap
        String url = restaurantImages.get(position);
        URL imageURL = null;
        try {
            imageURL = new URL(url);
            try {
                Bitmap bitmap = Utils.getBitmap(imageURL);
                holder.getImageView().setImageBitmap(bitmap);
            } catch (IOException ex) {
                Log.i(TAG, "Conversion to Bitmap Failed");
            }
        } catch (MalformedURLException ex) {
            Log.i(TAG, "Invalid Image URL");
            holder.getImageView().setImageResource(R.drawable.bulbasaur);
        }
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
