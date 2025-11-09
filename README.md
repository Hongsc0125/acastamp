# AcaStamp

A modern web application built with Spring Boot backend and Vue.js frontend.

## Tech Stack

### Backend
- Java 21
- Spring Boot 3.4.1
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok

### Frontend
- Vue.js 3
- Vite
- Tailwind CSS
- DaisyUI

## Project Structure

```
acastamp/
├── backend/          # Spring Boot application
└── frontend/         # Vue.js application
```

## Getting Started

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

The backend will be available at `http://localhost:8080`

### Frontend

```bash
cd frontend
npm install
npm run dev
```

The frontend will be available at `http://localhost:5173`

## Development

- Backend runs on port 8080
- Frontend runs on port 5173
- API endpoints will be prefixed with `/api`

## License

MIT
