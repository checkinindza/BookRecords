package org.books.hibernateControllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import javafx.scene.control.Alert;
import org.books.utils.FxUtils;

import java.util.ArrayList;
import java.util.List;

public class GenericHibernate {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public GenericHibernate (EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public <T> void create(T entity) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Error", "Error during CREATE operation");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> void update(T entity) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Error", "Error during UPDATE operation");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> List<T> getAllRecords(Class<T> entityClass) {

        List<T> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return list;

    }

    public <T> T getEntityById(Class<T> entityClass, int id) {
        T result = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            result = entityManager.find(entityClass, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return result;
    }

    public <T> void delete(Class<T> entityClass, int id) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entity = entityManager.find(entityClass, id);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Error", "Error during DELETE operation");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> T getEntityByCriteria(Class<T> entityClass, String criteria, String compareTo) {
        T result = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> rootEntry = cq.from(entityClass);
            cq.where(cb.equal(rootEntry.get(criteria), compareTo));
            result = entityManager.createQuery(cq).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return result;
    }
}
