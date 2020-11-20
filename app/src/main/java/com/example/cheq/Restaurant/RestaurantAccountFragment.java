package com.example.cheq.Restaurant;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.cheq.Login.LoginActivity;
import com.example.cheq.Login.RegistrationActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;

public class RestaurantAccountFragment extends Fragment {

    Button logoutRestBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_account, container, false);
    }
}
