package ma.projet2.service;

import ma.projet2.classes.Tache;
import ma.projet2.classes.EmployeTache;
import ma.projet2.util.HibernateUtil2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TacheService extends AbstractService2<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    // Named Query: Afficher les tâches dont le prix est supérieur à 1000 DH
    // The @NamedQuery is defined in the Tache entity class.
    public List<Tache> findTachesByPrixGreaterThan(double price) {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            Query<Tache> query = session.createNamedQuery("Tache.findByPrixGreaterThan", Tache.class);
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

    // Method: Afficher la liste des tâches réalisées entre deux dates.
    public List<EmployeTache> findTachesRealiseesBetweenDates(Date startDate, Date endDate) {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            Query<EmployeTache> query = session.createQuery(
                    "FROM EmployeTache et JOIN FETCH et.tache t " +
                            "WHERE et.dateDebutReal >= :startDate AND et.dateFinReal <= :endDate " +
                            "ORDER BY et.dateDebutReal", EmployeTache.class);
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
}