package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // login button is created and event is added on click
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myLogin();
            }
        });
    }

//    on click of login this method will be called
    public void myLogin() {

//        input fields reference is taken and stored into local variables as strings
        EditText usernameCtrl = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText passwordCtrl = (EditText) findViewById(R.id.editTextTextPassword);

        String username = usernameCtrl.getText().toString();
        String password = passwordCtrl.getText().toString();

        boolean validationFlag = false;

//        validation for inputs fields
        if(!username.isEmpty() && !password.isEmpty()){
            if(username.equals("Admin") && password.equals("Admin")) {
                validationFlag = true;
            }
        }

        if(validationFlag){
            Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(redirect);
            Toast login_success = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT);
            login_success.show();

        }
        else{
            Toast empty_password = Toast.makeText(getApplicationContext(), "Please check username and password", Toast.LENGTH_SHORT);
            empty_password.show();
        }

    }


}