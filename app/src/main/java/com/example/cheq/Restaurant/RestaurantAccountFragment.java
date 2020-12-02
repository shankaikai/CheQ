package com.example.cheq.Restaurant;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cheq.Login.LoginActivity;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.net.URL;

public class RestaurantAccountFragment extends Fragment{
    CardView contactUsBtn,leaveRatingBtn,faqBtn,logOutBtn;
    TextView restaurantAccountNameTextView, restaurantAccountEmailTextView;
    ImageView restaurantAccountPictureImageView;

    FirebaseManager firebaseManager;
    String restaurantId;
    SessionManager sessionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_restaurant_account, container, false);
        contactUsBtn = view.findViewById(R.id.restaurantAccountContactUsButton);
        leaveRatingBtn = view.findViewById(R.id.restaurantAccountLeaveRatingButton);
        faqBtn = view.findViewById(R.id.restaurantAccountFAQButton);
        logOutBtn = view.findViewById(R.id.restaurantAccountLogOutButton);
//        editNameBtn = view.findViewById(R.id.restaurantAccountNameTextView);
//        editPictureBtn = view.findViewById(R.id.restaurantAccountPictureEditButton);
        sessionManager = SessionManager.getSessionManager(getContext());
        firebaseManager = new FirebaseManager();
        restaurantId = sessionManager.getUserPhone();

        final DatabaseReference restaurantRef = firebaseManager.rootRef.child("Restaurants").child(restaurantId);
        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restaurantAccountNameTextView = view.findViewById(R.id.restaurantAccountNameTextView);
                restaurantAccountPictureImageView = view.findViewById(R.id.restaurantAccountPictureImageView);
                restaurantAccountEmailTextView = view.findViewById(R.id.restaurantAccountEmailTextView);

                //Set restaurant name, email and image
                restaurantAccountEmailTextView.setText(dataSnapshot.child("restEmail").getValue().toString());
                restaurantAccountNameTextView.setText( dataSnapshot.child("restName").getValue().toString());
                Log.i("restName", dataSnapshot.child("restName").getValue().toString());
                String imageUrl = dataSnapshot.child("restImageUri").getValue().toString();
                Log.i("imageUrl", dataSnapshot.child("restImageUri").getValue().toString());
                Glide.with(getContext()).load(imageUrl).into(restaurantAccountPictureImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://github.com/thelastblade");
            }
        });
        leaveRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://github.com/thelastblade");
            }
        });
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://github.com/thelastblade");
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = SessionManager.getSessionManager(getContext());
                sessionManager.removeSession();
                moveToMainActivity();
            }
        });
//        editNameBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        editPictureBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        return view;
    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
