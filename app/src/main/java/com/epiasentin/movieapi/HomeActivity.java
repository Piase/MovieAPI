package com.epiasentin.movieapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends Fragment {

    private static final String TAG = "Home";
    TextView username;
    Button logoff;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.textViewUser);
        logoff = view.findViewById(R.id.buttonLogout);

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
                Intent inToMain = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(inToMain);
            }
        });
    }
}
