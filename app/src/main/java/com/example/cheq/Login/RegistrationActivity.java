package com.example.cheq.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheq.Entities.User;
import com.example.cheq.Login.RestaurantOnboard.RestaurantOnboardingActivity;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;

public class RegistrationActivity extends AppCompatActivity {

    // Views
    EditText inputName;
    EditText inputEmail;
    EditText inputPassword;
    Button registerContinueBtn;
    RadioGroup userType;
    ImageView registerBackBtn;
    RelativeLayout registerProgressBar;

    // FirebaseManager
    FirebaseManager firebaseManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Hooks
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        userType = findViewById(R.id.userType);
        registerBackBtn = findViewById(R.id.registerBackBtn);
        registerBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLoginActivity();
            }
        });
        registerContinueBtn = findViewById(R.id.registerContinueBtn);
        registerContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUser();
            }
        });
        registerProgressBar = findViewById(R.id.registerProgressBar);

        firebaseManager = new FirebaseManager();
        sessionManager = SessionManager.getSessionManager(RegistrationActivity.this);
    }

    // Add new users to database after validation
    public void addNewUser() {
        registerProgressBar.setVisibility(View.VISIBLE);

        // Get user inputs
        String userName = inputName.getText().toString();
        String userEmail = inputEmail.getText().toString();
        String userPassword = inputPassword.getText().toString();
        int selectedRadio = userType.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedRadio);
        String userType = radioButton.getText().toString();

        // Get phone from previous activity
        String userPhone = getIntent().getStringExtra("userPhone");
        
        // Validate inputs
        if (userName == null || userPassword == null){
            Toast.makeText(RegistrationActivity.this, "Username/Password is empty", Toast.LENGTH_SHORT).show();
            registerProgressBar.setVisibility(View.GONE);
        } else if (!InputValidation.isValidEmail(userEmail)) {
            Toast.makeText(RegistrationActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            registerProgressBar.setVisibility(View.GONE);
        }
        else {

            // Create new User object
            User user = new User(userPhone, userName, userEmail, userPassword, userType);
            // Insert new user into firebase
            firebaseManager.registerNewUser(user);

            registerProgressBar.setVisibility(View.GONE);

            if (userType.equals("Customer")){
                // Save Session
                sessionManager.saveSession(userPhone, userType);
                moveToDoneActivity();
            } else {
                moveToRestaurantRegisterDetailsActivity(userPhone);
            }
        }
    }


    public void moveToDoneActivity() {
        Intent intent = new Intent(RegistrationActivity.this, DoneActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void moveToRestaurantRegisterDetailsActivity(String userPhone){
        Intent intent = new Intent(RegistrationActivity.this, RestaurantOnboardingActivity.class);
        intent.putExtra("userPhone", userPhone);

        // Add Transition
        Pair transition = new Pair<View, String>(registerContinueBtn, "transitionContinueBtn");

        // Check if SDK version is high enough for animation
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity.this, transition);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private void moveToLoginActivity() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        // Add Transition
        Pair transition = new Pair<View, String>(registerContinueBtn, "transitionContinueBtn");

        // Check if SDK version is high enough for animation
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity.this, transition);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}