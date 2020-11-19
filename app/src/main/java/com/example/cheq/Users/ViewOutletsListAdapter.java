package com.example.cheq.Users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;

public class ViewOutletsListAdapter extends RecyclerView.Adapter<com.example.cheq.Users.ViewOutletsListAdapter.ViewHolder> {

    String[] restaurantNames;
    String[] categories;
    Integer[] restaurantImages;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView categoryTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image);
            nameTextView = itemView.findViewById(R.id.restaurant_name);
            categoryTextView = itemView.findViewById(R.id.category);
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
    public ViewOutletsListAdapter(String[] names, String[] categories, Integer[] images) {
        this.restaurantNames = names;
        this.categories = categories;
        this.restaurantImages = images;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.outlets_list_details, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // retrieve the data for that position
        holder.getNameTextView().setText(restaurantNames[position]);
        holder.getCategoryTextView().setText(categories[position]);
        holder.getImageView().setImageResource(restaurantImages[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return restaurantNames.length;
    }
}
