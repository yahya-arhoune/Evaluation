package ma.projet3.beans;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme")
    private List<Mariage> mariages;

    public Femme() {
        super();
    }

    public Femme(String nom, String prenom, Date dateNaissance, String lieuNaissance) {
        super();
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}