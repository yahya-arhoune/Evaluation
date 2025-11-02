package ma.projet3.beans;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme")
    private List<Mariage> mariages; // List of marriages where this homme is the husband

    public Homme() {
        super();
    }

    public Homme(String nom, String prenom, Date dateNaissance, String lieuNaissance) {
        super();
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}