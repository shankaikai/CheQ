package com.example.cheq.Login.RestaurantOnboard;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheq.Entities.RestaurantInfoItem;
import com.example.cheq.Login.InputValidation;
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
    ProgressBar onboardProgressBar;
    TextView chooseRestaurantPicture;

    private Uri imageUri;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseManager firebaseManager;

    String userPhone;

    final int REQUEST_CODE_IMAGE = 1000;

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

    // This method calls an explicit intent to get an image from gallery
    private void choosePicture(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            chooseRestaurantPicture.setText("Great Picture!");
        } else {
            chooseRestaurantPicture.setText("Please Try again!");
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

        if (validateInputs(restName, restPhone, restEmail, restCategory, imageUri)) {
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
                        String downloadUrl = task.getResult().toString();

                        // Create RestaurantInfo object
                        RestaurantInfoItem restaurantInfo = new RestaurantInfoItem(restPhone, restName, restEmail, downloadUrl, restCategory);

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
        } else {
            onboardProgressBar.setVisibility(View.GONE);
        }

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

    // Validate all the inputs
    public boolean validateInputs(String restName, String restPhone, String restEmail, String restCategory, Uri imageUri){
        if (restName == null || restPhone == null) {
            Toast.makeText(this, "Restaurant Name/Phone is empty", Toast.LENGTH_SHORT).show();
        } else if (imageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
        } else if (!InputValidation.isValidEmail(restEmail)) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }
}