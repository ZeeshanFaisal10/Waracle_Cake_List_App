# Waracle Cake List App

An Android application built with Kotlin and Jetpack Compose for the Waracle mobile coding exercise.

The application retrieves cakes from the provided API, removes duplicates, sorts them alphabetically and displays their images and titles. Users can view cake descriptions, refresh the list and retry failed requests.

## Features

- Retrieve cakes from the Waracle API using Retrofit.
- Remove duplicate cakes using normalised titles.
- Display cakes alphabetically.
- Display cake images using Coil with a network image fallback.
- View cake descriptions in a Material 3 dialog.
- Refresh the cake list.
- Display loading, empty and error states.
- Retry failed initial API requests.
- Preserve existing cakes when refresh requests fail.
- Retain loaded state across screen orientation change using ViewModel.
- Animate list items using fade and slide effects.
- Support vertical scrolling with LazyColumn.

## Architecture

The application follows an MVVM architecture with separation between the presentation, domain and data layers.

### Presentation Layer

Contains the Jetpack Compose components, UI state and ViewModel.

CakeListViewModel manages the initial loading, refresh, error handling and cake selection state. UI components receive state and callbacks rather than accessing the repository directly.

### Domain Layer

Contains the Cake model and CakeListProcessor.

CakeListProcessor removes duplicate cakes by normalising their titles and sorts the resulting list alphabetically.

### Data Layer

Contains the Retrofit API service, DTO, mapper and repository implementation.

CakeRepositoryImpl retrieves the API response and maps the DTOs into domain models.

### Dependency Injection

The application uses manual constructor injection through AppContainer and CakeListViewModelFactory.

This approach was chosen to keep dependencies and configuration lightweight for the scope of the exercise.

```text
Waracle Cake List API
    |
CakeApiService (Retrofit)
    |
CakeRepositoryImpl
    |
CakeListViewModel
    |
CakeListProcessor
    |
CakeListUiState (StateFlow)
    |
CakeListScreen (Jetpack Compose)
```

## API

The application uses the following endpoint:

```text
GET https://raw.githubusercontent.com/Waracle/mobile-coding-test-api/refs/heads/main/cakes
```

The response is converted into domain models before being processed and displayed.

## Requirements

1. Android Studio with a compatible Android SDK installed.
2. A supported JDK configured for the project's Gradle version.
3. An Android emulator or physical Android device.
4. An internet connection to retrieve cake data.

## Build

Clone the repository:

```bash
git clone https://github.com/ZeeshanFaisal10/Waracle_Cake_List_App.git
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

On macOS or Linux:

```bash
./gradlew assembleDebug
```

## Tests

The application includes isolated local unit tests for cake processing and ViewModel behaviour.

### CakeListProcessor tests

Verify duplicate removal, alphabetical sorting, combined processing, and empty input.

### CakeListViewModel tests

Verify that:

1. Successful initial loading produces a sorted, deduplicated list of cakes.
2. Failed initial loading produces the appropriate error state.

The ViewModel tests use a fake repository to avoid real network requests and a coroutine test dispatcher to control asynchronous execution.

Windows:

```powershell
.\gradlew.bat testDebugUnitTest
```

macOS or Linux:

```bash
./gradlew testDebugUnitTest
```

To perform a clean build, execute unit tests and assemble the application:

```powershell
.\gradlew.bat clean testDebugUnitTest assembleDebug
```

The project was successfully built and the unit test task completed during development.

## Design Decisions

### MVVM with Repository Pattern

Chosen to separate UI state management from data retrieval and make the ViewModel independently testable.

### Manual Dependency Injection

Used instead of a dependency injection framework to reduce configuration and keep the implementation appropriate for a small application.

### Centralised Cake Processing

Duplicate removal and sorting are implemented in a separate Kotlin component so the behaviour can be tested independently from Android framework classes.

### StateFlow and Lifecycle Awareness

Used to expose observable UI state and collect it safely in Jetpack Compose.

### Refresh Error Handling

Existing content remains visible if a refresh fails, rather than replacing the entire screen with an error.

### Animations

List entry animations use drawing transformations to preserve item layout measurements and scrolling behaviour.

## Potential Enhancements

The source code includes TODO comments documenting possible enhancements:

1. Prevent concurrent duplicate API requests.
2. Provide an offline drawable fallback for unavailable network images.
3. Make the API base URL configurable for different environments.
4. Add stronger validation when mapping API responses.
5. Introduce unique cake identifiers if supported by the API.
6. Consider locale-aware alphabetical sorting.

These enhancements were intentionally left outside the exercise scope to prioritise the required functionality, testability and maintainability.

## Use of AI

AI development assistant was used for UI implementation guidance, unit testing guidance, and documentation preparation.

The implementation was reviewed, adapted and verified through Android Studio, emulator testing and Gradle build and test execution.
