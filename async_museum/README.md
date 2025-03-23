# Museum of Natural History Website

A modern, responsive website for a museum of natural history, built with Vue.js, TypeScript, and Vuetify. This frontend application allows users to explore exhibits, learn about the museum, and authenticate with social login providers.

## Features

- **Responsive Design**: Works on desktop, tablet, and mobile devices
- **Social Authentication**: Login with Google, Facebook, Apple, and Microsoft accounts
- **Exhibit Browsing**: View and search museum exhibits with filtering capabilities
- **User Profiles**: Authenticated users can view their profile and preferences
- **Contact Form**: Visitors can send messages to the museum
- **About & Information Pages**: Learn about the museum's history, mission, and visiting details

## Tech Stack

- **Vue.js 3**: Frontend framework with Composition API
- **TypeScript**: Type-safe JavaScript
- **Vuetify 3**: Material Design component library
- **Vue Router 4**: Client-side routing
- **Pinia**: State management
- **Firebase Authentication**: Secure user authentication
- **Vite**: Fast development and build tool

## Project Structure

```
src/
├── assets/         # Static assets like images and icons
├── components/     # Reusable Vue components
├── plugins/        # Plugin configurations (Vuetify, Firebase)
├── router/         # Vue Router configuration
├── services/       # API and service layer
├── store/          # Pinia store modules
├── views/          # Page components
├── App.vue         # Root component
├── main.ts         # Application entry point
└── style.css       # Global styles
```

## Setup and Installation

### Prerequisites

- Node.js (v14 or later)
- npm or yarn

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/museum-of-natural-history.git
   cd museum-of-natural-history
   ```

2. Install dependencies:
   ```bash
   npm install
   # or
   yarn install
   ```

3. Create a `.env` file in the root directory with your Firebase configuration:
   ```
   VITE_FIREBASE_API_KEY=your_api_key
   VITE_FIREBASE_AUTH_DOMAIN=your_auth_domain
   VITE_FIREBASE_PROJECT_ID=your_project_id
   VITE_FIREBASE_STORAGE_BUCKET=your_storage_bucket
   VITE_FIREBASE_MESSAGING_SENDER_ID=your_messaging_sender_id
   VITE_FIREBASE_APP_ID=your_app_id
   VITE_FIREBASE_MEASUREMENT_ID=your_measurement_id
   ```

4. Start the development server:
   ```bash
   npm run dev
   # or
   yarn dev
   ```

5. Build for production:
   ```bash
   npm run build
   # or
   yarn build
   ```

## Authentication

The website uses Firebase Authentication for secure user login. Users can authenticate with:

- Google
- Facebook
- Apple
- Microsoft

To set up authentication:

1. Create a Firebase project at [firebase.google.com](https://firebase.google.com)
2. Enable Authentication and configure the social providers
3. Add your application's domain to the authorized domains
4. Copy your Firebase configuration to the `.env` file

## Backend Integration

The website is designed to work with a backend API. Currently, it uses mock data, but it can be easily connected to a real backend:

1. Update the API service methods in `src/services/api.ts` to make actual API calls
2. Ensure your backend endpoints match the expected data structures
3. Implement proper error handling for API requests

## Development Notes

- The project uses Vue 3 `<script setup>` SFCs with TypeScript
- TypeScript type checking is handled by `vue-tsc`
- For IDE support, use the TypeScript Vue Plugin (Volar)

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements

- [Vue.js](https://vuejs.org/)
- [Vuetify](https://vuetifyjs.com/)
- [Firebase](https://firebase.google.com/)
- [Unsplash](https://unsplash.com/) for placeholder images
