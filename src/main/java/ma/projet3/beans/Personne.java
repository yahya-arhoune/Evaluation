package ma.projet3.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Or SINGLE_TABLE, TABLE_PER_CLASS depending on your design choice
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateNaissance;
    private String sexe; // "homme" or "femme"

    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL)
    private Set<Mariage> mariagesOuMarie;

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private Set<Mariage> mariagesOuFemme;

    public Personne() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Set<Mariage> getMariagesOuMarie() {
        return mariagesOuMarie;
    }

    public void setMariagesOuMarie(Set<Mariage> mariagesOuMarie) {
        this.mariagesOuMarie = mariagesOuMarie;
    }

    public Set<Mariage> getMariagesOuFemme() {
        return mariagesOuFemme;
    }

    public void setMariagesOuFemme(Set<Mariage> mariagesOuFemme) {
        this.mariagesOuFemme = mariagesOuFemme;
    }
}