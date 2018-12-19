# TheMovieDbMobile
Android application to browse the top 50 movies in popularity descending order, showing pictures and inspecting movies details.

## Libraries used in this project
- [Paging](https://developer.android.com/topic/libraries/architecture/paging.html#overview)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
- [Room](https://developer.android.com/topic/libraries/architecture/room.html)
- [Picasso & Palette](https://github.com/florent37/PicassoPalette)
- [Constraint layout](https://developer.android.com/training/constraint-layout/index.html)
- [Testing Support Library](https://developer.android.com/topic/libraries/testing-support-library/index.html)
- [Espresso](https://developer.android.com/training/testing/espresso/)
- [Mockito](https://site.mockito.org)
- [Stetho](http://facebook.github.io/stetho/)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://github.com/square/okhttp)
- [Gson](https://github.com/google/gson)
- [Justified TextView and EditText](https://github.com/programingjd/justified)

## TheMovieDbMobile Screens
- Movie list 
- Movie details 
- Settings

## What you should expect
- MVVM architecture pattern
- The database is inserted from the API results when it has connection
- All functions work offline 
- Showing last day and time sincronized on top of the list (in case it is offline)
- Setting screen can set how many movies will be returned on list (15 or 30 or 50[default])
- Push down to reload list (If has connection the last updated date disapears, in oposite it will be dispalyed)
- Aplied colors from The Movie DB website (https://www.themoviedb.org/)
- In the case that the movie title has more than 25 characters, display only 25 characters and concat "..." at the end 
- Application have a default icon and a round icon for devices with such configuration
- DEBUG and REALEASE mode
- Using Stetho lib for better debug and consult database tables (Stetho is only enabled on DEBUG mode)
- Smooth Transition between Movies List and Movie Details page
- Unit tests
- Instrumentation tests
- Show "No Connection" text when the movies' database is empty and there is no connection
- Using different sizes for larger devices on dimens and dimens-large
- Using styles resource for components with same attributes
- Project hosted on [GitHub](https://github.com/uhconst/TheMovieDbMobile.git)

# Bonus points
- **[DONE]** Feature: The user should be able to save items as favourites, and these should be retained
across app restarts, kill or catalog refresh
- **[DONE]** Feature: Work offline. Ability to download content and use it while offline. We love offline!
- **[DONE]** Feature: Infinite scroll / Lazy load of more items.
- **[DONE]** Transition: The ability to provide smooth transitions between screens and states

## Developed by
Uryel Constancio - [uryelhenrique.c@gmail.com](uryelhenrique.c@gmail.com)
