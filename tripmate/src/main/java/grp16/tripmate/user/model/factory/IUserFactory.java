package grp16.tripmate.user.model.factory;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.user.persistence.IUserPersistence;
import grp16.tripmate.user.persistence.IUserQueryGenerator;
import grp16.tripmate.user.model.encoder.IPasswordEncoder;
import grp16.tripmate.user.model.IUser;

public interface IUserFactory {

    IUser makeNewUser();

    IUserPersistence makeUserDatabase();

    IUserQueryGenerator makeUserQueryBuilder();

    IDatabaseExecutor makeNewDatabaseExecutor();

    IPasswordEncoder makePasswordEncoder();

    ILogger makeLogger(Object obj);
}
