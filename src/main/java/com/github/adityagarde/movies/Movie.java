package com.github.adityagarde.movies;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Movie", description = "Movie representation")
public class Movie {

    private int id;
    private String title;

    public Movie() {
    }

    public Movie(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}