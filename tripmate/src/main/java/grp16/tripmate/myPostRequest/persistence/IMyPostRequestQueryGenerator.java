package grp16.tripmate.myPostRequest.persistence;

import grp16.tripmate.myPostRequest.model.PostRequestStatus;

public interface IMyPostRequestQueryGenerator {
    String getMyPostRequests(int userid);

    String createJoinRequest(int post_id, int user_id);

    String getPostOwnerDetails(int post_id);

    String updateRequestStatus(int requestId, PostRequestStatus postRequestStatus);

    String getPostRequesterDetails(int request_id);

    String getMyRequestByUserId(int userid);
}
