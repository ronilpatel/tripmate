package grp16.tripmate.myPostRequest.model.factory;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestPersistence;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestQueryGenerator;
import grp16.tripmate.myPostRequest.model.IMyPostRequest;

public interface IMyPostRequestFactory {
    IMyPostRequest makeMyPostRequest();

    IMyPostRequestPersistence makeMyPostRequestDB();

    IMyPostRequestQueryGenerator makeMyPostRequestQueryGenerator();

    IDatabaseExecutor makeNewDatabaseExecutor();

    ILogger makeNewLogger(Object object);

}
