package com.epiasentin.movieapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FavoriteActivity extends Fragment {

    public static final String TAG = "FavoriteTag";

    TextView textViewFav;
    String documentId;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef = db.collection("favorites");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewFav = view.findViewById(R.id.textViewFav);
        //Getting the data from the database and displaying in on the app
        colRef.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Favorite favorite = documentSnapshot.toObject(Favorite.class);

                    documentId = favorite.getDocumentId();
                    String title = favorite.getTitle();
                    String released = favorite.getReleased();
                    String runtime = favorite.getDuration();
                    String rated = favorite.getRated();
                    String rating = favorite.getRating();

                    data += (title + "\nReleased: " + released + ", Duration: " + runtime + "\nRated: " + rated + ", IMDB Rating: " + rating + "/10\n\n");
                }

                textViewFav.setText(data);

                //Click function to delete the movie from favorites
                textViewFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        colRef.document(documentId).delete();
                        Toast.makeText(getActivity().getApplicationContext(),"Movie was removed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
