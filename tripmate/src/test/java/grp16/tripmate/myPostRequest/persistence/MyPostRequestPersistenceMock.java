package grp16.tripmate.myPostRequest.persistence;

import grp16.tripmate.myPostRequest.model.MyPostRequest;
import grp16.tripmate.myPostRequest.model.PostRequestStatus;
import grp16.tripmate.myPostRequest.model.factory.IMyPostRequestFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPostRequestPersistenceMock implements IMyPostRequestPersistence {

    private static final Map<Integer, Map<String, Object>> myPostRequestsDB = new HashMap<>();

    private final Map<String, Object> myPostRequest;
    private final List<Map<String, Object>> myPostRequests;

    public MyPostRequestPersistenceMock() {
        myPostRequests = new ArrayList<>();
        myPostRequest = new HashMap<>();

        myPostRequest.put("requestId", 29);
        myPostRequest.put("firstNameRequester", "Arpit");
        myPostRequest.put("lastNameRequester", "Patel");
        myPostRequest.put("idRequester", 9);
        myPostRequest.put("firstNameCreator", "Aman");
        myPostRequest.put("lastNameCreator", "Shah");
        myPostRequest.put("idCreator", 6);
        myPostRequest.put("postId", 1);
        myPostRequest.put("postTitle", "Arpit_Trip");
        myPostRequest.put("status", PostRequestStatus.PENDING);

        myPostRequests.add(myPostRequest);
    }

    @Override
    public boolean createJoinRequest(int postId) throws Exception {
        myPostRequestsDB.put(postId, myPostRequest);

        return postId == (Integer) myPostRequest.get("postId");
    }

    @Override
    public List<Map<String, Object>> getMyPostRequests() {
        return myPostRequests;
    }

    @Override
    public List<Map<String, Object>> getPostOwnerDetails(int postId) {
        List<Map<String, Object>> myPostRequestOwnerDetailList = new ArrayList<>();

        Map<String, Object> myPostRequestOwnerDetail = new HashMap<>();
        myPostRequestOwnerDetail.put("requestId", myPostRequests.get(0).get("requestId"));
        myPostRequestOwnerDetail.put("firstNameCreator", myPostRequests.get(0).get("firstNameCreator"));
        myPostRequestOwnerDetail.put("lastNameCreator", myPostRequests.get(0).get("lastNameCreator"));
        myPostRequestOwnerDetail.put("idCreator", myPostRequests.get(0).get("idCreator"));
        myPostRequestOwnerDetail.put("postId", myPostRequests.get(0).get("postId"));

        myPostRequestOwnerDetailList.add(myPostRequestOwnerDetail);
        return myPostRequestOwnerDetailList;
    }

    @Override
    public boolean updateRequest(int requestId, PostRequestStatus postRequestStatus) {
        myPostRequests.get(0).put("status", postRequestStatus);
        return true;
    }

    @Override
    public List<Map<String, Object>> getPostRequesterDetails(int requestId) {
        List<Map<String, Object>> myPostRequestRequesterDetailList = new ArrayList<>();
        Map<String, Object> myPostRequestRequesterDetails = new HashMap<>();
        myPostRequestRequesterDetails.put("requestId", myPostRequests.get(0).get("requestId"));
        myPostRequestRequesterDetails.put("firstNameRequester", myPostRequests.get(0).get("firstNameRequester"));
        myPostRequestRequesterDetails.put("lastNameRequester", myPostRequests.get(0).get("lastNameRequester"));
        myPostRequestRequesterDetails.put("idRequester", myPostRequests.get(0).get("idRequester"));
        myPostRequestRequesterDetails.put("postId", myPostRequests.get(0).get("postId"));

        myPostRequestRequesterDetailList.add(myPostRequestRequesterDetails);
        return myPostRequestRequesterDetailList;
    }

    @Override
    public List<MyPostRequest> getMyRequestByUserId(IMyPostRequestFactory myPostRequestFactory, int userId) {
        List<MyPostRequest> myPostRequestList = new ArrayList<>();
        MyPostRequest myRequest = (MyPostRequest) myPostRequestFactory.makeMyPostRequest();

        myRequest.setStatus((PostRequestStatus) myPostRequest.get("status"));
        myRequest.setPostTitle((String) myPostRequest.get("postTitle"));
        myRequest.setFirstNameCreator((String) myPostRequest.get("firstNameCreator"));
        myRequest.setLastNameCreator((String) myPostRequest.get("lastNameCreator"));

        myPostRequestList.add(myRequest);
        return myPostRequestList;
    }
}