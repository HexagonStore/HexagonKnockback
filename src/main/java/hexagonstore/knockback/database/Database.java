package hexagonstore.knockback.database;

import java.sql.Connection;

public interface Database {

    void createTable(String table);
    void open();
    void close();

    Connection getConnection();
}
