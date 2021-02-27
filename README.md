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
- [SonarCloud](https://sonarcloud.io/) to manage code quality (Not implemented yet)
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
![mvvm_android](https://github.com/cimenmus/mvvm-android/blob/master/images/mvvm_android.png?raw=true)

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
- Returns `Result<T>`

![data_flow_diagram](https://github.com/cimenmus/mvvm-android/blob/master/images/data_flow_diagram.png?raw=true)

## Live Templates
Live templates and install instructions can be found on `live_templates.txt` file
Available templates:
- `activity` -> Creates an `Activity` extended from `BaseActivity`
- `activityWithExtra` -> Creates an `Activity` extended from `BaseActivity` with extras
- `fragment` -> Creates a`Fragment` extended from `BaseFragment`
- `fragmentWithArgs` -> Creates a `Fragment` extended from `BaseFragment` with arguments
- `recyclerAdapter` -> Creates a `RecyclerAdapter`
- `recyclerAdapterMultiHolder` -> Creates a `RecyclerAdapter` with multiple `ViewHolder`
- `customView` -> Creates a Custom View extended from `LinearLayout`
- `viewModel` -> Creates a `ViewModel` instance
- `useCase` -> Creates a `UseCase` which extended from `UseCase`, runs synchronized and returns a `Result` instance with given type
- `useCase:Coroutine` ->Creates a `UseCase` which extended from `CoroutineUseCase`, runs `suspend` and returns a `Result` instance with given type
- `useCase:Result` -> Creates a `UseCase` which extended from `ResultUseCase`, runs `suspend` and returns a `Result` instance with given type. The return type of the `execute` method has to be a `Result` instance with given type.
- `repository` -> Creates a `Repository` interface
- `repositoryImpl` -> Creates a `Repository` instance which implemented from `Repository`
- `dataSource` -> Creates a `DataSource` interface
- `dataSource:Local` -> Creates a `LocalDataSource` instance which implemented from `DataSource`
- `dataSource:Remote` -> Creates a `RemoteDataSource` instance which implemented from `DataSource`
- `unitTest` -> Creates a `Unit Test` instance

## Fastlane
Yo can set up lanes at fastlane/fastfile. Available lanes:

###### test
Runs tests by options:
- type 
    - unit -> Runs Unit Tests for all variants. Default. To run, command `fastlane test` or `fastlane test type:unit` on terminal
    - unitRelease -> Runs Unit Tests for Release build. To run, command `fastlane test type:unitRelease` on terminal
    - android -> Runs all the Instrumented(Android) Tests on Release and Debug variants. An android device has to be connected. To run, command `fastlane test type:android` on terminal
    - all -> Runs all Unit Tests and Android Tests. An android device has to be connected. To run, command `fastlane test type:all` on terminal

###### createCoverage
- Creates coverage report with Jacoco and open it on Google Chrome. 
- To run, command `fastlane createCoverage` on terminal

###### showCoverage
- Show coverage report created by Jacoco on Google Chrome. 
- To run, command `fastlane showCoverage` on terminal

###### beta
Makes version increment and deploy a new version to the Firebase App Distribution with options:
- version
    - patch -> Makes patch increment on app/version.properties file. Default. To run, command `fastlane beta version:patch` on terminal
    - minor -> Makes minor increment on app/version.properties file. To run, command `fastlane beta version:minor` on terminal
    - major -> Makes major increment on app/version.properties file.  To run, command `fastlane beta version:major` on terminal
- runUnitTests
    - true -> Runs unit tests before creating APK. Default. To run, command `fastlane beta runUnitTests:true` on terminal
    - false -> Does not run unit test before creating APK. To run, command `fastlane beta runUnitTests:false` on terminal
- gitUserMail -> Git user mail address to make version bump commit. Default is nil. To run, command `fastlane beta gitUserMail:user@domain.com` on terminal
- gitUserName -> Git user name to make version bump commit. Default is nil. To run, command `fastlane beta gitUserName:your-username` on terminal
- sample usage -> `fastlane beta version:patch runUnitTests:false gitUserMail:user@domain.com gitUserName:your-username`

###### deploy
Deploys a new version to the Google Play (Not configured)

## GitHub Actions
There are two actions:
 
###### Build, Test and Check Code Quality 
- Defined on `.github/workflows/build_and_code_quality.yml` file
- Triggered on every push and pull request to every branch
- Jobs:
    - Build project
    - Run Unit Tests
    - Run Android tests on emulator
    - Create code coverage report, send it to Sonar Clould and check code quality

###### Deploy Release APK to Firebase App Distribution
- Defined on `.github/workflows/deploy_to_beta.yml` file
- Triggered on every push and pull request to master branch
- Jobs:
    - Make version increment, push version bump commit, build release APK and deploy to Firebase using Fastlane










































