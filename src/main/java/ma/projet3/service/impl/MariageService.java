package ma.projet3.service.impl;

import ma.projet3.beans.Femme;
import ma.projet3.beans.Homme;
import ma.projet3.beans.Mariage;
import ma.projet3.dao.impl.MariageDao;
import ma.projet3.service.IMariageService;
import ma.projet3.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MariageService implements IMariageService {
    private MariageDao mariageDao = new MariageDao(); // Inject this

    @Override
    public Mariage create(Mariage o) {
        return mariageDao.create(o);
    }

    @Override
    public void delete(Mariage o) {
        mariageDao.delete(o);
    }

    @Override
    public Mariage update(Mariage o) {
        return mariageDao.update(o);
    }

    @Override
    public List<Mariage> findAll() {
        return mariageDao.findAll();
    }

    @Override
    public Mariage findById(Long id) {
        return mariageDao.findById(id);
    }

    /**
     * Ajouter une méthode utilisant l'API Criteria pour afficher le nombre d'hommes mariés
     * à quatre femmes entre deux dates.
     * This method returns homens who were married to 4 or more *distinct* women,
     * with at least one of those marriages starting within the specified date range.
     * The result will be: Homme ID, Homme Nom, Count of Wives (>=4).
     */
    @Override
    public List<Object[]> getNombreHommesMariesFourFemmesBetweenDates(Date startDate, Date endDate) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Object[]> results = new ArrayList<>();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Mariage> mariageRoot = cq.from(Mariage.class);
            Join<Mariage, Homme> hommeJoin = mariageRoot.join("marie");

            // Predicate to filter marriages within the date range
            Predicate datePredicate = cb.between(mariageRoot.get("dateMariage"), startDate, endDate);

            cq.multiselect(
                            hommeJoin.get("id"),
                            hommeJoin.get("nom"),
                            cb.countDistinct(mariageRoot.get("femme")) // Count distinct wives for each man
                    )
                    .where(datePredicate) // Apply date filter to the marriages
                    .groupBy(hommeJoin.get("id"), hommeJoin.get("nom"))
                    .having(cb.greaterThanOrEqualTo(cb.countDistinct(mariageRoot.get("femme")), 4L)); // Filter men with >= 4 distinct wives

            results = em.createQuery(cq).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return results;
    }


    /**
     * Ajouter une méthode pour afficher les mariages d'un homme donné, avec les détails
     * (femme, dates, nombre d'enfants).
     * This method fetches all marriages for a specific homme, along with details for each marriage.
     * It includes a placeholder for 'nombre d'enfants' as the diagram doesn't specify child entity linkage.
     * In a real scenario, you'd calculate/fetch children linked to the Femme.
     */
    @Override
    public List<Map<String, Object>> getHommesMariesWithDetails(Homme homme) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            if (homme == null || homme.getId() == null) {
                System.err.println("Homme or Homme ID cannot be null.");
                return resultList;
            }

            // Ensure the Homme object is managed
            Homme managedHomme = em.find(Homme.class, homme.getId());
            if (managedHomme == null) {
                System.err.println("Homme not found in database for ID: " + homme.getId());
                return resultList;
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tuple> cq = cb.createTupleQuery();
            Root<Mariage> mariageRoot = cq.from(Mariage.class);
            Join<Mariage, Homme> hommeJoin = mariageRoot.join("marie");
            Join<Mariage, Femme> femmeJoin = mariageRoot.join("femme");

            cq.multiselect(
                            femmeJoin.get("nom").alias("femmeNom"),
                            mariageRoot.get("dateMariage").alias("dateDebut"),
                            mariageRoot.get("dateFin").alias("dateFin")
                            // cb.literal(0).alias("nombreEnfants") // Placeholder - needs actual child count logic
                    )
                    .where(cb.equal(hommeJoin.get("id"), managedHomme.getId()))
                    .orderBy(cb.asc(mariageRoot.get("dateMariage"))); // Order by marriage date

            List<Tuple> tuples = em.createQuery(cq).getResultList();

            // Structure the results for the specific Homme
            for (Tuple tuple : tuples) {
                Map<String, Object> marriageDetails = new HashMap<>();
                marriageDetails.put("Femme", tuple.get("femmeNom", String.class));
                marriageDetails.put("Date Debut", tuple.get("dateDebut", Date.class));
                marriageDetails.put("Date Fin", tuple.get("dateFin", Date.class));
                // To get actual 'Nombre Enfants', you would need to:
                // 1. Have an 'Enfant' entity.
                // 2. Link 'Enfant' to 'Femme' (e.g., ManyToOne from Enfant to Femme).
                // 3. Perform a subquery or join to count children for each 'Femme'.
                // For now, let's just put 0 as a placeholder, or you can call a service.
                // Example: marriageDetails.put("Nbr Enfants", new FemmeService().getNombreEnfants(em.find(Femme.class, tuple.get("femmeId", Long.class))));
                marriageDetails.put("Nbr Enfants", 0); // Placeholder
                resultList.add(marriageDetails);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return resultList;
    }
}