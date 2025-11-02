package ma.projet3.dao.impl;

import ma.projet3.beans.Mariage;
import ma.projet3.dao.IDao;
import ma.projet3.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class MariageDao implements IDao<Mariage> {
    @Override
    public Mariage create(Mariage o) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return o;
    }

    @Override
    public void delete(Mariage o) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.contains(o) ? o : em.merge(o));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Mariage update(Mariage o) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        Mariage updatedMariage = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            updatedMariage = em.merge(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return updatedMariage;
    }

    @Override
    public List<Mariage> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Mariage> mariages = null;
        try {
            mariages = em.createQuery("FROM Mariage", Mariage.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return mariages;
    }

    @Override
    public Mariage findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Mariage mariage = null;
        try {
            mariage = em.find(Mariage.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return mariage;
    }
}