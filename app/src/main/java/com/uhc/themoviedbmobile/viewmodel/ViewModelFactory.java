package com.uhc.themoviedbmobile.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.uhc.themoviedbmobile.data.DataRepository;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by const on 12/12/18.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository mRepository;

    public static ViewModelFactory createFactory(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Not yet attached to Application");
        }
        return new ViewModelFactory(DataRepository.getInstance(application));
    }

    public ViewModelFactory(DataRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(DataRepository.class).newInstance(mRepository);
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }

}
