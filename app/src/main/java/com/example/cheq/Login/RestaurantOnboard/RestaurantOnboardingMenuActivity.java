    package com.example.cheq.Login.RestaurantOnboard;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.Entities.FirebaseDishItem;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.example.cheq.Restaurant.RestaurantActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

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

    String userPhone;
    Uri imageUri;

    FirebaseManager firebaseManager;
    DatabaseReference menuRef;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    AlertDialog alertDialog;

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
                moveToRestaurantActivity();
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

        // ItemTouchHelper
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

        firebaseManager = new FirebaseManager();
        userPhone = getIntent().getStringExtra("userPhone");
        menuRef = firebaseManager.rootRef.child("Menu").child(userPhone);

        firebaseStorage = firebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("dishImages");

    }


        // This method opens an alert dialog to get user inputs for a dish
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
            .setPositiveButton("Done", null)
            .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        // Create alertDialog
        alertDialog = alertDialogBuilder.create();

        // Override the alert dialog positive button to enable validation
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button doneBtn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dishName = inputDishName.getText().toString();
                        String dishPrice = inputDishPrice.getText().toString();
                        String dishCategory = spinnerDishCategory.getSelectedItem().toString();

                        if (validateInputs(dishName, dishPrice, imageUri)) {

                            uploadDishToFirebase(dishName, dishPrice, dishCategory, imageUri);

                            // Reset imageUri to null;
                            imageUri = null;
                            Log.i("UploadDish", dishName + dishPrice + dishCategory);
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(RestaurantOnboardingMenuActivity.this, "Please input all fields for your dish", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Show the dialog
        alertDialog.show();
    }

    void removeDish(int position) {
        String dishName = menuList.get(position).getDishName();
        Log.i("removedDish", dishName);
        menuList.remove(position);
        Toast.makeText(this, "Dish removed!", Toast.LENGTH_SHORT).show();
        // Remove from database
        final DatabaseReference dishRef = firebaseManager.rootRef.child("Menu").child(userPhone).child(dishName);
        dishRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dishRef.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

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



    // Upload dish to firestorage
    public void uploadDishToFirebase(final String dishName, final String dishPrice, final String dishCategory, Uri imageUri) {
        onboardMenuProgressBar.setVisibility(View.VISIBLE);
        // Add it to local data source
        menuList.add(new DishItem(dishName, dishPrice, dishCategory, imageUri));
        // Update local recyclerview
        menuAdapter.notifyDataSetChanged();
        onboardMenuProgressBar.setVisibility(View.GONE);

        // Generate a random string for the image name
        final String randomKey = UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child(randomKey);

        // Add the file into firebase storage
        UploadTask uploadTask = ref.putFile(imageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String downloadUrl = task.getResult().toString();
                    Log.i("url", downloadUrl);
                    // Create FirebaseDish Object
                    FirebaseDishItem firebaseDishItem = new FirebaseDishItem(dishName, dishPrice, dishCategory, downloadUrl);

                    //Upload details to firebase
                    firebaseManager.addDish(firebaseDishItem, userPhone);

                } else {
                    onboardMenuProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RestaurantOnboardingMenuActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }



        // Validate all the inputs
        public boolean validateInputs(String dishName, String dishPrice, Uri imageUri){
            if (dishName == null) {
                Toast.makeText(this, "Please enter the dish name", Toast.LENGTH_SHORT).show();
            } else if (imageUri == null) {
                Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            } else if (dishPrice == null) {
                Toast.makeText(this, "Please enter the dish price", Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
            return false;
        }

        private void moveToRestaurantActivity() {
            Intent intent = new Intent(RestaurantOnboardingMenuActivity.this, RestaurantActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        @Override
        public void onBackPressed() {
            Toast.makeText(this, "Please add your dishes", Toast.LENGTH_SHORT).show();
        }
    }