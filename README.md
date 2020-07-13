## Spring Boot Vue Demo

### Build
Navigate to project root directory and run:
```
mvnw clean
mvnw install
```
Frontend production build output (`frontend/target/dist/`) will be copied to backend resources directory (`backend/src/main/resources/public/`).  
Built WAR file ready for deployment will be inside `backend/target/` directory.
