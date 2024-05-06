package com.example.bshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Random;

public class customize2 extends AppCompatActivity {

    private LinearLayout flowerList, tieList;

    private TextView totalAmount;
    private RelativeLayout tieContainer;
    private Button flowersButton, tiesButton, completeButton, finishButton, placeorder;
    private ImageView imagePreview;
    private RelativeLayout flowerContainer;
    private HashMap<Integer, Integer> flowerPrices;

    // Add this to store selected flowers and their quantities
    private HashMap<Integer, Integer> selectedFlowers;
    private int CakeCost;  // Added variable for wrapper cost
    private Integer selectedTie;
    private boolean completeButtonClicked = false;
    private TextView finalAmount;
    private static final int MAX_FLOWERS = 30;
    private int flowerCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize2);

        // Find views by ID
        flowerList = findViewById(R.id.flower_list);
        tieList = findViewById(R.id.tie_list);
        totalAmount = findViewById(R.id.total_amount);
        finalAmount = findViewById(R.id.final_amount);
        flowersButton = findViewById(R.id.flowers_button);
        placeorder = findViewById(R.id.placeorder_button);
        tiesButton = findViewById(R.id.ties_button);
        completeButton = findViewById(R.id.complete_button);
        finishButton = findViewById(R.id.finish_button);
        imagePreview = findViewById(R.id.image_preview);
        flowerContainer = findViewById(R.id.flower_container);
        tieContainer = findViewById(R.id.tie_container);

        // Initialize flower prices
        flowerPrices = new HashMap<>();
        flowerPrices.put(R.drawable.pineapplelovecake, 10);
        flowerPrices.put(R.drawable.chocolatelovecake, 15);
        flowerPrices.put(R.drawable.bluelovecake, 10);


        // Initialize selected flowers
        selectedFlowers = new HashMap<>();

        // Set initial visibility
        flowerList.setVisibility(View.VISIBLE);
        tieList.setVisibility(View.GONE);

        // Get extras from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String imageUrl = intent.getStringExtra("imageUrl");
            int price = intent.getIntExtra("price", 0);

            // Assign wrapperCost with the price from the previous page
            CakeCost = price;

            // Update imagePreview with the selected image
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(imagePreview);
            }

            // Handle the price as needed (e.g., updating totalAmount TextView)
            totalAmount.setText("Price: Rs " + price);
        }

        // Set click listeners
        flowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Show flower list and hide tie list
                flowerList.setVisibility(View.VISIBLE);
                tieList.setVisibility(View.GONE);
            }
        });

        tiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Show tie list and hide flower list
                flowerList.setVisibility(View.GONE);
                tieList.setVisibility(View.VISIBLE);
            }
        });

        // Set click listener for flowers in the flower list
        findViewById(R.id.pineapplelovecake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateImagePreview(R.drawable.pineapplelovecake);
            }
        });

        findViewById(R.id.chocolatelovecake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Add logic to update image_preview with the selected flower image
                updateImagePreview(R.drawable.chocolatelovecake);
            }
        });

        findViewById(R.id.bluelovecake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Add logic to update image_preview with the selected flower image
                updateImagePreview(R.drawable.bluelovecake);
            }
        });

        // Set click listener for ties in the tie list
        findViewById(R.id.flowercandle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addTieToPreview(R.drawable.flowercandle);
            }
        });

        findViewById(R.id.onecandle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addTieToPreview(R.drawable.onecandle);
            }
        });
        findViewById(R.id.logocandle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addTieToPreview(R.drawable.logocandle);
            }
        });

        // Add similar click listeners for other tie buttons

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show tie list and hide flower list
                displaySelectedFlowers();
                completeButtonClicked = true;
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateTotalPrice();
            }
        });
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Create an Intent to start the Address activity
                Intent addressIntent = new Intent(customize2.this, Address.class);

                // Start the Address activity
                startActivity(addressIntent);
            }
        });
    }

    private String getFlowerName(int flowerResourceId) {
        if (flowerResourceId == R.drawable.pineapplelovecake) {
            return "pineapplelovecake";
        } else if (flowerResourceId == R.drawable.chocolatelovecake) {
            return "chocolatelovecake";
        } else if (flowerResourceId == R.drawable.bluelovecake) {
            return "bluelovecake";
        } else {
            return "Unknown Flower";
        }
    }

    // Helper method to get tie cost
    private int getTieCost(int tieResourceId) {
        if (tieResourceId == R.drawable.flowercandle) {
            return 30;
        } else if (tieResourceId == R.drawable.onecandle) {
            return 40;
        } else if (tieResourceId == R.drawable.logocandle) {
            return 30;
        } else {
            return 0;
        }
    }

    // New method to calculate total amount based on selected flowers
    private int calculateTotalAmount() {
        int total = 0;

        // Calculate total amount based on selected flowers
        for (int flowerResourceId : selectedFlowers.keySet()) {
            int quantity = selectedFlowers.get(flowerResourceId);
            int price = flowerPrices.get(flowerResourceId);
            total += quantity * price;
        }
        if (selectedTie != null) {
            total += getTieCost(selectedTie);
        }


        return total;
    }

    // Helper method to show a toast message



    // New method to add the selected tie to the tie container
    private void addTieToPreview(int tieResourceId) {
        // Set the selected tie
        selectedTie = tieResourceId;

        // Create a new ImageView for the tie
        ImageView tieImageView = new ImageView(this);

        // Set tie image resource
        tieImageView.setImageResource(tieResourceId);

        // Set tie size to 30dp x 30dp
        RelativeLayout.LayoutParams tieLayoutParams = new RelativeLayout.LayoutParams(
                dpToPx(60), dpToPx(60)
        );
        tieImageView.setLayoutParams(tieLayoutParams);

        // Set the location for the tie (left: 150dp, top: 200dp)
        tieLayoutParams.leftMargin = dpToPx(0);
        tieLayoutParams.topMargin = dpToPx(0);

        // Add the tie ImageView to the tie_container directly in the XML layout
        tieContainer.removeAllViews();  // Remove any existing ties
        tieContainer.addView(tieImageView);
    }




    private void removeFlower(int flowerIndex) {
        if (completeButtonClicked) {
            return;
        }
        // Remove the flower at the specified index from the flowerContainer
        if (flowerIndex >= 0 && flowerIndex < flowerContainer.getChildCount()) {
            flowerContainer.removeViewAt(flowerIndex);
        }
    }



    private void displaySelectedFlowers() {

        totalAmount.setVisibility(View.VISIBLE);

        // Display selected flowers and their quantities in the flower container
        StringBuilder pricesText = new StringBuilder();


        // Display wrapper cost
        pricesText.append("       Cake Cost:                         Rs ").append(CakeCost).append("\n");

        // Display selected tie and its cost
        if (selectedTie != null) {
            int tieCost = getTieCost(selectedTie);
            pricesText.append("       Candle Cost:                           Rs ").append(tieCost).append("\n");
        }
        for (int flowerResourceId : selectedFlowers.keySet()) {
            int quantity = selectedFlowers.get(flowerResourceId);
            int price = flowerPrices.get(flowerResourceId);
            String flowerName = getFlowerName(flowerResourceId);

            // Display each flower and its price
            pricesText.append("       ").append(flowerName).append("  :          (").append(quantity).append("*").append(price).append(")    =    Rs ").append(quantity * price).append("\n");
            // Display the selected flower image in the flower container

        }

        // Display total price
        pricesText.append("       Total Price:                                 Rs ").append(calculateTotalAmount() + CakeCost );

        // Set the formatted prices list to the totalAmount TextView
        totalAmount.setText(pricesText.toString());

        // Save pricesText in UserData as a session
        UserData.getInstance().setDescription(pricesText.toString());

        // Hide buttons and lists after completion
        flowersButton.setVisibility(View.GONE);
        tiesButton.setVisibility(View.GONE);
        flowerList.setVisibility(View.GONE);
        tieList.setVisibility(View.GONE);
        completeButton.setVisibility(View.GONE);
        finishButton.setVisibility(View.VISIBLE);

        combineAndSaveImage();
        centerViewsHorizontally();

    }
    private void combineAndSaveImage() {
        // Capture and save the screenshot of the specified area
        Bitmap screenshotBitmap = captureScreenshotOfArea(
                flowerContainer,
                dpToPx(30),  // left margin in pixels
                dpToPx(150), // top margin in pixels
                dpToPx(222), // width in pixels
                dpToPx(234)  // height in pixels
        );

        // Save the screenshot in UserData
        UserData userData = UserData.getInstance();
        userData.setScreenshot(screenshotBitmap);


    }

    // Helper method to capture a screenshot of a specific area
    private Bitmap captureScreenshotOfArea(View view, int left, int top, int width, int height) {
        Bitmap screenshotBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Create a Canvas using the screenshotBitmap
        Canvas canvas = new Canvas(screenshotBitmap);

        // Translate the canvas to the specified area
        canvas.translate(-left, -top);

        // Draw the specified area onto the Canvas
        findViewById(android.R.id.content).draw(canvas);

        return screenshotBitmap;
    }



    // Helper method to convert dp to pixels
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
    private void updateTotalPrice() {
        // Calculate total price based on selected flowers, wrapper cost, and tie cost
        int totalPrice = calculateTotalAmount() + CakeCost;

        // Add delivery fee of 40
        int totalWithDelivery = totalPrice + 40;

        // Create a StringBuilder to build the formatted text
        StringBuilder deliveryFeeTextView = new StringBuilder();

        // Append total amount to the StringBuilder
        deliveryFeeTextView.append("       Total Amount:                       Rs ").append(totalPrice);

        // Append delivery fee if there is a selected tie
        if (selectedTie != null) {
            deliveryFeeTextView.append("\n       Delivery Fee:                          Rs 40");
        }

        // Append total with delivery to the StringBuilder
        deliveryFeeTextView.append("\n       Total:                                     Rs ").append(totalWithDelivery);

        // Set the formatted prices list to the finalAmount TextView
        totalAmount.setText(deliveryFeeTextView.toString());
        finishButton.setVisibility(View.GONE);

        placeorder.setVisibility(View.VISIBLE);
        // Save pricesText and totalWithDelivery in UserData as session data

        UserData.getInstance().setprice(totalWithDelivery);

    }
    private void centerViewsHorizontally() {
        // Center imagePreview
        RelativeLayout.LayoutParams imagePreviewLayoutParams = (RelativeLayout.LayoutParams) imagePreview.getLayoutParams();
        imagePreviewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imagePreview.setLayoutParams(imagePreviewLayoutParams);

        // Center flowerContainer
        RelativeLayout.LayoutParams flowerContainerLayoutParams = (RelativeLayout.LayoutParams) flowerContainer.getLayoutParams();
        flowerContainerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        flowerContainer.setLayoutParams(flowerContainerLayoutParams);

        // Center tieContainer
        RelativeLayout.LayoutParams tieContainerLayoutParams = (RelativeLayout.LayoutParams) tieContainer.getLayoutParams();
        tieContainerLayoutParams.leftMargin = 280;  // Set left margin directly
        tieContainer.setLayoutParams(tieContainerLayoutParams);
    }
    private void updateImagePreview(int flowerResourceId) {
        // Update imagePreview with the selected flower image
        ImageView flowerImageView = new ImageView(this);
        flowerImageView.setImageResource(flowerResourceId);
        imagePreview.setImageDrawable(flowerImageView.getDrawable());
    }

}
