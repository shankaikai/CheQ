package com.example.cheq.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.Button;

import com.example.cheq.Login.LoginActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class RestaurantAccountFragment extends Fragment{
    Button contactUsBtn,leaveRatingBtn,faqBtn,logOutBtn,editNameBtn,editPictureBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_account, container, false);
        contactUsBtn = view.findViewById(R.id.restaurantAccountContactUsButton);
        leaveRatingBtn = view.findViewById(R.id.restaurantAccountLeaveRatingButton);
        faqBtn = view.findViewById(R.id.restaurantAccountFAQButton);
        logOutBtn = view.findViewById(R.id.restaurantAccountLogOutButton);
//        editNameBtn = view.findViewById(R.id.restaurantAccountNameTextView);
//        editPictureBtn = view.findViewById(R.id.restaurantAccountPictureEditButton);

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
        editNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        editPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // Inflate the layout for this fragment
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
