package com.uhc.themoviedbmobile;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.uhc.themoviedbmobile.data.DataRepository;
import com.uhc.themoviedbmobile.viewmodel.MovieDetailsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import kotlin.jvm.JvmField;

/**
 * Created by const on 12/17/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsViewModelTest {

    @Rule
    @JvmField
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private DataRepository data_repository;

    private MovieDetailsViewModel movie_details_view_model;

    @Before
    public void setUp() {
        movie_details_view_model = new MovieDetailsViewModel(data_repository);
    }

    @Test
    public void assertMessageFavoriteAdded() {
        movie_details_view_model.updateMovieFavorite(1, true);
        String msg = movie_details_view_model.getFavoriteMsg().getValue();

        assert(msg != null);
        assert(msg.equals("Movie added to favorite list."));
    }

    @Test
    public void assertMessageFavoriteRemoved() {
        movie_details_view_model.updateMovieFavorite(1, false);
        String msg = movie_details_view_model.getFavoriteMsg().getValue();

        assert(msg != null);
        assert(msg.equals("Movie removed from favorite list."));
    }
}
