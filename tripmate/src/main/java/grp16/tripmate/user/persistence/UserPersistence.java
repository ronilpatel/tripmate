package grp16.tripmate.user.persistence;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.user.model.User;

import java.util.Map;

public class UserPersistence implements IUserPersistence {
    private final IUserQueryGenerator queryGenerator;
    private final IDatabaseExecutor databaseExecution;


    public UserPersistence(IUserQueryGenerator queryGenerator, IDatabaseExecutor databaseExecution) {
        this.queryGenerator = queryGenerator;
        this.databaseExecution = databaseExecution;
    }

    @Override
    public boolean insertUser(User user) {
        String query = queryGenerator.createUser(user);
        return databaseExecution.executeInsertQuery(query);
    }

    @Override
    public boolean updateUser(User user) {
        String query = queryGenerator.changeUserDetails(user);
        return databaseExecution.executeUpdateQuery(query);
    }

    @Override
    public Map<String, Object> getUserById(int userid) {
        String query = queryGenerator.getUserByUserID(userid);
        return databaseExecution.executeSelectQuery(query).get(0);
    }

    @Override
    public Map<String, Object> getUserByUsername(String username) {
        String query = queryGenerator.getUserByUsername(username);
        Map<String, Object> result = databaseExecution.executeSelectQuery(query).get(0);
        return result;
    }

    @Override
    public boolean changeUserPassword(String email, String password) {
        String query = queryGenerator.changeUserPassword(email, password);
        return databaseExecution.executeUpdateQuery(query);
    }
}