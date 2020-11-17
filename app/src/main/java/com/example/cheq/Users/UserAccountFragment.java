package com.example.cheq.Users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.cheq.MainActivity;
import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.R;
import com.google.firebase.database.DatabaseReference;

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

    // Variables for each View element
    CardView contactUs;
    CardView howToUse;
    CardView leaveRating;
    CardView logOut;

    // Firebase
    FirebaseManager firebaseManager;

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

        firebaseManager = new FirebaseManager();

        DatabaseReference rootRef = firebaseManager.rootRef;
        userName = rootRef.child("Users").child("name").getKey().toString();
        userEmail = rootRef.child("Users").child("userEmail").getKey().toString();
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

        // Connect the user text view elements to the variables
        userNameTextView = view.findViewById(R.id.userNameTextView);
        userEmailTextView = view.findViewById(R.id.userEmailTextView);

        // Set the user text view elements to the user's name and email
        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);

        // Set a click listener to each view element
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        leaveRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
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