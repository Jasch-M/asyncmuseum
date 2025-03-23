# Firebase Setup Guide for Museum of Natural History Website

This guide will walk you through the process of setting up Firebase for the Museum of Natural History website.

## Prerequisites

- A Google account
- Node.js and npm installed (already done as you have the project)

## Steps to Set Up Firebase

### 1. Create a Firebase Project

1. Go to the [Firebase Console](https://console.firebase.google.com/)
2. Click on "Add project"
3. Enter a name for your project (e.g., "Museum of Natural History")
4. Choose whether to enable Google Analytics (recommended)
5. Accept the terms and click "Create project"
6. Wait for the project to be created, then click "Continue"

### 2. Register Your Web App with Firebase

1. On the Firebase project dashboard, click on the web icon (</>) to add a web app
2. Enter a nickname for your app (e.g., "Museum Website")
3. Check the box for "Also set up Firebase Hosting" if you plan to deploy the site with Firebase
4. Click "Register app"
5. You'll see the Firebase configuration object that looks like this:

```javascript
const firebaseConfig = {
  apiKey: "YOUR_API_KEY",
  authDomain: "YOUR_AUTH_DOMAIN",
  projectId: "YOUR_PROJECT_ID",
  storageBucket: "YOUR_STORAGE_BUCKET",
  messagingSenderId: "YOUR_MESSAGING_SENDER_ID",
  appId: "YOUR_APP_ID",
  measurementId: "YOUR_MEASUREMENT_ID"
};
```

6. Copy these values as you'll need them for your `.env` file
7. Click "Continue to console"

### 3. Set Up Authentication

1. In the Firebase console, go to "Authentication" from the left sidebar
2. Click on "Get started"
3. Enable the sign-in methods you want to use:
   - Google
   - Facebook
   - Apple
   - Microsoft
4. For each provider, follow the specific setup instructions:

#### Google Sign-In
1. Click on Google in the Sign-in providers list
2. Enable it and provide your support email
3. Click "Save"

#### Facebook Sign-In
1. Click on Facebook in the Sign-in providers list
2. You'll need to create a Facebook app in the [Facebook Developers Console](https://developers.facebook.com/)
3. Follow the instructions to get the App ID and App Secret
4. Enter these values in Firebase
5. Copy the OAuth redirect URI from Firebase to your Facebook app settings
6. Click "Save"

#### Apple Sign-In
1. Click on Apple in the Sign-in providers list
2. You'll need an Apple Developer account
3. Follow the instructions to set up Sign in with Apple
4. Enter the required information in Firebase
5. Click "Save"

#### Microsoft Sign-In
1. Click on Microsoft in the Sign-in providers list
2. You'll need to register an application in the [Microsoft Azure Portal](https://portal.azure.com/)
3. Follow the instructions to get the Application ID
4. Enter this value in Firebase
5. Copy the OAuth redirect URI from Firebase to your Microsoft app settings
6. Click "Save"

### 4. Configure Your Application

1. Create a `.env` file in the root of your project (same level as `.env.example`)
2. Copy the contents of `.env.example` to your new `.env` file
3. Replace the placeholder values with the actual values from your Firebase project:

```
VITE_FIREBASE_API_KEY=your_actual_api_key
VITE_FIREBASE_AUTH_DOMAIN=your_actual_auth_domain
VITE_FIREBASE_PROJECT_ID=your_actual_project_id
VITE_FIREBASE_STORAGE_BUCKET=your_actual_storage_bucket
VITE_FIREBASE_MESSAGING_SENDER_ID=your_actual_messaging_sender_id
VITE_FIREBASE_APP_ID=your_actual_app_id
VITE_FIREBASE_MEASUREMENT_ID=your_actual_measurement_id
```

### 5. Test Your Configuration

#### Option 1: Test with the Development Server

1. Start your development server:
```bash
npm run dev
```

2. Try to log in using one of the configured authentication methods
3. If the login is successful, your Firebase configuration is working correctly

#### Option 2: Test with the Firebase Test Script

1. Install the dotenv package for loading environment variables:
```bash
npm install dotenv
```

2. Run the Firebase test script:
```bash
node src/firebase-test.js
```

3. If the connection is successful, you'll see a message confirming that your Firebase configuration is working correctly

## Troubleshooting

### Authentication Issues

- Make sure your Firebase project has the correct authentication methods enabled
- Check that the redirect URIs are correctly configured in each provider
- Ensure your `.env` file has the correct values from your Firebase project

### CORS Issues

If you encounter CORS issues:

1. Go to the Firebase Console
2. Navigate to Authentication > Settings > Authorized domains
3. Add your local development domain (e.g., localhost)

### API Key Restrictions

For production, it's recommended to restrict your API key:

1. Go to the Google Cloud Console
2. Navigate to APIs & Services > Credentials
3. Find your API key and add restrictions based on HTTP referrers

## Next Steps

After setting up Firebase Authentication, you might want to explore other Firebase services:

- **Firestore**: For storing user data and museum exhibits
- **Storage**: For storing images and other media
- **Functions**: For server-side logic
- **Hosting**: For deploying your application

Each of these services can be set up through the Firebase Console and integrated into your application.
