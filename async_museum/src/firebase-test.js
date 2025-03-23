// Firebase Connection Test Script
// Run this script with: node src/firebase-test.js

// Use CommonJS require instead of ES modules for Node.js compatibility
const { initializeApp } = require('firebase/app');
const { getAuth, signInAnonymously } = require('firebase/auth');
const dotenv = require('dotenv');

// Load environment variables
dotenv.config();

// Firebase configuration
const firebaseConfig = {
  apiKey: process.env.VITE_FIREBASE_API_KEY,
  authDomain: process.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: process.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.VITE_FIREBASE_APP_ID,
  measurementId: process.env.VITE_FIREBASE_MEASUREMENT_ID
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

// Test Firebase connection
async function testFirebaseConnection() {
  console.log('Testing Firebase connection...');

  try {
    // Try to sign in anonymously
    const userCredential = await signInAnonymously(auth);
    console.log('Firebase connection successful!');
    console.log('Anonymous user ID:', userCredential.user.uid);
    console.log('Your Firebase configuration is working correctly.');
  } catch (error) {
    console.error('Firebase connection failed!');
    console.error('Error code:', error.code);
    console.error('Error message:', error.message);
    console.error('Please check your Firebase configuration in the .env file.');
  }
}

// Run the test
testFirebaseConnection();
