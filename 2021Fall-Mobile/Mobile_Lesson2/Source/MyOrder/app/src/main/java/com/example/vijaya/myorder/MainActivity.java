package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int COFFEE_PRICE = 5;
    final int WHIPPED_CREAM_PRICE = 1;
    final int CHOCOLATE_PRICE = 2;
    final int OLIVES_PRICE = 2;
    final int CORN_PRICE = 5;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button orderButton = (Button) findViewById(R.id.button_order);

        Button summaryButton = (Button) findViewById(R.id.button_summary);

        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent summaryIntent = new Intent(MainActivity.this, SummaryActivity.class);

                EditText userInputNameView = (EditText) findViewById(R.id.user_input);
                String userInputName = userInputNameView.getText().toString();

                // Check if the Olives is selected
                CheckBox olives = (CheckBox) findViewById(R.id.olives_checked);
                boolean hasOlives = olives.isChecked();

                // Check if the Chocolate is selected
                CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
                boolean hasChocolate = chocolate.isChecked();

                // Check if the Olives is selected
                CheckBox whipped = (CheckBox) findViewById(R.id.whipped_cream_checked);
                boolean hasWhipped = whipped.isChecked();

                // Check if the Olives is selected
                CheckBox corn = (CheckBox) findViewById(R.id.corn_checked);
                boolean hasCorn = corn.isChecked();

                // Calculate the total Price and store it
                float totalPrice = calculatePrice(hasWhipped, hasChocolate);
                //Create an Order Summary Message to parse in HomeActivity
                String orderSummaryMessage = createOrderSummary(userInputName, hasWhipped, hasChocolate, totalPrice);

                // create and store the order summary
                summaryIntent.putExtra("USER NAME", userInputName);
                summaryIntent.putExtra("INGREDIENTS", orderSummaryMessage);
                startActivity(summaryIntent);
            }
        });
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder() {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if whipped cream is selected
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCream.isChecked();

        // check if chocolate is selected
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolate.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasWhippedCream, hasChocolate);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasWhippedCream, hasChocolate, totalPrice);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
        Intent redirect = new Intent(MainActivity.this, SummaryActivity.class);
        redirect.putExtra("MESSAGE", orderSummaryMessage);
        MainActivity.this.startActivity(redirect);

        sendEmail(userInputName, orderSummaryMessage);
    }

    public void sendEmail(String name, String output) {
        // Write the relevant code for triggering email

        // Hint to accomplish the task

        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }*/
        // read user input
        String userName = name;

        // read user email
        String userEmail = "maheshmokkapati21@gmail.com";
        String[] userEmails = userEmail.split(",");

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Log.i("Send email", "");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, userEmails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Delivery Details");
        emailIntent.putExtra(Intent.EXTRA_TEXT, output);
        try {
            startActivity(Intent.createChooser(emailIntent, "Choose any email client"));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasWhippedCream, boolean hasChocolate, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_whipped_cream, boolToString(hasWhippedCream)) + "\n" +
                getString(R.string.order_summary_chocolate, boolToString(hasChocolate)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = COFFEE_PRICE;
        if (hasWhippedCream) {
            basePrice += WHIPPED_CREAM_PRICE;
        }
        if (hasChocolate) {
            basePrice += CHOCOLATE_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred cups of coffee");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one cup of coffee");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}