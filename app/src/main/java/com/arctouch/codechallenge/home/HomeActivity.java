package com.arctouch.codechallenge.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arctouch.codechallenge.MVPInterfaces.IHomePresenter;
import com.arctouch.codechallenge.MVPInterfaces.IHomeView;
import com.arctouch.codechallenge.Presenter.HomePresenter;
import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Movie;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements IHomeView {

    /**
     * Presenter instance used for retain view logic.
     */
    private IHomePresenter presenter;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        presenter = new HomePresenter(this);
        presenter.onCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindAllViews() {
        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHomeAdapter(List<Movie> movies) {
        recyclerView.setAdapter(new HomeAdapter(movies, presenter));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProgressVisibility(boolean visibility) {
        if (visibility == true) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDetailsFragment(Movie movie) {
        //TODO: implements show details fragment.
        Toast.makeText(getApplicationContext(), "The movie clicked was " + movie.title.toString(), Toast.LENGTH_SHORT).show();
        FragmentManager fm = getSupportFragmentManager();
        DetailsDialogFragment detailsDialogFragment = DetailsDialogFragment.newInstance("Movie Details", movie);
        detailsDialogFragment.show(fm, "fragment_details");
    }

}
