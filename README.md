# Это приложение расписание использующее Compose Multiplatform для работы на Android, Web, Ios


## Web
Чтобы запустить дев сервер для демонстрации
```bash
./gradlew wasmJsBrowserDevelopmentRun -t
```

Чтобы сделать билд для развертывания 
```bash
./gradlew wasmJsBrowserDistribution
```

далее приложение бандл будет лежать в `composeApp/build/dist/wasmJs`