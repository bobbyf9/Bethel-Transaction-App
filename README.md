# Bethel Transaction App

## Overview
Bethel Transaction App is a mobile application for tracking financial transactions, allowing users to log, view, and manage transactions seamlessly. It integrates with Firebase for authentication and database storage.

## Features
- User authentication via Firebase
- Add, view, and delete financial transactions
- Store transaction data securely in Firebase
- Intuitive UI with RecyclerView for displaying transactions

## Tech Stack
### Frontend (Mobile App)
- **Language:** Java
- **Framework:** Android SDK
- **Database & Authentication:** Firebase

### Backend
- **Platform:** Firebase Realtime Database
- **Authentication:** Firebase Authentication

## Setup Instructions
### Prerequisites
Ensure you have the following installed:
- Android Studio
- Internet Connection
- Java Development Kit (JDK)
- Firebase account

### Steps to Run the Mobile App
1. Clone the repository:
   ```sh
   git clone https://github.com/bobbyf9/bethel-transaction-app.git
   cd bethel-transaction-app
   ```
2. Open the project in **Android Studio**.
3. Connect the app to Firebase:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new Firebase project
   - Add the Android app to the Firebase project
   - Add App name by copying the app name as is on the package name in Android Studio
   - You can optionally add the SHA-1 off the app to Firebase
   - To get the SHA-1 run this command in Android Studio terminal when the Project Files are open "./gradlew signingReport" do not run with the quotes
   - Download `google-services.json` and place it in `app/` directory on Android Studio
   - Activate Realtime Database on Firebase portal
   - Go to the Authentication Section on Firebase and on the Sign-in method select Email/Password as the provider
4. Sync Gradle and run the app on an emulator or physical device.
   
**Internet connection is necessary for the apk to communicate with the dataabse**

## Firebase Database Rules
Ensure your Firebase Database has the following security rules:
```json
{
  "rules": {
    "transactions": {
      "$userId": {
        ".read": "auth != null && auth.uid == $userId",
        ".write": "auth != null && auth.uid == $userId"
      }
    }
  }
}
```

### User Manual (using the app)

1. After opening the app for first-time users register on the app with email after successful registration use the login option
2. On successful login on the home page use the add icon on the bottom right of the screen to add transactions.
3. Transactions added will be displayed on the home screen
   
**note: user cannot see the transactions of other users only his/her transactions**

## Contribution Guidelines
1. Fork the repository.
2. Create a new branch for your feature.
3. Commit and push your changes.
4. Open a pull request for review.

## License
This project has no license as it is a mere demonstration so use it well.
Created by Bethel Chiguware.
