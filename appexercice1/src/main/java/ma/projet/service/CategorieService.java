package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class CategorieService implements IDao<Categorie, Long> {
    
    private SessionFactory sessionFactory;
    
    public CategorieService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public Categorie create(Categorie categorie) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(categorie);
            transaction.commit();
            return categorie;
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
    public Categorie update(Categorie categorie) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(categorie);
            transaction.commit();
            return categorie;
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
            Categorie categorie = session.get(Categorie.class, id);
            if (categorie != null) {
                session.delete(categorie);
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
    public Categorie findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Categorie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Categorie> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Categorie", Categorie.class).list();
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
