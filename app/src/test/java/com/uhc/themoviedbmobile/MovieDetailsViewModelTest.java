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
    private DataRepository userRepository;

    private MovieDetailsViewModel loginViewModel;

    @Before
    public void setUp() {
        loginViewModel = new MovieDetailsViewModel(userRepository);
    }

    @Test
    public void assertMessageFavoriteAdded() {
        loginViewModel.updateMovieFavorite(1, true);
        String msg = loginViewModel.getFavoriteMsg().getValue();

        assert(msg != null);
        assert(msg.equals("Movie added to favorite list."));
    }

    @Test
    public void assertMessageFavoriteRemoved() {
        loginViewModel.updateMovieFavorite(1, false);
        String msg = loginViewModel.getFavoriteMsg().getValue();

        assert(msg != null);
        assert(msg.equals("Movie removed from favorite list."));
    }
}
