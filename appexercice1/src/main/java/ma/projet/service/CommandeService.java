package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class CommandeService implements IDao<Commande, Long> {
    
    private SessionFactory sessionFactory;
    
    public CommandeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public Commande create(Commande commande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(commande);
            transaction.commit();
            return commande;
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
    public Commande update(Commande commande) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(commande);
            transaction.commit();
            return commande;
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
            Commande commande = session.get(Commande.class, id);
            if (commande != null) {
                session.delete(commande);
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
    public Commande findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Commande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Commande> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Commande", Commande.class).list();
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
