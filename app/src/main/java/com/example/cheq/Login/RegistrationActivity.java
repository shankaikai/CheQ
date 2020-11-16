package com.example.cheq.Login;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.example.cheq.Entities.User;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.MainActivity;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.Restaurant.RestaurantActivity;
import com.google.android.gms.tasks.Tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    // Views
    EditText inputName;
    EditText inputEmail;
    EditText inputPassword;
    Button registerContinueBtn;
    RadioGroup userType;
    ImageView registerBackBtn;

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

        firebaseManager = new FirebaseManager();
        sessionManager = SessionManager.getSessionManager(RegistrationActivity.this);
    }

    // Add new users to database after validation
    public void addNewUser() {

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
        if (userName.isEmpty() || !validEmail(userEmail) || userPassword.isEmpty()){
            Toast.makeText(RegistrationActivity.this, "One or more invalid inputs", Toast.LENGTH_SHORT).show();
        }
        else {
            // Save Session
            sessionManager.saveSession(userPhone, userType);

            // Create new User object
            User user = new User(userPhone, userName, userEmail, userPassword, userType);

            // Insert new user into firebase
            firebaseManager.registerNewUser(user);

            if (userType.equals("Customer")){
                moveToDoneActivity();
            } else {
                moveToRestaurantActivity();
            }
        }
    }

    private boolean validEmail(String userEmail) {
        // Email regex pattern
        String regex = "^(.+)@(.+)$";

        // Initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Initialize the Matcher object
        Matcher matcher = pattern.matcher(userEmail);

        // Return if email matches the regex pattern
        return matcher.matches();
    }

    public void moveToDoneActivity() {
        Intent intent = new Intent(RegistrationActivity.this, DoneActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void moveToRestaurantActivity() {
        Intent intent = new Intent(RegistrationActivity.this, RestaurantActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void moveToLoginActivity() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        // Add Transition
        Pair transition = new Pair<View, String>(registerContinueBtn, "transitionContinueBtn");

        // Check if SDK version is high enough for animation
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity.this, transition);
            startActivity(intent, options.toBundle());
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } else {
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    }
}