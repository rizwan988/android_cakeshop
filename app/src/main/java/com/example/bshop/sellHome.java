package com.example.bshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class sellHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private PopupMenu popupMenu;
    private boolean isPopupMenuShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_home);

        // Blue color bar
        View blueBar = findViewById(R.id.blueBar);

        // Menu Icon
        ImageView menuIcon = findViewById(R.id.menuIcon);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show or hide the popup menu based on your requirements
                if (isPopupMenuShowing) {
                    popupMenu.dismiss();
                } else {
                    showPopupMenu(v);
                }
            }
        });

        // Initialize and set up bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Get references to the TextView elements
        TextView wrappersTextView = findViewById(R.id.textView);
        TextView addWrapperTextView = findViewById(R.id.textView1);

        // Set click listener for "Wrappers" TextView
        wrappersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to WrapperItemActivity when "Wrappers" is clicked
                Intent wrappersIntent = new Intent(sellHome.this, WrapperItem.class);

                startActivity(wrappersIntent);
            }
        });

        // Set click listener for "Add Wrapper" TextView
        addWrapperTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to AddWrapperActivity when "Add Wrapper" is clicked
                Intent addWrapperIntent = new Intent(sellHome.this, addWrapper.class);

                startActivity(addWrapperIntent);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_home) {
            // Handle home icon click
            // Add your logic for the home screen if needed
        } else if (itemId == R.id.action_profile) {
            // Handle profile icon click
            // Redirect to the profile activity
            Intent profileIntent = new Intent(sellHome.this, ProfileActivity2.class);

            startActivity(profileIntent);
        } else if (itemId == R.id.action_orders) {
            Intent profileIntent = new Intent(sellHome.this, sellOrders.class);

            startActivity(profileIntent);
        }

        return true;
    }

    // This method gets the shopName from the intent
    private String getShopNameFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("shopName")) {
            return intent.getStringExtra("shopName");
        }
        return null;
    }

    private void showPopupMenu(View view) {
        popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu);

        // Set a click listener for each item in the popup menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_add_wrappers) {
                    // Redirect to AddWrapperActivity when "Add Wrappers" is clicked
                    Intent addWrapperIntent = new Intent(sellHome.this, addWrapper.class);
                    addWrapperIntent.putExtra("shopName", getShopNameFromIntent());
                    startActivity(addWrapperIntent);
                    return true;
                } else if (itemId == R.id.menu_view_wrappers) {
                    Intent viewWrappersIntent = new Intent(sellHome.this, WrapperItem.class);
                    viewWrappersIntent.putExtra("shopName", getShopNameFromIntent());
                    startActivity(viewWrappersIntent);
                    return true;
                } else if (itemId == R.id.menu_logout) {
                    Intent logoutIntent = new Intent(sellHome.this, home.class);
                    startActivity(logoutIntent);
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                isPopupMenuShowing = false;
            }
        });

        popupMenu.show();
        isPopupMenuShowing = true;
    }



    // Add other methods and overrides as needed
}
