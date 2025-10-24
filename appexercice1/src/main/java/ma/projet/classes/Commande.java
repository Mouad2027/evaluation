package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;
    
    @Column(name = "total", nullable = false)
    private Double total;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;
    
    // Constructeurs
    public Commande() {}
    
    public Commande(LocalDate dateCommande, Double total) {
        this.dateCommande = dateCommande;
        this.total = total;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getDateCommande() {
        return dateCommande;
    }
    
    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }
    
    public Double getTotal() {
        return total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }
    
    public List<LigneCommande> getLignesCommande() {
        return lignesCommande;
    }
    
    public void setLignesCommande(List<LigneCommande> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }
    
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", dateCommande=" + dateCommande +
                ", total=" + total +
                '}';
    }
}
