# NexVibe 🌐

![NexVibe Banner](https://via.placeholder.com/1200x300.png?text=NexVibe+-+Connect+with+the+Next+Vibe)

**NexVibe** is a modern social media platform built with Spring Boot, designed to connect users through posts, comments, likes, and follows. Whether you're sharing text, images, or videos, NexVibe provides a seamless and secure experience for building communities and expressing yourself. With a vibrant and intuitive design, NexVibe is your go-to platform for the next big vibe in social networking.

---

## 📖 Project Description

NexVibe is a full-stack social media application developed to empower users to create, share, and engage with content in a dynamic online environment. Built on **Spring Boot**, NexVibe leverages a robust tech stack to ensure security, scalability, and performance:

- **Spring Security**: Implements JWT-based authentication and authorization to secure API endpoints and protect user data.
- **Spring Data JPA**: Simplifies database operations with an ORM layer, enabling efficient data management.
- **Hibernate**: Powers the ORM functionality, mapping Java objects to PostgreSQL tables with minimal boilerplate.
- **Lombok**: Reduces boilerplate code (e.g., getters, setters, constructors) for cleaner and more maintainable code.
- **PostgreSQL**: A reliable relational database for storing user profiles, posts, comments, likes, and follow relationships.
- **Spring Web**: Provides the foundation for building RESTful APIs to handle HTTP requests and responses.

NexVibe supports core social media features:
- User registration and login with JWT authentication.
- Create, view, and delete posts (text, image, video).
- Comment on posts and like/unlike them.
- Follow/unfollow other users and view followers/following lists.
- File uploads for media content, stored locally on the server.

The project is designed with modularity and extensibility in mind, making it easy to add new features or integrate with a frontend application.

---

## 🚀 Features

- **User Authentication**: Secure registration and login with JWT tokens.
- **Post Management**: Create posts with text, images, or videos; view all posts or specific posts; delete your own posts.
- **Engagement**: Comment on posts, like/unlike posts, and follow/unfollow users.
- **Media Support**: Upload images or videos with posts, stored in a local `uploads` directory.
- **Profile Management**: View user profiles, including your own, with details on followers and following.
- **Responsive API**: RESTful endpoints for seamless integration with frontend applications.

---

## 🛠️ Tech Stack

| Technology         | Purpose                          |
|--------------------|----------------------------------|
| **Spring Boot**    | Backend framework for building the application |
| **Spring Security**| Authentication and authorization with JWT |
| **Spring Data JPA**| Simplified database access and ORM |
| **Hibernate**      | Object-relational mapping for database operations |
| **Lombok**         | Reduces boilerplate code (getters, setters, etc.) |
| **PostgreSQL**     | Relational database for data persistence |
| **Spring Web**     | RESTful API development and HTTP handling |

---

## 📦 Prerequisites

Before setting up NexVibe, ensure you have the following installed:

- **Java 17** or later
- **Maven** (for dependency management)
- **PostgreSQL** (database server)
- **Postman** (for API testing, optional but recommended)

---

## ⚙️ Setup Instructions

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/leandre000/nexvibe.git
   cd nexvibe
   ```

2. **Set Up PostgreSQL Database**  
   - Install PostgreSQL if not already installed.
   - Create a database named `socialmedia`:
     ```sql
     CREATE DATABASE socialmedia;
     ```
   - Update the `application.properties` file with your PostgreSQL credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/socialmedia
     spring.datasource.username=postgres
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
     app.media.upload-dir=./uploads
     jwt.secret=your-generated-base64-key-here
     jwt.expiration=31536000000
     ```

3. **Generate a JWT Secret Key**  
   - Use a tool like `openssl` to generate a secure key:
     ```bash
     openssl rand -base64 64
     ```
   - Copy the output and paste it into `application.properties` as `jwt.secret`.

4. **Install Dependencies**  
   Run the following command to download all dependencies:
   ```bash
   mvn clean install
   ```

5. **Run the Application**  
   Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The app will be available at `http://localhost:8080`.

6. **Test the API**  
   - Use Postman to test the API endpoints.
   - Start with `POST /api/auth/register` to create a user:
     ```json
     {
       "username": "testuser",
       "email": "testuser@example.com",
       "password": "password123"
     }
     ```
   - Then log in with `POST /api/auth/login` to get a JWT token:
     ```json
     {
       "email": "testuser@example.com",
       "password": "password123"
     }
     ```
   - Use the token in the `Authorization` header (`Bearer <token>`) for authenticated endpoints.

---

## 🌐 API Endpoints

### Authentication
- **POST /api/auth/register**: Register a new user.
- **POST /api/auth/login**: Log in and receive a JWT token.

### Users
- **GET /api/users/{id}**: Get user details by ID.
- **GET /api/users/me**: Get the current user's profile.

### Posts
- **POST /api/posts**: Create a post (multipart/form-data with `post` JSON and optional `file`).
- **GET /api/posts**: Get all posts.
- **GET /api/posts/{id}**: Get a specific post.
- **DELETE /api/posts/{id}**: Delete a post (owner only).

### Comments
- **POST /api/posts/{postId}/comments**: Add a comment to a post.
- **GET /api/posts/{postId}/comments**: Get all comments for a post.

### Likes
- **POST /api/posts/{postId}/likes**: Like a post.
- **DELETE /api/posts/{postId}/likes**: Unlike a post.

### Following
- **POST /api/users/{userId}/follow**: Follow a user.
- **DELETE /api/users/{userId}/unfollow**: Unfollow a user.
- **GET /api/users/me/following**: Get users the current user is following.
- **GET /api/users/me/followers**: Get the current user's followers.

---

## 📞 Contact

For questions or feedback, reach out to the project maintainer:
- **Email**: iamshemaleandre@gmail.com
- **GitHub**: [leandre000](https://github.com/leandre000)

---

