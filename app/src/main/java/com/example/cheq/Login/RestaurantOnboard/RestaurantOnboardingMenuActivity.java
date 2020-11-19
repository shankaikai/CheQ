package com.example.cheq.Login.RestaurantOnboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RestaurantOnboardingMenuActivity extends AppCompatActivity {
    private ArrayList<DishItem> menuList;

    // Views
    RecyclerView onboardMenuRecycler;
    EditText inputDishName;
    EditText inputDishPrice;
    ImageView dishImageView;
    ImageView addDishMenuBtn;
    Button onboardMenuContinueBtn;
    Button addDishBtn;
    Spinner spinnerDishCategory;

    RecyclerView.Adapter menuAdapter;
    RecyclerView.LayoutManager menuLayoutManger;

    String userPhone;
    Uri imageUri;

    // Firebase Storage
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_onboarding_menu);

        menuList = new ArrayList<>();
        userPhone = getIntent().getStringExtra("userPhone");
        firebaseStorage = firebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // Hooks
        dishImageView = findViewById(R.id.dishImageView);
        dishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        addDishMenuBtn = findViewById(R.id.addDishMenuBtn);
        addDishMenuBtn.setOnClickListener(new View.OnClickListener() {
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

        // Hooks from DishPrompt
        addDishBtn = findViewById(R.id.addDishBtn);
        inputDishName = findViewById(R.id.inputDishName);
        inputDishPrice = findViewById(R.id.inputDishPrice);
        spinnerDishCategory = findViewById(R.id.spinnerDishCategory);

        // Build Recycler View
        onboardMenuRecycler = findViewById(R.id.onboardMenuRecycler);
        onboardMenuRecycler.setHasFixedSize(true);
        menuAdapter = new MenuAdapter(menuList);
        menuLayoutManger = new LinearLayoutManager(this);
        onboardMenuRecycler.setLayoutManager(menuLayoutManger);
        onboardMenuRecycler.setAdapter(menuAdapter);
    }

    // Add a dish to the menu recycler view
    public void addDish(){
        // get dish_prompt.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dish_prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set dish_prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String dishName = inputDishName.getText().toString();
                                String dishPrice = inputDishPrice.getText().toString();
                                String dishCategory = spinnerDishCategory.getSelectedItem().toString();

                                insertDishIntoMenuList(dishName, dishPrice, dishCategory);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        // Create alertDialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Show the dialog
        alertDialog.show();
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Set the place holder image as the chosen image
            dishImageView.setImageURI(imageUri);
        }
    }


    // Insert DishItem into menuList
    private void insertDishIntoMenuList(String dishName, String dishPrice, String dishCategory) {
        menuList.add(new DishItem(dishName, dishPrice, dishCategory));
        menuAdapter.notifyDataSetChanged();
    }

    // Upload menu to database
    private void validateInputs() {
    }
}