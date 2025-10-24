package ma.projet.service;

import ma.projet.classes.LigneCommande;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommande, Long> {
    
    private SessionFactory sessionFactory;
    
    public LigneCommandeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public LigneCommande create(LigneCommande ligneCommande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(ligneCommande);
            transaction.commit();
            return ligneCommande;
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
    public LigneCommande update(LigneCommande ligneCommande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(ligneCommande);
            transaction.commit();
            return ligneCommande;
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
            LigneCommande ligneCommande = session.get(LigneCommande.class, id);
            if (ligneCommande != null) {
                session.delete(ligneCommande);
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
    public LigneCommande findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(LigneCommande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<LigneCommande> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM LigneCommande", LigneCommande.class).list();
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
}
