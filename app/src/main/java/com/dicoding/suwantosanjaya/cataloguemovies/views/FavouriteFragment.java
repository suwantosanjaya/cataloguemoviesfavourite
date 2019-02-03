package com.dicoding.suwantosanjaya.cataloguemovies.views;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.suwantosanjaya.cataloguemovies.BuildConfig;
import com.dicoding.suwantosanjaya.cataloguemovies.R;
import com.dicoding.suwantosanjaya.cataloguemovies.adapters.FavouriteAdapter;
import com.dicoding.suwantosanjaya.cataloguemovies.providers.databases.DatabaseContract;
import com.dicoding.suwantosanjaya.cataloguemovies.providers.models.FavouriteModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_favourite)
    RecyclerView rvMovie;
    private View view;
    private FavouriteAdapter adapter;
    private Cursor cursor = null;
    private FavouriteAdapter.MovieDetailAdapterListener buttonListener;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, this.view);

        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(this.view.getContext()));

        buttonListener = new FavouriteAdapter.MovieDetailAdapterListener() {
            @Override
            public void buttonDetailOnClick(View v, int position) {
                FavouriteFragment.this.goToDetail(v, position);
            }

            @Override
            public void buttonShareOnClick(View v, int position) {
                FavouriteFragment.this.goToShare(v, position);
            }
        };

        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(0, bundle, FavouriteFragment.this);
        return this.view;
    }

    public void displayFavourite() {
        this.adapter = new FavouriteAdapter(this.getContext(), this.cursor, this.buttonListener);
        this.adapter.setMoviesData(this.cursor);
        rvMovie.setAdapter(this.adapter);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this.getContext(), DatabaseContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        ContentResolver contentResolver = this.getContext().getContentResolver();
        this.cursor = contentResolver.query(DatabaseContract.CONTENT_URI, null, null, null, null);
        displayFavourite();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }


    public FavouriteModel getList(int position) {
        this.cursor.moveToPosition(position);
        FavouriteModel favouriteModel = new FavouriteModel();
        favouriteModel.setId(this.cursor.getInt(this.cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.ID)));
        favouriteModel.setTitle(this.cursor.getString(this.cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE)));
        favouriteModel.setPoster(this.cursor.getString(this.cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER)));
        favouriteModel.setOverview(this.cursor.getString(this.cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.OVERVIEW)));
        favouriteModel.setReleaseDate(this.cursor.getString(this.cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RELEASE_DATE)));
        favouriteModel.setVoteAverage(this.cursor.getFloat(this.cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.VOTE_AVERAGE)));
        return favouriteModel;
    }

    public void goToDetail(View v, int position) {
        FavouriteModel favouriteModel = this.getList(position);
        Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_ID, favouriteModel.getId());
        intent.putExtra(MovieDetailActivity.EXTRA_TITLE, favouriteModel.getTitle());
        intent.putExtra(MovieDetailActivity.EXTRA_OVERVIEW, favouriteModel.getOverview());
        intent.putExtra(MovieDetailActivity.EXTRA_POSTER, favouriteModel.getPoster());
        intent.putExtra(MovieDetailActivity.EXTRA_VOTE, favouriteModel.getVoteAverage());
        intent.putExtra(MovieDetailActivity.EXTRA_RELEASE_DATE, favouriteModel.getReleaseDate());
        startActivity(intent);
    }

    public void goToShare(View v, int position) {
        FavouriteModel favouriteModel = this.getList(position);
        String releaseDate = favouriteModel.getReleaseDate();
        try {
            SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = shortFormat.parse(releaseDate);
            SimpleDateFormat longFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            releaseDate = longFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String poster = BuildConfig.IMAGE_URL_POSTER_PATH + favouriteModel.getPoster();
        String text = favouriteModel.getTitle() + "\n\n" +
                favouriteModel.getOverview() + "\n\n" +
                "Rating: " + favouriteModel.getVoteAverage() + "\n\n" +
                "Release Date: " + releaseDate + "\n\n" + poster;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, favouriteModel.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_using)));
    }
}
