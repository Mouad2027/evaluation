# Application de Gestion de Stock

Cette application permet de gérer le stock d'un magasin de produits informatiques en utilisant Hibernate et Spring. L'application a été testée avec succès et toutes les fonctionnalités sont opérationnelles.

## ✅ Statut du Projet
- **Compilation** : ✅ Réussie
- **Exécution** : ✅ Testée avec succès
- **Base de données** : ✅ Tables créées automatiquement
- **Fonctionnalités** : ✅ Toutes implémentées et testées

## Structure du Projet

### Couche Persistance
- **Entités** (`ma.projet.classes`):
  - `Categorie` : Gestion des catégories de produits
  - `Produit` : Gestion des produits avec référence, prix, stock
  - `Commande` : Gestion des commandes avec date
  - `LigneCommande` : Détails des produits dans une commande

### Couche Service
- **Interface générique** (`ma.projet.dao.IDao`) : Interface CRUD générique
- **Services** (`ma.projet.service`):
  - `CategorieService` : Gestion des catégories
  - `ProduitService` : Gestion des produits avec méthodes spécialisées
  - `CommandeService` : Gestion des commandes
  - `LigneCommandeService` : Gestion des lignes de commande
  - `SpringProduitService` : Version Spring du service produit

### Configuration
- **HibernateUtil** (`ma.projet.util`) : Configuration Hibernate standalone avec C3P0
- **AppConfig** (`ma.projet.config`) : Configuration Spring
- **application.properties** : Configuration de la base de données

## Fonctionnalités Implémentées

### Méthodes Spécialisées du ProduitService

1. **Produits par catégorie** : `findByCategorie(Long categorieId)`
2. **Produits commandés entre dates** : `findProduitsCommandesEntreDates(LocalDate debut, LocalDate fin)`
3. **Produits d'une commande** : `findProduitsByCommande(Long commandeId)`
4. **Détails d'une commande** : `afficherDetailsCommande(Long commandeId)`
5. **Produits avec prix supérieur** : `findByPrixSuperieurA(Double prixMinimum)` (utilise requête nommée)

## Configuration de la Base de Données

### Prérequis
- MySQL 8.0+
- Java 8+ (testé avec Java 8 et Java 23)
- Maven 3.6+

### Configuration
1. Créer une base de données MySQL nommée `stock_management`
2. Modifier les paramètres dans `src/main/resources/application.properties` si nécessaire :
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/stock_management?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=votre_mot_de_passe
   ```

## Exécution

### Compilation
```bash
mvn clean compile
```

### Tests
```bash
# Test avec Hibernate seul (RECOMMANDÉ - Testé avec succès)
mvn exec:java

# Test avec Spring + Hibernate
mvn exec:java -Dexec.mainClass="ma.projet.test.SpringStockManagementTest"
```

### Résultats des Tests
L'application a été testée avec succès et a démontré :
- ✅ Création automatique des tables de base de données
- ✅ Insertion de 3 catégories (Ordinateurs, Périphériques, Réseau)
- ✅ Insertion de 5 produits avec différentes références
- ✅ Création de 2 commandes avec lignes de commande
- ✅ Affichage des produits par catégorie
- ✅ Recherche de produits commandés entre dates
- ✅ Affichage des détails de commande au format demandé
- ✅ Utilisation des requêtes nommées pour les produits > 100 DH

## Exemple d'Affichage Réel

L'application produit exactement le format demandé :

```
Commande : 1     Date : 2023-03-14
Liste des produits :
Référence   Prix    Quantité
PC001      4500     DH  2
SOU001     150      DH  3
CLAV001    200      DH  1
```

### Autres Exemples de Sortie

**Produits par catégorie :**
```
Catégorie: Ordinateurs
  - PC001 - Ordinateur portable Dell - 4500.0 DH
  - PC002 - Ordinateur de bureau HP - 3200.0 DH

Catégorie: Périphériques
  - SOU001 - Souris sans fil Logitech - 150.0 DH
  - CLAV001 - Clavier mécanique - 200.0 DH
```

**Produits avec prix > 100 DH :**
```
Produits avec prix > 100 DH:
  - PC001 - Ordinateur portable Dell - 4500.0 DH
  - PC002 - Ordinateur de bureau HP - 3200.0 DH
  - SOU001 - Souris sans fil Logitech - 150.0 DH
  - CLAV001 - Clavier mécanique - 200.0 DH
  - ROU001 - Routeur WiFi 6 - 300.0 DH
```

## Architecture

### Hibernate Standalone
- Utilise `HibernateUtil` pour la gestion des sessions
- Gestion manuelle des transactions
- Configuration via `application.properties`

### Spring + Hibernate
- Utilise `@Transactional` pour la gestion des transactions
- Injection de dépendances avec `@Autowired`
- Configuration via `AppConfig` et `application.properties`

## Technologies Utilisées

- **Java 8** (compatible avec Java 8+)
- **Hibernate 5.6.9** avec C3P0 connection pooling
- **Spring Framework 5.3.21**
- **MySQL 8.0** avec MySQL Connector 8.0.29
- **Maven 3.9.11** avec Maven Compiler Plugin 3.11.0
- **JUnit 4.13.2** (pour les tests)
- **SLF4J 1.7.36** (pour les logs)

## Structure des Tables

### Table `categorie`
- `id` (PK, AUTO_INCREMENT)
- `nom` (VARCHAR(100), NOT NULL)
- `description` (VARCHAR(255))

### Table `produit`
- `id` (PK, AUTO_INCREMENT)
- `reference` (VARCHAR(50), UNIQUE, NOT NULL)
- `designation` (VARCHAR(200), NOT NULL)
- `prix` (DOUBLE, NOT NULL)
- `stock` (INT, NOT NULL)
- `categorie_id` (FK vers categorie.id)

### Table `commande`
- `id` (PK, AUTO_INCREMENT)
- `date_commande` (DATE, NOT NULL)
- `total` (DOUBLE, NOT NULL)

### Table `ligne_commande`
- `id` (PK, AUTO_INCREMENT)
- `quantite` (INT, NOT NULL)
- `prix_unitaire` (DOUBLE, NOT NULL)
- `sous_total` (DOUBLE, NOT NULL)
- `commande_id` (FK vers commande.id)
- `produit_id` (FK vers produit.id)

## Modifications Apportées

### Résolution des Problèmes de Compilation
1. **Version Java** : Changé de Java 11 vers Java 8 pour compatibilité avec Maven
2. **Maven Compiler Plugin** : Ajouté la version 3.11.0 avec configuration explicite
3. **Connection Pool** : Simplifié la configuration C3P0 dans HibernateUtil
4. **Dépendances** : Ajouté hibernate-c3p0 pour le support du pool de connexions

### Configuration Optimisée
- **Auto-création de base de données** : `createDatabaseIfNotExist=true`
- **Pool de connexions C3P0** : Configuration automatique avec 3-15 connexions
- **Logs Hibernate** : Affichage des requêtes SQL pour le débogage
- **Gestion des transactions** : Implémentation manuelle et Spring

### Tests Validés
- ✅ Compilation réussie avec `mvn clean compile`
- ✅ Exécution réussie avec `mvn exec:java`
- ✅ Création automatique des tables
- ✅ Insertion et récupération de données
- ✅ Toutes les requêtes spécialisées fonctionnelles

## Dépannage

### Problèmes Courants
1. **Erreur de compilation Java** : Vérifier que Maven utilise Java 8+
2. **Erreur de connexion MySQL** : Vérifier que MySQL est démarré et accessible
3. **Erreur C3P0** : Les dépendances hibernate-c3p0 sont incluses

### Commandes de Vérification
```bash
# Vérifier la version Java de Maven
mvn -version

# Vérifier la compilation
mvn clean compile

# Exécuter les tests
mvn exec:java
```
