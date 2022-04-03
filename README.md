# Build Configurations

-----------------------------------------------
Gradle 7.2
-----------------------------------------------

Ant:          Apache Ant(TM) version 1.10.9
JVM:          12.0.1 (Oracle Corporation 12.0.1+12)
OS:           Mac OS X 10.12.6 x86_64

## This project uses JDK 12
Please export JDK 12 path to PATH

# How to import project?

Kindly follow below instructions to import project into Android Studio:
1. Click on File Menu
2. Click on Open
3. Let the Android Studio completes the import and dependencies downloading

# How to build?

Once import is done, clicks on Build menu and follow the below instructions:
1. Clean Project (Let it complete)
2. Make Project

# How to run/install?
To successfully run/install in Android Device, attach the said device with your computer.
Select app option from the configuration and your attached device should be selected into Device Selector.
Press the run/play button.

# Architecture

Programming Language: Kotlin
Architectural Pattern: MVVM

Google recommended [architecture](https://developer.android.com/jetpack/guide)

This project demonstrates MVVM architectural design pattern. SOLID principles are the core part.
Repository handles the data flow either from the remote source or from local.
ViewModel converts data provided by repository into the form required by the presentation layer.
ViewModel along with LiveData take cares of the data issues arise during lifecycle changes as they are lifecycle aware components.

# Libraries

Material Design + Architecture Component + AndroidX Artifacts
Retrofit2 - Networking Api
Gson - Json Handling
