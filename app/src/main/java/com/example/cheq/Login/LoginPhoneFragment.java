package com.example.cheq.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cheq.R;


public class LoginPhoneFragment extends Fragment {

    // Temporary validation
    String validuser = "93821490";

    // Views
    EditText inputPhone;
    TextView errorMsg;
    Button phoneContinueBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Phone number input
        inputPhone = (EditText) view.findViewById(R.id.inputPhone);

        // Error Message
        errorMsg = (TextView) view.findViewById(R.id.errorMsg);

        // Continue Button
        phoneContinueBtn = (Button) view.findViewById(R.id.phoneContinueBtn);

        // Create the onClickListener for the Continue Button
        phoneContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser(v);
            }
        });
    }

    // Check if new or existing user
    public void checkUser(View view) {

        // Grab user input
        String userPhone = inputPhone.getText().toString();

        // Check if the phone number input is valid
        if (isValidNumber(userPhone)) {

            // TODO: Check database for userName
            if (userPhone.equals(validuser)) {

                // Call PasswordFragment using changeFragment
                ((LoginActivity) getActivity()).changeFragment("existing");

                // Create a new User object (ID generation to be done by SQL)
                // User user = new User(1, userPhone, );

                // Save Session of User
//                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
//                sessionManagement.saveSession(user);
//                moveToMainActivity();

            } else {

                // Call RegistrationFragment using changeFragment
                ((LoginActivity) getActivity()).changeFragment("new");
            }
        }
        else {
            errorMsg.setText("Please enter a valid phone number");
        }
    }

    // Check phone number is valid
    public boolean isValidNumber(String s) {
        if (s.length() == 8) {
            try {
                int num = Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number!");
            }
        }
        return false;
    }

}