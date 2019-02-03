package com.dicoding.suwantosanjaya.cataloguemovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicoding.suwantosanjaya.cataloguemovies.BuildConfig;
import com.dicoding.suwantosanjaya.cataloguemovies.R;
import com.dicoding.suwantosanjaya.cataloguemovies.providers.databases.DatabaseContract;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suwanto Sanjaya on 18/12/2018.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private Cursor moviesData;
    private FavouriteAdapter.MovieDetailAdapterListener movieDetailAdapterListener;
    private Context context;

    public FavouriteAdapter(Context context, Cursor moviesData, FavouriteAdapter.MovieDetailAdapterListener movieDetailAdapterListener) {
        this.context = context;
        this.moviesData = moviesData;
        this.movieDetailAdapterListener = movieDetailAdapterListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Cursor getMoviesData() {
        return moviesData;
    }

    public void setMoviesData(Cursor moviesData) {
        this.moviesData = moviesData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.MyViewHolder myViewHolder, int i) {
        getMoviesData().moveToPosition(i);

        int id = moviesData.getInt(moviesData.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.ID));
        String title = moviesData.getString(moviesData.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE));
        String image_url = BuildConfig.IMAGE_URL_BASE_PATH + moviesData.getString(moviesData.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER));
        String overview = moviesData.getString(moviesData.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.OVERVIEW));
        String releaseDate = moviesData.getString(moviesData.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RELEASE_DATE));
        float voteAverage = moviesData.getFloat(moviesData.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.VOTE_AVERAGE));


        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_contact_card)
                .error(android.R.drawable.stat_notify_error)
                .into(myViewHolder.imgPoster);

        myViewHolder.tvTitle.setText(title);
        myViewHolder.tvOverview.setText(overview);

        try {
            SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = shortFormat.parse(releaseDate);

            SimpleDateFormat longFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            releaseDate = longFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myViewHolder.tvReleaseDate.setText("Release: " + releaseDate);

        myViewHolder.btnDetail.setOnClickListener(v -> movieDetailAdapterListener.buttonDetailOnClick(v, i));

        myViewHolder.btnShare.setOnClickListener(v -> movieDetailAdapterListener.buttonShareOnClick(v, i));
    }

    @Override
    public int getItemCount() {
        return getMoviesData().getCount();
    }

    public interface MovieDetailAdapterListener {
        void buttonDetailOnClick(View v, int position);

        void buttonShareOnClick(View v, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_movie)
        ImageView imgPoster;
        @BindView(R.id.tv_movie_title)
        TextView tvTitle;
        @BindView(R.id.tv_movie_overview)
        TextView tvOverview;
        @BindView(R.id.tv_movie_release_date)
        TextView tvReleaseDate;
        @BindView(R.id.btn_movie_detail)
        Button btnDetail;
        @BindView(R.id.btn_share)
        Button btnShare;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
