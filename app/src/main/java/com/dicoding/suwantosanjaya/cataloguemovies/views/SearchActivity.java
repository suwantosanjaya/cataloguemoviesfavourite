package com.dicoding.suwantosanjaya.cataloguemovies.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dicoding.suwantosanjaya.cataloguemovies.R;

/**
 * Created by Suwanto Sanjaya on 01/12/2018.
 */

public class SearchActivity extends AppCompatActivity {
    static final String EXTRA_QUERY = "EXTRA_QUERY";
    private SearchFragment fragment = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.query = getIntent().getStringExtra(EXTRA_QUERY);
        initFrame(this.query);
    }

    public void initFrame(String query) {
        try {
            fragment = new SearchFragment();
            fragment.setQuery(query);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_search, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.e("PESAN", e.getMessage());
        }
    }

}
