# 🏛️ Civil Status Management Application (État Civil)

## 📋 Exercise 3 - Complete Implementation

This Spring Boot application implements a comprehensive civil status management system for managing citizens and their marital relationships in a province.

## ✅ **All Requirements Successfully Implemented**

### 1. **Persistence Layer (Couche Persistance)**

#### **JPA Entities in `ma.projet.beans`:**
- **`Personne`** - Base entity with common attributes
- **`Homme`** - Extends Personne for male citizens
- **`Femme`** - Extends Personne for female citizens  
- **`Mariage`** - Marriage entity with proper relationships

#### **Key Features:**
- ✅ JPA annotations with proper inheritance
- ✅ `@OneToMany` and `@ManyToOne` relationships
- ✅ Named queries and native queries
- ✅ Database schema auto-generation
- ✅ MySQL integration with Hibernate

### 2. **Service Layer (Couche Service)**

#### **Repository Interfaces:**
- **`IDao<T>`** - Generic DAO interface
- **`GenericDao<T>`** - Generic implementation with CRUD operations
- **`HommeRepository`** - Spring Data JPA repository
- **`FemmeRepository`** - Spring Data JPA repository
- **`MariageRepository`** - Spring Data JPA repository

#### **Service Classes with @Service annotations:**
- **`HommeService`** - Business logic for male citizens
- **`FemmeService`** - Business logic for female citizens
- **`MariageService`** - Business logic for marriages

### 3. **Required Methods Implementation**

#### **In HommeService:**
- ✅ `getEpousesEntreDates(Long hommeId, Date dateDebut, Date dateFin)` - Display wives of a man between two dates
- ✅ `afficherMariagesHomme(Long hommeId)` - Display all marriages of a man with details

#### **In FemmeService:**
- ✅ `getNombreEnfantsEntreDates(Long femmeId, Date dateDebut, Date dateFin)` - **Native Query** for children count between dates
- ✅ `getFemmesMarieesAuMoinsDeuxFois()` - **Named Query** for women married at least twice
- ✅ `getFemmeLaPlusAgee()` - Find the oldest woman

#### **In MariageService:**
- ✅ `getHommesMarieesQuatreFemmesEntreDates(Date dateDebut, Date dateFin)` - **Criteria API** for men married to 4+ women

### 4. **REST API Endpoints**

#### **EtatCivilController with @RestController:**
- ✅ `GET /api/etat-civil/hommes` - List all men
- ✅ `GET /api/etat-civil/femmes` - List all women
- ✅ `GET /api/etat-civil/femmes/plus-agee` - Get oldest woman
- ✅ `GET /api/etat-civil/femmes/mariees-deux-fois` - Women married 2+ times
- ✅ `GET /api/etat-civil/hommes/{id}/mariages` - Get man's marriages

### 5. **Test Program Features**

#### **TestEtatCivil class demonstrates all 8 required features:**

1. ✅ **Create 10 women and 5 men** with sample data
2. ✅ **Display list of women** with names and birth dates
3. ✅ **Find oldest woman** using date comparison
4. ✅ **Display wives of a man** between specific dates
5. ✅ **Count children of a woman** between two dates
6. ✅ **Find women married 2+ times** using named queries
7. ✅ **Find men married to 4+ women** using Criteria API
8. ✅ **Display detailed marriage information** with format:

```
Nom : SAFI SAID
Mariages En Cours :
1. Femme : SALIMA RAMI   Date Début : 03/09/1990    Nbr Enfants : 4
2. Femme : AMAL ALI      Date Début : 03/09/1995    Nbr Enfants : 2
3. Femme : WAFA ALAOUI   Date Début : 04/11/2000    Nbr Enfants : 3

Mariages échoués :
1. Femme : KARIMA ALAMI  Date Début : 03/09/1989    Date Fin : 03/09/1990    Nbr Enfants : 0
```

## 🔧 **Technical Implementation Details**

### **Database Configuration:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/etat_civil_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### **Entity Relationships:**
- **Personne** (Base) → **Homme** (Inheritance)
- **Personne** (Base) → **Femme** (Inheritance)
- **Homme** ↔ **Mariage** (OneToMany)
- **Femme** ↔ **Mariage** (OneToMany)

### **Query Types Implemented:**
- **JPQL Queries** - Object-oriented queries
- **Native SQL Queries** - Direct SQL execution
- **Named Queries** - Predefined query definitions
- **Criteria API** - Programmatic query building

## 🚀 **How to Run the Application**

### **Prerequisites:**
1. **MySQL Database** running on localhost:3306
2. **Java 8+** installed
3. **Maven** installed

### **Commands:**
```bash
# Start the Spring Boot application
mvn spring-boot:run

# Run with test data population
mvn exec:java

# Test API endpoints
curl http://localhost:8080/api/etat-civil/hommes
curl http://localhost:8080/api/etat-civil/femmes
```

## 📊 **Project Structure**
```
src/main/java/ma/projet/
├── beans/                    # JPA Entities
│   ├── Personne.java        # Base entity
│   ├── Homme.java           # Male citizen
│   ├── Femme.java           # Female citizen
│   └── Mariage.java         # Marriage entity
├── controller/              # REST Controllers
│   └── EtatCivilController.java
├── dao/                     # Repository Interfaces
│   ├── IDao.java
│   ├── GenericDao.java
│   ├── HommeRepository.java
│   ├── FemmeRepository.java
│   └── MariageRepository.java
├── service/                 # Business Logic
│   ├── HommeService.java
│   ├── FemmeService.java
│   └── MariageService.java
├── util/                    # Utilities
│   └── HibernateUtil.java
├── test/                    # Test Classes
│   └── TestEtatCivil.java
└── EtatCivilApplication.java # Spring Boot Main
```

## 🎯 **Exercise Requirements - 100% Complete**

- ✅ **JPA Entities** with proper inheritance and relationships
- ✅ **Named Queries** and **Native Queries** implementation
- ✅ **Criteria API** for complex queries
- ✅ **Service Layer** with all required business methods
- ✅ **REST API** with comprehensive endpoints
- ✅ **Test Program** demonstrating all 8 required features
- ✅ **Database Integration** with MySQL and Hibernate
- ✅ **Spring Boot** configuration and auto-configuration
- ✅ **Maven** project structure with dependencies

## 🏆 **Conclusion**

The **Civil Status Management Application** is a complete, production-ready Spring Boot application that successfully implements all requirements from Exercise 3. The application demonstrates advanced JPA concepts, complex query implementations, and modern Spring Boot architecture patterns.

**Status: ✅ COMPLETE - All requirements implemented and tested**
