package com.example.cheq.Login.RestaurantOnboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cheq.R;

import java.util.ArrayList;

public class RestaurantOnboardingMenuActivity extends AppCompatActivity {
    private ArrayList<DishItem> menuList;

    // Views
    RecyclerView onboardMenuRecycler;
    EditText inputDishName;
    EditText inputDishPrice;
    Button addDishBtn;
    Button onboardMenuContinueBtn;

    RecyclerView.Adapter menuAdapter;
    RecyclerView.LayoutManager menuLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_onboarding_menu);

        menuList = new ArrayList<>();

        // Hooks
        inputDishName = findViewById(R.id.inputDishName);
        inputDishPrice = findViewById(R.id.inputDishPrice);
        addDishBtn = findViewById(R.id.addDishBtn);
        addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDish();
            }
        });
        onboardMenuContinueBtn = findViewById(R.id.onboardMenuContinueBtn);
        onboardMenuContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMenu();
            }
        });

        // Build Recycler View
        onboardMenuRecycler = findViewById(R.id.onboardMenuRecycler);
        onboardMenuRecycler.setHasFixedSize(true);
        menuAdapter = new MenuAdapter(menuList);
        menuLayoutManger = new LinearLayoutManager(this);
        onboardMenuRecycler.setLayoutManager(menuLayoutManger);
        onboardMenuRecycler.setAdapter(menuAdapter);
    }


    public void addDish(){
        String dishName = inputDishName.getText().toString();
        String dishPrice = inputDishPrice.getText().toString();
        menuList.add(new DishItem(1, dishName, dishPrice));
        menuAdapter.notifyDataSetChanged();
        // Clear EditText inputs
        inputDishName.setText("");
        inputDishPrice.setText("");
    }

    private void uploadMenu() {
    }
}