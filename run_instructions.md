# Build and Run Instructions

This document describes how to build and run **Tasks Management** from the `main` branch.

## Prerequisites

- **Android Studio** (Ladybug or newer recommended)
- **JDK 17**
- **Android SDK 36** (installed via Android Studio SDK Manager)
- A physical device or emulator (API 24+)

## One-time setup

### 1. Clone or extract the project

```bash
git clone https://github.com/AleksaaDj/tasks-management.git
cd tasks-management
git checkout main
```

### 2. Create `local.properties`

Android Studio creates this automatically on first open. If building from the command line, create it manually:

```properties
sdk.dir=/path/to/Android/sdk
```

No API keys, Firebase config, or release signing are required for debug builds.

## Build with Android Studio

1. Open the project folder in Android Studio.
2. Wait for Gradle sync to finish.
3. Select a device or emulator.
4. Click **Run** or press `Shift+F10` / `Ctrl+R`.

## Build from the command line

```bash
chmod +x gradlew
./gradlew assembleDebug
```

Output:

```
app/build/outputs/apk/debug/app-debug.apk
```

Install and launch:

```bash
./gradlew installDebug
adb shell am start -n com.softwavegames.tasksmanagement/.MainActivity
```

### Release APK

```bash
./gradlew assembleRelease
```

### Clean build

```bash
./gradlew clean assembleDebug
```

## Run tests

```bash
./gradlew testDebugUnitTest
./gradlew connectedDebugAndroidTest
```

Or all unit tests:

```bash
./gradlew test
```

## Data the app uses at runtime

| Source | Type | When used |
|--------|------|-----------|
| `http://demo9877360.mockable.io/` | Public mock REST API | First launch if Room is empty |
| `assets/sample_tasks.json` | Bundled fallback | When remote fetch fails |
| Room database | Local only | All reads after initial load; status/comment updates |

The app works fully offline after the first successful load (remote or bundled).

## Using the app

1. Launch the app — on first run it tries the public mock API, then falls back to bundled sample tasks if needed.
2. Browse tasks by day using previous/next navigation.
3. Tap a task for details.
4. Mark as resolved, unresolved, or can't resolve (with optional comment).

## Troubleshooting

| Issue | Fix |
|-------|-----|
| Gradle sync fails | Use JDK 17 in **Settings → Build → Gradle → Gradle JDK** |
| SDK not found | Install Android SDK 36; set `sdk.dir` in `local.properties` |
| Empty list on first launch | Normal if mock API is down — bundled `sample_tasks.json` should load |
| KSP / Room errors | Run `./gradlew clean` then rebuild |

## Project info

| Setting | Value |
|---------|-------|
| Application ID | `com.softwavegames.tasksmanagement` |
| Min SDK | 24 |
| Target / Compile SDK | 36 |
| Gradle | 8.13 (via wrapper) |
| Mock API base URL | `http://demo9877360.mockable.io/` |
