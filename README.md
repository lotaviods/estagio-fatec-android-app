# FATEC Internship Management Android App (Student Version)

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge&logo=appveyor)
![Kotlin](https://img.shields.io/badge/Kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-%233DDC84.svg?style=for-the-badge&logo=android&logoColor=white)
![JetpackCompose](https://img.shields.io/badge/Jetpack%20Compose-%23039BE5.svg?style=for-the-badge&logo=android&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-%23009296.svg?style=for-the-badge&logo=retrofit&logoColor=white)
![Koin](https://img.shields.io/badge/Koin-%237E4F32.svg?style=for-the-badge&logo=koin&logoColor=white)

This Android app allows students to view internship applications and apply for them within the FATEC Internship Management system. With this app, students can conveniently access internship opportunities and submit their applications using their mobile devices.

## Features

- View available internship applications.
- Submit internship applications for desired positions.

## Prerequisites

Before setting up the app, make sure you have the following:

- An Android development environment (Android Studio).
- Access to the FATEC Internship Management API.
- Basic knowledge of Android app development.

## Setup

1. Clone the project repository:

   ```bash
   git clone git@github.com:lotaviods/link-fatec-android-app.git
   ```
2. Open the project in Android Studio.

3. Configure the API endpoint: Create or update the local.properties file in the root directory of your project. Add the following line to the local.properties file, replacing the URL with the appropriate endpoint:

   ```bash
   LINK_BASE_URL=http://localhost:3000/api/
   ```
   This sets the LINK_BASE_URL variable to the base URL of the FATEC Internship Management API.



Build and run the app on an emulator or physical device.


## Technologies Used
The app utilizes the following technologies and libraries:

1. Coroutines: Provides asynchronous programming and simplifies working with background tasks.
Android Jetpack Compose: A modern UI toolkit for building native Android apps using a declarative approach.

2. MVVM Architecture: Utilizes the Model-View-ViewModel architectural pattern for separation of concerns and testability.
   
3. Retrofit: A type-safe HTTP client for making API requests.
   
4. Koin: A lightweight dependency injection framework for managing dependencies.
License


This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.