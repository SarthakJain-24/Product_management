# Product Management System

## Technologies
- Java 8
- Spring Boot 2.7
- MongoDB
- Spring Security with JWT
- Swagger API Docs

## How to Run
1. Start MongoDB locally (`localhost:27017`)
2. Run the Spring Boot Application
3. Access Swagger UI at: `http://localhost:8080/swagger-ui.html`
4. Authenticate using:
   - Username: `admin`
   - Password: `admin@12345`
5. Use the JWT Token in `Authorization: Bearer <token>` header for secured APIs.

## API Endpoints
- `POST /api/authenticate` -> Get JWT Token
- `POST /api/products` -> Create product
- `GET /api/products/{id}` -> Get product
- `GET /api/products` -> List all products
- `PUT /api/products/{id}` -> Update product
- `DELETE /api/products/{id}` -> Delete product

## Testing
- Run unit tests with `mvn test`
