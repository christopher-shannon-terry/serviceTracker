#!/bin/bash

# --- Configuration ---
MINIMUM_JDK_VERSION=17
DB_USER="general"
DB_PASS="123456"
ADMIN_EMAIL="general@piusxi.org"
ADMIN_PASS="Password123!"
ADMIN_FNAME="general"
ADMIN_LNAME="general"

# Print messages
print_message() {
    echo "--------------------------------------------------"
    echo "$1"
    echo "--------------------------------------------------"
}

# --- Check and install MariaDB ---
print_message "Checking for MariaDB..."
if ! command -v mysql &> /dev/null; then
    print_message "MariaDB not found. Installing..."
    
    # quiet call for system package updates
    sudo apt update -y -qq
    sudo apt install -y mariadb-server
else
    print_message "MariaDB already installed."
fi

# --- Check and install JDK ---
print_message "Checking for JDK..."
JDK_OK=false
if command -v java &> /dev/null; then
    # Extract java version
    VERSION_STRING=$(java -version 2>&1)
    VERSION_ON_SYSTEM=$(echo "$VERSION_STRING" | grep -oP 'version "\K(?:1\.)?[0-9]+')

    if [[ -n "$VERSION_ON_SYSTEM" ]] && (( VERSION_ON_SYSTEM >= MINIMUM_JDK_VERSION )); then
        echo "JDK version $VERSION_ON_SYSTEM is installed and meets the requirement (>= $MINIMUM_JDK_VERSION)."
        JDK_OK=true
    else
        echo "An existing JDK was found, but its version ($VERSION_ON_SYSTEM) is below the required version: $MINIMUM_JDK_VERSION"
        print_message "Installing minimum required version..."
        sudo apt install -y "openjdk-$MINIMUM_JDK_VERSION-jdk"
    fi
else
    print_message "JDK not found. Installing minimum required version..."
    sudo apt install -y "openjdk-$MINIMUM_JDK_VERSION-jdk"
fi

# --- Check for Maven ---
print_message "Checking for Maven..."
if ! command -v mvn &> /dev/null; then
    echo "Maven not found. Installing..."
    sudo apt install -y maven
else
    echo "Maven is already installed."
fi

# --- Database Setup ---
print_message "Setting up MariaDB databases, tables, and user..."
# Using a heredoc to pass multiple SQL commands to mysql
# The -e flag executes the command and exits.
sudo mysql -u root <<EOF
CREATE DATABASE IF NOT EXISTS student_info;
CREATE DATABASE IF NOT EXISTS service_info;
CREATE DATABASE IF NOT EXISTS admin_database;

USE student_info;

CREATE TABLE IF NOT EXISTS Students (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    grad_year INT,
    grade_year INT
);

USE service_info;

CREATE TABLE IF NOT EXISTS service_submissions (
    submission_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    service_type VARCHAR(255),
    service_event_length DOUBLE,
    service_description TEXT,
    supervisor_email VARCHAR(255),
    status VARCHAR(50) DEFAULT 'pending',
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student_info.Students(student_id)
);

USE admin_database;

CREATE TABLE IF NOT EXISTS Administrators (
    email VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255)
);

-- Insert the default administrator, ignoring if it already exists
INSERT IGNORE INTO Administrators (first_name, last_name, email, password) VALUES ('${ADMIN_FNAME}', '${ADMIN_LNAME}', '${ADMIN_EMAIL}', '${ADMIN_PASS}');


-- Create the user and grant privileges as requested
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASS}';
GRANT ALL PRIVILEGES ON *.* TO '${DB_USER}'@'localhost';
FLUSH PRIVILEGES;

EOF

if [ $? -eq 0 ]; then
    echo "Databases, tables, and user configured successfully."
else
    echo "Error: Database setup failed." >&2
    exit 1
fi

# --- Verification Step ---
print_message "Verifying database user access..."
# We will try to connect with the new user and run a simple command.
# Output is sent to /dev/null, we only care about the success (exit code 0).
if sudo mysql -u"${DB_USER}" -p"${DB_PASS}" -e "SHOW DATABASES;" &> /dev/null; then
    echo "SUCCESS: User '${DB_USER}' can connect to the database."
else
    echo "FAILURE: User '${DB_USER}' could not connect to the database. Please check MariaDB logs for errors." >&2
    exit 1
fi

print_message "Setup script finished!"

