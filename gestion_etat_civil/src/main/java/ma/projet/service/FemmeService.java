package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.GenericDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FemmeService extends GenericDao<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    // Méthode native pour le nombre d'enfants d'une femme entre deux dates
    public Long getNombreEnfantsEntreDates(Long femmeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Long> query = session.createNamedQuery("Mariage.countEnfantsByFemmeAndDates", Long.class);
            query.setParameter("femmeId", femmeId);
            query.setParameter("startDate", dateDebut);
            query.setParameter("endDate", dateFin);
            Long result = query.uniqueResult();
            return result != null ? result : 0L;
        } finally {
            session.close();
        }
    }

    // Méthode pour les femmes mariées au moins deux fois
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createNamedQuery("Femme.marieesAuMoinsDeuxFois", Femme.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode pour trouver la femme la plus âgée
    public Femme getFemmeLaPlusAgee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Femme ORDER BY dateNaissance ASC";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
}