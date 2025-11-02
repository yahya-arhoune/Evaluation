package ma.projet3.dao.impl;

import ma.projet3.beans.Homme;
import ma.projet3.dao.IDao;
import ma.projet3.util.HibernateUtil; // Or inject EntityManager if using Spring

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class HommeDao implements IDao<Homme> {
    @Override
    public Homme create(Homme o) {
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
    public void delete(Homme o) {
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
    public Homme update(Homme o) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        Homme updatedHomme = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            updatedHomme = em.merge(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return updatedHomme;
    }

    @Override
    public List<Homme> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Homme> hommes = em.createQuery("FROM Homme", Homme.class).getResultList();
        em.close();
        return hommes;
    }

    @Override
    public Homme findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Homme homme = em.find(Homme.class, id);
        em.close();
        return homme;
    }
}