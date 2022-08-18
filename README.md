# CVS_IMAGE_VIEWER
This is an Android application that retrieves image information from a server and displays them in list and detail formats.

## Build tools & versions used
This project was developed using:
- Android Studio Chipmunk | 2021.2.1 Patch 1
- kotlin v1.7
- gradle plugin v1.7.0
- jetpack compose v2.0
- com.android.application v7.2.2
- com.android.library v7.2.2
- It relies on repositories provided by `gradlePluginPortal()`, `google()` and `mavenCentral()`
- It uses Glide via (Landscapist) for image loading, Retrofit for Networking, and hilt for Dependency Injection

## Steps to run the app
Download project from git repository, then
click `run app` from the `Run` menu in Android Studio
OR
Enter the following in terminal after connecting a device running AndroidSDK 24 or newer:
```adb shell am start -n "dev.calebcodes.cvsimageviewer/dev.calebcodes.cvsimageviewer.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER```

## Note
 - I ran out of time to parse the HTML  and implement the image height and width on the detail screen.