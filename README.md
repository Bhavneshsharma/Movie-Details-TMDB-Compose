# 🎬 Movie Details TMDB Compose App

Welcome to the **Movie Details App**, a sample Android application that showcases the use of modern Android development tools and best practices. This app leverages the power of the [TMDB API](https://www.themoviedb.org/) to provide detailed information about movies.

## 📱 Features

- **Two Screen Layout**:
  - **Movie List Screen**: Browse a list of trending movies. The screen includes a powerful search functionality, allowing users to search for movies by title. If a movie is not found, a 'No Data Found' screen is displayed.
  - **Movie Detail Screen**: Dive into detailed information about the selected movie, including an overview, title and banner.

- **Search Functionality**: Users can effortlessly search for movies. If the searched movie is available, it appears in the list; otherwise, a 'No Data Found' screen gracefully handles the empty state.

- **Error and Loading State Management**: The app gracefully manages various states, ensuring a smooth user experience. Whether it's loading data or handling errors, the app's UI responds appropriately.

## 🛠️ Technologies Used

- **Kotlin Coroutines & Flow**: For managing asynchronous operations and handling data streams efficiently.
- **Dagger Hilt**: For dependency injection, providing a robust and scalable architecture.
- **Retrofit**: For network requests to interact with the TMDB API.
- **Interceptor**: For handling network responses and managing API keys securely.
- **Jetpack Compose**: For building the UI declaratively, ensuring a modern and flexible user interface.
- **State Management**: For handling the UI state, ensuring the app responds dynamically to data changes.
- **Navigation**: For seamless navigation between the Movie List Screen and Movie Detail Screen.
- **Glide**: For efficient image loading and caching.

## 🎥 Demo

Check out a quick demo of the app in action: [Demo Video](https://drive.google.com/file/d/1cCRaTFpaIOZXfOi3b_rbMHMLITV03RTl/view?usp=sharing)  
