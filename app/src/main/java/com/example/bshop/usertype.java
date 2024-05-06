package com.example.bshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class usertype extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);

        Button bt1 = findViewById(R.id.bt);
        Button bt2 = findViewById(R.id.bt1);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the UserActivity when the USER button is clicked
                Intent intent = new Intent(usertype.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the AdminActivity when the ADMIN button is clicked
                Intent intent = new Intent(usertype.this, login2.class);
                startActivity(intent);
            }
        });
    }
}