# Client Quickstart

Command Line Java application to act as an HTTP client

## Requirements
* JDK 11 or higher

## Running

**Build and test**
```
./gradlew clean build
```
Test results can be found in `build/reports/tests/test` from the root directory. 
You can navigate this in file explorer in WSL2 by simply running
```
wsl2-user@DESKTOP:~/DEV/java-client-quickstart$ explorer.exe .
```
Obviously on Linux or Mac you can just natively nagivate to the directory.

**To run main class**

Run `./gradlew run` with arguments. 

```
./gradlew run --args="arg1 arg2 arg3"
```

Events are logged to the console and to `app.log` in the `$ROOT_DIR/logs` directory

