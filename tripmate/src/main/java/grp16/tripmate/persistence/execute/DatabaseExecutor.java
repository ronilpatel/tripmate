package grp16.tripmate.persistence.execute;

import grp16.tripmate.persistence.connection.DatabaseConnection;
import grp16.tripmate.persistence.connection.IDatabaseConnection;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseExecutor implements IDatabaseExecutor {

    private final IDatabaseConnection dbConnection;
    private final ILogger logger = new MyLoggerAdapter(this);

    public DatabaseExecutor() {
        dbConnection = new DatabaseConnection();
    }

    @Override
    public List<Map<String, Object>> executeSelectQuery(String query) {
        return executeQueryReturningResultSet(query);
    }

    @Override
    public boolean executeUpdateQuery(String query) {
        return executeQuery(query);
    }

    @Override
    public boolean executeDeleteQuery(String query) {
        return executeQuery(query);
    }

    @Override
    public boolean executeInsertQuery(String query) {
        return executeQuery(query);
    }


    private boolean executeQuery(String query) {
        logger.info(query);
        try {
            final Connection connection = dbConnection.getDatabaseConnection();
            Statement statement = connection.createStatement();
            int rowsUpdated = statement.executeUpdate(query);
            connection.close();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Map<String, Object>> executeQueryReturningResultSet(String query) {
        logger.info(query);
        try {
            final Connection connection = dbConnection.getDatabaseConnection();
            final ResultSet resultSet = connection.createStatement().executeQuery(query);
            List<Map<String, Object>> resultMap = resultSetToMap(resultSet);
            connection.close();
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Map<String, Object>> resultSetToMap(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();


        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
            }
            resultList.add(row);
        }
        return resultList;
    }
}
