package com.arctouch.codechallenge.MVPInterfaces;

import com.arctouch.codechallenge.model.Movie;

/**
 * Interface used for {@link com.arctouch.codechallenge.Presenter.HomePresenter} contracts.
 */
public interface IHomePresenter {

    /**
     * Call onCreate method on Presenter.
     */
    void onCreate();

    /**
     * Call onMovieClicked method on Presenter.
     *
     * @param movie
     */
    void onMovieClick(Movie movie);

    /**
     * Verify if recycle view is loading.
     *
     * @return {@link boolean}
     */
    boolean isRecycleLoading();

    /**
     * Call onRecycleEndScrolled method on Presenter.
     */
    void onRecycleEndScrolled();

}
