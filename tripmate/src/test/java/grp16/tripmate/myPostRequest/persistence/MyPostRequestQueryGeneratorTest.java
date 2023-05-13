package grp16.tripmate.myPostRequest.persistence;

import grp16.tripmate.myPostRequest.model.PostRequestStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyPostRequestQueryGeneratorTest {

    IMyPostRequestQueryGenerator queryGenerator = MyPostRequestQueryGenerator.getInstance();


    @Test
    void getMyPostRequestsTest() {
        String actual = "SELECT pr.id as requestId, u.first_name as firstNameRequester, u.last_name as lastNameRequester, u.id as idRequester, p.title as postTitle, p.created_by as idCreator, post_owner.first_name as firstNameCreator, post_owner.last_name lastNameCreator \n" + "FROM PostRequest pr\n" + "JOIN Post p on pr.post_id = p.id\n" + "JOIN User u on pr.request_owner = u.id\n" + "JOIN User post_owner on post_owner.id = p.created_by\n" + "WHERE pr.status = \"PENDING\" and p.created_by = 1;";

        Assertions.assertEquals(actual, queryGenerator.getMyPostRequests(1));
    }

    @Test
    void getMyPostRequestsNegativeTest() {
        String actual = "SELECT pr.id as requestId, u.firstname as firstNameRequester, u.lastname as lastNameRequester, u.id as idRequester, p.title as postTitle, p.created_by as idCreator, post_owner.firstname as firstNameCreator, post_owner.lastname lastNameCreator \n" + "FROM PostRequest pr\n" + "JOIN Post p on pr.post_id = p.id\n" + "JOIN User u on pr.request_owner = u.id\n" + "JOIN User post_owner on post_owner.id = p.created_by\n" + "WHERE pr.status = \"PENDING\" and p.created_by = 1;";

        Assertions.assertNotEquals(actual, queryGenerator.getMyPostRequests(2));
    }

    @Test
    void createJoinRequestTest() {
        String actual = "INSERT INTO PostRequest\n" + "(status,\n" + "post_id,\n" + "request_owner)\n" + "VALUES\n" + "('PENDING',\n" + "1,1);\n";
        Assertions.assertEquals(actual, queryGenerator.createJoinRequest(1, 1));
    }

    @Test
    void createJoinRequestNegativeTest() {
        String actual = "INSERT INTO PostRequest\n" + "(status,\n" + "post_id,\n" + "request_owner)\n" + "VALUES\n" + "('PENDING',\n" + "1,1);\n";
        Assertions.assertNotEquals(actual, queryGenerator.createJoinRequest(2, 2));
    }

    @Test
    void getPostOwnerDetailsTest() {
        String actual = "SELECT p.title as postTitle, u.email as postOwnerEmail, u.first_name as postOwnerFirstName, u.last_name as postOwnerLastName from Post p \n" + "JOIN User u on p.created_by = u.id\n" + "WHERE p.id = 1;";
        Assertions.assertEquals(actual, queryGenerator.getPostOwnerDetails(1));
    }

    @Test
    void getPostOwnerDetailsNegativeTest() {
        String actual = "SELECT p.title as postTitle, u.email as postOwnerEmail, u.firstname as postOwnerFirstName, u.lastname as postOwnerLastName from Post p \n" + "JOIN User u on p.created_by = u.id\n" + "WHERE p.id = 1;";
        Assertions.assertNotEquals(actual, queryGenerator.getPostOwnerDetails(2));
    }

    @Test
    void updateRequestStatusTest() {
        String actual = "update PostRequest set status = 'ACCEPT' where id = 1;";
        Assertions.assertEquals(actual, queryGenerator.updateRequestStatus(1, PostRequestStatus.ACCEPT));
    }

    @Test
    void updateRequestStatusNegativeTest() {
        String actual = "update PostRequest set status = 'ACCEPT' where id = 1;";
        Assertions.assertNotEquals(actual, queryGenerator.updateRequestStatus(2, PostRequestStatus.DECLINE));
    }

    @Test
    void getPostRequesterDetailsTest() {
        String actual = "SELECT p.title as postTitle, u.email as postOwnerEmail, u.first_name as postOwnerFirstName, u.last_name as postOwnerLastName from Post p \n" + "JOIN User u on p.created_by = u.id\n" + "WHERE p.id = 1;";
        Assertions.assertEquals(actual, queryGenerator.getPostOwnerDetails(1));
    }

    @Test
    void getPostRequesterDetailsNegativeTest() {
        String actual = "SELECT p.title as postTitle, u.email as postOwnerEmail, u.firstname as postOwnerFirstName, u.lastname as postOwnerLastName from Post p \n" + "JOIN User u on p.created_by = u.id\n" + "WHERE p.id = 1;";
        Assertions.assertNotEquals(actual, queryGenerator.getPostOwnerDetails(2));
    }

    @Test
    void getMyRequestByUserIdTest() {
        String actual = "SELECT p.title as postTitle, status, post_owner.first_name as firstNameCreator, post_owner.last_name lastNameCreator FROM PostRequest pr JOIN Post p on pr.post_id = p.id JOIN User u on pr.request_owner = u.id JOIN User post_owner on post_owner.id = p.created_by WHERE u.id = 1;";
        Assertions.assertEquals(actual, queryGenerator.getMyRequestByUserId(1));
    }

    @Test
    void getMyRequestByUserIdNegativeTest() {
        String actual = "SELECT p.title as postTitle, status, post_owner.firstname as firstNameCreator, post_owner.lastname lastNameCreator FROM PostRequest pr JOIN Post p on pr.post_id = p.id JOIN User u on pr.request_owner = u.id JOIN User post_owner on post_owner.id = p.created_by WHERE u.id = 1;";
        Assertions.assertNotEquals(actual, queryGenerator.getMyRequestByUserId(2));
    }
}