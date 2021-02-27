# Movies

A template Android Project using [The Movie Database(TMDb) API](https://www.themoviedb.org/documentation/api)

## Installation

- Create a TMDb account and API Key. Then replace `YOUR_TMDB_API_KEY` with your TMDb API Key inside `dependencies.gradle` file
- Create a [Firebase](firebase.google.com) account and then create an Android app on [Firebase Console](https://console.firebase.google.com/)
- Install [Fastlane](https://docs.fastlane.tools/) to your computer. 
    - Install [Fastlane Property File Read Plugin](https://github.com/unitedclassifiedsapps/fastlane-plugin-property_file_read)
    - Install [Fastlane Firebase App Distribution Plugin](https://firebase.google.com/docs/app-distribution/android/distribute-fastlane) and get your Firebase Token
- Replace `YOUR_FIREBASE_TOKEN` with your Firebase Token inside `fastlane/Fastfile` file
- Replace `YOUR_FIREBASE_APP_ID` with your Firebase App Id inside `fastlane/Fastfile` file
- Replace `YOUR_FIREBASE_APP_ID` with your Firebase App Id inside `fastlane/Fastfile` file
- Replace `YOUR_GIT_USER_MAIL_ADDRESS` with your git user mail address inside `.github/workflows/deploy_to_beta.yml` file (.github folder may be invisible)
- Replace `YOUR_FIREBASE_TOKEN` with your git username inside `.github/workflows/deploy_to_beta.yml` file (.github folder may be invisible)

## Used Features

- [MVVM](https://developer.android.com/jetpack/guide) + [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Navigation Component](https://medium.com/@korwin22/jacoco-for-android-e56bffedef48) for `Single Activity Architecture`
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for synchronously or asynchronously programming
- [Live Templates](https://medium.com/google-developers/writing-more-code-by-writing-less-code-with-android-studio-live-templates-244f648d17c7s) for code generation
- [JaCoCo](https://medium.com/@korwin22/jacoco-for-android-e56bffedef48) to manage code coverage
- [SonarCloud](https://sonarcloud.io/) to manage code quality
- [Fastlane](https://fastlane.tools/) to automate every aspect of development and release workflow
- [GitHub Actions](https://docs.github.com/en/actions) for `CI/CD`
- Automated version management by gradle with `app/version-utils.gradle` and `app/version.properties` files

## Used Libraries

The dependencies are managed on dependencies.gradle file

- [Hilt](https://github.com/google/dagger/tree/master/java/dagger/hilt) for Dependency Injection
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Navigation Component](https://developer.android.com/guide/navigation)
- [Retrofit](https://github.com/square/retrofit) for Networking
- [OkHttp](https://github.com/square/okhttp) for Networking
- [Room](https://developer.android.com/training/data-storage/room) for Datavase
- [Gson](https://github.com/google/gson) for JSON serialization/deserialization
- [Picasso](https://github.com/square/picasso) for Image loading
- [MockK](https://github.com/mockk/mockk) for Testing


## Used Architecture: MVVM + Clean Architecture

#### Result
- Wrapper class contains Data, Error and Data State
- There are 3 data states: `Loading`, `Succees`, `Error`
- `Fragment` just observers that data, so it does not know where the data comes from and how the data is fetched. It just knows the result of the operation and its state

#### DataObserver
- Observes `LiveData<Result<T>>` data on `Fragment`
- Shows loading dialog when operation is started
- Hides loading dialog when operation is finished
- Shows alert dialog when operation is failed

#### NetworkBoundResource
- Decides which data source is used to fetch data
- If no need to fetch data from network or network is not available, data is fetched from database. Otherwise data is fetched from network
- If network call succeed, fetched data is saved to database
- Returns `Result<T>`

#### NetworkResource
- Fetches data from network and converts it to `Result<T>` with execute() method
- Fetches `Result<ApiResponse>` data from Network with `load()` method
- Reads `T` data from `ApiResponse` with `getResult()` method
- Returns `Result<T>`

#### DatabaseResource
- Fetches data from network and converts it to `Result<T>` with `execute()` method
- Fetches `T` data from Database with `load()` method
- Returns `Result<T>`

#### Fragment
- Extended from `BaseFragment.kt`
- Implemented from `BaseView.kt` interface to make some UI operations like showing/hiding loading/error dialogs
- Observes `LiveData<Result<T>>` variable of `ViewModel` via `DataObserver<T>` instance to be avare of data state and make UI actions
- When `Fragment` needs data, it calls appropriate method on `ViewModel`

#### ViewModel
- Contains a `LiveData<Result<T>>` instance which is observed by `Fragment` to make UI actions
- Fragment needs to call method inside `ViewModel` to fetch data. 
- That method calls `UseCase` and sets the value of the `LiveData` with returned data

#### UseCase
- Contains business logic
- Inside `Domain Layer`
- Communicates between `Data Layer` and `UI(Presentation) Layer`
- Extended from `CoroutineUseCase<P, R>`, `ResultUseCase<P, R>` or `UseCase<P, R>`
- Returns `Result<T>`

#### Repository
- Implemented from `Repository.kt` interface
- Decides which data source is used to fetch data using a `NetworkBoundResource<T>` and uses it
- Returns `Result<T>`

#### RemoteDataSource
- Implemented from `DataSource.kt` interface
- Fetches data from API using `Retrofit`
- Converts `Result<ApiResponse>` to `Result<T>` using a `NetworkResource<ApiResponse, T>` instance
- Returns `Result<T>`

#### LocalDataSource
- Implemented from `DataSource.kt` interface
- Fetches/Retrieves data from/to database using `Room`
- Converts `T` to `Result<T>` using a `DatabaseResource<T>` instance and returns `Result<T>`

#### ApiService (Retrofit)
- Makes API requests
- Converts `ApiResponse` to `Result<T>` using `SuspendCallAdapterFactory.kt` and returns `Result<T>`
- Creates `AppError` instanceif HTTP request is not succeed

#### Dao (Room)
- Makes Database operations
- Returns `ResultT`

![data_flow_diagram](https://github.com/cimenmus/mvvm-android/blob/master/images/data_flow_diagram.png?raw=true)








































