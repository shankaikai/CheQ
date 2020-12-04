package com.example.cheq.RestaurantInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cheq.R;
import com.example.cheq.Users.ViewOutletsListAdapter;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<com.example.cheq.RestaurantInfo.MenuAdapter.MenuViewHolder> {

    ArrayList<String> itemNames, itemPrices, itemImages;
    Context context;

    private MenuAdapter.onRestaurantListener mOnRestaurantListener;

    public MenuAdapter(Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> img, onRestaurantListener onRestaurantListener) {
        this.context = ct;
        this.itemNames = s1;
        this.itemPrices = s2;
        this.itemImages = img;

        this.mOnRestaurantListener = onRestaurantListener;
    }

    @NonNull
    @Override
    public com.example.cheq.RestaurantInfo.MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view, mOnRestaurantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.getItemNameTextView().setText(itemNames.get(position));
        holder.getItemPriceTextView().setText(itemPrices.get(position));
        // holder.getItemImageView().setImageResource(itemImages[position]);
        Glide.with(this.context).load(itemImages.get(position)).centerCrop().into(holder.getItemImageView());
    }


    @Override
    public int getItemCount() {
        return itemNames.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemNameTextView, itemPriceTextView;
        ImageView itemImageView;

        MenuAdapter.onRestaurantListener onRestaurantListener;

        public MenuViewHolder(@NonNull View itemView, onRestaurantListener listener) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemName);
            itemPriceTextView = itemView.findViewById(R.id.itemPrice);
            itemImageView = itemView.findViewById(R.id.itemImage);

            this.onRestaurantListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int idx = getAdapterPosition();
            String dishName = itemNames.get(idx);
            onRestaurantListener.onRestaurantClick(dishName);
        }

        public ImageView getItemImageView() {
            return itemImageView;
        }

        public TextView getItemNameTextView() {
            return itemNameTextView;
        }

        public TextView getItemPriceTextView() {
            return itemPriceTextView;
        }
    }

    public interface onRestaurantListener {
        void onRestaurantClick(String dishName);
    }
}
