package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SpringProduitService implements IDao<Produit, Long> {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    @Transactional
    public Produit create(Produit produit) {
        Session session = sessionFactory.getCurrentSession();
        session.save(produit);
        return produit;
    }
    
    @Override
    @Transactional
    public Produit update(Produit produit) {
        Session session = sessionFactory.getCurrentSession();
        session.update(produit);
        return produit;
    }
    
    @Override
    @Transactional
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Produit produit = session.get(Produit.class, id);
        if (produit != null) {
            session.delete(produit);
            return true;
        }
        return false;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Produit findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Produit.class, id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Produit> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Produit", Produit.class).list();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return findById(id) != null;
    }
    
    /**
     * Afficher la liste des produits par catégorie
     */
    @Transactional(readOnly = true)
    public List<Produit> findByCategorie(Long categorieId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId", 
            Produit.class
        ).setParameter("categorieId", categorieId).list();
    }
    
    /**
     * Afficher les produits commandés entre deux dates
     */
    @Transactional(readOnly = true)
    public List<Produit> findProduitsCommandesEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "SELECT DISTINCT p FROM Produit p " +
            "JOIN p.lignesCommande lc " +
            "JOIN lc.commande c " +
            "WHERE c.dateCommande BETWEEN :dateDebut AND :dateFin", 
            Produit.class
        )
        .setParameter("dateDebut", dateDebut)
        .setParameter("dateFin", dateFin)
        .list();
    }
    
    /**
     * Afficher les produits commandés dans une commande donnée
     */
    @Transactional(readOnly = true)
    public List<Produit> findProduitsByCommande(Long commandeId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "SELECT p FROM Produit p " +
            "JOIN p.lignesCommande lc " +
            "WHERE lc.commande.id = :commandeId", 
            Produit.class
        ).setParameter("commandeId", commandeId).list();
    }
    
    /**
     * Afficher les détails d'une commande avec ses produits
     */
    @Transactional(readOnly = true)
    public void afficherDetailsCommande(Long commandeId) {
        Session session = sessionFactory.getCurrentSession();
        
        // Récupérer la commande
        Commande commande = session.get(Commande.class, commandeId);
        if (commande == null) {
            System.out.println("Commande non trouvée avec l'ID: " + commandeId);
            return;
        }
        
        System.out.println("Commande : " + commande.getId() + "     Date : " + commande.getDateCommande());
        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix    Quantité");
        
        // Récupérer les lignes de commande
        List<LigneCommande> lignes = session.createQuery(
            "SELECT lc FROM LigneCommande lc " +
            "WHERE lc.commande.id = :commandeId", 
            LigneCommande.class
        ).setParameter("commandeId", commandeId).list();
        
        for (LigneCommande ligne : lignes) {
            System.out.printf("%-10s %-8.0f DH  %d%n", 
                ligne.getProduit().getReference(),
                ligne.getPrixUnitaire(),
                ligne.getQuantite()
            );
        }
    }
    
    /**
     * Afficher la liste des produits dont le prix est supérieur à un montant donné
     * Utilise une requête nommée
     */
    @Transactional(readOnly = true)
    public List<Produit> findByPrixSuperieurA(Double prixMinimum) {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Produit.findByPrixSuperieurA", Produit.class)
            .setParameter("prix", prixMinimum)
            .list();
    }
}
