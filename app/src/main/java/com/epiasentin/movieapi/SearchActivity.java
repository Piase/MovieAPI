package com.epiasentin.movieapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    TextView info;
    Button searchBtn;
    Button posterBtn;
    WebView posterWv;

    String titleStr = null;
    String releasedStr = null;
    String posterStr = null;
    String runtimeStr = null;
    String ratedStr = null;
    String ratingStr = null;

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
        info = view.findViewById(R.id.textViewInfo);
        searchBtn = view.findViewById(R.id.buttonSearch);
        posterBtn = view.findViewById(R.id.buttonPoster);
        posterWv = view.findViewById(R.id.webViewPoster);
        posterWv.setWebViewClient(new MyBrowser());

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

        posterBtn.setVisibility(View.INVISIBLE);
        posterWv.setVisibility(View.INVISIBLE);

        posterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posterBtn.setVisibility(View.INVISIBLE);
                posterWv.setVisibility(View.VISIBLE);

                //Get poster URL and makes it fit the WebView in its full size.
                String posterUrl = posterStr;
                String html = "<html><body><img src=\"" + posterUrl + "\" width=\"100%\" height=\"100%\"\"/></body></html>";

                posterWv.getSettings().setLoadsImagesAutomatically(true);
                posterWv.getSettings().setJavaScriptEnabled(true);
                posterWv.getSettings().setLoadWithOverviewMode(true);
                posterWv.getSettings().setUseWideViewPort(true);
                posterWv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                
                posterWv.loadData(html, "text/html", null);

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

                        try {
                            titleStr = response.getString("Title");
                            releasedStr = response.getString("Released");
                            posterStr = response.getString("Poster");
                            runtimeStr = response.getString("Runtime");
                            ratedStr = response.getString("Rated");
                            ratingStr = response.getString("imdbRating");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        title.setText(titleStr);
                        info.setText("Released: " + releasedStr + ", Duration: " + runtimeStr + "\n Rated: " + ratedStr + ", IMDB Rating: " + ratingStr + "/10");

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
        posterBtn.setVisibility(View.VISIBLE);
        posterWv.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG); }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
