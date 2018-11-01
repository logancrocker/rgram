package com.rgram.rgram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String dummyEmail = "email@email.com";
        final String dummyPass = "pass";

        //define the buttons
        final Button loginButton = findViewById(R.id.button_login);
        final Button registerButton = findViewById(R.id.button_register);
        final EditText emailEntry = findViewById(R.id.email);
        final EditText passwordEntry = findViewById(R.id.password);

        //define action for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEntry.getText().toString();
                String password = passwordEntry.getText().toString();
                if (email == dummyEmail && password == dummyPass)
                {
                    setContentView(R.layout.activity_feed);
                }
                else
                {

                }

            }
        });
    }
}
