#!/bin/bash
# db_setup.sh - Start PostgreSQL (using Homebrew) and create the database and user for Popcorn Palace

echo "Starting PostgreSQL server via Homebrew..."
brew services start postgresql

echo "Verifying PostgreSQL status..."
pg_isready

echo "Creating database and user..."
psql postgres <<EOF
CREATE DATABASE "popcorn-palace";
CREATE USER "popcorn-palace" WITH PASSWORD 'popcorn-palace';
GRANT ALL PRIVILEGES ON DATABASE "popcorn-palace" TO "popcorn-palace";
\q
EOF

echo "Database and user creation completed."
