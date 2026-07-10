# Tasks Management

Android task management app with offline-first local storage, optional REST sync, and a Jetpack Compose UI. Users browse tasks by day, open task details, and mark items as resolved, unresolved, or can't resolve—with optional comments.

## Data sources

**No proprietary task database is stored in this project.** Data comes from:

- **Public mock REST endpoint** — `http://demo9877360.mockable.io/` on first launch when Room is empty (often unavailable; see fallback below)
- **Bundled sample tasks** — `app/src/main/assets/sample_tasks.json` used when the remote fetch fails
- **Room** — tasks, status, and comments stored **locally on the device only** after sync or fallback load

There is no custom backend or user account sync. All task updates stay on the device.

## Features

- **Day-based task list** — navigate previous/next day, empty states, and retry on errors
- **Task details** — priority, due date, target date, and status actions
- **Comments** — optional comment when marking a task as can't resolve
- **Offline cache** — Room database persists tasks after initial load
- **Status workflow** — resolved, unresolved, and can't resolve with visual indicators

## Architecture

```
Compose UI → ViewModel → Repository → TasksApi (Retrofit) / TaskDao (Room) / BundledTasksDataSource
```

| Layer | Technology |
|-------|------------|
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM, StateFlow + events |
| DI | Hilt |
| Networking | Retrofit, OkHttp, Gson |
| Local storage | Room (schema v3) |
| Navigation | Navigation Compose |
| Images | Coil |
| Testing | JUnit, MockK, Mockito, Coroutines Test |

## Requirements

- Android Studio (Ladybug or newer recommended)
- JDK 17
- Android SDK 36
- Network access optional (bundled fallback works offline)

## Getting started

1. Open the project in Android Studio.
2. Ensure `local.properties` exists (Studio generates it on first open).
3. Sync Gradle and run on a device or emulator.

See [run_instructions.md](run_instructions.md) for full build and run steps.

## Sharing this project

When uploading an archive to a third party:

1. Export from the `main` branch
2. Exclude `build/`, `.gradle/`, and `local.properties`
3. Include `.git/` if commit history is required (see `run_instructions.md`)
