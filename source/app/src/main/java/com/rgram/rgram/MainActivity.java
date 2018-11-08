package com.rgram.rgram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.pbLoading);

        final Button login_button = findViewById(R.id.button_login);
        final Button register_button = findViewById(R.id.button_register);

        final TextView errorMsg = findViewById(R.id.error_msg);

        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login logic goes here
                String inputEmail = email.getText().toString().trim();
                String inputPassword = password.getText().toString().trim();
                if (inputEmail.contains("@") && !inputPassword.isEmpty())
                {
                    errorMsg.setVisibility(View.GONE);
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        progressBar.setVisibility(ProgressBar.GONE);
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        Intent intent = new Intent(getBaseContext(), FeedActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(ProgressBar.GONE);
                                    }
                                }
                            });
                }
                else
                {
                    errorMsg.setVisibility(View.VISIBLE);
                }
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we want to redirect to register page
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


}
