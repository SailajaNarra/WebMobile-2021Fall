package com.example.vijaya.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        //Variable Initialization with Intents from the MainActivity which have user data of Orders.
        String username = getIntent().getStringExtra("USER NAME");
        String summaryInfo = getIntent().getStringExtra("INGREDIENTS");

        // Printing Data using Text Views that we got through Intents
        TextView name = (TextView) findViewById(R.id.dearUserText);
        name.setText("Dear " + " " + username);
        TextView summary = (TextView) findViewById(R.id.summary_details);
        //Summary of Orders that we got through Intents
        summary.setText(summaryInfo);
        //Order Button Initialization
        Button orderButton = (Button) findViewById(R.id.button_backToOrder);

    }
    public void goToOrder (View view){
        //Function which return to the MainActivity that from where we can Order
        Intent goOrderIntent = new Intent(SummaryActivity.this,MainActivity.class);
        startActivity(goOrderIntent);
    }
}
