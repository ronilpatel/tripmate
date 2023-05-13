package grp16.tripmate.persistence.connection;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.properties.MyProperties;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection implements IDatabaseConnection {
    private final ILogger logger = new MyLoggerAdapter(this);

    private Connection connectToDatabase() throws Exception {
        String databaseURL = MyProperties.getInstance().getDatabaseURL();
        String databaseUserName = MyProperties.getInstance().getDatabaseUserName();
        String databasePassword = MyProperties.getInstance().getDatabasePassword();

        return DriverManager.getConnection(databaseURL, databaseUserName, databasePassword);
    }

    @Override
    public Connection getDatabaseConnection() throws Exception {
        return connectToDatabase();
    }
}
