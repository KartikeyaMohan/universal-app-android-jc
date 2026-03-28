# UniversalAppJc

A sample Android application built with **Kotlin**, **Jetpack Compose**, and **Material 3**. It demonstrates a multi-feature app with authentication, movies (list and detail with paging), user profile bootstrap, and location tracking with Room, WorkManager, and a foreground service.

## Features

- **Startup**: Login and register against a REST API; tokens stored with **EncryptedSharedPreferences**.
- **Home**: Entry points to Movies, Location, and More.
- **Movies**: Paginated movie list, detail screen with cast and trailer support (Media3).
- **Location**: Foreground location collection, local persistence (**Room**), periodic sync (**WorkManager**), and bulk upload to the API.
- **Navigation**: Navigation 3 with typed `NavKey` routes and Hilt-assisted ViewModels.

## Tech stack

| Area | Libraries |
|------|-----------|
| UI | Jetpack Compose, Material 3, Coil |
| DI | Dagger Hilt |
| Networking | Retrofit, OkHttp, Kotlin Serialization |
| Async | Kotlin Coroutines, Flow |
| Local data | Room, DataStore (via security-crypto for tokens) |
| Background | WorkManager, foreground service |
| Media | Media3 ExoPlayer |

## Requirements

- Android Studio Ladybug or newer (or compatible Gradle / AGP)
- JDK 17 (as used by current Android Gradle Plugin defaults)
- **minSdk 24**, **targetSdk 36**

## Configuration

The app uses `BuildConfig.BASE_URL`:

- **Debug** (in `app/build.gradle.kts`): points to a local development server (update the IP/host for your machine).
- **Release**: uses the production-style URL placeholder.

Change the `buildTypes { debug { buildConfigField(...) } }` value before running against your backend.

## Build and run

```bash
./gradlew :app:assembleDebug
```

Install the debug APK on a device or emulator, or run from Android Studio.

## Testing

The project uses **unit tests** (`src/test`) and **instrumented / integration tests** (`src/androidTest`).

### Unit tests (JVM)

Run all debug unit tests:

```bash
./gradlew :app:testDebugUnitTest
```

These cover:

- Repositories and remote data sources (including auth, user, location, movies).
- Use cases (auth, user profile, movies).
- ViewModels (login, register, main/session bootstrap, movies, movie details, location sync actions).
- Helpers (`DataTypeHelper`, `GenericResponse` behavior).
- **MockWebServer** integration against `ApiService` (JSON parsing and HTTP behavior).

Shared patterns: **MockK**, **kotlinx-coroutines-test** (`MainDispatcherRule`, `StandardTestDispatcher`), and **Paging** test utilities where applicable.

### Instrumented tests (device / emulator)

```bash
./gradlew :app:connectedDebugAndroidTest
```

Uses **Hilt** test modules (`TestApiModule` replaces `ApiModule` with a `MockWebServer` base URL), **Compose UI Test**, and **HiltTestActivity** as the host for composable integration tests.

Integration suites include:

- Movie list and movie detail screens (loading, success, errors, pagination).
- Login and register flows (mock API + navigation assertions).
- Home and More screens (navigation and visibility).

Ensure an emulator is running or a device is connected with USB debugging enabled.

## Project layout (high level)

- `app/src/main/java/.../data` — API, DTOs, Room, repositories, remote data sources.
- `app/src/main/java/.../domain` — Repository interfaces, use cases.
- `app/src/main/java/.../ui` — Compose screens, ViewModels, navigation, theme, workers.
- `app/src/test` — JVM unit tests.
- `app/src/androidTest` — Instrumented tests, `HiltTestRunner`, fakes, test `ApiModule`.
