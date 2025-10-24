package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "ligne_commande")
public class LigneCommande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "quantite", nullable = false)
    private Integer quantite;
    
    @Column(name = "prix_unitaire", nullable = false)
    private Double prixUnitaire;
    
    @Column(name = "sous_total", nullable = false)
    private Double sousTotal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    
    // Constructeurs
    public LigneCommande() {}
    
    public LigneCommande(Integer quantite, Double prixUnitaire, Commande commande, Produit produit) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.commande = commande;
        this.produit = produit;
        this.sousTotal = quantite * prixUnitaire;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getQuantite() {
        return quantite;
    }
    
    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
        if (prixUnitaire != null) {
            this.sousTotal = quantite * prixUnitaire;
        }
    }
    
    public Double getPrixUnitaire() {
        return prixUnitaire;
    }
    
    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        if (quantite != null) {
            this.sousTotal = quantite * prixUnitaire;
        }
    }
    
    public Double getSousTotal() {
        return sousTotal;
    }
    
    public void setSousTotal(Double sousTotal) {
        this.sousTotal = sousTotal;
    }
    
    public Commande getCommande() {
        return commande;
    }
    
    public void setCommande(Commande commande) {
        this.commande = commande;
    }
    
    public Produit getProduit() {
        return produit;
    }
    
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    @Override
    public String toString() {
        return "LigneCommande{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", sousTotal=" + sousTotal +
                '}';
    }
}
