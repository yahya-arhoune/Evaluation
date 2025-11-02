package ma.projet2.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmployeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary key for the join table
    @Temporal(TemporalType.DATE)
    private Date dateDebutReal; // Date Debut Reelle
    @Temporal(TemporalType.DATE)
    private Date dateFinReal;   // Date Fin Reelle

    @ManyToOne
    @JoinColumn(name = "employe_id") // Foreign key to Employe
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "tache_id") // Foreign key to Tache
    private Tache tache;

    public EmployeTache() {}

    public EmployeTache(Date dateDebutReal, Date dateFinReal, Employe employe, Tache tache) {
        this.dateDebutReal = dateDebutReal;
        this.dateFinReal = dateFinReal;
        this.employe = employe;
        this.tache = tache;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDateDebutReal() { return dateDebutReal; }
    public void setDateDebutReal(Date dateDebutReal) { this.dateDebutReal = dateDebutReal; }
    public Date getDateFinReal() { return dateFinReal; }
    public void setDateFinReal(Date dateFinReal) { this.dateFinReal = dateFinReal; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }

    @Override
    public String toString() {
        return "EmployeTache{" +
                "id=" + id +
                ", dateDebutReal=" + dateDebutReal +
                ", dateFinReal=" + dateFinReal +
                ", employe=" + (employe != null ? employe.getNom() : "null") +
                ", tache=" + (tache != null ? tache.getNom() : "null") +
                '}';
    }
}