
# Instructions.md

## 🧱 Early Setup

Make sure you have the following installed:

- **Java 21+** (Java 23 recommended)
- **Maven**
- **PostgreSQL** (via Homebrew on macOS, or manually on Windows)
- Git


If any of these aren't installed yet, use these commands:

### On macOS (using Homebrew)
```bash
brew install openjdk@21 maven postgresql git
```

For Java, add to PATH:
```bash
sudo ln -sfn /usr/local/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
```

---

## Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/AdarAzulay/popcorn-palace.git
cd popcorn-palace
```

---

### 2. Setup Database (macOS)

```bash
chmod +x ./db_setup.sh
./db_setup.sh
```

> 💡 If you're on **Windows**, manually:
> - Start PostgreSQL
> - Create the database and user (`popcorn-palace`)
> - Credentials: username `popcorn-palace`, password `popcorn-palace`

### Alternatively, using Docker:
 If you prefer Docker, run:
```bash
docker-compose up -d
````
Make sure Docker is installed and running, and that port 5432 is exposed.
* Note: The database is initially empty. You will need to add your own sample data if required.


---

### 3. Run the App

```bash
chmod +x ./run_app.sh
./run_app.sh
```

> This will build the project (skipping tests) and run the Spring Boot app on `http://localhost:8080`

---

## Run Tests

```bash
mvn test
```

---

## API Reference

All endpoints are documented in the [`README.md`](./README.md) file.

---

## Troubleshooting
PostgreSQL Connection Issues:\
Verify PostgreSQL is running:
```bash
pg_isready
```
Ensure the database popcorn-palace exists and that the credentials match those in your application configuration.

### File Permissions:
If you get "permission denied" errors when running scripts, ensure the scripts are executable:

```bash
chmod +x ./db_setup.sh
chmod +x ./run_app.sh
```

---
## ❓ Need Help?

Contact the project owner or open an issue in the repo.
