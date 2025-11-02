package ma.projet3.service.impl;

import ma.projet3.beans.Femme;
import ma.projet3.dao.impl.FemmeDao;
import ma.projet3.service.IFemmeService;
import ma.projet3.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class FemmeService implements IFemmeService {
    private FemmeDao femmeDao = new FemmeDao(); // Inject this

    @Override
    public Femme create(Femme o) {
        return femmeDao.create(o);
    }

    @Override
    public void delete(Femme o) {
        femmeDao.delete(o);
    }

    @Override
    public Femme update(Femme o) {
        return femmeDao.update(o);
    }

    @Override
    public List<Femme> findAll() {
        return femmeDao.findAll();
    }

    @Override
    public Femme findById(Long id) {
        return femmeDao.findById(id);
    }

    /**
     * Méthode pour exécuter une requête native nommée retournant le nombre d'enfants d'une femme entre deux dates.
     * Note: The current Personne/Femme entity doesn't explicitly link to 'enfants'.
     * This implementation assumes a conceptual `enfant` table with `mere_id` (mother's ID) and `date_naissance`
     * columns, matching a common database design. You might need to adjust the query
     * if your actual database schema for children is different.
     */
    @Override
    public int getNombreEnfants(Femme femme, Date startDate, Date endDate) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        int nombreEnfants = 0;
        try {
            if (femme == null || femme.getId() == null) {
                System.err.println("Femme or Femme ID cannot be null.");
                return 0;
            }

            // You'd ideally define this as a named native query or a standard JPA query if 'Enfant' was an entity.
            // For a direct native query without an 'Enfant' entity:
            String sql = "SELECT COUNT(e.id) FROM Enfant e WHERE e.mere_id = :femmeId AND e.date_naissance BETWEEN :startDate AND :endDate";
            // Important: Replace 'Enfant' and column names with your actual table/column names if they exist.
            // If you don't have an 'Enfant' entity, this query is illustrative/conceptual.

            Query query = em.createNativeQuery(sql);
            query.setParameter("femmeId", femme.getId());
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            Object result = query.getSingleResult();
            if (result instanceof BigInteger) {
                nombreEnfants = ((BigInteger) result).intValue();
            } else if (result instanceof Number) { // Handles other numeric types like Long, Integer
                nombreEnfants = ((Number) result).intValue();
            }

        } catch (Exception e) {
            System.err.println("Error executing native query for nombre d'enfants: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return nombreEnfants;
    }

    /**
     * Méthode pour exécuter une requête native retournant le nombre de femmes mariées au moins deux fois.
     * This method counts how many *distinct* women have been married at least twice (historically or currently).
     */
    @Override
    public long getNombreFemmesMarieesAuMoinsDeuxFois() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        long count = 0;
        try {
            String sql = "SELECT COUNT(DISTINCT m.femme_id) " +
                    "FROM mariage m " +
                    "GROUP BY m.femme_id " +
                    "HAVING COUNT(m.id) >= 2";

            Query query = em.createNativeQuery(sql);
            List<Object> results = query.getResultList(); // Returns a list of counts, one per femme_id matching criteria

            count = results.size(); // The size of the list indicates how many distinct women matched the criteria.

        } catch (Exception e) {
            System.err.println("Error executing native query for femmes mariées au moins deux fois: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return count;
    }
}