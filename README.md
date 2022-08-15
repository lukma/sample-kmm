# GPlay

[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Kotlin](https://img.shields.io/badge/kotlin-1.7.10-blue.svg?logo=kotlin)](http://kotlinlang.org)

This is **Kotlin Multiplatform** project for learning purpose.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Dependencies](#dependencies)
- [Project Structure](#project-structure)

## Features

- [ ] Auth Feature
- [ ] Article Feature

## Requirements

- JDK 11+
- Android 6+
- iOS 13+
- Swift 5.3+

## Dependencies

- [kotlinx.coroutines](https://kotlinlang.org/docs/coroutines-guide.html) is a rich library for coroutines developed by JetBrains.
- [kotlinx.serialization](https://kotlinlang.org/docs/serialization.html) consists of a compiler plugin, that generates visitor code for serializable classes, runtime library with core serialization API and support libraries with various serialization formats.
- [MockK](https://mockk.io) is mocking library for Kotlin.
- [Koin](https://insert-koin.io) a pragmatic lightweight dependency injection framework for Kotlin.
- [SQLDelight](https://cashapp.github.io/sqldelight) generates typesafe kotlin APIs from your SQL statements.
- [Ktor](https://ktor.io) a multiplatform asynchronous HTTP client, which allows you to make requests and handle responses, extend its functionality with plugins, such as authentication, JSON serialization, and so on.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) is Android’s modern toolkit for building native UI.
- [Material Design 3](https://m3.material.io) is an adaptable system of guidelines, components, and tools that support the best practices of user interface design.

## Project Structure

This project implement [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Clean architecture

![Image Clean architecture](https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/architecture/clean_architecture_reloaded_main.png)

### Data Layer: Repository

![Image Data Layer: Repository](https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/architecture/clean_archictecture_reloaded_repository.png)
