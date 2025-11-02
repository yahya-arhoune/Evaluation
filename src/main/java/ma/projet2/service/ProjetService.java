package ma.projet2.service;

import ma.projet2.classes.Projet;
import ma.projet2.classes.ChefProjet;
import ma.projet2.classes.Tache;
import ma.projet2.classes.EmployeTache;
import ma.projet2.util.HibernateUtil2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ProjetService extends AbstractService2<Projet> {

    public ProjetService() {
        super(Projet.class);
    }

    // Method: Afficher la liste des tâches planifiées pour un projet.
    public List<Tache> findPlannedTachesByProjet(Projet projet) {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            Query<Tache> query = session.createQuery("FROM Tache t WHERE t.projet = :projet", Tache.class);
            query.setParameter("projet", projet);
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

    // Method: Afficher la liste des tâches réalisées avec les dates réelles.
    public List<EmployeTache> findRealizedTachesWithRealDatesByProjet(Projet projet) {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            Query<EmployeTache> query = session.createQuery(
                    "FROM EmployeTache et JOIN FETCH et.tache t WHERE t.projet = :projet AND et.dateDebutReal IS NOT NULL AND et.dateFinReal IS NOT NULL", EmployeTache.class);
            query.setParameter("projet", projet);
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