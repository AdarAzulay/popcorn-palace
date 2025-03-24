
# Instructions.md

## 🧱 Early Setup

Make sure you have the following installed:

- **Java 21+** (Java 23 recommended)
- **Maven**
- **PostgreSQL** (via Homebrew on macOS, or manually on Windows)
- Git

---

## 🚀 Run the Project

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

## 🧪 Run Tests

```bash
mvn test
```

---

## 📦 API Reference

All endpoints are documented in the [`README.md`](./README.md) file.

---

## ❓ Need Help?

Contact the project owner or open an issue in the repo.
