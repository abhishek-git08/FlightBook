# Fly-Book ✈️ Flight Booking System — Spring Boot + PostgreSQL

A full-stack backend project built with **Spring Boot** and **PostgreSQL**, designed to manage flight bookings efficiently with proper seat allocation, relationship mapping, and automatic payment handling.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5+-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

---

## 🚀 Features

### ✅ Core Functionality
- Create, Read, Update, Delete operations for all entities  
- **31 fully working APIs**
- Automatic payment calculation → `flight price × number of passengers`
- **Seat management system:**
  - Prevent double booking of the same seat  
  - Restrict selection beyond available capacity  
  - Check available seats per flight  
- Pagination & Sorting for Flights, Bookings, and Passengers  

### ✅ Relationship Mapping (JPA/Hibernate)
- Booking → Flight (**Many-to-One**)  
- Booking → Passenger (**One-to-Many**)  
- Booking → Payment (**One-to-One**)  
- Passenger → Booking (**Many-to-One**)  

### ✅ Database
- **PostgreSQL** managed via **pgAdmin 4**  
- Unique constraint to prevent duplicate seat bookings:
  ```sql
  ALTER TABLE passenger 
  ADD CONSTRAINT unique_seat_per_booking UNIQUE (booking_id, seat_number);
