package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.GenericDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Service
public class MariageService extends GenericDao<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }

    // Méthode Criteria pour hommes mariés à 4 femmes entre deux dates
    public List<Object[]> getHommesMarieesQuatreFemmesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

            Root<Mariage> mariage = cq.from(Mariage.class);
            Join<Mariage, Homme> homme = mariage.join("homme");

            cq.multiselect(
                    homme.get("id"),
                    homme.get("nom"),
                    homme.get("prenom"),
                    cb.countDistinct(mariage.get("femme"))
            );

            cq.where(cb.between(mariage.get("dateDebut"), dateDebut, dateFin));
            cq.groupBy(homme.get("id"));
            cq.having(cb.ge(cb.countDistinct(mariage.get("femme")), 4));

            Query<Object[]> query = session.createQuery(cq);
            return query.list();

        } finally {
            session.close();
        }
    }
}