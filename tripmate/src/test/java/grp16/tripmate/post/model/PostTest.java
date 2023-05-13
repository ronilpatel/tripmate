package grp16.tripmate.post.model;

import grp16.tripmate.post.persistance.IPostPersistence;
import grp16.tripmate.post.persistance.PostPersistenceMock;
import grp16.tripmate.post.model.exception.MinAgeGreaterThanMaxAgeException;
import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;
import grp16.tripmate.post.model.exception.StartDateBeforeTodayException;
import grp16.tripmate.post.model.factory.IPostFactory;
import grp16.tripmate.post.model.factory.PostFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostTest {

    IPostFactory factory;
    IPostPersistence database;
    Post post;

    public PostTest() throws ParseException {
        factory = PostFactory.getInstance();
        database = new PostPersistenceMock();

        post = (Post) factory.makeNewPost();
        post.setId(1);
        post.setOwner_id(2);
        post.setTitle("Test Title");
        post.setCapacity(6);
        post.setSource("A");
        post.setDestination("B");
        post.setStartDate("2024-12-01");
        post.setEndDate("2025-12-01");
        post.setMinAge(5);
        post.setMaxAge(10);
        post.setDescription("Test Description");
        post.setHidden(false);
    }

    @Test
    @Order(1)
    void createPost() throws Exception {
        assertTrue(post.createPost(database));
    }

    @Test
    @Order(11)
    void createPostAgain() throws Exception {
        assertFalse(post.createPost(database));
    }

    @Test
    @Order(2)
    void getPostsByUserId() throws Exception {
        post.createPost(database);
        assertEquals(1, post.getPostsByUserId(database, 2).size());
    }

    @Test
    @Order(12)
    void getPostsByUserIdDNE() throws Exception {
        post.createPost(database);
        assertEquals(0, post.getPostsByUserId(database, 5).size());
    }

    @Test
    @Order(3)
    void getAllPosts() throws Exception {
        post.createPost(database);
        assertEquals(1, post.getAllPosts(database, 2).size());
    }

    @Test
    @Order(4)
    void getPostByPostId() throws Exception {
        post.createPost(database);
        Post postFromDb = post.getPostByPostId(database, post.getId());
        assertEquals(postFromDb.getId(), post.getId());
    }

    @Test
    @Order(14)
    void getPostByPostIdWithNoPost() {
        Post postFromDb = post.getPostByPostId(database, 5);
        assertNull(postFromDb);
    }

    @Test
    @Order(5)
    void updatePost() throws Exception {
        post.createPost(database);
        post.setTitle("Updated Title");
        post.updatePost(database);
        Post postFromDb = post.getPostByPostId(database, post.getId());
        assertEquals("Updated Title", postFromDb.getTitle());
    }

    @Test
    @Order(6)
    void deletePost() throws Exception {
        post.createPost(database);
        post.deletePost(database);
        Post postFromDB = post.getPostByPostId(database, post.getId());
        assertNull(postFromDB);
    }

    @Test
    @Order(7)
    void hidePost() throws Exception {
        post.createPost(database);
        post.hidePost(database);
        Post postFromDb = post.getPostByPostId(database, post.getId());
        assertTrue(postFromDb.isHidden());
    }

    @Test
    @Order(8)
    void validatePostStartDateBeforeTodayThrows() throws Exception {
        post.setStartDate("2022-12-01");
        post.createPost(database);
        assertThrows(StartDateBeforeTodayException.class, () -> post.validatePost(factory.makePostValidator()));
    }

    @Test
    @Order(15)
    void validatePostStartDateBeforeTodayDoesNotThrow() throws Exception {
        post.setStartDate("2023-12-01");
        post.createPost(database);
        assertDoesNotThrow(() -> post.validatePost(factory.makePostValidator()));
    }

    @Test
    @Order(9)
    void validatePostStartDateAfterEndDateThrows() throws Exception {
        post.setStartDate("2040-12-01");
        post.createPost(database);
        assertThrows(StartDateAfterEndDateException.class, () -> post.validatePost(factory.makePostValidator()));
    }

    @Test
    @Order(16)
    void validatePostStartDateAfterEndDateDoesNotThrow() throws Exception {
        post.setStartDate("2023-12-01");
        post.createPost(database);
        assertDoesNotThrow(() -> post.validatePost(factory.makePostValidator()));
    }

    @Test
    @Order(10)
    void validatePostMinAgeGreaterThanMaxAgeThrows() throws Exception {
        post.setMinAge(15);
        post.createPost(database);
        assertThrows(MinAgeGreaterThanMaxAgeException.class, () -> post.validatePost(factory.makePostValidator()));
    }

    @Test
    @Order(17)
    void validatePostMinAgeGreaterThanMaxAgeDoesNotThrow() throws Exception {
        post.setMinAge(6);
        post.createPost(database);
        assertDoesNotThrow(() -> post.validatePost(factory.makePostValidator()));
    }
}