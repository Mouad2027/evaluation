package ma.projet.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"homme", "femme"})
    private List<Mariage> mariages;

    public Homme() {}

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }

}