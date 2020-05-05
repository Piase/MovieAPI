package com.epiasentin.movieapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class SearchActivity extends Fragment {

    public static final String TAG = "MyTag";
    StringRequest stringRequest;
    RequestQueue requestQueue;

    EditText search;
    TextView title;
    TextView year;
    TextView poster;
    Button searchBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search = view.findViewById(R.id.editTextSearch);
        title = view.findViewById(R.id.textViewTitle);
        year = view.findViewById(R.id.textViewYear);
        poster = view.findViewById(R.id.textViewPoster);
        searchBtn = view.findViewById(R.id.buttonSearch);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = search.getText().toString();
                if (message.isEmpty()) {
                    search.setError("Please enter your movie!");
                    search.setText("");
                    search.requestFocus();
                } else {
                    searchAPI(message);
                }
            }
        });
    }

    public void searchAPI (String message) {
        //Searches based on Title
        String url = "http://www.omdbapi.com/?t=" + message + "&apikey=bacbcffb";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //title.setText("Response: " + response.toString());

                        String titleStr = null;
                        String yearStr = null;
                        String posterStr = null;

                        try {
                            //JSONObject jsonObj = new JSONObject(response);
                            //titleStr = jsonObj.getString("Title");
                            //yearStr = jsonObj.getString("Year");
                            //posterStr = jsonObj.getString("Poster");

                            titleStr = response.getString("Title");
                            yearStr = response.getString("Year");
                            posterStr = response.getString("Poster");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        title.setText(titleStr);
                        year.setText(yearStr);
                        poster.setText(posterStr);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        title.setText(error.toString());
                    }
                });
        //Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG); }
    }

}
