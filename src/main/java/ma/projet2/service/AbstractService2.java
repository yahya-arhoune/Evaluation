package ma.projet2.service;

import ma.projet2.dao.IDao;
import ma.projet2.util.HibernateUtil2; // Use HibernateUtil2 for this exercise
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class AbstractService2<T> implements IDao<T> {

    private Class<T> entityClass;

    public AbstractService2(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public boolean create(T o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean delete(T o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean update(T o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public T findById(int id) {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            return session.get(entityClass, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        Session session = null;
        try {
            session = HibernateUtil2.getSessionFactory().openSession();
            return session.createQuery("from " + entityClass.getName(), entityClass).list();
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