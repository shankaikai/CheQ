package com.example.cheq.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cheq.MainActivity;
import com.example.cheq.R;

public class BlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new ViewBasketFragment(), "user activities").addToBackStack("user activities").commit();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(BlankActivity.this, MainActivity.class);
        intent.putExtra("currentActivity", "1");
        startActivity(intent);
    }
}