package sample.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookId;

    private String bookTitle;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookName) {
        this.bookTitle = bookName;
    }

    @Override
    public String toString() {
        return "Book{"
                + "bookId=" + bookId
                + ", bookTitle='" + bookTitle + '\''
                + '}';
    }
}
