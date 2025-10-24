package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class ProduitService implements IDao<Produit, Long> {
    
    private SessionFactory sessionFactory;
    
    public ProduitService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public Produit create(Produit produit) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(produit);
            transaction.commit();
            return produit;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public Produit update(Produit produit) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(produit);
            transaction.commit();
            return produit;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Produit produit = session.get(Produit.class, id);
            if (produit != null) {
                session.delete(produit);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public Produit findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Produit> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Produit", Produit.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean exists(Long id) {
        return findById(id) != null;
    }
    
    /**
     * Afficher la liste des produits par catégorie
     * @param categorieId l'ID de la catégorie
     * @return la liste des produits de cette catégorie
     */
    public List<Produit> findByCategorie(Long categorieId) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery(
                "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId", 
                Produit.class
            ).setParameter("categorieId", categorieId).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Afficher les produits commandés entre deux dates
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return la liste des produits commandés dans cette période
     */
    public List<Produit> findProduitsCommandesEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        Session session = sessionFactory.openSession();
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Afficher les produits commandés dans une commande donnée
     * @param commandeId l'ID de la commande
     * @return la liste des produits de cette commande
     */
    public List<Produit> findProduitsByCommande(Long commandeId) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery(
                "SELECT p FROM Produit p " +
                "JOIN p.lignesCommande lc " +
                "WHERE lc.commande.id = :commandeId", 
                Produit.class
            ).setParameter("commandeId", commandeId).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Afficher les détails d'une commande avec ses produits
     * @param commandeId l'ID de la commande
     */
    public void afficherDetailsCommande(Long commandeId) {
        Session session = sessionFactory.openSession();
        try {
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
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    /**
     * Afficher la liste des produits dont le prix est supérieur à un montant donné
     * Utilise une requête nommée
     * @param prixMinimum le prix minimum
     * @return la liste des produits
     */
    public List<Produit> findByPrixSuperieurA(Double prixMinimum) {
        Session session = sessionFactory.openSession();
        try {
            return session.createNamedQuery("Produit.findByPrixSuperieurA", Produit.class)
                .setParameter("prix", prixMinimum)
                .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
