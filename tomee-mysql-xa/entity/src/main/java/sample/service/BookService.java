package sample.service;

import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.entity.Book;

@Stateless
public class BookService {

    private final static Logger L = LoggerFactory.getLogger(BookService.class);

    @PersistenceContext(unitName = "book-pu")
    private EntityManager entityManager;

    public void generateBooks(int amount) {
        for (int i = 0; i < amount; i++) {
            Book book = new Book();
            book.setBookTitle(UUID.randomUUID().toString());
            entityManager.persist(book);
        }
    }

}
