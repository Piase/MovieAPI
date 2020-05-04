package com.epiasentin.movieapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";
    StringRequest stringRequest;
    RequestQueue requestQueue;

    EditText search;
    TextView title;
    TextView year;
    TextView poster;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.editTextSearch);
        title = findViewById(R.id.textViewTitle);
        year = findViewById(R.id.textViewYear);
        poster = findViewById(R.id.textViewPoster);
        searchBtn = findViewById(R.id.buttonSearch);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = search.getText().toString();
                if (message.isEmpty()) {
                    search.setError("Please enter your movie!");
                    search.setText("");
                    search.requestFocus();
                } else {
                    String url = "http://www.omdbapi.com/?t=Rocketman&apikey=bacbcffb";
                    //String url = "http://www.omdbapi.com/?t=" + message + "&apikey=bacbcffb";

                    StringRequest stringRequest = new StringRequest
                            (Request.Method.GET, url, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    //title.setText("Response: " + response.toString());


                                    String titleStr = null;
                                    String yearStr = null;
                                    String posterStr = null;

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        titleStr = jsonObj.getString("Title");
                                        yearStr = jsonObj.getString("Year");
                                        posterStr = jsonObj.getString("Poster");
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
                                    title.setText("That didn't work!");
                                }
                            });

                    //Access the RequestQueue through your singleton class.
                    MySingleton.getInstance(this).addToRequestQueue(stringRequest);
                }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG); }
    }

}
