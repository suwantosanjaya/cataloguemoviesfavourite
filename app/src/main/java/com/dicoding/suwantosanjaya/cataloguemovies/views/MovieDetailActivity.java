package com.dicoding.suwantosanjaya.cataloguemovies.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.suwantosanjaya.cataloguemovies.BuildConfig;
import com.dicoding.suwantosanjaya.cataloguemovies.R;
import com.dicoding.suwantosanjaya.cataloguemovies.providers.databases.FavouriteHelper;
import com.dicoding.suwantosanjaya.cataloguemovies.providers.models.FavouriteModel;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suwanto Sanjaya on 01/12/2018.
 */

public class MovieDetailActivity extends AppCompatActivity {
    public static String EXTRA_ID = "EXTRA_ID";
    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_OVERVIEW = "EXTRA_OVERVIEW";
    public static String EXTRA_POSTER = "EXTRA_POSTER";
    public static String EXTRA_VOTE = "EXTRA_VOTE";
    public static String EXTRA_RELEASE_DATE = "EXTRA_RELEASE_DATE";
    @BindView(R.id.btn_favourite)
    MaterialFavoriteButton btnFavorite;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_overview)
    TextView detailOverview;
    @BindView(R.id.detail_img)
    ImageView detailImg;
    @BindView(R.id.detail_rating)
    RatingBar detailRating;
    @BindView(R.id.detail_release_date)
    TextView detailReleaseDate;
    private Context context;
    private FavouriteHelper favouriteHelper;
    private int countMovie;
    private FavouriteModel favouriteModel;
    private int id;
    private String title;
    private String overview;
    private String poster;
    private String releaseDate;
    private float vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        this.id = getIntent().getIntExtra(EXTRA_ID, 0);
        this.title = getIntent().getStringExtra(EXTRA_TITLE);
        this.overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        this.poster = getIntent().getStringExtra(EXTRA_POSTER);
        this.vote = getIntent().getFloatExtra(EXTRA_VOTE, 0);

        this.releaseDate = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        try {
            SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = shortFormat.parse(releaseDate);

            SimpleDateFormat longFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            this.releaseDate = longFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        detailReleaseDate.setText("Release Date : " + this.releaseDate);
        Picasso.with(context).load(BuildConfig.IMAGE_URL_POSTER_PATH + poster).into(detailImg);
        detailTitle.setText(title);
        detailOverview.setText(overview);
        detailRating.setRating(vote * 5 / 10);

        favouriteModel = new FavouriteModel();
        favouriteModel.setId(this.id);
        favouriteModel.setTitle(this.title);
        favouriteModel.setPoster(this.poster);
        favouriteModel.setOverview(this.overview);
        favouriteModel.setReleaseDate(this.releaseDate);
        favouriteModel.setVoteAverage(this.vote);

        loadFavourite(this.id);

        btnFavorite.setOnFavoriteChangeListener((buttonView, favorite) -> {
            if (favorite) {
                addToFavourite(MovieDetailActivity.this.id);
            } else {
                removeFromFavourite(MovieDetailActivity.this.id);
            }
        });
    }

    public void loadFavourite(int id) {
        favouriteHelper = new FavouriteHelper(this);
        favouriteHelper.open();
        countMovie = favouriteHelper.countMovie(id);
        if (countMovie > 0) {
            btnFavorite.setFavorite(true);
        } else {
            btnFavorite.setFavorite(false);
        }
        favouriteHelper.close();
    }

    public void addToFavourite(int id) {
        favouriteHelper = new FavouriteHelper(this);
        favouriteHelper.open();
        countMovie = favouriteHelper.countMovie(id);
        if (countMovie <= 0) {
            favouriteHelper.insertTransaction(MovieDetailActivity.this.favouriteModel);
            Toast.makeText(MovieDetailActivity.this, "Movie was added to favourite", Toast.LENGTH_LONG).show();
        }
        favouriteHelper.close();
    }

    public void removeFromFavourite(int id) {
        favouriteHelper = new FavouriteHelper(this);
        favouriteHelper.open();
        countMovie = favouriteHelper.countMovie(id);

        if (countMovie > 0) {
            favouriteHelper.delete(id);
            Toast.makeText(MovieDetailActivity.this, "Movie was removed from favourite", Toast.LENGTH_LONG).show();
        }
        favouriteHelper.close();
    }
}
