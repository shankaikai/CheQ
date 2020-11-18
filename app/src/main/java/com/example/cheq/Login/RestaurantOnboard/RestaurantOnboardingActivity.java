package com.example.cheq.Login.RestaurantOnboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheq.Entities.RestaurantInfo;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class RestaurantOnboardingActivity extends AppCompatActivity {

    // Views
    Spinner onboardSpinner;
    EditText onboardRestaurantName;
    EditText onboardRestaurantNum;
    EditText onboardRestaurantEmail;
    Button onboardContinueBtn;
    ImageView onboardBackBtn;
    RelativeLayout onboardProgressBar;
    TextView chooseRestaurantPicture;

    private Uri imageUri;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseManager firebaseManager;

    String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_onboarding);

        // Hooks
        onboardContinueBtn = findViewById(R.id.onboardContinueBtn);
        onboardContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRestaurantDetails();
            }
        });
        onboardBackBtn = findViewById(R.id.onboardBackBtn);
        onboardRestaurantName = findViewById(R.id.onboardResturantName);
        onboardRestaurantNum = findViewById(R.id.onboardResturantNum);
        onboardRestaurantEmail = findViewById(R.id.onboardResturantEmail);
        onboardSpinner = findViewById(R.id.onboardSpinner);
        onboardProgressBar = findViewById(R.id.onboardProgressBar);
        chooseRestaurantPicture = findViewById(R.id.chooseRestaurantPicture);
        chooseRestaurantPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        // Firebase Storage
        firebaseStorage = firebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        firebaseManager = new FirebaseManager();

        userPhone = getIntent().getStringExtra("userPhone");
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
            chooseRestaurantPicture.setText("Great Picture!");
        }
    }

    private void uploadRestaurantDetails(){
        // Set progress bar to visible
        onboardProgressBar.setVisibility(View.VISIBLE);

        // Get inputs
        final String restName = onboardRestaurantName.getText().toString();
        final String restPhone = onboardRestaurantNum.getText().toString();
        final String restEmail = onboardRestaurantEmail.getText().toString();
        final String restCategory = onboardSpinner.getSelectedItem().toString();

        // Generate a random string for the image name
        final String randomKey = UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child("images/" + randomKey);

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
                    String downloadUri = task.getResult().toString();

                    // Create RestaurantInfo object
                    RestaurantInfo restaurantInfo = new RestaurantInfo(restPhone, restName, restEmail, downloadUri, restCategory);

                    //Upload details to firebase
                    firebaseManager.addRestaurantDetails(restaurantInfo, userPhone);

                    // Set progress bar to gone
                    onboardProgressBar.setVisibility(View.GONE);

                    // Move To RestaurantOnboardingMenuActivity
                    moveToMenuActivity(userPhone);

                } else {
                    Toast.makeText(RestaurantOnboardingActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void moveToMenuActivity(String userPhone) {
        Intent intent = new Intent(RestaurantOnboardingActivity.this, RestaurantOnboardingMenuActivity.class);
        intent.putExtra("userPhone", userPhone);
        // Add Transition
        Pair transition = new Pair<View, String>(onboardContinueBtn, "transitionContinueBtn");

        // Check if SDK version is high enough for animation
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RestaurantOnboardingActivity.this, transition);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}