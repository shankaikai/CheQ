package com.example.cheq.Users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cheq.R;

import java.util.ArrayList;
import java.util.HashMap;

public class QueueAgainListAdapter extends RecyclerView.Adapter<QueueAgainListAdapter.ViewHolder> {

    private static final String TAG = "test";
    ArrayList<String> restaurantNames;
    ArrayList<String> categories;
    ArrayList<String> restaurantImages;
    static ArrayList<String> restaurantID;
    String url = "";
    Context context;

    private onRestaurantListener mOnRestaurantListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView categoryTextView;
        ImageView imageView;

        onRestaurantListener onRestaurantListener;

        public ViewHolder(View itemView, onRestaurantListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image);
            nameTextView = itemView.findViewById(R.id.restaurant_name);
            categoryTextView = itemView.findViewById(R.id.category);
            this.onRestaurantListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int idx = getAdapterPosition();
            String restID = restaurantID.get(idx);
            onRestaurantListener.onClick(restID);
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
    }

    // Constructor
    public QueueAgainListAdapter(HashMap<String, HashMap<String, String>> info, onRestaurantListener onRestaurantListener, Context context) {
        this.restaurantNames = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.restaurantImages = new ArrayList<>();
        this.restaurantID = new ArrayList<>();

        for (String id: info.keySet()) {
            this.restaurantID.add(id);
            this.restaurantNames.add(info.get(id).get("name"));
            this.categories.add(info.get(id).get("category"));
            this.restaurantImages.add(info.get(id).get("image"));
        }

        this.mOnRestaurantListener = onRestaurantListener;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.outlets_list_details, parent, false);

        return new ViewHolder(v, mOnRestaurantListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // retrieve the data for that position
        holder.getNameTextView().setText(restaurantNames.get(position));
        holder.getCategoryTextView().setText(categories.get(position));
        Glide.with(this.context).load(restaurantImages.get(position)).centerCrop().into(holder.getImageView());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return restaurantNames.size();
    }

    public interface onRestaurantListener {
        void onClick(String id);
    }

    public ArrayList<String> getRestaurantID() {
        return restaurantID;
    }
}
