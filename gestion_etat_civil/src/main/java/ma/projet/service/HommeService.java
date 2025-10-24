package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.GenericDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HommeService extends GenericDao<Homme> {

    public HommeService() {
        super(Homme.class);
    }

    // Méthode pour afficher les épouses d'un homme entre deux dates
    public List<Object[]> getEpousesEntreDates(Long hommeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT f.nom, f.prenom, m.dateDebut, m.dateFin, m.nbrEnfant " +
                    "FROM Mariage m JOIN m.femme f " +
                    "WHERE m.homme.id = :hommeId " +
                    "AND m.dateDebut BETWEEN :dateDebut AND :dateFin";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode pour afficher les mariages d'un homme avec détails
    public void afficherMariagesHomme(Long hommeId) {
        Homme homme = findById(hommeId);
        if (homme == null) {
            System.out.println("Homme non trouvé !");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
        System.out.println("Mariages En Cours :");

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Mariages en cours (sans date de fin)
            String hqlEnCours = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NULL ORDER BY m.dateDebut";
            Query<Mariage> queryEnCours = session.createQuery(hqlEnCours, Mariage.class);
            queryEnCours.setParameter("hommeId", hommeId);
            List<Mariage> mariagesEnCours = queryEnCours.list();

            int count = 1;
            for (Mariage m : mariagesEnCours) {
                System.out.println(count + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                        "   Date Début : " + dateFormat.format(m.getDateDebut()) + "    Nbr Enfants : " + m.getNbrEnfant());
                count++;
            }

            System.out.println("\nMariages échoués :");

            // Mariages échoués (avec date de fin)
            String hqlEchoues = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NOT NULL ORDER BY m.dateDebut";
            Query<Mariage> queryEchoues = session.createQuery(hqlEchoues, Mariage.class);
            queryEchoues.setParameter("hommeId", hommeId);
            List<Mariage> mariagesEchoues = queryEchoues.list();

            count = 1;
            for (Mariage m : mariagesEchoues) {
                System.out.println(count + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                        "  Date Début : " + dateFormat.format(m.getDateDebut()) + "    Date Fin : " + dateFormat.format(m.getDateFin()) +
                        "    Nbr Enfants : " + m.getNbrEnfant());
                count++;
            }

        } finally {
            session.close();
        }
    }
}