package grp16.tripmate.post.model.feedback;

import grp16.tripmate.post.persistance.feedback.FeedbackPersistenceMock;
import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;
import grp16.tripmate.post.model.factory.IPostFactory;
import grp16.tripmate.post.model.factory.PostFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    IPostFactory factory;
    IFeedbackPersistence database;
    Feedback feedback;

    public FeedbackTest() {
        factory = PostFactory.getInstance();
        database = new FeedbackPersistenceMock();

        feedback = new Feedback();
        feedback.setId(11);
        feedback.setPostId(1);
        feedback.setUserId(2);
        feedback.setFeedback("Smooth experience");
        feedback.setRating(5);
    }

    @Test
    void createFeedback() {
        assertTrue(feedback.createFeedback(database));
    }
}