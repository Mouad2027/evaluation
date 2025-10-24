package ma.projet.dao;

import ma.projet.beans.Femme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FemmeRepository extends JpaRepository<Femme, Long> {

    // Requête native nommée
    @Query(value = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM mariage m WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Long countEnfantsByFemmeAndDates(@Param("femmeId") Long femmeId,
                                     @Param("startDate") Date startDate,
                                     @Param("endDate") Date endDate);

    // Requête JPQL pour femmes mariées au moins deux fois
    @Query("SELECT DISTINCT f FROM Femme f JOIN f.mariages m GROUP BY f HAVING COUNT(m) >= 2")
    List<Femme> findFemmesMarieesAuMoinsDeuxFois();

    // Femme la plus âgée
    @Query("SELECT f FROM Femme f ORDER BY f.dateNaissance ASC")
    List<Femme> findAllOrderByDateNaissanceAsc();

    default Femme findPlusAgee() {
        List<Femme> femmes = findAllOrderByDateNaissanceAsc();
        return femmes.isEmpty() ? null : femmes.get(0);
    }
}