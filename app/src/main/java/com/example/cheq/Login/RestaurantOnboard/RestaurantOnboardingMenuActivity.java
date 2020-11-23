package com.example.cheq.Login.RestaurantOnboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cheq.R;

import java.util.ArrayList;

public class RestaurantOnboardingMenuActivity extends AppCompatActivity {
    private ArrayList<DishItem> menuList;

    // Views
    ImageView addDishMenuBtn;
    Button onboardMenuContinueBtn;
    ProgressBar onboardMenuProgressBar;
    RecyclerView onboardMenuRecycler;

    RecyclerView.Adapter menuAdapter;
    RecyclerView.LayoutManager menuLayoutManger;
    ItemTouchHelper itemTouchHelper;

    // TODO: Grab from intent
    String userPhone = "99999999";
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_onboarding_menu);

        menuList = new ArrayList<>();

        // Hooks
        addDishMenuBtn = findViewById(R.id.addDishMenuBtn);
        addDishMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDishPrompt();
            }
        });
        onboardMenuContinueBtn = findViewById(R.id.onboardMenuContinueBtn);
        onboardMenuContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMenu();
            }
        });
        onboardMenuProgressBar = findViewById(R.id.onboardMenuProgressBar);

        // Build Recycler View
        onboardMenuRecycler = findViewById(R.id.onboardMenuRecycler);
        onboardMenuRecycler.setHasFixedSize(true);
        menuAdapter = new MenuAdapter(menuList);
        menuLayoutManger = new LinearLayoutManager(this);
        onboardMenuRecycler.setLayoutManager(menuLayoutManger);
        onboardMenuRecycler.setAdapter(menuAdapter);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MenuAdapter.MenuViewHolder menuViewHolder = (MenuAdapter.MenuViewHolder) viewHolder;
                int position = menuViewHolder.getAdapterPosition();
                removeDish(position);
                menuAdapter.notifyDataSetChanged();
            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(onboardMenuRecycler);
    }

    private void openDishPrompt() {
        // get dish_prompt.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dish_prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set dish_prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText inputDishName = promptsView.findViewById(R.id.inputDishName);
        final EditText inputDishPrice = promptsView.findViewById(R.id.inputDishPrice);
        final Spinner spinnerDishCategory = promptsView.findViewById(R.id.spinnerDishCategory);
        final ImageView dishImageView = promptsView.findViewById(R.id.dishImageView);

        dishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
//                dishImageView.setImageResource(R.drawable.check_circle_orange);
            }
        });

        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String dishName = inputDishName.getText().toString();
                                String dishPrice = inputDishPrice.getText().toString();
                                String dishCategory = spinnerDishCategory.getSelectedItem().toString();

                                insertDishIntoMenuList(dishName, dishPrice, dishCategory, imageUri);

                                // Reset imageUri to null;
                                imageUri = null;
                                Log.i("Hi", dishName + dishPrice + dishCategory);
//                                insertDishIntoMenuList(dishName, dishPrice, dishCategory, imageUri);
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

    void removeDish(int position) {
        Toast.makeText(this, "Dish removed!", Toast.LENGTH_SHORT).show();
        menuList.remove(position);
    }

    // Choose dish picture
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
        }
    }

    // Insert DishItem into menuList
    private void insertDishIntoMenuList(String dishName, String dishPrice, String dishCategory, Uri imageUri) {
        menuList.add(new DishItem(dishName, dishPrice, dishCategory, imageUri));
        menuAdapter.notifyDataSetChanged();
    }

    public void uploadMenu() {

    }
}