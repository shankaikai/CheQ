package com.example.cheq.RestaurantInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;

public class MenuAdapter extends RecyclerView.Adapter<com.example.cheq.RestaurantInfo.MenuAdapter.MenuViewHolder> {

    String data1[], data2[];
    int images[];
    Context context;

    public MenuAdapter() {

    }

    public MenuAdapter(Context ct, String s1[], String s2[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }

    @NonNull
    @Override
    public com.example.cheq.RestaurantInfo.MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.menuText1.setText(data1[position]);
        holder.menuText2.setText(data2[position]);
        holder.menuImage.setImageResource(images[position]);
    }


    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView menuText1, menuText2;
        ImageView menuImage;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuText1 = itemView.findViewById(R.id.itemName);
            menuText2 = itemView.findViewById(R.id.itemPrice);
            menuImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
