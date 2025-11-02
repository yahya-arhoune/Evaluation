package ma.projet.classes;

import javax.persistence.*;

@Entity
public class LigneCommandeProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Primary key for the join table
    private int quantite;

    @ManyToOne
    @JoinColumn(name = "produit_id") // Foreign key to Produit
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id") // Foreign key to Commande
    private Commande commande;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(int quantite, Produit produit, Commande commande) {
        this.quantite = quantite;
        this.produit = produit;
        this.commande = commande;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    @Override
    public String toString() {
        return "LigneCommandeProduit{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", produit=" + (produit != null ? produit.getDesignation() : "null") +
                ", commande=" + (commande != null ? commande.getId() : "null") +
                '}';
    }
}