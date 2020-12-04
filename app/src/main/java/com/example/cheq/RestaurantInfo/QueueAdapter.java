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

import java.util.ArrayList;
import java.util.HashMap;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {

    ArrayList<Integer> currentQInfo;

    Context context;

    public QueueAdapter(Context ct, ArrayList<Integer> currentQInfo) {
        this.context = ct;
        this.currentQInfo = currentQInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.queue_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer num = currentQInfo.get(position);
        String idx = (position + 1)+ "";
        holder.getHeaderTextView().setText(idx);
        String num_ppl = num + "";
        holder.getNoOfGroupsTextView().setText(num_ppl);
        if (num == 0) {
            String timeWait = "0";
            holder.getEstTimeTextView().setText(timeWait);
        } else {
            String timeWait = (num * 20) + "";
            holder.getEstTimeTextView().setText(timeWait);
        }
    }


    @Override
    public int getItemCount() {
        return currentQInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView headerTextView, noOfGroupsTextView, estTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.noOfPax);
            noOfGroupsTextView = itemView.findViewById(R.id.noInLine);
            estTimeTextView = itemView.findViewById(R.id.estTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }

        public TextView getHeaderTextView() {
            return headerTextView;
        }

        public TextView getNoOfGroupsTextView() {
            return noOfGroupsTextView;
        }

        public TextView getEstTimeTextView() {
            return estTimeTextView;
        }
    }

}
