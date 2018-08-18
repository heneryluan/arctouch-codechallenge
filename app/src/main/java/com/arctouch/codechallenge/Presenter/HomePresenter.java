package com.arctouch.codechallenge.Presenter;

import com.arctouch.codechallenge.MVPInterfaces.IHomePresenter;
import com.arctouch.codechallenge.MVPInterfaces.IHomeView;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * HomePresenter class used to guarantee that logic will not be implemented on views.
 */
public class HomePresenter implements IHomePresenter {

    private static final long FIRST_PAGE = 1L;
    private static long currentPage;

    private boolean isRecycleLoading = true;

    protected TmdbApi api = new Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi.class);

    private IHomeView view;

    private List<Movie> movies;

    /**
     * HomePresenter constructor injecting a {@link IHomeView} interface contract.
     *
     * @param view
     */
    public HomePresenter(IHomeView view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        view.bindAllViews();
        view.setAllListeners();
        setupApiConfig();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMovieClick(Movie movie) {
        view.showDetailsFragment(movie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRecycleLoading() {
        return isRecycleLoading;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRecycleEndScrolled() {
        isRecycleLoading = false;
        currentPage++;
        requestMoreItems();
    }

    private void setupApiConfig() {
        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Cache.setGenres(response.genres);
                });

        currentPage = FIRST_PAGE;
        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, FIRST_PAGE, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (!response.results.isEmpty()) {
                        for (Movie movie : response.results) {
                            movie.genres = new ArrayList<>();
                            for (Genre genre : Cache.getGenres()) {
                                if (movie.genreIds.contains(genre.id)) {
                                    movie.genres.add(genre);
                                }
                            }
                        }
                        movies = response.results;
                        view.setHomeAdapter(movies);
                        view.setProgressVisibility(false);
                    }
                });

    }

    private void requestMoreItems() {
        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, currentPage, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (!response.results.isEmpty()) {
                        for (Movie movie : response.results) {
                            movie.genres = new ArrayList<>();
                            for (Genre genre : Cache.getGenres()) {
                                if (movie.genreIds.contains(genre.id)) {
                                    movie.genres.add(genre);
                                }
                            }
                        }
                        isRecycleLoading = true;
                        movies.addAll(response.results);
                        view.notifyDataSetChanged(movies);
                    }
                });

    }

}
