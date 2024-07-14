
<h1 align="center"> CROPDOC - The Farmer's Friend  </h1>
<p align="center"> <img alt="API" src="https://img.shields.io/badge/Api%2024+-50f270?logo=android&logoColor=black&style=for-the-badge"/>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-a503fc?logo=kotlin&logoColor=white&style=for-the-badge"/>
  <img alt="Jetpack Compose" src="https://img.shields.io/static/v1?style=for-the-badge&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label="/>
</p>

<p id="description">CropDoc is an essential tool for farmers, offering precise disease identification, personalized crop recommendations, timely agricultural news updates, and seamless crop activity tracking, all in one user-friendly application.</p>

## Installation ⬇️

<a href="https://github.com/atmikgoswami/CropDoc/releases/download/v2.1/app-debug.apk"><img alt="Get it on GitHub" src="https://user-images.githubusercontent.com/69304392/148696068-0cfea65d-b18f-4685-82b5-329a330b1c0d.png" height=80px />


<h2>🧐 Features</h2>

Here're some of the project's best features:

*   Email and Password Authentication
*   Disease Prediction : Upload any image of a diseased plant or a leaf, and receive predictions and possible cures, at the click of a button.
*   Crop Recommendation : Get tailored recommendation about suitable crops that can be grown, at your location given soil pH, water availability and season.
*   AgriNews : Get latest agriculture related news of India, and stay updated about the happenings in the agriculture industry.
*   Your Crops : Personalised crop activity tracker. Add your current crops. Add daily activities that are performed for each crop.

<h2>Project Screenshots📱:</h2>
<p align="start">

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376028/cropdoc_screenshots/IMG-20240614-WA0024_gkaomp.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376028/cropdoc_screenshots/IMG-20240614-WA0026_raxiof.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376028/cropdoc_screenshots/IMG-20240614-WA0025_hmuaaw.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376030/cropdoc_screenshots/IMG-20240614-WA0027_gjeh13.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376028/cropdoc_screenshots/IMG-20240614-WA0028_rlfxfs.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376029/cropdoc_screenshots/IMG-20240614-WA0029_ue5tbw.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376030/cropdoc_screenshots/IMG-20240614-WA0034_fflwky.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376030/cropdoc_screenshots/IMG-20240614-WA0033_ithvcx.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376030/cropdoc_screenshots/IMG-20240614-WA0030_ih6k2t.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376029/cropdoc_screenshots/IMG-20240614-WA0031_eio8hs.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376029/cropdoc_screenshots/IMG-20240614-WA0032_p2uqp4.jpg" alt="project-screenshot" width="160px" >

<img src="https://res.cloudinary.com/ddgeelsit/image/upload/v1718376030/cropdoc_screenshots/IMG-20240614-WA0035_kewpga.jpg" alt="project-screenshot" width="160px" >

</p>

## Backend Server
The models for crop recommendation and disease prediction have been developed and deployed as a Flask app.
https://github.com/atmikgoswami/CropDoc-Backend

## Building 🏗️

1. Clone the project
2. Open Android Studio IDE
3. Go to File » New » Project from VCS
4. Paste ``` https://github.com/atmikgoswami/CropDoc.git ```
5. Open the project
6. Create a Firebase project and add the ```google-services.json``` in the app directory of the project
7. Grab your ```YOUR_WEATHER_API_KEY``` from [https://openweathermap.org/api]
8. Grab your ```YOUR_NEWS_API_KEY``` from [https://newsdata.io/api-key]
9. Now, in your local.properties add the block
``` 
  WEATHER_API_KEY=YOUR_WEATHER_API_KEY
  NEWS_API_KEY=YOUR_NEWS_API_KEY
```
10. Build and run
  
<h2>Built with 💻</h2>

Technologies used in the project:

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous calls and tasks to utilize threads.
- [Jetpack Compose UI Toolkit](https://developer.android.com/jetpack/compose) - Modern UI development toolkit.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class.) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
    - [Dagger-Hilt](https://dagger.dev/hilt/) - A standard way to incorporate Koin dependency injection into an Android application.
    - [Hilt-ViewModel](https://dagger.dev/hilt/view-model) - DI for injecting ```ViewModel```. 
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Firebase Authentication and Firestore Database](https://firebase.google.com/) - Comprehensive platform for developing mobile and web apps, offering services like authentication and cloud storage. Firestore is its flexible, scalable NoSQL database designed for real-time data synchronization and seamless offline support
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room) - Room Database is an Android SQLite object mapping library that provides an abstraction layer over SQLite to allow for more robust database access while leveraging the full power of SQLite queries.
  
# Architecture 👷‍♂️
This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch)  and Clean architecture.

## Contributing 🤝

Contributions are always welcome. Feel free to make a pull request!

Highly appreciate leaving a :star: if you liked it!
