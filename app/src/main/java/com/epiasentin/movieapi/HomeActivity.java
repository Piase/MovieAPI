package com.epiasentin.movieapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";
    TextView username;
    Button logoff;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = findViewById(R.id.textViewUser);
        logoff = findViewById(R.id.buttonLogout);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            String name = user.getEmail();
            username.setText(name);
        }

        logoff.setOnClickListener(new View.OnClickListener() { //Log out button to exit app.
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                Intent inToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(inToMain);
                finish();
            }
        });
    }
}
