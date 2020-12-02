package com.example.cheq.RestaurantInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheq.Managers.FirebaseManager;
import com.example.cheq.Managers.SessionManager;
import com.example.cheq.R;
import com.example.cheq.Users.ViewBasketFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MenuFragment extends Fragment implements MenuAdapter.onRestaurantListener {

    private RecyclerView recyclerView;

    // Popup Window Variables
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog addDishDialog;
    private TextView addDishName, addDishQuantity;
    private CardView addDishBtn;
    private CardView cancelAddBtn;

    // Session Manager
    SessionManager sessionManager;

    // Firebase
    FirebaseManager firebaseManager;

    // Restaurant ID
    String restaurantID;

    // Menu Variables
    private ArrayList<String> dishNames;
    private ArrayList<String> dishPrices;
    private ArrayList<String> dishImages;
    private ArrayList<Double> rawPrices;

    // 2 Decimal Places
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    // Variables needed for preorder
    HashMap<String, HashMap<String, String>> preorderItems;
    Double currentTotal;
    Integer currentItems;
    CardView basketCardView;
    TextView itemsTextView;
    TextView totalBasketPrice;
    ConstraintLayout basketCL;
    ConstraintLayout menuCL;

    public MenuFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Initialise Preorder Items
        preorderItems = new HashMap<>();

        // Initialise data variables
        dishNames = new ArrayList<>();
        dishPrices = new ArrayList<>();
        dishImages = new ArrayList<>();
        rawPrices = new ArrayList<>();
        currentTotal = 0.0;
        currentItems = 0;

        // Initialise menu fragment constraint layout
        menuCL = view.findViewById(R.id.menuCL);

        sessionManager = SessionManager.getSessionManager(getActivity());

        // Initialise firebaseManager
        firebaseManager = new FirebaseManager();

        final DatabaseReference rootRef = firebaseManager.rootRef;
        RestaurantInfoActivity activity = (RestaurantInfoActivity) getActivity();
        restaurantID = activity.getRestaurantID();

        // sessionManager.removePreorder();
        // sessionManager.cancelPreorder();

        // If there are preorder items in the basket, restore it
        if (sessionManager.hasPreorder() && restaurantID.equals(sessionManager.getPreorderRest())) {
            currentTotal = Double.parseDouble(sessionManager.getPreorderTotal());
            currentItems = Integer.parseInt(sessionManager.getPreorderCount());
            preorderItems = restoreMap(sessionManager.getPreorder(), Integer.parseInt(sessionManager.getPreorderUniqueCount()));
            basketCardView = getActivity().findViewById(R.id.basketCardView);
            basketCL = getActivity().findViewById(R.id.basketCL);
            menuCL.setPadding(0, 0, 0, 160);
            basketCL.setVisibility(View.VISIBLE);
            basketCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewBasketFragment fragment = new ViewBasketFragment();

                    // Toggle visibility of the restaurant info to toggle to the basket view
                    View restInfo = getActivity().findViewById(R.id.restInfoLayout);
                    restInfo.setVisibility(View.INVISIBLE);
                    basketCL.setVisibility(View.INVISIBLE);
                    menuCL.setPadding(0, 0, 0, 0);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, fragment).commit();
                }
            });
            itemsTextView = getActivity().findViewById(R.id.itemsTextView);
            totalBasketPrice = getActivity().findViewById(R.id.totalBasketPrice);
            if (currentItems == 1) {
                itemsTextView.setText(currentItems.toString() + " item");
            } else {
                itemsTextView.setText(currentItems.toString() + " items");
            }
            totalBasketPrice.setText("$" + df2.format(currentTotal));
        } else if (sessionManager.hasPreorder() && !restaurantID.equals(sessionManager.getPreorderRest())) {
            if (!sessionManager.getPreorderStatus().equals("Ordered")) {
                Toast.makeText(getContext(), "Your basket has items from another restaurant", Toast.LENGTH_LONG).show();
            }
        }

        // Retrieve menu items
        rootRef.child("Menu").child(restaurantID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (Iterator<DataSnapshot> item = snapshot.getChildren().iterator(); item.hasNext();) {
                    String dishName = item.next().getKey();
                    Double price = Double.parseDouble(snapshot.child(dishName).child("dishPrice").getValue().toString());
                    rawPrices.add(price);
                    String dishPrice = "$" + df2.format(price);
                    String imageURL = snapshot.child(dishName).child("downloadUrl").getValue().toString();
                    dishNames.add(dishName);
                    dishPrices.add(dishPrice);
                    dishImages.add(imageURL);
                }

                // Initialise menu view
                recyclerView = view.findViewById(R.id.menuRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(MenuFragment.this.getContext()));
                recyclerView.setAdapter(new MenuAdapter(getContext(), dishNames, dishPrices, dishImages, MenuFragment.this));
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onRestaurantClick(String dishName) {
        if (sessionManager.getPreorderStatus().equals("Ordered")) {
            Toast.makeText(getContext(), "You have already made a pre-order. You may view it under Current Activities.", Toast.LENGTH_LONG).show();
        } else if (!sessionManager.getQueueStatus().equals("In Queue")) {
            Toast.makeText(getContext(), "Please join the queue to make a pre-order", Toast.LENGTH_LONG).show();
        } else {
            addDishPopup(dishName);
        }
    }

    public void addDishPopup(final String dishName) {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View addDishPopupView = getLayoutInflater().inflate(R.layout.add_dish_popup, null);

        // connect the UI elements to the variables
        addDishName = (TextView) addDishPopupView.findViewById(R.id.addDishName);
        addDishQuantity = (TextView) addDishPopupView.findViewById(R.id.addDishQuantity);
        addDishBtn = (CardView) addDishPopupView.findViewById(R.id.addDishBtn);
        cancelAddBtn = (CardView) addDishPopupView.findViewById(R.id.cancelAddBtn);

        // create the dialog
        dialogBuilder.setView(addDishPopupView);
        addDishDialog = dialogBuilder.create();
        addDishDialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        addDishDialog.show();

        // set the text
        addDishName.setText(dishName);

        // set the onClick listener for the cancelAddBtn
        cancelAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDishDialog.dismiss();
            }
        });

        // set the onClick listener for the addDishBtn
        addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if sessionManager does not have any preorder records (maybe due to deletion previously)
                // initialise all preorder data variables to 0
                if (!sessionManager.hasPreorder()) {
                    preorderItems = new HashMap<>();
                    currentTotal = 0.0;
                    currentItems = 0;
                }

                // Find index of the dish in the data variable
                Integer idx = dishNames.indexOf(dishName);
                Double price = rawPrices.get(idx);

                // Increase number of items in basket and total price of basket
                currentTotal += price;
                currentItems += 1;

                // Prepare the item info to store locally
                if (preorderItems.containsKey(dishName)) {
                    Integer currentQuantity = Integer.parseInt(preorderItems.get(dishName).get("quantity"));
                    Integer newQuantity = currentQuantity + 1;
                    preorderItems.get(dishName).put("quantity", newQuantity.toString());
                } else {
                    HashMap<String, String> itemInfo = new HashMap<>();
                    itemInfo.put("quantity", "1");
                    itemInfo.put("price", dishPrices.get(idx));
                    preorderItems.put(dishName, itemInfo);
                }

                // Initialise basketCardView if it has not been initialised
                if (basketCardView == null || basketCL == null) {
                    basketCL = getActivity().findViewById(R.id.basketCL);
                    basketCardView = getActivity().findViewById(R.id.basketCardView);
                    itemsTextView = getActivity().findViewById(R.id.itemsTextView);
                    totalBasketPrice = getActivity().findViewById(R.id.totalBasketPrice);
                    basketCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ViewBasketFragment fragment = new ViewBasketFragment();

                            // Toggle visibility of the restaurant info to toggle to the basket view
                            View restInfo = getActivity().findViewById(R.id.restInfoLayout);
                            restInfo.setVisibility(View.INVISIBLE);
                            basketCL.setVisibility(View.INVISIBLE);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, fragment, "menu").addToBackStack("menu").commit();
                        }
                    });
                }

                // Display the view basket constraint layout
                getActivity().findViewById(R.id.basketCL).setVisibility(View.VISIBLE);
                menuCL.setPadding(0, 0, 0, 160);

                // Display updated item count and total price
                if (currentItems == 1) {
                    itemsTextView.setText(currentItems.toString() + " item");
                } else {
                    itemsTextView.setText(currentItems.toString() + " items");
                }
                totalBasketPrice.setText("$" + df2.format(currentTotal));

                // Save to shared preferences or update the current preorders
                sessionManager.savePreorder(currentItems.toString(), currentTotal.toString(), stringify(preorderItems), restaurantID);
                Integer uniqCount = new Integer(preorderItems.size());
                sessionManager.uniqDishCount(uniqCount.toString());

                // Dismiss the dialog
                addDishDialog.dismiss();
            }
        });
    }

    public static String stringify(HashMap<String, HashMap<String, String>> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            HashMap<String, String> details = map.get(key);
            sb.append(key);
            for (String miniKey : details.keySet()) {
                sb.append(",");
                sb.append(miniKey + ":");
                sb.append(details.get(miniKey));
            }
            sb.append("/");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static HashMap<String, HashMap<String, String>> restoreMap(String preorderString, Integer count) {
        Log.i("preorder", preorderString);
        HashMap<String, HashMap<String, String>> output = new HashMap<>();
        if (count == 1) {
            HashMap<String, String> map = new HashMap<>();
            String[] indiv = preorderString.split(",");
            String[] split1 = indiv[1].split(":");
            String[] split2 = indiv[2].split(":");
            map.put(split1[0], split1[1]);
            map.put(split2[0], split2[1]);
            output.put(indiv[0], map);
        } else {
            String items[] = preorderString.split("/");
            for (String item : items) {
                HashMap<String, String> map = new HashMap<>();
                String[] indiv = item.split(",");
                String[] split1 = indiv[1].split(":");
                String[] split2 = indiv[2].split(":");
                map.put(split1[0], split1[1]);
                map.put(split2[0], split2[1]);
                output.put(indiv[0], map);
            }
        }
        return output;
    }
}