package ma.projet3.service.impl;

import ma.projet3.beans.Femme;
import ma.projet3.beans.Homme;
import ma.projet3.beans.Mariage;
import ma.projet3.dao.impl.HommeDao;
import ma.projet3.service.IHommeService;
import ma.projet3.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService implements IHommeService {
    private HommeDao hommeDao = new HommeDao(); // In a real app, inject this (e.g., via constructor or Spring's @Autowired)

    @Override
    public Homme create(Homme o) {
        return hommeDao.create(o);
    }

    @Override
    public void delete(Homme o) {
        hommeDao.delete(o);
    }

    @Override
    public Homme update(Homme o) {
        return hommeDao.update(o);
    }

    @Override
    public List<Homme> findAll() {
        return hommeDao.findAll();
    }

    @Override
    public Homme findById(Long id) {
        return hommeDao.findById(id);
    }

    /**
     * Ajoute une méthode pour afficher les épouses d'un homme entre deux dates.
     * This method retrieves all wives a man had, whose marriage started within the given date range.
     */
    @Override
    public List<Femme> findEpousesBetweenDates(Homme homme, Date startDate, Date endDate) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Femme> epouses = new ArrayList<>();
        try {
            if (homme == null || homme.getId() == null) {
                System.err.println("Homme or Homme ID cannot be null.");
                return epouses;
            }

            // Ensure the Homme object is managed
            Homme managedHomme = em.find(Homme.class, homme.getId());
            if (managedHomme == null) {
                System.err.println("Homme not found in database.");
                return epouses;
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Femme> cq = cb.createQuery(Femme.class);
            Root<Mariage> mariageRoot = cq.from(Mariage.class);
            Join<Mariage, Homme> hommeJoin = mariageRoot.join("marie");
            Join<Mariage, Femme> femmeJoin = mariageRoot.join("femme");

            cq.select(femmeJoin)
                    .distinct(true) // Ensure unique wives
                    .where(cb.equal(hommeJoin.get("id"), managedHomme.getId()),
                            cb.between(mariageRoot.get("dateMariage"), startDate, endDate));

            epouses = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return epouses;
    }
}