# QuotesApp_2025
A beautifully crafted Jetpack Compose Quotes App that showcases inspirational quotes with dynamic visuals in a reels-style vertical pager. Built with clean architecture, offline support, and modern Android development practices.

📱 QuotesApp_2025 is a modern Android app built using Jetpack Compose that displays inspirational quotes in a vertical swipe format, similar to Reels. It features clean architecture, offline support, dynamic visuals, and background syncing with Firebase.

✨ Key Features

📜 Swipe up and down to browse quotes using **VerticalPager**
🎨 Each quote is shown with a dynamic, themed background
❤️ Like or favorite quotes (saved in **RoomDB**)
📤 Share quotes via Android share intent
📶 Works offline by caching quotes locally in Room
☁️ **Syncs** data from **Firebase** Firestore
🕒 Sync happens on first launch (if database is empty) and then daily using **WorkManager**
🌗 Supports both dark and light **themes** with custom backgrounds and typography

🧱 Architecture and Technologies

🏗️ Clean Architecture with separate layers for UI, Domain, and Data
🧩 **Jetpack Compose** for UI
🗃️ RoomDB for local storage
🔥 Firebase Firestore as cloud backend
📦 **Moshi** for JSON parsing
📥 WorkManager for background syncing
🧪 ViewModel + StateFlow for state management
🗺️ Navigation-Compose for screen transitions
🔧 **Koin** for dependency injection
