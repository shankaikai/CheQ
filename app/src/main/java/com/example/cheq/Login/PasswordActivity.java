package com.example.cheq.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.MainActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.Restaurant.RestaurantActivity;
import com.example.cheq.Users.UserActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PasswordActivity extends AppCompatActivity {

    // Views
    EditText inputPassword;
    Button passwordContinueBtn;
    ImageView passwordBackBtn;
    ProgressBar passwordProgressBar;

    // Managers
    FirebaseManager firebaseManager;
    SessionManager sessionManager;

    String userPhone;
    String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        firebaseManager = new FirebaseManager();
        sessionManager = SessionManager.getSessionManager(PasswordActivity.this);

        // Hooks
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        passwordContinueBtn = (Button) findViewById(R.id.passwordContinueBtn);
        passwordContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });
        passwordProgressBar = findViewById(R.id.passwordProgressBar);
        passwordBackBtn = findViewById(R.id.passwordBackBtn);
        passwordBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLoginActivity();
            }
        });

    }

    // Check password of user
    public void checkPassword() {
        passwordProgressBar.setVisibility(View.VISIBLE);

        // Get userPhone from LoginActivity
        userPhone = getIntent().getStringExtra("userPhone");

        userPassword = inputPassword.getText().toString();

        final DatabaseReference rootRef = firebaseManager.rootRef;

        // Check if the userPassword is more than 6 characters
        if (InputValidation.isValidPassword(userPassword)) {

            // Check firebase for password match
           final DatabaseReference userRef = rootRef.child("Users").child(userPhone);

           userRef.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (snapshot.child("userPassword").getValue().toString().equals(userPassword)) {
                       // Save Session of User
                       String userType = snapshot.child("userType").getValue().toString();
                       sessionManager.saveSession(userPhone, userType);
                       passwordProgressBar.setVisibility(View.GONE);

                       if (userType.equals("Customer")) {
                           moveToMainActivity();
                       } else {
                           moveToRestaurantActivity();
                       }

                   } else {
                       passwordProgressBar.setVisibility(View.GONE);
                       // Display error toast
                       Toast.makeText(PasswordActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   passwordProgressBar.setVisibility(View.GONE);
                   // Display error toast
                   Toast.makeText(PasswordActivity.this, "Service is unavailable", Toast.LENGTH_SHORT).show();
               }
           });
        }
        else {
            passwordProgressBar.setVisibility(View.GONE);
            // Display error toast
            Toast.makeText(PasswordActivity.this, "Please input a password with at least 6 characters", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void moveToRestaurantActivity() {
        Intent intent = new Intent(PasswordActivity.this, RestaurantActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void moveToLoginActivity() {
        Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
        // Add Transition
        Pair transition = new Pair<View, String>(passwordContinueBtn, "transitionContinueBtn");
        // Check if SDK version is high enough for animation
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PasswordActivity.this, transition);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        moveToLoginActivity();
    }
}