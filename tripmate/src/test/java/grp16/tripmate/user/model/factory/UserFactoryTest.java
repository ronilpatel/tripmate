package grp16.tripmate.user.model.factory;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.user.model.IUser;
import grp16.tripmate.user.model.encoder.IPasswordEncoder;
import grp16.tripmate.user.persistence.IUserPersistence;
import grp16.tripmate.user.persistence.IUserQueryGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    IUserFactory factory;

    UserFactoryTest() {
        factory = UserFactory.getInstance();
    }

    @Test
    void makeNewUserTest() {
        Assertions.assertInstanceOf(IUser.class, factory.makeNewUser());
    }

    @Test
    void makeUserDatabaseTest() {
        Assertions.assertInstanceOf(IUserPersistence.class, factory.makeUserDatabase());
    }


    @Test
    void makeUserQueryBuilderTest() {
        Assertions.assertInstanceOf(IUserQueryGenerator.class, factory.makeUserQueryBuilder());
    }

    @Test
    void makeNewDatabaseExecutorTest() {
        Assertions.assertInstanceOf(IDatabaseExecutor.class, factory.makeNewDatabaseExecutor());
    }

    @Test
    void makePasswordEncoderTest() {
        Assertions.assertInstanceOf(IPasswordEncoder.class, factory.makePasswordEncoder());
    }

    @Test
    void makeLoggerTest() {
        Assertions.assertInstanceOf(IUserPersistence.class, factory.makeUserDatabase());
    }
}