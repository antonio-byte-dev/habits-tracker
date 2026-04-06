# Task List

A comprehensive task list application that allows users to create and manage their daily tasks. Built with Vue.js frontend, SpringBoot backend and Docker containerization.

## Usage

This page is no longer online. To try it please run it locally.

## Features

- 🔐 **User Authentication** - Secure user registration and login with Firebase Auth
- 📝 **Task Management** - Create, update, and delete custom habits
- 🗄️ **Data Persistence** - MongoDB database for reliable data storage
- 🐳 **Containerized Deployment** - Docker containers for easy setup and deployment
- 🔒 **User Isolation** - Each user's habits are securely stored and managed separately

## Tech Stack

- **Frontend**: Vue.js (containerized)
- **Backend**: SpringBoot(containerized)
- **Database**: MongoDB (Docker container)
- **Authentication**: Firebase Auth
- **Containerization**: Docker & Docker Compose

## Installation

### Prerequisites

- Docker and Docker Compose installed on your system
- Firebase project setup for authentication

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/antonio-byte-dev/habits-tracker.git
   cd habits-tracker
   ```

2. **Environment Configuration**
   ```bash
   cp .env.example .env
   ```
   Edit the `.env` file with your configuration:
   - Firebase configuration
   - MongoDB connection settings
   - API endpoints

3. **Start the application**
   ```bash
   docker-compose up -d
   ```

4. **Access the application**
   - Frontend: `http://localhost:5173` (or configured port)
   - Backend API: `http://localhost:8080` (or configured port)

## Usage

1. **Register/Login**: Create an account or sign in using Firebase authentication
2. **Create Tasks**: Add new habits you want to track
3. **Manage Tasks**: Edit or delete existing habits as needed

### API Endpoints

- `GET /api/` - Retrieve user's habits
- `GET /api/{id}` - Retrieve a specific habit
- `POST /api/` - Create a new habit for the current user
- `PUT /api/:id` - Update an existing habit
- `DELETE /api/:id` - Delete a habit


### Project Structure

```
habits-tracker/
├── frontend/          # Vue.js frontend application
├── backend/           # Springboot backend API
├── docker-compose.yml # Docker services configuration
└── .env.example       # Environment variables template
```




