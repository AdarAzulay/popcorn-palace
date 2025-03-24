# Instructions.md

- **Java SDK:** Version 21 (or later; Java 23 is acceptable)
- **Maven:** Ensure it's installed and added to your PATH
- **Git:** To clone the repository
- **PostgreSQL:** Either via Homebrew (macOS/Linux) or installed locally/using Docker Desktop (Windows)
- **(Optional) Docker Desktop:** If you prefer using Docker for PostgreSQL on Windows

---

## For macOS / Linux Users

### 1. Clone the Repository

Open Terminal and run:
```bash
git clone https://github.com/AdarAzulay/popcorn-palace.git
cd popcorn-palace
```

### 2. Start PostgreSQL via Homebrew

If PostgreSQL is not already running, start it with:
```bash
brew services start postgresql
```
Verify it’s running:
```bash
pg_isready
```
You should see a message like:
```
/var/run/postgresql:5432 - accepting connections
```

### 3. Create Database and User

Run the provided script:
```bash
./db_setup.sh
```
This script does the following:
- Connects to PostgreSQL.
- Creates the database "popcorn-palace".
- Creates the user "popcorn-palace" with password "popcorn-palace".
- Grants all privileges on the "popcorn-palace" database to that user.

Alternatively, you can run these commands manually in the PostgreSQL shell:
```bash
psql postgres
```
Inside the psql shell, run:
```sql
CREATE DATABASE "popcorn-palace";
CREATE USER "popcorn-palace" WITH PASSWORD 'popcorn-palace';
GRANT ALL PRIVILEGES ON DATABASE "popcorn-palace" TO "popcorn-palace";
\q
```

### 4. Build the Project

Build the project using Maven (skipping tests if desired):
```bash
./run_app.sh
```
*This script does:*
```bash
mvn clean install -DskipTests
```

### 5. Run the Application

The same script (`run_app.sh`) will also start your Spring Boot application:
```bash
mvn spring-boot:run
```
Your app should now be running on [http://localhost:8080](http://localhost:8080).

---

## For Windows Users

### 1. Clone the Repository

Open Command Prompt or PowerShell and run:
```powershell
git clone https://github.com/AdarAzulay/popcorn-palace.git
cd popcorn-palace
```

### 2. Start PostgreSQL

**Option A: Using Docker Desktop (Recommended)**
- Ensure Docker Desktop is running.
- Open PowerShell or Command Prompt and run:
  ```powershell
  docker-compose up -d
  ```

**Option B: Using a Locally Installed PostgreSQL**
- Ensure PostgreSQL is installed and running as a Windows service.

### 3. Create Database and User

Open Command Prompt or PowerShell and connect to PostgreSQL:
```powershell
psql -U postgres
```
Once in the PostgreSQL shell, run:
```sql
CREATE DATABASE "popcorn-palace";
CREATE USER "popcorn-palace" WITH PASSWORD 'popcorn-palace';
GRANT ALL PRIVILEGES ON DATABASE "popcorn-palace" TO "popcorn-palace";
\q
```
*(If you have difficulties, consider using pgAdmin for a GUI approach.)*

### 4. Build the Project

In Command Prompt or PowerShell, run:
```powershell
mvn clean install -DskipTests
```

### 5. Run the Application

After building, start the application with:
```powershell
mvn spring-boot:run
```
Your application will be accessible at [http://localhost:8080](http://localhost:8080).

---

## Additional Notes

- **Testing:**  
  Run all tests with:
  ```bash
  mvn test
  ```
  (This applies to both macOS/Linux and Windows.)

- **API Endpoints:**  
  Refer to the README.md for a summary of API endpoints and example JSON bodies.

- **Global Exception Handling & Input Validation:**  
  The project includes a Global Exception Handler to return informative error responses, and DTOs have validation annotations.

---

By following these instructions, anyone cloning your repository should be able to set up, build, and run your project with ease. Let me know if you need any further adjustments!