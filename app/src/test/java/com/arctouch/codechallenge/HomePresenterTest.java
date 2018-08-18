package com.arctouch.codechallenge;

import com.arctouch.codechallenge.MVPInterfaces.IHomePresenter;
import com.arctouch.codechallenge.MVPInterfaces.IHomeView;
import com.arctouch.codechallenge.Presenter.HomePresenter;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.model.Movie;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    /**
     * Mocking activity MVP interface.
     */
    @Mock
    IHomeView homeView;

    /**
     * Mocking TmdbApi.
     */
    @Mock
    TmdbApi api;

    private IHomePresenter presenter;

    /**
     * Test case setup.
     */
    @Before
    public void setUp() {
        presenter = new HomePresenter(homeView);
    }

    /**
     * test case teardown.
     */
    @After
    public void tearDown() {
        presenter = null;
    }

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Test
    public void shouldVerifyOnCreateEvent() throws Exception {

        presenter.onCreate();

        verify(homeView, times(1)).bindAllViews();
        verify(homeView, times(1)).setAllListeners();
    }

    @Test
    public void shouldVerifyOnMovieClickEvent() throws Exception {

        presenter.onMovieClick(Mockito.any(Movie.class));

        verify(homeView, times(1)).showDetailsFragment(Mockito.any(Movie.class));
    }

}
