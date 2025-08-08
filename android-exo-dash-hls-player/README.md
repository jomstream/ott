# Exo Dash/HLS Player (Android)

A minimal Android app using Media3 ExoPlayer to play HLS or DASH streams.

## Requirements
- Android Studio Giraffe/Koala or newer
- Android SDK 34+
- JDK 17

## How to run
1. Open this folder in Android Studio: `android-exo-dash-hls-player`
2. Let Android Studio download dependencies and sync Gradle.
3. Run the `app` configuration on a device/emulator with internet access.

## Usage
- By default, the app plays an HLS sample.
- To play a specific URL or force DASH via `adb`:

```bash
adb shell am start -n com.example.exodashhls/.MainActivity \
  --es url "https://your-stream-url.m3u8"
```

or to use the built-in DASH sample:

```bash
adb shell am start -n com.example.exodashhls/.MainActivity \
  --ez dash true
```

## Notes
- Uses Media3 `ExoPlayer` with `media3-exoplayer`, `media3-exoplayer-dash`, `media3-exoplayer-hls`, and `media3-ui`.
- Add DRM keys or custom data sources as needed.