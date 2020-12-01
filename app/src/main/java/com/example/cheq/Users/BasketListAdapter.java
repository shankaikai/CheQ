package com.example.cheq.Users;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;

import java.util.ArrayList;
import java.util.HashMap;

public class BasketListAdapter extends RecyclerView.Adapter<com.example.cheq.Users.BasketListAdapter.ViewHolder> {

    private static final String TAG = "test";
    static ArrayList<String> dishName;
    ArrayList<String> dishPrice;
    ArrayList<String> dishQuantity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            priceTextView = itemView.findViewById(R.id.itemPrice);
            nameTextView = itemView.findViewById(R.id.itemName);
            quantityTextView = itemView.findViewById(R.id.itemQuantity);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }

        public TextView getQuantityTextView() {
            return quantityTextView;
        }
    }

    // Constructor
    public BasketListAdapter(HashMap<String, HashMap<String, String>> info) {
        this.dishName = new ArrayList<>();
        this.dishPrice = new ArrayList<>();
        this.dishQuantity = new ArrayList<>();

        for (String id: info.keySet()) {
            this.dishName.add(id);
            this.dishQuantity.add(info.get(id).get("quantity"));
            this.dishPrice.add(info.get(id).get("price"));
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BasketListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_list_details, parent, false);

        return new BasketListAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BasketListAdapter.ViewHolder holder, int position) {

        // retrieve the data for that position
        holder.getNameTextView().setText(dishName.get(position));
        holder.getPriceTextView().setText(dishPrice.get(position));
        holder.getQuantityTextView().setText(dishQuantity.get(position) + "x");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dishName.size();
    }
}
