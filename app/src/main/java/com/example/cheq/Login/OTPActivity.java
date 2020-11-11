package com.example.cheq.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.cheq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    // Views
    PinView pin;
    TextView otpPrompt;
    TextView otpMsg;
    Button otpContinueBtn;
    ImageView otpCloseBtn;

    // Firebase Auth
    private FirebaseAuth mAuth;
    String verificationId;

    // Intent Strings
    String userPhone;
    String userName;
    String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        // Hooks
        pin = findViewById(R.id.pinVew);
        otpPrompt = findViewById(R.id.otpPrompt);
        otpMsg = findViewById(R.id.otpMsg);
        otpCloseBtn = findViewById(R.id.otpBackBtn);
        otpCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLoginActivity();
            }
        });
        otpContinueBtn = findViewById(R.id.otpContinueBtn);
        otpContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMainActivity();
            }
        });

        // Get name and phone number and source from LoginActivity
        userPhone = getIntent().getStringExtra("usePhone");
        userName = getIntent().getStringExtra("userName");
        source = getIntent().getStringExtra("source");

        // Set TextViews depending on whether existing or new
        if (source == "existing") {
            otpMsg.setText("Welcome Back " + userName + "!");
        } else {
            otpMsg.setText("Welcome " + userName + "!");
        }

        otpPrompt.setText(getResources().getString(R.string.otpPrompt) + " " + userPhone);

        sendOTP(userPhone);
    }

    // Function to send OTP to user
    private void sendOTP(String userPhone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(userPhone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    // Automatically fill OTP for user
                    pin.setText(code);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT);
            }
        };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // TODO:
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT);
                        }
                    }
                }
            });
    }

    // TODO: moveFunctions
    private void moveToMainActivity() {
    }

    private void moveToLoginActivity() {
    }
}