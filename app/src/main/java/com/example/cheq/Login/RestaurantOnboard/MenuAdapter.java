package com.example.cheq.Login.RestaurantOnboard;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private ArrayList<DishItem> menuList;

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView dishImage;
        public TextView dishName;
        public TextView dishPrice;
        public TextView dishCategory;

        public MenuViewHolder(View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.dishImage);
            dishName = itemView.findViewById(R.id.dishName);
            dishPrice = itemView.findViewById(R.id.dishPrice);
            dishCategory = itemView.findViewById(R.id.dishCategory);
        }
    }

    public MenuAdapter(ArrayList<DishItem> menuList) {
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        MenuViewHolder mvh = new MenuViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        DishItem currentItem = menuList.get(position);

        holder.dishImage.setImageURI(currentItem.getDishImageUri());
        holder.dishCategory.setText(currentItem.getDishCategory());
        holder.dishName.setText(currentItem.getDishName());
        holder.dishPrice.setText("$"+currentItem.getDishPrice());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
