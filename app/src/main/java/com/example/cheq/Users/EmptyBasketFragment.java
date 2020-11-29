package com.example.cheq.Users;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheq.MainActivity;
import com.example.cheq.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmptyBasketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmptyBasketFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // UI Elements
    TextView totalPriceTextView;
    RecyclerView basketList;
    CardView placeOrder;
    CardView orderPlaced;

    BasketListAdapter basketAdapter;

    // Order Items
    HashMap<String, HashMap<String, String>> basketItems;

    public EmptyBasketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmptyBasketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmptyBasketFragment newInstance(String param1, String param2) {
        EmptyBasketFragment fragment = new EmptyBasketFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_basket, container, false);

        // Initialise FB reference


        // Initialise UI Elements
        totalPriceTextView = view.findViewById(R.id.basketTotalPrice);
        placeOrder = view.findViewById(R.id.placeOrder);
        orderPlaced = view.findViewById(R.id.orderPlaced);

        // TODO: Check if the order is placed
        // If placed, the orderPlaced cardView is activated
//        if (true) {
//            placeOrder.setVisibility(View.INVISIBLE);
//            orderPlaced.setVisibility(View.VISIBLE);
//        }

        // TODO: Change hardcoded data to retrieve data from previous view
        // Sample Data
        basketItems = new HashMap<>();
        HashMap<String, String> itemDetail = new HashMap<>();
        itemDetail.put("quantity", "1");
        itemDetail.put("price", "$9.50");
        basketItems.put("char siew ramen", itemDetail);

        // Initialise Recycler View
        basketList = (RecyclerView) view.findViewById(R.id.basketList);
        basketList.setLayoutManager(new LinearLayoutManager(EmptyBasketFragment.this.getContext()));
        basketAdapter = new com.example.cheq.Users.BasketListAdapter(basketItems);
        basketList.setAdapter(basketAdapter);

        // Configure settings upon click for placeOrderBtn
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send preorder items to Firebase
                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_LONG).show();
                placeOrder.setVisibility(View.INVISIBLE);
                orderPlaced.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}