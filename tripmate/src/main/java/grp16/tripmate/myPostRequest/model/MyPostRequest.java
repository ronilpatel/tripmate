package grp16.tripmate.myPostRequest.model;

import grp16.tripmate.myPostRequest.model.factory.IMyPostRequestFactory;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestPersistence;
import grp16.tripmate.myPostRequest.model.factory.MyPostRequestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyPostRequest implements IMyPostRequest {

    private int idRequest;
    private int postId;     // Required in HTML
    private PostRequestStatus status;
    private String firstNameRequester;
    private String lastNameRequester;
    private int idRequester;
    private String emailRequester;
    private String postTitle;
    private int idCreator;
    private String firstNameCreator;
    private String lastNameCreator;
    private String emailCreator;

    public MyPostRequest() {
    }

    public int getIdRequest() {     // Required in HTML
        return this.idRequest;
    }

    public String getFirstNameRequester() {     // Required in HTML
        return firstNameRequester;
    }

    public String getLastNameRequester() { // Required in HTML
        return lastNameRequester;
    }

    public String getEmailRequester() {
        return emailRequester;
    }

    public String getPostTitle() {      // Required in HTML
        return postTitle;
    }

    public int getIdCreator() {     // Required in HTML
        return idCreator;
    }

    public int getIdRequester() {   // Required in HTML
        return idRequester;
    }

    public String getEmailCreator() {
        return emailCreator;
    }

    public String getFirstNameCreator() {
        return firstNameCreator;
    }

    public String getLastNameCreator() {    // Required in HTML
        return lastNameCreator;
    }

    public PostRequestStatus getStatus() {
        return status;
    }

    public void setStatus(PostRequestStatus status) {
        this.status = status;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public void setFirstNameRequester(String firstNameRequester) {
        this.firstNameRequester = firstNameRequester;
    }

    public void setLastNameRequester(String lastNameRequester) {
        this.lastNameRequester = lastNameRequester;
    }

    public void setEmailRequester(String emailRequester) {
        this.emailRequester = emailRequester;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setIdCreator(int idCreator) {       // Required in HTML
        this.idCreator = idCreator;
    }

    public void setIdRequester(int idRequester) {
        this.idRequester = idRequester;
    }

    public void setFirstNameCreator(String firstNameCreator) {
        this.firstNameCreator = firstNameCreator;
    }

    public void setLastNameCreator(String lastNameCreator) {
        this.lastNameCreator = lastNameCreator;
    }

    public void setEmailCreator(String emailCreator) {
        this.emailCreator = emailCreator;
    }

    @Override
    public List<MyPostRequest> getMyPostRequests(IMyPostRequestPersistence myPostRequestDB) throws Exception {
        List<MyPostRequest> myPostRequestList = listToMyPostRequest(myPostRequestDB.getMyPostRequests());
        return myPostRequestList;
    }

    @Override
    public boolean createJoinRequest(IMyPostRequestPersistence myPostRequestDB, int postId) throws Exception {
        return myPostRequestDB.createJoinRequest(postId);
    }

    @Override
    public MyPostRequest getPostOwnerDetails(IMyPostRequestPersistence myPostRequestDB, int postId) throws Exception {
        List<MyPostRequest> myPostRequestPostOwnerList = listToMyPostRequestPostOwner(myPostRequestDB.getPostOwnerDetails(postId));
        return myPostRequestPostOwnerList.get(0);
    }

    @Override
    public boolean updateRequest(IMyPostRequestPersistence myPostRequestDB, int requestId, PostRequestStatus postRequestStatus) {
        return myPostRequestDB.updateRequest(requestId, postRequestStatus);
    }

    @Override
    public MyPostRequest getPostRequesterDetails(IMyPostRequestPersistence myPostRequestDB, int requestId) {
        List<MyPostRequest> postRequesterDetails = listToPostRequesterDetails(myPostRequestDB.getPostRequesterDetails(requestId));
        return postRequesterDetails.get(0);
    }

    private List<MyPostRequest> listToMyPostRequest(List<Map<String, Object>> results) {
        List<MyPostRequest> myPostRequestList = new ArrayList<>();
        for (Map<String, Object> result : results) {
            MyPostRequest myPostRequest = (MyPostRequest) MyPostRequestFactory.getInstance().makeMyPostRequest();

            myPostRequest.setIdRequest((Integer) result.get("requestId"));
            myPostRequest.setFirstNameRequester((String) result.get("firstNameRequester"));
            myPostRequest.setLastNameRequester((String) result.get("lastNameRequester"));
            myPostRequest.setIdRequester((Integer) result.get("idRequester"));
            myPostRequest.setPostTitle((String) result.get("postTitle"));
            myPostRequest.setFirstNameCreator((String) result.get("firstNameCreator"));
            myPostRequest.setLastNameCreator((String) result.get("lastNameCreator"));

            myPostRequestList.add(myPostRequest);
        }
        return myPostRequestList;
    }

    private List<MyPostRequest> listToMyPostRequestPostOwner(List<Map<String, Object>> results) {
        List<MyPostRequest> myPostRequestPostOwnerList = new ArrayList<>();
        for (Map<String, Object> result : results) {
            MyPostRequest myPostRequest = (MyPostRequest) MyPostRequestFactory.getInstance().makeMyPostRequest();

            myPostRequest.setPostTitle((String) result.get("postTitle"));
            myPostRequest.setEmailCreator((String) result.get("postOwnerEmail"));
            myPostRequest.setFirstNameCreator((String) result.get("postOwnerFirstName"));
            myPostRequest.setLastNameCreator((String) result.get("postOwnerLastName"));

            myPostRequestPostOwnerList.add(myPostRequest);
        }
        return myPostRequestPostOwnerList;
    }

    private List<MyPostRequest> listToPostRequesterDetails(List<Map<String, Object>> results) {
        List<MyPostRequest> postRequesterDetails = new ArrayList<>();
        for (Map<String, Object> result : results) {
            MyPostRequest myPostRequest = (MyPostRequest) MyPostRequestFactory.getInstance().makeMyPostRequest();

            myPostRequest.setEmailRequester((String) result.get("email"));
            myPostRequest.setPostTitle((String) result.get("title"));

            postRequesterDetails.add(myPostRequest);
        }
        return postRequesterDetails;
    }

    @Override
    public List<MyPostRequest> getMyRequestByUserId(IMyPostRequestFactory requestFactory, IMyPostRequestPersistence database, int userId) {
        return database.getMyRequestByUserId(requestFactory, userId);
    }

}


