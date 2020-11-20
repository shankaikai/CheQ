package com.example.cheq.Users;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.cheq.Login.PasswordActivity;
import com.example.cheq.MainActivity;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.cheq.Users.UserAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Edit Profile
    Button editProfileBtn;

    // Variables for each View element
    CardView contactUs;
    CardView howToUse;
    CardView leaveRating;
    CardView logOut;

    // Variables for User Info elements
    TextView userNameTextView;
    TextView userEmailTextView;
    String userName;
    String userEmail;

    public UserAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.cheq.Users.UserAccountFragment newInstance(String param1, String param2) {
        com.example.cheq.Users.UserAccountFragment fragment = new com.example.cheq.Users.UserAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Retrieve the local storage of the user's name and email
        MainActivity activity = (MainActivity) getActivity();
        userEmail = activity.getUserEmail();
        userName = activity.getUserName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        // Connect the view elements to the variables
        contactUs = view.findViewById(R.id.contact_us);
        howToUse = view.findViewById(R.id.how_to_use);
        leaveRating = view.findViewById(R.id.leave_a_rating);
        logOut = view.findViewById(R.id.log_out);
        editProfileBtn = view.findViewById(R.id.editProfileBtn);

        // Connect the user text view elements to the variables
        userNameTextView = view.findViewById(R.id.userNameTextView);
        userEmailTextView = view.findViewById(R.id.userEmailTextView);

        // Set the user text view elements to the user's name and email
        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);

        // Set a click listener to the editProfileBtn
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Edit Profile Fragment
            }
        });

        // Set a click listener to each view element
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Connect Email
            }
        });

        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Connect to FAQ
            }
        });

        leaveRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Connect to Play Store/Leave a Rating Page
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).logout(view);
            }
        });

        return view;
    }
}