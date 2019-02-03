package com.dicoding.suwantosanjaya.cataloguemovies.adapters;

import android.content.Context;
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
import com.dicoding.suwantosanjaya.cataloguemovies.models.Movies;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suwanto Sanjaya on 27/11/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private ArrayList<Movies> moviesData;
    private LayoutInflater mInflater;
    private MovieDetailAdapterListener movieDetailAdapterListener;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        moviesData = new ArrayList<>();
    }

    public MovieAdapter(Context context, ArrayList<Movies> moviesData, MovieDetailAdapterListener movieDetailAdapterListener) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.moviesData = moviesData;
        this.movieDetailAdapterListener = movieDetailAdapterListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Movies> getMoviesData() {
        return moviesData;
    }

    public void setMoviesData(ArrayList<Movies> moviesData) {
        this.moviesData = moviesData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Movies movies = getMoviesData().get(i);
        String image_url = BuildConfig.IMAGE_URL_BASE_PATH + movies.getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_contact_card)
                .error(android.R.drawable.stat_notify_error)
                .into(myViewHolder.imgPoster);

        myViewHolder.tvTitle.setText(movies.getTitle());
        myViewHolder.tvOverview.setText(movies.getOverview());

        String releaseDate = movies.getReleaseDate();
        try {
            SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = shortFormat.parse(movies.getReleaseDate());

            SimpleDateFormat longFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            releaseDate = longFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myViewHolder.tvReleaseDate.setText("Release: " + releaseDate);

        myViewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDetailAdapterListener.buttonDetailOnClick(v, i);
            }
        });

        myViewHolder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDetailAdapterListener.buttonShareOnClick(v, i);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return getMoviesData().size();
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
