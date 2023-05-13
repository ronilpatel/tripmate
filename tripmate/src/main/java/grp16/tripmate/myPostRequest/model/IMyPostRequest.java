package grp16.tripmate.myPostRequest.model;

import grp16.tripmate.myPostRequest.model.factory.IMyPostRequestFactory;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestPersistence;

import java.util.List;

public interface IMyPostRequest {
    List<MyPostRequest> getMyPostRequests(IMyPostRequestPersistence myPostRequestDB) throws Exception;

    boolean createJoinRequest(IMyPostRequestPersistence myPostRequestDB, int postId) throws Exception;

    MyPostRequest getPostOwnerDetails(IMyPostRequestPersistence myPostRequestDB, int postId) throws Exception;

    boolean updateRequest(IMyPostRequestPersistence myPostRequestDB, int requestId, PostRequestStatus postRequestStatus);

    MyPostRequest getPostRequesterDetails(IMyPostRequestPersistence myPostRequestDB, int requestId) ;

    List<MyPostRequest> getMyRequestByUserId(IMyPostRequestFactory requestFactory, IMyPostRequestPersistence database, int userId);
}
