package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ProduitService extends AbstractService<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    // Method to display products by category
    public List<Produit> findProduitsByCategorie(Categorie categorie) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Produit> query = session.createQuery("FROM Produit p WHERE p.categorie = :category", Produit.class);
            query.setParameter("category", categorie);
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    // Method to display ordered products between two dates
    public List<LigneCommandeProduit> findProduitsCommandesBetweenDates(Date startDate, Date endDate) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // This query fetches LigneCommandeProduit which links to the Commande date
            Query<LigneCommandeProduit> query = session.createQuery(
                    "FROM LigneCommandeProduit lcp JOIN FETCH lcp.commande c JOIN FETCH lcp.produit p " +
                            "WHERE c.date BETWEEN :startDate AND :endDate", LigneCommandeProduit.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    // Method to display ordered products in a given command
    public List<LigneCommandeProduit> findProduitsByCommande(int commandeId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<LigneCommandeProduit> query = session.createQuery(
                    "FROM LigneCommandeProduit lcp JOIN FETCH lcp.produit p WHERE lcp.commande.id = :commandeId", LigneCommandeProduit.class);
            query.setParameter("commandeId", commandeId);
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    // Named Query: Products with price > 100 DH
    // This query needs to be defined in the Produit entity
    // @NamedQuery(name = "Produit.findByPrixGreaterThan", query = "FROM Produit p WHERE p.prix > :price")
    public List<Produit> findProduitsByPrixGreaterThan(float price) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Produit> query = session.createNamedQuery("Produit.findByPrixGreaterThan", Produit.class);
            query.setParameter("price", price);
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}