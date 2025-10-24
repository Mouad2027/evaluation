package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.time.LocalDate;
import java.util.List;

public class StockManagementTest {
    
    private CategorieService categorieService;
    private ProduitService produitService;
    private CommandeService commandeService;
    private LigneCommandeService ligneCommandeService;
    
    public StockManagementTest() {
        this.categorieService = new CategorieService();
        this.produitService = new ProduitService();
        this.commandeService = new CommandeService();
        this.ligneCommandeService = new LigneCommandeService();
    }
    
    public void runAllTests() {
        System.out.println("=== DÉBUT DES TESTS DE GESTION DE STOCK ===\n");
        
        // Test 1: Création des catégories
        testCreationCategories();
        
        // Test 2: Création des produits
        testCreationProduits();
        
        // Test 3: Création des commandes
        testCreationCommandes();
        
        // Test 4: Affichage des produits par catégorie
        testProduitsParCategorie();
        
        // Test 5: Affichage des produits commandés entre deux dates
        testProduitsCommandesEntreDates();
        
        // Test 6: Affichage des détails d'une commande
        testDetailsCommande();
        
        // Test 7: Affichage des produits avec prix supérieur à 100 DH
        testProduitsPrixSuperieurA();
        
        System.out.println("\n=== FIN DES TESTS ===");
    }
    
    private void testCreationCategories() {
        System.out.println("1. Test de création des catégories:");
        
        Categorie ordinateurs = new Categorie("Ordinateurs", "Ordinateurs de bureau et portables");
        Categorie peripheriques = new Categorie("Périphériques", "Souris, claviers, écrans");
        Categorie reseau = new Categorie("Réseau", "Routeurs, switches, câbles");
        
        categorieService.create(ordinateurs);
        categorieService.create(peripheriques);
        categorieService.create(reseau);
        
        System.out.println("✓ Catégories créées avec succès");
        System.out.println();
    }
    
    private void testCreationProduits() {
        System.out.println("2. Test de création des produits:");
        
        // Récupérer les catégories
        List<Categorie> categories = categorieService.findAll();
        Categorie ordinateurs = categories.get(0);
        Categorie peripheriques = categories.get(1);
        Categorie reseau = categories.get(2);
        
        // Créer des produits
        Produit pc1 = new Produit("PC001", "Ordinateur portable Dell", 4500.0, 10, ordinateurs);
        Produit pc2 = new Produit("PC002", "Ordinateur de bureau HP", 3200.0, 5, ordinateurs);
        Produit souris = new Produit("SOU001", "Souris sans fil Logitech", 150.0, 25, peripheriques);
        Produit clavier = new Produit("CLAV001", "Clavier mécanique", 200.0, 15, peripheriques);
        Produit routeur = new Produit("ROU001", "Routeur WiFi 6", 300.0, 8, reseau);
        
        produitService.create(pc1);
        produitService.create(pc2);
        produitService.create(souris);
        produitService.create(clavier);
        produitService.create(routeur);
        
        System.out.println("✓ Produits créés avec succès");
        System.out.println();
    }
    
    private void testCreationCommandes() {
        System.out.println("3. Test de création des commandes:");
        
        // Créer des commandes
        Commande commande1 = new Commande(LocalDate.of(2023, 3, 14), 0.0);
        Commande commande2 = new Commande(LocalDate.of(2023, 3, 15), 0.0);
        
        commandeService.create(commande1);
        commandeService.create(commande2);
        
        // Récupérer les produits
        List<Produit> produits = produitService.findAll();
        Produit pc1 = produits.get(0);
        Produit souris = produits.get(2);
        Produit clavier = produits.get(3);
        Produit routeur = produits.get(4);
        
        // Créer des lignes de commande pour la première commande
        LigneCommande lc1 = new LigneCommande(2, pc1.getPrix(), commande1, pc1);
        LigneCommande lc2 = new LigneCommande(3, souris.getPrix(), commande1, souris);
        LigneCommande lc3 = new LigneCommande(1, clavier.getPrix(), commande1, clavier);
        
        ligneCommandeService.create(lc1);
        ligneCommandeService.create(lc2);
        ligneCommandeService.create(lc3);
        
        // Créer des lignes de commande pour la deuxième commande
        LigneCommande lc4 = new LigneCommande(1, routeur.getPrix(), commande2, routeur);
        LigneCommande lc5 = new LigneCommande(2, souris.getPrix(), commande2, souris);
        
        ligneCommandeService.create(lc4);
        ligneCommandeService.create(lc5);
        
        System.out.println("✓ Commandes et lignes de commande créées avec succès");
        System.out.println();
    }
    
    private void testProduitsParCategorie() {
        System.out.println("4. Test des produits par catégorie:");
        
        List<Categorie> categories = categorieService.findAll();
        for (Categorie categorie : categories) {
            System.out.println("Catégorie: " + categorie.getNom());
            List<Produit> produits = produitService.findByCategorie(categorie.getId());
            for (Produit produit : produits) {
                System.out.println("  - " + produit.getReference() + " - " + produit.getDesignation() + " - " + produit.getPrix() + " DH");
            }
            System.out.println();
        }
    }
    
    private void testProduitsCommandesEntreDates() {
        System.out.println("5. Test des produits commandés entre deux dates:");
        
        LocalDate dateDebut = LocalDate.of(2023, 3, 1);
        LocalDate dateFin = LocalDate.of(2023, 3, 31);
        
        List<Produit> produits = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
        System.out.println("Produits commandés entre " + dateDebut + " et " + dateFin + ":");
        for (Produit produit : produits) {
            System.out.println("  - " + produit.getReference() + " - " + produit.getDesignation());
        }
        System.out.println();
    }
    
    private void testDetailsCommande() {
        System.out.println("6. Test des détails d'une commande:");
        
        // Afficher les détails de la première commande
        produitService.afficherDetailsCommande(1L);
        System.out.println();
    }
    
    private void testProduitsPrixSuperieurA() {
        System.out.println("7. Test des produits avec prix supérieur à 100 DH:");
        
        List<Produit> produits = produitService.findByPrixSuperieurA(100.0);
        System.out.println("Produits avec prix > 100 DH:");
        for (Produit produit : produits) {
            System.out.println("  - " + produit.getReference() + " - " + produit.getDesignation() + " - " + produit.getPrix() + " DH");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        StockManagementTest test = new StockManagementTest();
        test.runAllTests();
    }
}
