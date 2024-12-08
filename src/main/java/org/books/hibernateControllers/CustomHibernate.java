package org.books.hibernateControllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.books.Model.Comment;
import org.books.Model.PeriodicRecord;
import org.books.Model.Publication;
import org.books.Model.User;
import org.books.Model.enums.PublicationStatus;

import java.util.ArrayList;
import java.util.List;

public class CustomHibernate extends GenericHibernate {

    public CustomHibernate(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public User getUserByCredentials(String username, String password) {
        User user = null;

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.equal(root.get("login"), username), cb.equal(root.get("password"), password)));
            Query q = entityManager.createQuery(query);
            user = (User) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<Publication> getAvailablePublications(User user) {
        List<Publication> publications = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Publication> query = cb.createQuery(Publication.class);
            Root<Publication> root = query.from(Publication.class);
            query.select(root).where(cb.and(cb.equal(root.get("publicationStatus"), PublicationStatus.AVAILABLE), cb.notEqual(root.get("owner"), user)));
            Query q = entityManager.createQuery(query);
            publications = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return publications;
    }

    public List<Publication> getBorrowedPublications(User user) {
        List<Publication> publications = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Publication> query = cb.createQuery(Publication.class);
            Root<Publication> root = query.from(Publication.class);
            query.select(root).where(cb.and(cb.equal(root.get("publicationStatus"), PublicationStatus.BORROWED), cb.equal(root.get("owner"), user)));
            Query q = entityManager.createQuery(query);
            publications = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return publications;
    }

    public void  deleteComment(int id) {
        try {
            entityManager.getTransaction().begin();
            var comment = entityManager.find(Comment.class, id);

            if (comment.getParentComment() != null) {
                Comment parentComment = entityManager.find(Comment.class, comment.getParentComment().getId());
                parentComment.getReplies().remove(comment);
                entityManager.merge(parentComment);
            }

            comment.getReplies().clear();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Publication> getUserPublications(User user) {
        List<Publication> publications = new ArrayList<>();
        try {
            entityManager.clear();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Publication> query = cb.createQuery(Publication.class);
            Root<Publication> root = query.from(Publication.class);
            query.select(root).where(cb.equal(root.get("owner"), user));
            query.orderBy(cb.desc(root.get("requestDate")));
            Query q = entityManager.createQuery(query);
            publications = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return publications;
    }

    public List<PeriodicRecord> getPeriodicById(int id) {
        List<PeriodicRecord> periodicRecords = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PeriodicRecord> query = cb.createQuery(PeriodicRecord.class);
            Root<PeriodicRecord> root = query.from(PeriodicRecord.class);
            Publication publication = entityManager.find(Publication.class, id);

            query.select(root).where(cb.equal(root.get("publication"), publication));
            query.orderBy(cb.desc(root.get("transactionDate")));

            Query q = entityManager.createQuery(query);
            periodicRecords = q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return periodicRecords;
    }
}
