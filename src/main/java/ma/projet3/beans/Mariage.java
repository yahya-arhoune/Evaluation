package ma.projet3.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateMariage;
    private Date dateFin; // For divorce or end of marriage

    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateMariage() {
        return dateMariage;
    }

    public void setDateMariage(Date dateMariage) {
        this.dateMariage = dateMariage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMarie(Homme h1) {
    }
}