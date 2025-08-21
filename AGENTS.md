# Contributor Guide

## Overview
- Android app built with Kotlin and Jetpack Compose.
- Main source code resides in the `app` module.

## Dev Environment Tips
- Open the project in Android Studio and allow Gradle to sync.
- Use `./gradlew assembleDebug` to build the debug app locally.

## Testing and Lint
- Run `./gradlew test` to execute unit tests.
- Run `./gradlew lint` to perform static analysis.
- Ensure both commands succeed before committing changes.

## PR Instructions
- Title format: `[app] <Title>`
- Describe changes and testing steps in the PR body.
