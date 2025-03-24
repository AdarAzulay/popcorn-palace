
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
# For Java, add to PATH:
sudo ln -sfn /usr/local/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
```
Maven:

```bash
brew install maven
```
PostgreSQL:

```bash
brew install postgresql
```
Git:
```bash
brew install git
```

### On Windows (using Chocolatey)
Open PowerShell as Administrator and run:
```powershell
choco install openjdk maven postgresql git -y
```
Or install manually from their official websites:
- [Java](https://jdk.java.net/)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/windows/)
- [Git](https://git-scm.com/download/win)

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

## ❓ Need Help?

Contact the project owner or open an issue in the repo.
