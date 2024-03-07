# Spring Boot MVC Project with Spring Security 
## Overview

This project is a Spring Boot MVC application with Spring Security integration. It includes REST endpoints, user authentication, authorization, and additional security features.

## Security Features
1. User authentication with email/password combination
2. Passwords stored securely using salt and hashing
3. Role-based access control with VIEW_INFO and VIEW_ADMIN permissions
4. Custom Login/Logout pages
5. Brute Force Protection: Blocks user email for 5 minutes after 3 unsuccessful login attempts
6. Endpoint to view blocked users
