# Movies

A template Android Project using [The Movie Database(TMDb) API](https://www.themoviedb.org/documentation/api)

# To Run Project

- Create a TMDb account and API Key. Then replace `YOUR_TMDB_API_KEY` with your TMDb API Key inside `dependencies.gradle` file
- Create a [Firebase](firebase.google.com) account and then create an Android app on [Firebase Console](https://console.firebase.google.com/)
- Install [Fastlane](https://docs.fastlane.tools/) to your computer. 
    - Install [Fastlane Property File Read Plugin](https://github.com/unitedclassifiedsapps/fastlane-plugin-property_file_read)
    - Install [Fastlane Firebase App Distribution Plugin](https://firebase.google.com/docs/app-distribution/android/distribute-fastlane) and get your Firebase Token
- Replace `YOUR_FIREBASE_TOKEN` with your Firebase Token inside `fastlane/Fastfile ` file
- Replace `YOUR_FIREBASE_APP_ID` with your Firebase App Id inside `fastlane/Fastfile` file
- Replace `YOUR_FIREBASE_APP_ID` with your Firebase App Id inside `fastlane/Fastfile` file
- Replace `YOUR_GIT_USER_MAIL_ADDRESS` with your git user mail address inside `.github/workflows/deploy_to_beta.yml` file (.github folder may be invisible)
- Replace `YOUR_FIREBASE_TOKEN` with your git username inside `.github/workflows/deploy_to_beta.yml` file (.github folder may be invisible)

# Used Features

- [MVVM](https://developer.android.com/jetpack/guide) + [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Navigation Component](https://medium.com/@korwin22/jacoco-for-android-e56bffedef48) for Single Activity Architecture
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for synchronously or asynchronously programming
- [Live Templates](https://medium.com/google-developers/writing-more-code-by-writing-less-code-with-android-studio-live-templates-244f648d17c7s) for code generation
- [JaCoCo](https://medium.com/@korwin22/jacoco-for-android-e56bffedef48) to manage code coverage
- [SonarCloud](https://sonarcloud.io/) to manage code quality
- [Fastlane](https://fastlane.tools/) to automate every aspect of development and release workflow
- [GitHub Actions](https://docs.github.com/en/actions) for CI/CD
- Automated version management by gradle with app/version-utils.gradle and app/version.properties files

# Used Libraries

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


# Used Architecture: MVVM + Clean Architecture

![architecture](https://github.com/icmenyunus/yemeksepeti-iOSChallenge/blob/main/images/mvvm_ios_diagram.jpg?raw=true)


# App Data Flow Diagram

![data_flow_diagram](https://github.com/cimenmus/mvvm-android/blob/main/images/data_flow_diagram.png?raw=true)








































