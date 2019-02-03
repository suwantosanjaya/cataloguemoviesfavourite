package com.dicoding.suwantosanjaya.cataloguemovies.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.suwantosanjaya.cataloguemovies.BuildConfig;
import com.dicoding.suwantosanjaya.cataloguemovies.R;
import com.dicoding.suwantosanjaya.cataloguemovies.adapters.MovieAdapter;
import com.dicoding.suwantosanjaya.cataloguemovies.loader.DataLoader;
import com.dicoding.suwantosanjaya.cataloguemovies.models.Movies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suwanto Sanjaya on 27/11/2018.
 */

public class NowplayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movies>> {
    static final String PILIHAN = "NOWPLAYING";
    @BindView(R.id.rv_playnow)
    RecyclerView rvMovie;
    private View view;
    private MovieAdapter adapter;
    private ArrayList<Movies> listMovie = new ArrayList<>();
    private MovieAdapter.MovieDetailAdapterListener buttonListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nowplaying, container, false);

        ButterKnife.bind(this, view);
        try {
            rvMovie.setHasFixedSize(true);
            rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
            buttonListener = new MovieAdapter.MovieDetailAdapterListener() {
                @Override
                public void buttonDetailOnClick(View v, int position) {
                    NowplayingFragment.this.goToDetail(v, position);
                }

                @Override
                public void buttonShareOnClick(View v, int position) {
                    NowplayingFragment.this.goToShare(v, position);
                }
            };
            adapter = new MovieAdapter(view.getContext(), listMovie, buttonListener);
            adapter.setMoviesData(listMovie);
            rvMovie.setAdapter(adapter);

            Bundle bundle = new Bundle();
            getLoaderManager().initLoader(0, bundle, NowplayingFragment.this);
        } catch (Exception e) {
            Log.e("PESAN", e.getMessage());
        }
        return view;
    }

    public void goToDetail(View v, int position) {
        Movies movies = listMovie.get(position);
        Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_ID, movies.getId());
        intent.putExtra(MovieDetailActivity.EXTRA_TITLE, movies.getTitle());
        intent.putExtra(MovieDetailActivity.EXTRA_OVERVIEW, movies.getOverview());
        intent.putExtra(MovieDetailActivity.EXTRA_POSTER, movies.getPosterPath());
        intent.putExtra(MovieDetailActivity.EXTRA_VOTE, movies.getVoteAverage());
        intent.putExtra(MovieDetailActivity.EXTRA_RELEASE_DATE, movies.getReleaseDate());
        startActivity(intent);
    }

    public void goToShare(View v, int position) {
        Movies movies = listMovie.get(position);

        String releaseDate = movies.getReleaseDate();
        try {
            SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = shortFormat.parse(releaseDate);

            SimpleDateFormat longFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            releaseDate = longFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String poster = BuildConfig.IMAGE_URL_POSTER_PATH + movies.getPosterPath();

        String text = movies.getTitle() + "\n\n" +
                movies.getOverview() + "\n\n" +
                "Rating: " + movies.getVoteAverage() + "\n\n" +
                "Release Date: " + releaseDate + "\n\n" +
                poster;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, movies.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_using)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new DataLoader(view.getContext(), PILIHAN, "");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movies>> loader, ArrayList<Movies> movies) {
        listMovie = movies;
        adapter.setMoviesData(listMovie);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movies>> loader) {

    }

}
