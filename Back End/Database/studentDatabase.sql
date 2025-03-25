CREATE DATABASE studentInformation;

USE studentInformation;

CREATE TABLE studentInfo (
    first_name VARCHAR(50), -- -> Student first name
    last_name VARCHAR(50), -- -> Student last name
    student_id INT PRIMARY KEY, -- -> Unique student ID; going to be used as main identifier
    email VARCHAR(50) NOT NULL UNIQUE, -- -> email address; going to be secondary identifier
    password_hash VARCHAR(255) NOT NULL, -- -> hashed password for security reasons (needs to be reversible)
    remember_me_token VARCHAR(255), DEFAULT NULL, -- -> random token of some mash of string and/or ints for forgotten password
    
)