package grp16.tripmate.myPostRequest.model;

import grp16.tripmate.myPostRequest.model.factory.MyPostRequestFactory;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestPersistence;
import grp16.tripmate.myPostRequest.persistence.MyPostRequestPersistenceMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class MyPostRequestTest {

    private IMyPostRequestPersistence databaseMock;
    private MyPostRequestFactory factory;
    private MyPostRequest myPostRequest;

    public MyPostRequestTest() {
        databaseMock = new MyPostRequestPersistenceMock();
        factory = (MyPostRequestFactory) MyPostRequestFactory.getInstance();
        myPostRequest = (MyPostRequest) MyPostRequestFactory.getInstance().makeMyPostRequest();
    }

    @Test
    void createJoinRequestTest() throws Exception {
        int postId = 1;
        Assertions.assertTrue(myPostRequest.createJoinRequest(databaseMock, postId));
    }

    @Test
    void getMyPostRequestsTest() throws Exception {
        List<Map<String, Object>> myPostRequests = databaseMock.getMyPostRequests();
        Map<String, Object> myPostRequest = myPostRequests.get(0);

        Assertions.assertEquals(29, myPostRequest.get("requestId"));
        Assertions.assertEquals("Arpit", myPostRequest.get("firstNameRequester"));
        Assertions.assertEquals("Patel", myPostRequest.get("lastNameRequester"));
        Assertions.assertEquals(9, myPostRequest.get("idRequester"));
        Assertions.assertEquals(1, myPostRequest.get("postId"));
    }

    @Test
    void getMyPostRequestsNegativeTest() throws Exception {
        List<Map<String, Object>> myPostRequests = databaseMock.getMyPostRequests();
        Map<String, Object> myPostRequest = myPostRequests.get(0);

        Assertions.assertNotEquals(90, myPostRequest.get("requestId"));
        Assertions.assertNotEquals("Shreya", myPostRequest.get("firstNameRequester"));
        Assertions.assertNotEquals("PATIL", myPostRequest.get("lastNameRequester"));
        Assertions.assertNotEquals(80, myPostRequest.get("idRequester"));
        Assertions.assertNotEquals(2, myPostRequest.get("postId"));
    }

    @Test
    void getPostOwnerDetailsTest() {
        int postId = 1;
        List<Map<String, Object>> myPostRequests = databaseMock.getPostOwnerDetails(postId);
        Map<String, Object> myPostRequest = myPostRequests.get(0);

        Assertions.assertEquals(29, myPostRequest.get("requestId"));
        Assertions.assertEquals("Aman", myPostRequest.get("firstNameCreator"));
        Assertions.assertEquals("Shah", myPostRequest.get("lastNameCreator"));
        Assertions.assertEquals(6, myPostRequest.get("idCreator"));
        Assertions.assertEquals(postId, myPostRequest.get("postId"));
    }

    @Test
    void getPostOwnerDetailsNegativeTest() {
        int postId = 9;
        List<Map<String, Object>> myPostRequests = databaseMock.getPostOwnerDetails(postId);
        Map<String, Object> myPostRequest = myPostRequests.get(0);

        Assertions.assertNotEquals(7, myPostRequest.get("requestId"));
        Assertions.assertNotEquals("Arpit", myPostRequest.get("firstNameCreator"));
        Assertions.assertNotEquals("Patel", myPostRequest.get("lastNameCreator"));
        Assertions.assertNotEquals(1, myPostRequest.get("idCreator"));
        Assertions.assertNotEquals(postId, myPostRequest.get("postId"));
    }

    @Test
    void updateRequestTest() {
        boolean result = databaseMock.updateRequest(1, PostRequestStatus.ACCEPT);
        Assertions.assertTrue(result);
    }

    @Test
    void getPostRequesterDetailsTest() {
        int postId = 1;
        List<Map<String, Object>> myPostRequests = databaseMock.getPostRequesterDetails(postId);
        Map<String, Object> myPostRequest = myPostRequests.get(0);

        Assertions.assertEquals(29, myPostRequest.get("requestId"));
        Assertions.assertEquals("Arpit", myPostRequest.get("firstNameRequester"));
        Assertions.assertEquals("Patel", myPostRequest.get("lastNameRequester"));
        Assertions.assertEquals(9, myPostRequest.get("idRequester"));
        Assertions.assertEquals(postId, myPostRequest.get("postId"));
    }

    @Test
    void getPostRequesterDetailsNegativeTest() {
        int postId = 2;
        List<Map<String, Object>> myPostRequests = databaseMock.getPostRequesterDetails(postId);
        Map<String, Object> myPostRequest = myPostRequests.get(0);

        Assertions.assertNotEquals(2, myPostRequest.get("requestId"));
        Assertions.assertNotEquals("Aman", myPostRequest.get("firstNameRequester"));
        Assertions.assertNotEquals("Shah", myPostRequest.get("lastNameRequester"));
        Assertions.assertNotEquals(10, myPostRequest.get("idRequester"));
        Assertions.assertNotEquals(postId, myPostRequest.get("postId"));
    }

    @Test
    void getMyRequestByUserIdTest() {
        int userId = 2;
        List<MyPostRequest> myPostRequestListExpected = new ArrayList<>();
        MyPostRequest myRequest = (MyPostRequest) factory.makeMyPostRequest();

        myRequest.setStatus(PostRequestStatus.PENDING);
        myRequest.setPostTitle("Arpit_Trip");
        myRequest.setFirstNameCreator("Aman");
        myRequest.setLastNameCreator("Shah");
        myPostRequestListExpected.add(myRequest);

        List<MyPostRequest> myPostRequestList = databaseMock.getMyRequestByUserId(factory, userId);

        Assertions.assertEquals(myPostRequestListExpected.get(0).getStatus(), myPostRequestList.get(0).getStatus());
        Assertions.assertEquals(myPostRequestListExpected.get(0).getPostTitle(), myPostRequestList.get(0).getPostTitle());
        Assertions.assertEquals(myPostRequestListExpected.get(0).getFirstNameCreator(), myPostRequestList.get(0).getFirstNameCreator());
        Assertions.assertEquals(myPostRequestListExpected.get(0).getLastNameCreator(), myPostRequestList.get(0).getLastNameCreator());

    }

    @Test
    void getMyRequestByUserIdNegativeTest() {
        int userId = 2;
        List<MyPostRequest> myPostRequestListExpected = new ArrayList<>();
        MyPostRequest myRequest = (MyPostRequest) factory.makeMyPostRequest();

        myRequest.setStatus(PostRequestStatus.ACCEPT);
        myRequest.setPostTitle("Aman_Trip");
        myRequest.setFirstNameCreator("Arpit");
        myRequest.setLastNameCreator("Patel");
        myPostRequestListExpected.add(myRequest);

        List<MyPostRequest> myPostRequestList = databaseMock.getMyRequestByUserId(factory, userId);

        Assertions.assertNotEquals(myPostRequestListExpected.get(0).getStatus(), myPostRequestList.get(0).getStatus());
        Assertions.assertNotEquals(myPostRequestListExpected.get(0).getPostTitle(), myPostRequestList.get(0).getPostTitle());
        Assertions.assertNotEquals(myPostRequestListExpected.get(0).getFirstNameCreator(), myPostRequestList.get(0).getFirstNameCreator());
        Assertions.assertNotEquals(myPostRequestListExpected.get(0).getLastNameCreator(), myPostRequestList.get(0).getLastNameCreator());
    }
}