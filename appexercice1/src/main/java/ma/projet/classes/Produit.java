package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedQueries({
    @NamedQuery(
        name = "Produit.findByPrixSuperieurA",
        query = "SELECT p FROM Produit p WHERE p.prix > :prix"
    )
})
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reference", nullable = false, unique = true, length = 50)
    private String reference;
    
    @Column(name = "designation", nullable = false, length = 200)
    private String designation;
    
    @Column(name = "prix", nullable = false)
    private Double prix;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;
    
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;
    
    // Constructeurs
    public Produit() {}
    
    public Produit(String reference, String designation, Double prix, Integer stock, Categorie categorie) {
        this.reference = reference;
        this.designation = designation;
        this.prix = prix;
        this.stock = stock;
        this.categorie = categorie;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReference() {
        return reference;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public Double getPrix() {
        return prix;
    }
    
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public Categorie getCategorie() {
        return categorie;
    }
    
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    public List<LigneCommande> getLignesCommande() {
        return lignesCommande;
    }
    
    public void setLignesCommande(List<LigneCommande> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }
    
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", designation='" + designation + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                '}';
    }
}
