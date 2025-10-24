package ma.projet.dao;

import ma.projet.beans.Mariage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MariageRepository extends JpaRepository<Mariage, Long>, JpaSpecificationExecutor<Mariage> {

    List<Mariage> findByHommeId(Long hommeId);

    @Query("SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NULL ORDER BY m.dateDebut")
    List<Mariage> findMariagesEnCoursByHomme(@Param("hommeId") Long hommeId);

    @Query("SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NOT NULL ORDER BY m.dateDebut")
    List<Mariage> findMariagesEchouesByHomme(@Param("hommeId") Long hommeId);

    @Query("SELECT COUNT(DISTINCT m.femme) FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateDebut BETWEEN :start AND :end")
    Long countDistinctFemmesByHommeAndDates(@Param("hommeId") Long hommeId,
                                            @Param("start") Date start,
                                            @Param("end") Date end);
}