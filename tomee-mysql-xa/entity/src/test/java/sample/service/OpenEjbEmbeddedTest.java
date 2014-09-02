package sample.service;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.ejb.*;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.junit.*;
import sample.BookPu;
import sample.entity.Book;

/**
 *
 * @author oliver.guenther
 */
public class OpenEjbEmbeddedTest {

    private EJBContainer container;

    @Inject
    private PersistenceBean persistenceBean;

    @Before
    public void setUp() throws NamingException {
        Map<String, Object> c = new HashMap<>();
        c.putAll(BookPu.CMT_PRODUCTIVE);
        container = EJBContainer.createEJBContainer(c);
        container.getContext().bind("inject", this);
    }

    @After
    public void after() {
        container.close();
    }

    @Test
    @Ignore
    public void testInContainer() throws IOException, SQLException, InterruptedException {
//        persistenceBean.fillPersistenceSource();
        try {
            persistenceBean.showStepWise();
        } catch (Exception e) {
            System.out.println("Execption ----------");
        }
        persistenceBean.printProcessList();
        System.out.print("Waiting 30s ... ");
        for (int i = 29; i > 0; i--) {
            System.out.print(i + " ");
            Thread.sleep(1000);
        }
    }

    @Test
//    @Ignore
    public void testOutOfContainerCall() throws IOException, SQLException, InterruptedException {
//        persistenceBean.fillPersistenceSource();
        try {
            int amount = 5;
            int start = 0;
            while (persistenceBean.show(start, amount)) {
                start += 5;
            }
        } catch (Exception e) {
            System.out.println("Execption ----------");
        }
        persistenceBean.printProcessList();
        System.out.print("Waiting 30s ... ");
        for (int i = 29; i > 0; i--) {
            System.out.print(i + " ");
            Thread.sleep(1000);
        }
    }

    @Stateless
    public static class PersistenceBean {

        @Inject
        private BookEao eao;

        @Inject
        private BookService bs;

        @Inject
        private DataSource ds;

        public void fillPersistenceSource() {
            bs.generateBooks(10000);
        }

        public void showStepWise() {
            System.out.println(eao.count() + " elemets in Database");
            List<Book> result = null;
            int start = 0;
            int amount = 5;
            do {
                result = eao.findAll(start, amount);
                start += amount;
                System.out.println("Loaded from " + start + " amount " + amount);
            } while (!result.isEmpty());
        }

        public boolean show(int start, int amount) {
            List<Book> result = eao.findAll(start, amount);
            start += amount;
            System.out.println("Loaded from " + start + " amount " + amount);
            return !result.isEmpty();
        }

        public void printProcessList() throws SQLException {
            Connection connection = ds.getConnection(BookPu.USER2, BookPu.PASS2); // need another user for that.
            ResultSet result = connection.createStatement().executeQuery("SHOW PROCESSLIST");
            System.out.println("SHOW PROCESSLIST");
            String line = "+----------+--------------+----------------------+--------+----------+-------+----------+----------------------+%n";
            System.out.format(line);
            System.out.printf("| Id       | User         | Host                 | db     | Command  | Time  | State    | Info                 |%n");
            System.out.format(line);
            while (result.next()) {
                System.out.format("| %-8d | %-12s | %-20s | %-6s | %-8s | %5d | %-8s | %-20s | %n",
                        result.getInt("Id"),
                        result.getString("User"),
                        result.getString("Host"),
                        result.getString("db"),
                        result.getString("Command"),
                        result.getInt("Time"),
                        result.getString("State"),
                        result.getString("Info"));
            }
            System.out.format(line);
        }

    }
}
