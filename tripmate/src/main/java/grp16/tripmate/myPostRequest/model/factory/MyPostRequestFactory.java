package grp16.tripmate.myPostRequest.model.factory;

import grp16.tripmate.persistence.execute.DatabaseExecutor;
import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestPersistence;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestQueryGenerator;
import grp16.tripmate.myPostRequest.persistence.MyPostRequestPersistence;
import grp16.tripmate.myPostRequest.persistence.MyPostRequestQueryGenerator;
import grp16.tripmate.myPostRequest.model.IMyPostRequest;
import grp16.tripmate.myPostRequest.model.MyPostRequest;

public class MyPostRequestFactory implements IMyPostRequestFactory {

    private static IMyPostRequestFactory instance = null;

    private MyPostRequestFactory() {

    }

    public static IMyPostRequestFactory getInstance() {
        if (instance == null) {
            instance = new MyPostRequestFactory();
        }
        return instance;

    }

    @Override
    public IMyPostRequest makeMyPostRequest() {
        return new MyPostRequest();
    }

    @Override
    public IMyPostRequestPersistence makeMyPostRequestDB() {
        return new MyPostRequestPersistence(makeMyPostRequestQueryGenerator(), makeNewDatabaseExecutor());
    }

    @Override
    public IMyPostRequestQueryGenerator makeMyPostRequestQueryGenerator() {
        return MyPostRequestQueryGenerator.getInstance();
    }

    @Override
    public IDatabaseExecutor makeNewDatabaseExecutor() {
        return new DatabaseExecutor();
    }

    @Override
    public ILogger makeNewLogger(Object object) {
        return new MyLoggerAdapter(object);
    }

}
