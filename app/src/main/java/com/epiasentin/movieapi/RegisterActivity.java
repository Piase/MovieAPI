package com.epiasentin.movieapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register";
    private FirebaseAuth mAuth;
    private EditText eSU, eSU2, pwSU, pwSU2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assigned Variables
        final Button btnRegister = findViewById(R.id.buttonRegister);
        eSU = findViewById(R.id.editTextEmailSU);
        eSU2 = findViewById(R.id.editTextEmailSU2);
        pwSU = findViewById(R.id.editTextPasswordSU);
        pwSU2 = findViewById(R.id.editTextPasswordSU2);
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eSU.getText().toString();
                String email2 = eSU2.getText().toString();
                String password = pwSU.getText().toString();
                String password2 = pwSU2.getText().toString();

                if (email.isEmpty()) { //Check to see if user entered an email address
                    eSU.setError("Please enter an email address!");
                    eSU.setText("");
                    eSU.requestFocus();
                } else if (email2.isEmpty()){ //Check to see if user entered the email address again
                    eSU2.setError("Please enter the confirmed email address!");
                    eSU2.setText("");
                    eSU2.requestFocus();
                } else if (password.isEmpty()) { //Check to see if user entered a password
                    pwSU.setError("Please enter a password!");
                    pwSU.setText("");
                    pwSU.requestFocus();
                } else if (password2.isEmpty()) { //Check to see if user entered the password again
                    pwSU2.setError("Please enter the confirmed password!");
                    pwSU2.setText("");
                    pwSU2.requestFocus();
                } else if (!email.equals(email2) || !password.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Email or Password don't match", Toast.LENGTH_SHORT).show();
                } else { //When both email and password are entered
                    mAuth.createUserWithEmailAndPassword(email2, password2)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Toast.makeText(RegisterActivity.this, "Sign Up Successful. \n Welcome to the Movie App",
                                                Toast.LENGTH_SHORT).show();
                                        Intent inToHome = new Intent (getApplicationContext(), HomeActivity.class);
                                        startActivity(inToHome);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Sign Up failed please try again.",
                                                Toast.LENGTH_SHORT).show();
                                        RegisterActivity.this.finish();
                                        RegisterActivity.this.startActivity(RegisterActivity.this.getIntent());
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent inToHome = new Intent (getApplicationContext(), HomeActivity.class);
            startActivity(inToHome);
            finish();
        }
    }
}
