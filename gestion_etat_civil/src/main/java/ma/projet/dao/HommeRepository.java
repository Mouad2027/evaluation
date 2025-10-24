package ma.projet.dao;

import ma.projet.beans.Homme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HommeRepository extends JpaRepository<Homme, Long> {

    @Query("SELECT m.femme FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateDebut BETWEEN :start AND :end")
    List<Object[]> findEpousesByHommeAndDates(@Param("hommeId") Long hommeId,
                                              @Param("start") Date start,
                                              @Param("end") Date end);

    @Query("SELECT h FROM Homme h WHERE SIZE(h.mariages) >= :minMariages")
    List<Homme> findHommesWithMinMariages(@Param("minMariages") int minMariages);
}