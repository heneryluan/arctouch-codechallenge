package com.arctouch.codechallenge.Presenter;

import com.arctouch.codechallenge.Interfaces.IHomePresenter;
import com.arctouch.codechallenge.Interfaces.IHomeView;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;

import java.util.ArrayList;

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

    protected TmdbApi api = new Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi.class);

    private IHomeView view;

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
        setupApiConfig();
    }

    private void setupApiConfig() {
        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Cache.setGenres(response.genres);
                });

        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    for (Movie movie : response.results) {
                        movie.genres = new ArrayList<>();
                        for (Genre genre : Cache.getGenres()) {
                            if (movie.genreIds.contains(genre.id)) {
                                movie.genres.add(genre);
                            }
                        }
                    }
                    view.setHomeAdapter(response.results);
                    view.setProgressVisibility(false);
                });
    }
}
