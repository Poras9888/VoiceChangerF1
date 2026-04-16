# VoiceChangerF1

Android Java MVP app for recording voice, applying effects, generating text-to-audio clips, prank sounds, and managing saved recordings.

## Tech Stack

- Android (Java 17)
- Gradle (AGP 8.6.1)
- Material Components
- Room (local storage)
- Dagger 2 (DI)
- AdMob + UMP (ads and consent)
- Native C++ DSP bridge (JNI)
- Vendored SoundTouch (pinned source)

## Project Structure

- `app/src/main/java/com/voice/changer/sound/effects/recorder/presentation/` : MVP screens and presenters
- `app/src/main/java/com/voice/changer/sound/effects/recorder/domain/usecase/` : domain use cases
- `app/src/main/java/com/voice/changer/sound/effects/recorder/data/` : Room entities, DAO, repositories
- `app/src/main/java/com/voice/changer/sound/effects/recorder/audio/` : audio recording, playback, processing
- `app/src/main/cpp/` : JNI layer + CMake + vendored SoundTouch
- `app/src/main/res/` : layouts, drawables, menus, animations, strings

## Native Audio Processing

`app/src/main/cpp/CMakeLists.txt` is configured to deterministically build against vendored SoundTouch source at:

- `app/src/main/cpp/third_party/soundtouch`

`app/src/main/cpp/soundtouch_jni.cpp` exposes JNI entrypoint used by `SoundTouchBridge` in Java.

## Requirements

- Android Studio Iguana+ (or compatible with AGP 8.6.x)
- Android SDK 34
- Java 17
- CMake (via Android SDK)
- NDK (via Android SDK)

## Setup

1. Clone the repository.
2. Open in Android Studio.
3. Ensure SDK/NDK/CMake are installed from SDK Manager.
4. Create `local.properties` if needed:

```properties
sdk.dir=/path/to/Android/Sdk
```

5. Sync Gradle.

## Build

From project root:

```bash
./gradlew assembleDebug
```

If build fails with SDK errors, confirm `sdk.dir` and Android SDK 34 installation.

## Run

- Start an emulator or connect a device (API 24+).
- Run the `app` configuration from Android Studio.

## Main Screens

- Home
- Recording
- Add Effect
- Prank Sound
- Text To Audio
- Saved Recordings
- Settings
- Reverse Voice
- Switch Voice

## Notes and Current Limitations

- Text-to-audio flow currently stores generated placeholder payload in a `.pcm` file and metadata in DB; full Android `TextToSpeech` rendering pipeline can be expanded.
- Reverse/Switch screens are now polished and interactive at UI level, but advanced processing pipelines are still basic scaffolding.
- AdMob test IDs are used in source.

## Testing

Run unit tests:

```bash
./gradlew test
```

## License

This repository currently has no explicit license file. Add one before distribution.
