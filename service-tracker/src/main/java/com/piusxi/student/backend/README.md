# Service Tracker Backend - Student Module

This directory contains the backend implementation for the student-facing portions of the Service Tracker application.

## Overview
The backend provides the "server-side" logic necessary for the Service Tracker application to function. It handles all data processing, business rules, and database interactions for student users.

## Key Components
- Account management (creation, verification, password management)
- Login functionality and session management
- Service hour submission and tracking
- Student data validation
- Database interaction for student records

## Package Structure
- `accounts/` - User account management functionality
  - `createAccount.java` - Account creation logic
  - `strongPasswordCheck.java` - Password validation utilities
  - `verifyEmail.java` - Email verification process
    - more to come as well
- `login/` - Authentication and session management
  - `forgotPassword.java` - Password recovery functionality
  - `rememberMe.java` - Persistent login implementation
    - more to come as well
- `service/` - Service hour submission and management (to be implemented)
- `profile/` - Student profile management (to be implemented)

## Implementation Guidelines
- NO GUI elements here, unless you're referencing a UI component through interfaces
- Implement proper error handling and logging
- ADD PRECISE AND VERBOSE COMMENTS!!!!
- Include input validation for all data received from frontend

## Data Flow
1. Frontend sends request to appropriate backend class
2. Backend validates input data
3. Backend processes business logic
4. Backend interacts with database if needed
5. Backend returns response to frontend

THANKS :)
