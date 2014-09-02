package sample.service;

import java.util.List;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.entity.Book;

/**
 *
 * @author bastian.venz
 */
@Stateless
public class BookEao {

    private final static Logger L = LoggerFactory.getLogger(BookEao.class);

    @PersistenceContext(unitName = "book-pu")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Book findById(int id) {
        return em.find(Book.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

    public List<Book> findAll() {
        javax.persistence.criteria.CriteriaQuery<Book> cq = em.getCriteriaBuilder().createQuery(Book.class);
        cq.select(cq.from(Book.class));
        return em.createQuery(cq).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Book> findAll(int start, int amount) {
        javax.persistence.criteria.CriteriaQuery<Book> cq = em.getCriteriaBuilder().createQuery(Book.class);
        cq.select(cq.from(Book.class));
        return em.createQuery(cq).setFirstResult(start).setMaxResults(amount).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long count() {
        javax.persistence.criteria.CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        javax.persistence.criteria.Root<Book> rt = cq.from(Book.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult();
    }
}
