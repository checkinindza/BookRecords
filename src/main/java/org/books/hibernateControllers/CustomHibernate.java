package org.books.hibernateControllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.books.Model.*;
import org.books.Model.enums.PublicationStatus;
import org.books.utils.PasswordUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomHibernate extends GenericHibernate {

    public CustomHibernate(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
    public User getUserByCredentials(String username, String password) {
        User user = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.equal(root.get("login"), username));
            Query q = entityManager.createQuery(query);
            user = (User) q.getSingleResult();
            if (PasswordUtils.verifyPassword(password, user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Publication> getAvailablePublications(User user) {
        List<Publication> publications = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
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
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Publication> query = cb.createQuery(Publication.class);
            Root<Publication> root = query.from(Publication.class);
            query.select(root).where(cb.and(cb.equal(root.get("publicationStatus"), PublicationStatus.BORROWED), cb.equal(root.get("borrowerClientList"), user)));
            Query q = entityManager.createQuery(query);
            publications = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return publications;
    }

    public List<Publication> getUserPublications(User user) {
        List<Publication> publications = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
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

    public <T> List<T> getRecordsByCriteria(Class<T> entityClass, String criteria, String compareTo) {
        List<T> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> rootEntry = cq.from(entityClass);
            cq.where(cb.equal(rootEntry.get(criteria), compareTo));
            Query q = entityManager.createQuery(cq);
            list = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserByPublication(int publicationId) {
        User user = null;
        Publication publication = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Publication> queryPublication = cb.createQuery(Publication.class);
            Root<Publication> rootPublication = queryPublication.from(Publication.class);
            queryPublication.select(rootPublication).where(cb.equal(rootPublication.get("id"), publicationId));
            Query q = entityManager.createQuery(queryPublication);
            publication = (Publication) q.getSingleResult();
            if (publication.getOwner() != null) {
                CriteriaQuery<User> queryUser = cb.createQuery(User.class);
                Root<User> rootUser = queryUser.from(User.class);
                queryUser.select(rootUser).where(cb.equal(rootUser.get("id"), publication.getOwner().getId()));
                q = entityManager.createQuery(queryUser);
                user = (User) q.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public void deleteComment(int id) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
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

    public void deletePublication(int id) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Publication publication = entityManager.find(Publication.class, id);
            Client client;
            Client borrower;
            if (publication != null) {
                if (publication.getOwner() != null) {
                    client = publication.getOwner();
                    publication.setOwner(null);
                    client.getOwnedPublications().remove(publication);
                    entityManager.merge(client);
                }
              /*  if (publication.getBorrowerClientList() != null) {
                    borrower = publication.getBorrowerClientList();
                    publication.setBorrowerClientList(null);
                    borrower.getBorrowedPublications().remove(publication);
                    entityManager.merge(borrower);
                }*/
                if (publication.getRecords() != null) {
                   for (PeriodicRecord record : publication.getRecords()) {
                       entityManager.remove(record);
                    }
                    publication.getRecords().clear();
                }
                entityManager.flush();
                publication = entityManager.find(Publication.class, id);
                if (publication != null) {
                    entityManager.remove(publication);
                }
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
