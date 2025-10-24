# 🎯 Civil Status Management - Feature Demonstration

## 🏛️ **Application Overview**

This Spring Boot application manages civil status records for citizens and their marital relationships in a province. It demonstrates advanced JPA concepts, complex database queries, and modern Spring architecture.

## 📋 **Implemented Features**

### **1. Entity Management**
- **Personne** (Base Entity)
  - Common attributes: nom, prenom, telephone, adresse, dateNaissance
  - Inheritance strategy for Homme and Femme

- **Homme** (Male Citizen)
  - Extends Personne
  - OneToMany relationship with Mariage

- **Femme** (Female Citizen)  
  - Extends Personne
  - OneToMany relationship with Mariage

- **Mariage** (Marriage Record)
  - ManyToOne relationships with Homme and Femme
  - Attributes: dateDebut, dateFin, nbrEnfant

### **2. Query Implementations**

#### **JPQL Queries:**
```java
@Query("SELECT DISTINCT f FROM Femme f JOIN f.mariages m GROUP BY f HAVING COUNT(m) >= 2")
List<Femme> findFemmesMarieesAuMoinsDeuxFois();
```

#### **Native SQL Queries:**
```java
@Query(value = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM mariage m WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :startDate AND :endDate", nativeQuery = true)
Long countEnfantsByFemmeAndDates(@Param("femmeId") Long femmeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
```

#### **Named Queries:**
```java
@NamedQuery(name = "Femme.marieesAuMoinsDeuxFois", query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2")
```

#### **Criteria API:**
```java
public List<Object[]> getHommesMarieesQuatreFemmesEntreDates(Date dateDebut, Date dateFin) {
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
    // Complex criteria implementation...
}
```

### **3. REST API Endpoints**

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/etat-civil/hommes` | GET | List all men |
| `/api/etat-civil/femmes` | GET | List all women |
| `/api/etat-civil/femmes/plus-agee` | GET | Get oldest woman |
| `/api/etat-civil/femmes/mariees-deux-fois` | GET | Women married 2+ times |
| `/api/etat-civil/hommes/{id}/mariages` | GET | Get man's marriages |

### **4. Business Logic Methods**

#### **HommeService:**
- `getEpousesEntreDates()` - Find wives between dates
- `afficherMariagesHomme()` - Display detailed marriage info

#### **FemmeService:**
- `getNombreEnfantsEntreDates()` - Count children between dates
- `getFemmesMarieesAuMoinsDeuxFois()` - Find women married 2+ times
- `getFemmeLaPlusAgee()` - Find oldest woman

#### **MariageService:**
- `getHommesMarieesQuatreFemmesEntreDates()` - Find men married to 4+ women

### **5. Test Program Features**

The `TestEtatCivil` class demonstrates all required functionality:

1. **Data Creation**: Creates 10 women and 5 men with sample data
2. **List Display**: Shows all women with names and birth dates
3. **Age Analysis**: Finds and displays the oldest woman
4. **Relationship Queries**: Shows wives of a specific man
5. **Child Counting**: Counts children of a woman between dates
6. **Marriage Statistics**: Finds women married multiple times
7. **Complex Queries**: Identifies men married to 4+ women
8. **Detailed Reports**: Displays comprehensive marriage information

### **6. Sample Output Format**

```
🚀 DÉMARRAGE DE L'APPLICATION ÉTAT CIVIL

1. 📝 CRÉATION DES PERSONNES
✅ Données créées : 5 hommes, 10 femmes, 8 mariages

2. 👩 LISTE DES FEMMES
Nombre de femmes : 10
1. SALIMA RAMI - 12/06/1972
2. AMAL ALI - 18/09/1976
3. WAFA ALAOUI - 22/11/1981
...

3. 👵 FEMME LA PLUS ÂGÉE
SALIMA RAMI - Née le 12/06/1972

4. 💑 ÉPOUSES D'UN HOMME ENTRE DEUX DATES
Épouses de SAFI SAID :
- SALIMA RAMI (Mariée le 03/09/1990, Enfants: 4)
- AMAL ALI (Mariée le 03/09/1995, Enfants: 2)

5. 👶 NOMBRE D'ENFANTS D'UNE FEMME
SALIMA RAMI a eu 4 enfant(s)

6. 💍 FEMMES MARIÉES AU MOINS DEUX FOIS
Femmes mariées au moins 2 fois :
- SOUAD CHAFIK

7. 👨‍👩‍👧‍👦 HOMMES MARIÉS À QUATRE FEMMES
Hommes mariés à au moins 4 femmes :
- SAFI SAID (4 femmes)

8. 📊 DÉTAILS DES MARIAGES D'UN HOMME
Nom : SAFI SAID
Mariages En Cours :
1. Femme : SALIMA RAMI   Date Début : 03/09/1990    Nbr Enfants : 4
2. Femme : AMAL ALI      Date Début : 03/09/1995    Nbr Enfants : 2
3. Femme : WAFA ALAOUI   Date Début : 04/11/2000    Nbr Enfants : 3

Mariages échoués :
1. Femme : KARIMA ALAMI  Date Début : 03/09/1989    Date Fin : 03/09/1990    Nbr Enfants : 0

✅ TOUS LES TESTS ONT RÉUSSI !
```

## 🛠️ **Technical Architecture**

### **Spring Boot Configuration:**
- Auto-configuration for JPA/Hibernate
- Embedded Tomcat server
- RESTful web services
- Database connection pooling

### **Database Schema:**
```sql
CREATE TABLE Personne (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    telephone VARCHAR(255),
    adresse VARCHAR(255),
    dateNaissance DATE
);

CREATE TABLE Homme (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Personne(id)
);

CREATE TABLE Femme (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Personne(id)
);

CREATE TABLE Mariage (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dateDebut DATE,
    dateFin DATE,
    nbrEnfant INT DEFAULT 0,
    homme_id BIGINT,
    femme_id BIGINT,
    FOREIGN KEY (homme_id) REFERENCES Homme(id),
    FOREIGN KEY (femme_id) REFERENCES Femme(id)
);
```

## 🎯 **Exercise Requirements - Complete**

✅ **All 8 required features implemented and tested**
✅ **JPA entities with proper inheritance**
✅ **Named queries and native queries**
✅ **Criteria API implementation**
✅ **Service layer with business logic**
✅ **REST API endpoints**
✅ **Comprehensive test program**
✅ **Database integration**
✅ **Spring Boot configuration**

**Status: 🏆 COMPLETE - Production Ready Application**
