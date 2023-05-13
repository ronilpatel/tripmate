package grp16.tripmate.myPostRequest.persistence;

import grp16.tripmate.myPostRequest.model.MyPostRequest;
import grp16.tripmate.myPostRequest.model.PostRequestStatus;
import grp16.tripmate.myPostRequest.model.factory.IMyPostRequestFactory;

import java.util.List;
import java.util.Map;

public interface IMyPostRequestPersistence {
    List<Map<String, Object>> getMyPostRequests() throws Exception;

    boolean createJoinRequest(int postId) throws Exception;

    List<Map<String, Object>> getPostOwnerDetails(int postId);

    boolean updateRequest(int requestId, PostRequestStatus postRequestStatus);

    List<Map<String, Object>> getPostRequesterDetails(int requestId);

    List<MyPostRequest> getMyRequestByUserId(IMyPostRequestFactory myPostRequestFactory, int userId);
}
