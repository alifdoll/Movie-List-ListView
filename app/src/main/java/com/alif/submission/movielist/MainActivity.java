package com.alif.submission.movielist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alif.submission.movielist.fragment.FavoriteFragment;
import com.alif.submission.movielist.fragment.MovieFragment;
import com.alif.submission.movielist.fragment.SearchFragment;
import com.alif.submission.movielist.fragment.ShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_movie:
                        fragment = new MovieFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.nav_show:
                        fragment = new ShowFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.nav_fav:
                        fragment = new FavoriteFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        setupSearch(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearch(final SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                String type;
                if (query.length() > 0) {
                    if (bottomNavigationView.getSelectedItemId() == R.id.nav_movie) {
                        type = "movie";
                    } else if (bottomNavigationView.getSelectedItemId() == R.id.nav_show) {
                        type = "show";
                    } else {
                        type = "fav";
                    }

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString(SearchFragment.QUERY, query);
                    bundle.putString(SearchFragment.TYPE, type);

                    SearchFragment searchFragment = new SearchFragment();
                    searchFragment.setArguments(bundle);
                    transaction.replace(R.id.nav_host_fragment, searchFragment);
                    transaction.commit();
                } else {
                    bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                    loadFragment(new MovieFragment());
                }

                return false;
            }
        });
    }
}
