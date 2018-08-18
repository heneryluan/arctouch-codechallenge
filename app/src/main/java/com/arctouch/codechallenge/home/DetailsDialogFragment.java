package com.arctouch.codechallenge.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailsDialogFragment extends DialogFragment {

    private static final String DIALOG_TITLE_KEY = "title";
    private static final String POSTER_PATH_KEY = "posterImage";
    private static final String BACKDROP_PATH_KEY = "backdropImage";
    private static final String TITLE_TEXT_KEY = "titleText";
    private static final String GENRES_TEXT_KEY = "genresText";
    private static final String OVERVIEW_TEXT_KEY = "overviewText";
    private static final String RELEASE_DATE_KEY = "releaseDate";
    private static final String UNKNOWN = "Unknown";

    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

    private static ImageView posterImageView;
    private static ImageView backdropImageView;
    private static TextView titleTextView;
    private static TextView genresTextView;
    private static TextView overviewTextView;
    private static TextView releaseDateTextView;

    public DetailsDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DetailsDialogFragment newInstance(String title, Movie movie) {
        DetailsDialogFragment frag = new DetailsDialogFragment();
        Bundle args = new Bundle();
        setupMovieInfo(title, movie, args);
        frag.setArguments(args);
        return frag;
    }

    private static void setupMovieInfo(String title, Movie movie, Bundle args) {
        args.putString(DIALOG_TITLE_KEY, title);
        args.putString(POSTER_PATH_KEY, movie.posterPath);
        args.putString(BACKDROP_PATH_KEY, movie.backdropPath);
        args.putString(TITLE_TEXT_KEY, movie.title);
        args.putString(GENRES_TEXT_KEY, TextUtils.join(", ", movie.genres));
        args.putString(OVERVIEW_TEXT_KEY, movie.overview);
        args.putString(RELEASE_DATE_KEY, movie.releaseDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindAllViews(view);
        retrieveAllArgs();
    }

    private void bindAllViews(View view) {
        posterImageView = view.findViewById(R.id.posterImageView);
        backdropImageView = view.findViewById(R.id.backdropImageView);
        titleTextView = view.findViewById(R.id.titleTextView);
        genresTextView = view.findViewById(R.id.genresTextView);
        overviewTextView = view.findViewById(R.id.overviewTextView);
        releaseDateTextView = view.findViewById(R.id.releaseDateTextView);
    }

    private void retrieveAllArgs() {
        String title = getArguments().getString(DIALOG_TITLE_KEY, UNKNOWN);
        getDialog().setTitle(title);

        titleTextView.setText(getArguments().getString(TITLE_TEXT_KEY, UNKNOWN));
        genresTextView.setText(getArguments().getString(GENRES_TEXT_KEY, UNKNOWN));
        overviewTextView.setText(getArguments().getString(OVERVIEW_TEXT_KEY, UNKNOWN));
        releaseDateTextView.setText(getArguments().getString(RELEASE_DATE_KEY, UNKNOWN));

        String posterPath = getArguments().getString(POSTER_PATH_KEY, UNKNOWN);
        if (TextUtils.isEmpty(posterPath) == false) {
            Glide.with(posterImageView)
                    .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(posterImageView);
        }

        String backdropPath = getArguments().getString(BACKDROP_PATH_KEY, UNKNOWN);
        if (TextUtils.isEmpty(backdropPath) == false) {
            Glide.with(backdropImageView)
                    .load(movieImageUrlBuilder.buildPosterUrl(backdropPath))
                    .apply(new RequestOptions().placeholder(R.color.colorPrimaryDark))
                    .into(backdropImageView);
        }

    }

}
