# Embeddings Recommendation Engine

## Overview
This is a simplified demo of a lesson recommender system for language learning apps, showcasing AI-driven personalization. Users can explore lessons, book up to three, and receive recommendations based on semantic title similarities. The demo uses precalculated embeddings, cosine similarities, and weighted attributes (60% text, 30% language, 10% CEFR). For a real-world, robust solution with advanced methods and models, hiring a professional is recommended!

## Features
- **Explore Lessons**: View a list of lessons with title, language, and CEFR level (A1 to C2).
- **Book Lessons**: Add up to three lessons to your bookings (client-side, reload to reset).
- **AI Recommendations**: Get personalized lesson recommendations based on your three most recent bookings.
- **Svelte UI**: A clean, responsive interface with a collapsible guide for users.
- **Deployment**: Deployed to a VPS using GitHub Actions.

## Tech Stack
- **Frontend**: Svelte (UI), Native JavaScript (logic), Native CSS (styling)
- **Backend**: Spring Boot, Java, SQL
- **AI**: TensorFlow (embeddings, cosine similarities)
- **Build**: Maven
- **Deployment**: GitHub Actions to VPS

## Setup
### Prerequisites
- Java 21+
- Maven
- MariaDB 11.7
- Node.js (for Svelte build)

### Steps
1. **Clone the Repository**:
```bash
git clone https://github.com/ferderer/embeddings-recommendation-engine
cd embeddings-recommendation-engine
```

2. **Backend Setup**:
- Configure MariaDB and update application.properties with your database credentials.
- Build and run the Spring Boot app:
```bash
mvn spring-boot:run -P dev
```

3. **Frontend Setup**:
- Navigate to the Svelte app directory (if separate, e.g., frontend/):
```bash
cd ui-svelte
npm install
npm run build
```
- Copy the built files (e.g., dist/) to src/main/resources/static/ in the Spring Boot project.

4. **Run the App**:
- Access the app at http://localhost:8080/.

5. **Deployment**:
- The project uses GitHub Actions to deploy to a VPS. Update the workflow in .github/workflows/ with your VPS credentials.

## Usage Guide
- Explore Lessons: View all lessons in the table (title, language, CEFR).  
- Book a Lesson: Click "Book" to add a lesson (up to three). A checkmark and "Booked" will appear.  
- View Booked Lessons: See your bookings with timestamps (latest on top). Reload the page to start over.  
- Get Recommendations: After booking, view AI-driven recommendations based on your recent bookings.

**Note**: This demo uses a simple recommendation engine with precalculated embeddings and cosine similarities. Real-world engines are more complex, supporting various methods (collaborative filtering, content-based) and models (deep learning, matrix factorization). For a professional solution, hire me!


## Hiring a Professional
This demo is a simplified prototype. To build a robust, scalable recommender system tailored to your needs, with advanced methods and models, contact me! I’m a freelance senior developer with 25+ years of experience in AI solutions and fullstack development. DM me on LinkedIn [your LinkedIn profile link] to discuss your project!

## License
© 2025 Vadim Ferderer. All rights reserved for commercial use.
