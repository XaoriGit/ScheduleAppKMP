
# 📅 Multiplatform Schedule App

Приложение расписание колледжа на **Compose Multiplatform**

## 🔧 Запуск проекта

### 📱 Android

Собрать и запустить на эмуляторе/устройстве в Android Studio
### 📺 Web

Запуск дев-сервера:

```bash
./gradlew wasmJsBrowserDevelopmentRun -t
```

Билд для продакшена:

```bash
./gradlew wasmJsBrowserDistribution
```

Собранный бандл будет лежать в:

```
composeApp/build/dist/wasmJs
```

## 📂 Структура проекта

```
composeApp/
├─ src/
│  ├─ androidMain/ // Проект Android приложения
│  ├─ commonMain/  // Основной код
│  ├─ iosMain/     // Проект Ios приложенния
│  ├─ wasmJsMain/  // Проект для Web
build.gradle.kts
```

## 🛠 Используемые технологии

- [Kotlin Multiplatform](https://kotlinlang.org/lp/multiplatform/)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor](https://ktor.io/)
- [Koin](https://insert-koin.io/)
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)

## 📄 Лицензия

Этот проект распространяется под лицензией MIT. Подробности см. в LICENSE.a
