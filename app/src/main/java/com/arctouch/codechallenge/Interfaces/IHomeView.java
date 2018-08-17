package com.arctouch.codechallenge.Interfaces;

import com.arctouch.codechallenge.model.Movie;

import java.util.List;

/**
 * Interface used for {@link com.arctouch.codechallenge.home.HomeActivity} contracts.
 */
public interface IHomeView {

    /**
     * Notify activity to bind all
     */
    void bindAllViews();

    /**
     * Sets the home adapter on activity.
     *
     * @param movies
     */
    void setHomeAdapter(List<Movie> movies);

    /**
     * Sets the progress bar visibility state.
     *
     * @param visibility
     */
    void setProgressVisibility(boolean visibility);

}
