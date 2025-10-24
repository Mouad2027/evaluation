package ma.projet.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"homme", "femme"})
    private List<Mariage> mariages;

    public Femme() {}

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}