package com.rgram.rgram;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    LinearLayout msignupContainer;
    AnimationDrawable mAnimationDrwable;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //grab progress bar

        msignupContainer=findViewById(R.id.signip_container);
        mAnimationDrwable= (AnimationDrawable) msignupContainer.getBackground();
        mAnimationDrwable.setEnterFadeDuration(2000);
        mAnimationDrwable.setExitFadeDuration(2000);

        progressBar = findViewById(R.id.pbLoading);
        //get instance of the database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        //grab button
        final Button register_button = findViewById(R.id.button_register_submit);
        //grab inputs
        final EditText email = findViewById(R.id.register_email);
        final EditText password = findViewById(R.id.register_password);
        final EditText confirm_password = findViewById(R.id.register_confirm_password);
        final EditText name = findViewById(R.id.register_name);
        //grab error message
        final TextView register_error_msg = findViewById(R.id.register_error_msg);

        TextView haveaccount=findViewById(R.id.have_account);
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear error message
                register_error_msg.setVisibility(View.GONE);

                //here we get all the input information
                final String inputEmail = email.getText().toString().trim();
                final String inputPassword = password.getText().toString().trim();
                final String inputConfirmPassword = confirm_password.getText().toString().trim();
                final String inputName = name.getText().toString().trim();
                if (inputEmail.contains("@") && inputPassword.equals(inputConfirmPassword) && !inputName.isEmpty()
                        && !inputPassword.isEmpty() && !inputConfirmPassword.isEmpty())
                {
                    progressBar.setVisibility(ProgressBar.VISIBLE);

                    //here we register the user with authentication
                    firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                //show progress bar
                                                progressBar.setVisibility(ProgressBar.GONE);

                                                //get current user
                                                FirebaseUser currUser = firebaseAuth.getCurrentUser();
                                                String uid = currUser.getUid();
                                                //create new user with input data
                                                User user = new User(inputName, inputEmail, uid);
                                                //write the user to the users tree under its uid
                                                database.child("users").child(currUser.getUid()).setValue(user);

                                                //load feed activity
                                                Intent intent = new Intent(getBaseContext(), FeedActivity.class);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(ProgressBar.GONE);
                                            }
                                        }
                            });
                }
                else
                {
                    register_error_msg.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(mAnimationDrwable!=null&&!mAnimationDrwable.isRunning()){
            mAnimationDrwable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAnimationDrwable!=null&&mAnimationDrwable.isRunning()){
            mAnimationDrwable.stop();
        }
    }
}
