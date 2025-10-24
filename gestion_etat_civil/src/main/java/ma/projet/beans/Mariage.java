package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Femme.marieesAuMoinsDeuxFois",
                query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2")
})
@NamedNativeQuery(
        name = "Mariage.countEnfantsByFemmeAndDates",
        query = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM mariage m WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :startDate AND :endDate"
)
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int nbrEnfant;

    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    // Constructeurs
    public Mariage() {}

    public Mariage(Date dateDebut, Date dateFin, int nbrEnfant, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
        this.homme = homme;
        this.femme = femme;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }
    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }
}