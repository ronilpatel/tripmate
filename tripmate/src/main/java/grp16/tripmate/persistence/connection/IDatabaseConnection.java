package grp16.tripmate.persistence.connection;

import java.sql.Connection;

public interface IDatabaseConnection {
    Connection getDatabaseConnection() throws Exception;
}
