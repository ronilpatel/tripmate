package grp16.tripmate.post.persistance.feedback;

import grp16.tripmate.post.model.feedback.Feedback;

import java.util.List;

public interface IFeedbackPersistence {

    boolean createFeedback(Feedback feedback);

    List<Feedback> getFeedbacksByPostId(int post_id) throws Exception;

    boolean deleteFeedbackByPostId(int postId);
}

