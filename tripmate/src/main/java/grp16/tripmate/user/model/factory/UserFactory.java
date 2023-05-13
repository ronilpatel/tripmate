package grp16.tripmate.user.model.factory;

import grp16.tripmate.persistence.execute.DatabaseExecutor;
import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.user.persistence.IUserPersistence;
import grp16.tripmate.user.persistence.IUserQueryGenerator;
import grp16.tripmate.user.persistence.UserPersistence;
import grp16.tripmate.user.persistence.UserQueryGenerator;
import grp16.tripmate.user.model.encoder.IPasswordEncoder;
import grp16.tripmate.user.model.encoder.PasswordEncoder;
import grp16.tripmate.user.model.IUser;
import grp16.tripmate.user.model.User;

public class UserFactory implements IUserFactory {
    private static IUserFactory instance;

    private UserFactory() {
        //Required private empty constructor
    }

    public static IUserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }

    @Override
    public IUser makeNewUser() {
        return new User();
    }

    @Override
    public IUserPersistence makeUserDatabase() {
        return new UserPersistence(makeUserQueryBuilder(), makeNewDatabaseExecutor());
    }

    @Override
    public IUserQueryGenerator makeUserQueryBuilder() {
        return UserQueryGenerator.getInstance();
    }

    @Override
    public IDatabaseExecutor makeNewDatabaseExecutor() {
        return new DatabaseExecutor();
    }

    @Override
    public IPasswordEncoder makePasswordEncoder() {
        return PasswordEncoder.getInstance();
    }

    @Override
    public ILogger makeLogger(Object obj) {
        return new MyLoggerAdapter(obj);
    }

}