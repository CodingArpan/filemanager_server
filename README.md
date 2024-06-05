
# File Storage Manager

File Storage Manager is a Spring Boot application designed to manage file uploads, storage, and access in a secure manner. This project leverages Spring Security for authentication and Amazon S3 for file storage, providing a robust backend system for handling file operations securely and efficiently.

## Features

- User authentication (Signup and Login)
- JWT (JSON Web Token) based session management
- Secure file upload to Amazon S3
- Generation of presigned URLs for secure file access
- List all files associated with a user
- Cross-Origin Resource Sharing (CORS) support

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java 17 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA, Eclipse)
- MySQL Server
- Amazon AWS account with access to S3

## Setting Up

### Clone the Repository

Start by cloning the repository to your local machine:

```bash
git clone https://github.com/your-username/filestoragemanager.git
cd filestoragemanager
```

### Configure Application Properties

Update `src/main/resources/application.properties` with your MySQL database credentials and AWS S3 configuration:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# AWS S3 Configuration
cloud.aws.credentials.accessKey=YOUR_AWS_ACCESS_KEY
cloud.aws.credentials.secretKey=YOUR_AWS_SECRET_KEY
cloud.aws.region.static=YOUR_AWS_REGION
app.s3.bucketName=YOUR_BUCKET_NAME

# JWT Configuration
jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

### Build and Run

Use Maven to build and run the application:

```bash
mvn clean install
mvn spring-boot:run
```

The application should now be running on `http://localhost:8080`.

## Usage

### API Endpoints

#### User Authentication

- **Signup**

    ```bash
    curl -X POST http://localhost:8080/api/auth/signup \
    -H 'Content-Type: application/json' \
    -d '{
        "email": "user@example.com",
        "password": "password123"
    }'
    ```

- **Login**

    ```bash
    curl -X POST http://localhost:8080/api/auth/login \
    -H 'Content-Type: application/json' \
    -d '{
        "email": "user@example.com",
        "password": "password123"
    }'
    ```

- **Logout**

    ```bash
    curl -X POST http://localhost:8080/api/auth/logout
    ```

#### File Handling

- **Upload File**

    ```bash
    curl -X POST http://localhost:8080/api/v1/upload \
    -H "Authorization: Bearer Your_Jwt_Token" \
    -F "file=@path_to_your_file"
    ```

- **List All Files**

    ```bash
    curl -X POST http://localhost:8080/api/v1/allfiles \
    -H "Authorization: Bearer Your_Jwt_Token"
    ```



## Contributing

Contributions are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License

Distributed under the MIT License. See `LICENSE` for more information.


This `README.md` file provides a comprehensive guide to setting up, running, and using your Spring Boot file storage manager application. Make sure to replace placeholders like `your-username`, `your_database`, `your_username`, `your_password`, `YOUR_AWS_ACCESS_KEY`, `YOUR_AWS_SECRET_KEY`, `YOUR_AWS_REGION`, `YOUR_BUCKET_NAME`, and `YOUR_SECRET_KEY` with actual values.