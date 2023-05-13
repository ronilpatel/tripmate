package grp16.tripmate.myPostRequest.model.factory;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.myPostRequest.model.IMyPostRequest;
import grp16.tripmate.myPostRequest.model.MyPostRequest;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestPersistence;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestQueryGenerator;
import grp16.tripmate.myPostRequest.persistence.MyPostRequestPersistence;
import grp16.tripmate.myPostRequest.persistence.MyPostRequestQueryGenerator;
import grp16.tripmate.persistence.execute.DatabaseExecutor;
import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyPostRequestFactoryTest {

    private IMyPostRequestFactory factory;

    MyPostRequestFactoryTest() {
        factory = MyPostRequestFactory.getInstance();
    }

    @Test
    void makeMyPostRequestTest() {
        Assertions.assertInstanceOf(IMyPostRequest.class, factory.makeMyPostRequest());
    }

    @Test
    void makeMyPostRequestDBTest() {
        Assertions.assertInstanceOf(IMyPostRequestPersistence.class, factory.makeMyPostRequestDB());
    }

    @Test
    void makeMyPostRequestQueryBuilderTest() {
        Assertions.assertInstanceOf(IMyPostRequestQueryGenerator.class, factory.makeMyPostRequestQueryGenerator());
    }

    @Test
    void makeNewDatabaseExecutorTest() {
        Assertions.assertInstanceOf(IDatabaseExecutor.class, factory.makeNewDatabaseExecutor());
    }

    @Test
    void makeNewLoggerTest() {
        Assertions.assertInstanceOf(ILogger.class, factory.makeNewLogger(this));
    }
}