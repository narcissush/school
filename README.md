)

---

# 🎓 School Management System (Spring Boot)

یک سیستم مدیریت آموزشی مبتنی بر **Spring Boot** و **Spring Security** برای مدیریت دانشجویان، اساتید، دوره‌ها، گواهینامه ها، دروس و کاربران با احراز هویت و کنترل دسترسی.

---

## ✨ Features

* احراز هویت با Spring Security 6
* مدیریت نقش‌ها و دسترسی‌ها (RBAC)
* مدیریت دانشجو و استاد (ارث‌بری از Person)
* مدیریت دوره، درس، مهارت و سوابق کاری
* داشبورد مدیریتی با آمار سیستم
* رمزگذاری رمز عبور با BCrypt
* لاگ‌گیری با AOP
* مدیریت خطا با GlobalExceptionHandler
* UI با Thymeleaf + Bootstrap 5

---

## 🛠 Tech Stack

* Java 17
* Spring Boot 3
* Spring Security 6
* Spring Data JPA (Hibernate)
* MySQL
* Thymeleaf
* Bootstrap 5
* Maven

---

## 📁 Project Structure

```
src/main/java/com/mftplus/school
├── controller
├── config (Security)
├── core
│   ├── model (Person, Student, Teacher, User, Role, Permission)
│   ├── repository
│   └── service
├── course
├── lesson
├── license
├── skill
├── experience
└── aspect
```

---

## 🚀 Run the Project

### 1. Create Database

```D2
CREATE DATABASE school;
```

### 2. Configure `application.yaml`


### 3. Run

```bash
mvn clean install
mvn spring-boot:run
```

App URL:

```
http://localhost:80
```

---

## 🔒 Security

* Form Login (`/login`)
* BCrypt Password Encoding
* Role-Based Access Control (ADMIN, MANAGER, USER , TEACHER , STUDENT)
* CSRF Protection
* Secure Session Management

---


## 🧩 Architecture

Layered Architecture:

* Entity (Model)
* Repository (JPA)
* Service (Business Logic)
* Controller (MVC)
* Security Layer (Spring Security)

---

## 👤 Author

Developed for educational purposes using Spring Boot & Spring Security.

کوتاه، حرفه‌ای، قابل ارائه، و بدون شلوغ‌کاری اضافی. دقیقاً همون چیزی که استاد و ریویوِر حوصله خوندنشو دارن.
