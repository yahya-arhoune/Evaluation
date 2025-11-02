package ma.projet2.service;

import ma.projet2.classes.Employe;
import ma.projet2.classes.EmployeTache;
import ma.projet2.classes.Projet;
import ma.projet2.util.HibernateUtil2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeService extends AbstractService2<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    // Method: Afficher la liste des tâches réalisées par un employé.
    public List<EmployeTache> findTachesRealiseesByEmploye(Employe employe) {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            Query<EmployeTache> query = session.createQuery(
                    "FROM EmployeTache et JOIN FETCH et.tache t WHERE et.employe = :employe AND et.dateDebutReal IS NOT NULL AND et.dateFinReal IS NOT NULL", EmployeTache.class);
            query.setParameter("employe", employe);
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

    // Method: Afficher la liste des projets gérés par un employé (meaning projects where this employee has performed tasks)
    public List<Projet> findProjetsManagedByEmploye(Employe employe) {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            // This query is a bit complex as an employe "manages" projects by participating in their tasks.
            // We need to distinct projects where the employee has an associated EmployeTache.
            Query<Projet> query = session.createQuery(
                    "SELECT DISTINCT t.projet FROM EmployeTache et JOIN et.tache t WHERE et.employe = :employe", Projet.class);
            query.setParameter("employe", employe);
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