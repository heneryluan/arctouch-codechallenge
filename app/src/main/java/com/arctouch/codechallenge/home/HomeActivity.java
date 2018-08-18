package com.arctouch.codechallenge.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.MVPInterfaces.IHomePresenter;
import com.arctouch.codechallenge.MVPInterfaces.IHomeView;
import com.arctouch.codechallenge.Presenter.HomePresenter;
import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Movie;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements IHomeView {

    private static final String MOVIE_DETAILS_KEY = "Movie Details";
    private static final String FRAGMENT_DETAILS_KEY = "fragment_details";

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

    @Override
    public void setAllListeners() {

        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (presenter.isRecycleLoading()) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.v("...", "Last Item Wow !");
                            presenter.onRecycleEndScrolled();
                        }
                    }
                }
            }
        });
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
        FragmentManager fm = getSupportFragmentManager();
        DetailsDialogFragment detailsDialogFragment = DetailsDialogFragment.newInstance(MOVIE_DETAILS_KEY, movie);
        detailsDialogFragment.show(fm, FRAGMENT_DETAILS_KEY);
    }

    @Override
    public void notifyDataSetChanged(List<Movie> movies) {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

}
