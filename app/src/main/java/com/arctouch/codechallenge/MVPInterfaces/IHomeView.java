package com.arctouch.codechallenge.MVPInterfaces;

import com.arctouch.codechallenge.model.Movie;

import java.util.List;

/**
 * Interface used for {@link com.arctouch.codechallenge.home.HomeActivity} contracts.
 */
public interface IHomeView {

    /**
     * Notify activity to bind all views.
     */
    void bindAllViews();

    /**
     *  Notify activity to set all listeners.
     */
    void setAllListeners();

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

    /**
     * Shows the details fragment on activity.
     *
     * @param movie
     */
    void showDetailsFragment(Movie movie);

    /**
     * Notify when the view dataset is changed.
     *
     * @param movies
     */
    void notifyDataSetChanged(List<Movie> movies);

}
