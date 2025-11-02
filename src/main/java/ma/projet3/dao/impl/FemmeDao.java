package ma.projet3.dao.impl;

import ma.projet3.beans.Femme;
import ma.projet3.dao.IDao;
import ma.projet3.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class FemmeDao implements IDao<Femme> {
    @Override
    public Femme create(Femme o) {
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
    public void delete(Femme o) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            // Re-attach the entity if it's detached before removing
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
    public Femme update(Femme o) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        Femme updatedFemme = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            updatedFemme = em.merge(o); // merge returns the managed instance
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return updatedFemme;
    }

    @Override
    public List<Femme> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Femme> femmes = null;
        try {
            femmes = em.createQuery("FROM Femme", Femme.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return femmes;
    }

    @Override
    public Femme findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Femme femme = null;
        try {
            femme = em.find(Femme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return femme;
    }
}