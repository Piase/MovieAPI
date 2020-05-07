package com.epiasentin.movieapi;

import com.google.firebase.firestore.Exclude;

public class Favorite  {
    private String documentId;
    private String title;
    private String released;
    private String duration;
    private String rated;
    private String rating;

    public Favorite() {
        //public no-arg constructor needed
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    //Function to get documentID
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Favorite(String title, String released, String runtime, String rated, String rating) {
        this.title = title;
        this.released = released;
        this.duration = runtime;
        this.rated = rated;
        this.rating = rating;
    }

    //Basic functions to get data from database
    public String getTitle() {
        return title;
    }

    public String getReleased() {
        return released;
    }

    public String getDuration() {
        return duration;
    }

    public String getRated() {
        return rated;
    }

    public String getRating() {
        return rating;
    }
}