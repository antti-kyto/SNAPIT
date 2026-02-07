# SNAPIT!

SNAPIT! is an open-source mobile party game.
The game challenges users to find and photograph real-world objects within a one-minute window, 
using on-device or API-based AI to make the challenge and validate the results.

## Configure

Update APIKEY with your google AI apikey.

Kooq supports other models too: https://docs.koog.ai/llm-providers/

If you want to use different model just replace `simpleGoogleAIExecutor`.

## Screenshots

 1 | 2
:-------------------------:|:-------------------------:
![](images/Screenshot_20260207-115914.png)  |  ![](images/Screenshot_20260207-121116.png) | 
![](images/Screenshot_20260207-125853.png) | ![](images/Screenshot_20260207-125830.png)

This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

Easiest way to build android version is to open the project with android studio.

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…