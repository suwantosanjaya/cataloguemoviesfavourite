package com.dicoding.suwantosanjaya.cataloguemovies.models;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Suwanto Sanjaya on 24/11/2018.
 */

public class Movies {
    private String overview;
    private String originalLanguage;
    private String originalTitle;
    private boolean video;
    private String title;
    private JSONArray genreIds;
    private String posterPath;
    private String backdropPath;
    private String releaseDate;
    private float voteAverage;
    private double popularity;
    private int id;
    private boolean adult;
    private int voteCount;

    public Movies(JSONObject object) {
        try {
            setOverview(object.getString("overview"));
            setOriginalLanguage(object.getString("original_language"));
            setOriginalTitle(object.getString("original_title"));
            setVideo(object.getBoolean("video"));
            setTitle(object.getString("title"));
            setGenreIds(object.getJSONArray("genre_ids"));
            setPosterPath(object.getString("poster_path"));
            setBackdropPath(object.getString("backdrop_path"));
            setReleaseDate(object.getString("release_date"));
            setVoteAverage((float) object.getDouble("vote_average"));
            setPopularity(object.getDouble("popularity"));
            setId(object.getInt("id"));
            setAdult(object.getBoolean("adult"));
            setVoteCount(object.getInt("vote_count"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONArray getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(JSONArray genreIds) {
        this.genreIds = genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}