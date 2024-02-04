# Weather Forecast application


## Pre Requisities

- [A valid Google Maps Api Key](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
- [A valid Open Weather API Key](https://openweathermap.org/appid)
- **NOTE: This uses the 3.0 version of the openweather API**

## Setup
- Create `secrets.properties` from `local.defaults.properties`
- Change `GOOGLE_MAPS_KEY` to your API key
- Change `OPEN_WEATHER_API_KEY` to your API key
- Build the project


### Background

Develop an application that:

* gets current weather forecast of a location and displays forecast over the next week.
* Shows a list of locations, and the same on a map.
* Add a new location using Google Places API


## Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
    * [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.
    * [Jetpack](https://developer.android.com/jetpack)
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library provides an abstraction layer over SQLite.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
        * [Jetpack Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) -  Implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.

* Architecture
    * MVVM - Model View View Model
### TODOs   
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing)
    * [[JUnit](https://junit.org/junit4/)) - a simple framework to write repeatable unit tests.
    * [MockK](https://github.com/mockk) - mocking library for Kotlin.

* CI/CD
    * GitHub Actions

* States
    * Update the ViewModels to use states
      
