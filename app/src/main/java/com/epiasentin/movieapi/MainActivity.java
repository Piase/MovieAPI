package com.epiasentin.movieapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private FirebaseAuth mAuth;
    private EditText eID, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigned Variables
        final Button btnSI = findViewById(R.id.buttonSignIn);
        final Button btnSU = findViewById(R.id.buttonSignUp);
        eID = findViewById(R.id.editTextEmail);
        pw = findViewById(R.id.editTextPw);
        mAuth = FirebaseAuth.getInstance();

        //Sign Up Button Event
        btnSU.setOnClickListener(new View.OnClickListener() { //Redirects to user to Register Activity on click
            @Override
            public void onClick(View v) {
                Intent inToRegister = new Intent (getApplicationContext(), RegisterActivity.class);
                startActivity(inToRegister);
                finish();
            }
        });

        //Sign In Button Event
        btnSI.setOnClickListener(new View.OnClickListener() { //Signs In User
            @Override
            public void onClick(View v) {
                String email = eID.getText().toString();
                String password = pw.getText().toString();

                if (email.isEmpty()) { //Check to see if user entered an email address
                    eID.setError("Please enter an email address!");
                    eID.setText("");
                } else if (password.isEmpty()) { //Check to see if user entered a password
                    pw.setError("Please enter a password!");
                    pw.setText("");
                } else { //When both email and password are entered
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        Toast.makeText(MainActivity.this, "Login Successful. \n Welcome to the Movie App",
                                                Toast.LENGTH_SHORT).show();
                                        Intent inToTab = new Intent (getApplicationContext(), TabActivity.class);
                                        startActivity(inToTab);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Login failed please try again.",
                                                Toast.LENGTH_SHORT).show();
                                        MainActivity.this.finish();
                                        MainActivity.this.startActivity(MainActivity.this.getIntent());
                                    }
                                }
                            });
                }
            }
        });

    }

    //Allows the user to connect automatically if current user redirecting to Home Page
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent inToTab = new Intent (getApplicationContext(), TabActivity.class);
            startActivity(inToTab);
            finish();
        }
    }
}
