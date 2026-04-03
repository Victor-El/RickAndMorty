# Rick and Morty Android App

A modern Android application built with Jetpack Compose that showcases characters, locations, and episodes from the Rick and Morty universe. This project demonstrates best practices in Android development, including Clean Architecture, multi-module setup, and the latest Jetpack libraries.

## 🚀 Features

- **Character Explorer**: Browse through the extensive list of Rick and Morty characters.
- **Detailed Information**: View in-depth details for each character, including their status, species, origin, and last known location.
- **Favorites**: Save your favorite characters for quick access (Offline support).
- **Pagination**: Seamlessly scroll through long lists of data using Paging 3.
- **Clean UI**: A responsive and modern user interface built entirely with Jetpack Compose.

## 🛠 Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture**: Clean Architecture with MVI/MVVM patterns.
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Pagination**: [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data)
- **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Navigation**: [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) & [Gson](https://github.com/google/gson)

## 🏗 Project Structure

The project is organized into multiple modules to promote separation of concerns and scalability:

- `:app`: The main entry point of the application.
- `:domain`: Contains business logic, entities, and repository interfaces.
- `:data`: Implementation of repositories, API services, and local database logic.
- `:core`: Shared UI components, themes, and utility classes.
- `:feature:feeds`: The main character list and discovery feature.
- `:feature:favourites`: Logic and UI for managing favorite characters.

## ⚙️ Setup

1.  Clone the repository:
    ```bash
    git clone https://github.com/yourusername/RickAndMorty.git
    ```
2.  Open the project in **Android Studio (Ladybug or newer)**.
3.  Sync the project with Gradle files.
4.  Run the `app` module on an emulator or physical device.

## 🧪 Testing

The project includes unit tests for business logic and data layers.
To run the tests:
```bash
./gradlew test
```

## 📜 License

This project is for educational purposes. Data is provided by [The Rick and Morty API](https://rickandmortyapi.com/).
