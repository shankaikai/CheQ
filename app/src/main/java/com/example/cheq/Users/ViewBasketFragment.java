package com.example.cheq.Users;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.RestaurantInfo.MenuFragment;
import com.example.cheq.RestaurantInfo.RestaurantInfoActivity;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;

public class ViewBasketFragment extends Fragment {

    // 2 Decimal Places
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    // Session Manager
    SessionManager sessionManager;

    // Firebase
    FirebaseManager firebaseManager;

    // Restaurant Info
    String restaurantName;
    String restaurantID;

    // User Info
    String userID;

    // UI Elements
    TextView restaurantNameBasket;
    TextView totalPriceTextView;
    RecyclerView basketList;
    CardView placeOrder;
    CardView orderPlaced;
    Button backArrow;
    ConstraintLayout basketLayout;

    BasketListAdapter basketAdapter;

    // Item Deletion
    ItemTouchHelper itemTouchHelper;

    // Order Items
    HashMap<String, HashMap<String, String>> basketItems;

    public ViewBasketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_basket, container, false);

        // Initialise firebase
        firebaseManager = new FirebaseManager();
        final DatabaseReference rootRef = firebaseManager.rootRef;

        // Initialise session manager & userID to send preorder to firebase
        sessionManager = SessionManager.getSessionManager(getActivity());
        userID = sessionManager.getUserPhone();

        // Restore basketItems if sharedPreferences has preorder
        basketItems = MenuFragment.restoreMap(sessionManager.getPreorder(), Integer.parseInt(sessionManager.getPreorderUniqueCount()));

        // Initialise UI Elements
        restaurantNameBasket = view.findViewById(R.id.restaurantNameBasket);
        totalPriceTextView = view.findViewById(R.id.basketTotalPrice);
        placeOrder = view.findViewById(R.id.placeOrder);
        orderPlaced = view.findViewById(R.id.orderPlaced);
        backArrow = view.findViewById(R.id.backArrow);
        basketLayout = view.findViewById(R.id.basketLayout);

        // Retrieve Restaurant info based on the previous Activity
        if (getActivity().toString().contains("RestaurantInfoActivity")) {
            RestaurantInfoActivity activity = (RestaurantInfoActivity) getActivity();
            restaurantName = activity.getRestaurantName();
            restaurantID = activity.getRestaurantID();
        } else {
            restaurantName = sessionManager.getPreorderRestName();
            Log.i("rest", restaurantName);
        }

        // if there are preorder items, button is greyed out when they view their order
        if (sessionManager.getPreorderStatus().equals("Ordered")) {
            placeOrder.setVisibility(View.INVISIBLE);
            orderPlaced.setVisibility(View.VISIBLE);
        }

        restaurantNameBasket.setText(restaurantName);
        totalPriceTextView.setText("$" + sessionManager.getPreorderTotal());

        // Initialise Recycler View
        basketList = (RecyclerView) view.findViewById(R.id.basketList);
        basketList.setLayoutManager(new LinearLayoutManager(ViewBasketFragment.this.getContext()));
        basketAdapter = new com.example.cheq.Users.BasketListAdapter(basketItems);
        basketList.setAdapter(basketAdapter);

        // Configure settings upon click for placeOrderBtn
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String dish: basketItems.keySet()) {
                    Integer quantity = Integer.parseInt(basketItems.get(dish).get("quantity"));
                    Preorder item = new Preorder(quantity, dish, userID, restaurantID);
                    firebaseManager.addPreorder(item);
                }
                sessionManager.setPreorderRestName(restaurantName);
                sessionManager.updatePreorderStatus("Ordered");

                placeOrder.setVisibility(View.INVISIBLE);
                orderPlaced.setVisibility(View.VISIBLE);

                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_LONG).show();

                if (getActivity().toString().contains("RestaurantInfoActivity")) {
                    View restInfo = getActivity().findViewById(R.id.restInfoLayout);
                    restInfo.setVisibility(View.VISIBLE);
                    basketLayout.setVisibility(View.INVISIBLE);
                    getActivity().findViewById(R.id.menuCL).setPadding(0, 0, 0, 0);
                } else {
                    getActivity().onBackPressed();
                }
            }
        });

        // set onClickListener for back arrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getFragmentManager().popBackStackImmediate();

                // Toggle visibility of the restaurant info to toggle to the basket view
                if (getActivity().toString().contains("RestaurantInfoActivity")) {
                    View restInfo = getActivity().findViewById(R.id.restInfoLayout);
                    View basketCL = getActivity().findViewById(R.id.basketCL);
                    ConstraintLayout menuCL = getActivity().findViewById(R.id.menuCL);
                    restInfo.setVisibility(View.VISIBLE);
                    if (!sessionManager.hasPreorder()) {
                        basketCL.setVisibility(View.INVISIBLE);
                        menuCL.setPadding(0, 0, 0, 0);
                    } else {
                        basketCL.setVisibility(View.VISIBLE);
                        menuCL.setPadding(0, 0, 0, 160);
                    }
                    basketLayout.setVisibility(View.INVISIBLE);
                } else {
                    getActivity().onBackPressed();
                }
            }
        });

        // Enable deleting of items only if order is not sent
        if (!sessionManager.getPreorderStatus().equals("Ordered")) {
            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    BasketListAdapter.ViewHolder vH = (BasketListAdapter.ViewHolder) viewHolder;
                    int position = vH.getAdapterPosition();
                    String dishName = BasketListAdapter.dishName.get(position);
                    removeDish(dishName);
                    basketAdapter.notifyItemRemoved(position);
                    basketList.setAdapter(new BasketListAdapter(basketItems));
                    if (basketItems.size() == 0) {
                        // Toggle back to restaurant info if basket is empty
                        View restInfo = getActivity().findViewById(R.id.restInfoLayout);
                        View basketCL = getActivity().findViewById(R.id.basketCL);
                        ConstraintLayout menuCL = getActivity().findViewById(R.id.menuCL);
                        restInfo.setVisibility(View.VISIBLE);
                        if (!sessionManager.hasPreorder()) {
                            basketCL.setVisibility(View.INVISIBLE);
                            menuCL.setPadding(0, 0, 0, 0);
                        } else {
                            basketCL.setVisibility(View.VISIBLE);
                            menuCL.setPadding(0, 0, 0, 160);
                        }
                        basketLayout.setVisibility(View.INVISIBLE);
                    }
                }
            };

            itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(basketList);
        }

        return view;
    }

    public void removeDish(String dishName) {
        Integer quantityToRemove = Integer.parseInt(basketItems.get(dishName).get("quantity"));
        Double priceToRemove = Double.parseDouble(basketItems.get(dishName).get("price").substring(1));
        basketItems.remove(dishName);
        if (basketItems.size() == 0) {
            sessionManager.removePreorder();
        } else {
            Integer currentCount = Integer.parseInt(sessionManager.getPreorderCount()) - quantityToRemove;
            Double currentTotal = Double.parseDouble(sessionManager.getPreorderTotal()) - (priceToRemove * quantityToRemove);
            String newString = MenuFragment.stringify(basketItems);
            sessionManager.savePreorder(currentCount.toString(), currentTotal.toString(), newString, restaurantID);
        }
        totalPriceTextView.setText("$" + sessionManager.getPreorderTotal());
    }
}