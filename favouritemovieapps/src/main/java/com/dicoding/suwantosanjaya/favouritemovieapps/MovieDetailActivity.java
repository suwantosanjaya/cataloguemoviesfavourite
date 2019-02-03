package com.dicoding.suwantosanjaya.favouritemovieapps;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dicoding.suwantosanjaya.favouritemovieapps.databases.FavouriteModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    public static String EXTRA_ID = "EXTRA_ID";
    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_OVERVIEW = "EXTRA_OVERVIEW";
    public static String EXTRA_POSTER = "EXTRA_POSTER";
    public static String EXTRA_VOTE = "EXTRA_VOTE";
    public static String EXTRA_RELEASE_DATE = "EXTRA_RELEASE_DATE";
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
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        this.id = getIntent().getIntExtra(EXTRA_ID, 0);
        this.title = getIntent().getStringExtra(EXTRA_TITLE);
        this.overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        this.poster = getIntent().getStringExtra(EXTRA_POSTER);
        this.vote = (float) getIntent().getFloatExtra(EXTRA_VOTE, 0);

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
    }
}
