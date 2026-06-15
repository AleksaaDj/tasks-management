# Tasks Management

Android task management app with offline-first local storage, REST sync, and a Jetpack Compose UI. Users browse tasks by day, open task details, and mark items as resolved, unresolved, or can't resolve—with optional comments.

## Features

- **Day-based task list** — navigate previous/next day, empty states, and retry on errors
- **Task details** — priority, due date, target date, and status actions
- **Comments** — optional comment when marking a task as can't resolve
- **Offline cache** — Room database persists tasks after initial sync
- **Status workflow** — resolved, unresolved, and can't resolve with visual indicators

## Tech stack

| Layer | Technology |
|-------|------------|
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM, unidirectional data flow (`StateFlow` + events) |
| DI | Hilt |
| Networking | Retrofit, OkHttp, Gson |
| Local storage | Room (schema v3, exported migrations) |
| Navigation | Navigation Compose |
| Images | Coil |
| Testing | JUnit, MockK, Mockito, Coroutines Test |

- **Min SDK:** 24  
- **Target / Compile SDK:** 36  
- **Kotlin:** 2.2  
- **JDK:** 11+

## Architecture

```
┌─────────────┐     ┌──────────────────┐     ┌─────────────┐
│  Compose UI │ ──► │    ViewModel     │ ──► │ Repository  │
└─────────────┘     └──────────────────┘     └──────┬──────┘
                                                    │
                              ┌─────────────────────┴─────────────────────┐
                              ▼                                           ▼
                       ┌─────────────┐                            ┌─────────────┐
                       │  TasksApi   │                            │   TaskDao   │
                       │  (Retrofit) │                            │   (Room)    │
                       └─────────────┘                            └─────────────┘
```

**Package layout**

- `data/` — models, API, Room entities/DAO, repository
- `presenter/` — screens and reusable Compose components
- `ui/` — theme, UI state, events
- `di/` — Hilt modules
- `navigation/` — routes and `NavHost`

On first launch, if the local database is empty, the app fetches tasks from a remote endpoint and caches them in Room. Subsequent reads are served from the database; status and comment updates are written locally.

## Getting started

### Prerequisites

- Android Studio (Ladybug or newer recommended)
- JDK 11+
- Android SDK 36

### Build and run

```bash
./gradlew assembleDebug
```

Install the debug APK on a device or emulator, or run from Android Studio.

### Run tests

```bash
./gradlew test
```

Unit tests cover the repository, ViewModels, mappers, navigation, date utilities, and DI wiring.

## API note

The app syncs from a public mock endpoint on first launch:

```
http://demo9877360.mockable.io/
```

That service is often unavailable. When the remote fetch fails, the app **falls back to bundled sample tasks** in `app/src/main/assets/sample_tasks.json`, so first launch still works offline.

## Project structure

```
app/src/main/java/com/softwavegames/tasksmanagement/
├── data/
├── di/
├── navigation/
├── presenter/
└── ui/
```

Room schema exports live under `app/schemas/`.

## License

MIT — see [LICENSE](LICENSE).
