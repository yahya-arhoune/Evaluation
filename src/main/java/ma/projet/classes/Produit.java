package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "Produit.findByPrixGreaterThan", query = "FROM Produit p WHERE p.prix > :price")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private String designation;
    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id") // Foreign key column
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommandeProduit> ligneCommandeProduits;

    public Produit() {}

    public Produit(String reference, String designation, float prix, Categorie categorie) {
        this.reference = reference;
        this.designation = designation;
        this.prix = prix;
        this.categorie = categorie;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    public List<LigneCommandeProduit> getLigneCommandeProduits() { return ligneCommandeProduits; }
    public void setLigneCommandeProduits(List<LigneCommandeProduit> ligneCommandeProduits) { this.ligneCommandeProduits = ligneCommandeProduits; }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", designation='" + designation + '\'' +
                ", prix=" + prix +
                '}';
    }
}