# Expected system configuration:
Repository is configured and was tested for use under following host system configuration:
- Ubuntu 14.04 LTS
- ruby 1.9.3
- GNU Make >= 3.81
- openjdk-7-jdk installed with deb
- Android SDK installed to `$HOME/Android/Sdk (default setting)
- calabash-android 0.6.0

# Makefile based build:
- run `source ./env` to set environment variables: ANDROID_HOME, JAVA_HOME and update PATH for Android binaries. Edit ./env if your Android SDK and JDK binaries have different location.
- run `make` in your terminal to  build CreditCard application
Application apk file will be located in ./debug/ directory. File isn't signed, sign it with your signature if neccessary. 

# Manual build:
- run `source ./env` to set environment variables: ANDROID_HOME, JAVA_HOME and update PATH for Android binaries. Edit ./env if your Android SDK and JDK binaries have different location.
- cd to CreditCard directory
- run `bash gradlew assembleDebug` to build apk. Resulting apk will be located in `CreditCard/app/build/outputs/apk` directory

# Application tests:
- launch your prefered Android Device via Android Virtual Device Manager 
- run `source ./env` to set environment variables: ANDROID_HOME, JAVA_HOME and update PATH for Android binaries. Edit ./env if your Android SDK and JDK binaries have different location.
- run `make test` to launch calabash tests. Make will successfully exit on tests pass or fail if tests were failed

