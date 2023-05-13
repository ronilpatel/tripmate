package grp16.tripmate.myPostRequest.persistence;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.myPostRequest.model.MyPostRequest;
import grp16.tripmate.myPostRequest.model.PostRequestStatus;
import grp16.tripmate.myPostRequest.model.factory.IMyPostRequestFactory;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.user.persistence.UserDbColumnNames;

import java.util.*;

public class MyPostRequestPersistence implements IMyPostRequestPersistence {

    private final ILogger logger = new MyLoggerAdapter(this);
    private final IMyPostRequestQueryGenerator queryGenerator;
    private final IDatabaseExecutor databaseExecution;

    public MyPostRequestPersistence(IMyPostRequestQueryGenerator queryGenerator, IDatabaseExecutor databaseExecution) {
        this.queryGenerator = queryGenerator;
        this.databaseExecution = databaseExecution;
    }

    @Override
    public List<Map<String, Object>> getMyPostRequests() throws Exception {

        String query = queryGenerator.getMyPostRequests((Integer) SessionManager.getInstance().getValue(UserDbColumnNames.ID));
        logger.info(query);
        return databaseExecution.executeSelectQuery(query);
    }

    @Override
    public boolean createJoinRequest(int postId) throws Exception {
        String query = queryGenerator.createJoinRequest(postId, (Integer) SessionManager.getInstance().getValue(UserDbColumnNames.ID));
        logger.info(query);
        return databaseExecution.executeUpdateQuery(query);
    }

    @Override
    public List<Map<String, Object>> getPostOwnerDetails(int postId) {
        String query = queryGenerator.getPostOwnerDetails(postId);
        logger.info(query);
        return databaseExecution.executeSelectQuery(query);
    }

    @Override
    public List<Map<String, Object>> getPostRequesterDetails(int requestId) {
        String query = queryGenerator.getPostRequesterDetails(requestId);
        logger.info(query);
        return databaseExecution.executeSelectQuery(query);
    }

    @Override
    public boolean updateRequest(int requestId, PostRequestStatus postRequestStatus) {
        String query = queryGenerator.updateRequestStatus(requestId, postRequestStatus);
        logger.info(query);
        return databaseExecution.executeUpdateQuery(query);
    }

    @Override
    public List<MyPostRequest> getMyRequestByUserId(IMyPostRequestFactory myPostRequestFactory, int userId) {
        String query = queryGenerator.getMyRequestByUserId(userId);
        return listToMyRequests(databaseExecution.executeSelectQuery(query), myPostRequestFactory);
    }

    private List<MyPostRequest> listToMyRequests(List<Map<String, Object>> results, IMyPostRequestFactory requestFactory) {
        List<MyPostRequest> myRequests = new ArrayList<>();

        for (Map<String, Object> result : results) {

            MyPostRequest myRequest = (MyPostRequest) requestFactory.makeMyPostRequest();
            String status = (String) result.get("status");
            myRequest.setStatus(PostRequestStatus.valueOf(status));

            myRequest.setPostTitle((String) result.get("postTitle"));
            myRequest.setFirstNameCreator((String) result.get("firstNameCreator"));
            myRequest.setLastNameCreator((String) result.get("lastNameCreator"));

            myRequests.add(myRequest);
        }
        return myRequests;
    }
}
