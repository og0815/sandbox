package sample;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 *
 * @author oliver.guenther
 */
public class BookPu {

    public final static String URL = "jdbc:mysql://192.168.1.136/book";

    public final static String USER = "testuser";

    public final static String PASS = "testuser";

    public final static Map<String, String> CMT_IN_MEMORY;

    public final static Map<String, String> CMT_PRODUCTIVE;

    static {
        String persistenceUnit = "book-pu";
        String dataSourceManaged = "bookDs";
        String dataSourceUnmanaged = "bookDsNoJta";
        Map<String, String> o = new HashMap<>();
        o.put(persistenceUnit + ".hibernate.show_sql", "false");
        o.put(persistenceUnit + ".hibernate.hbm2ddl.auto", "create-drop");
        o.put(persistenceUnit + ".hibernate.jdbc.batch_size", "0");
        o.put(dataSourceManaged, "new://Resource?type=DataSource");
        o.put(dataSourceManaged + ".JdbcDriver", "org.hsqldb.jdbcDriver");
        o.put(dataSourceManaged + ".JdbcUrl", "jdbc:hsqldb:mem:" + dataSourceManaged);
        CMT_IN_MEMORY = o;

        o = new HashMap<>();
        o.put(persistenceUnit + ".hibernate.jdbc.batch_size", "0");
        o.put(persistenceUnit + ".hibernate.hbm2ddl.auto", "validate");
        o.put(dataSourceManaged, "new://Resource?type=DataSource");
        o.put(dataSourceManaged + ".XaDataSource", "repairXa");
        o.put(dataSourceManaged + ".DataSourceCreator", "dbcp-alternative");

        o.put("repairXa", "new://Resource?type=javax.sql.XADataSource&class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        o.put("repairXa.user", USER);
        o.put("repairXa.password", PASS);
        o.put("repairXa.url", URL);

        o.put(dataSourceUnmanaged, "new://Resource?type=DataSource");
        o.put(dataSourceUnmanaged + ".UserName", USER);
        o.put(dataSourceUnmanaged + ".Password", PASS);
        o.put(dataSourceUnmanaged + ".JdbcDriver", "com.mysql.jdbc.Driver");
        o.put(dataSourceUnmanaged + ".JdbcUrl", URL);
        o.put(dataSourceUnmanaged + ".JtaManaged", "false");
        CMT_PRODUCTIVE = o;
    }

    @PersistenceContext(unitName = "book-pu")
    @Produces
    private EntityManager entityManager;

    @Resource(name = "bookDs")
    @Produces
    private DataSource datasource;

}
