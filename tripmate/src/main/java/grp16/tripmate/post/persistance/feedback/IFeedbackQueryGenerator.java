package grp16.tripmate.post.persistance.feedback;

import grp16.tripmate.post.model.feedback.Feedback;

public interface IFeedbackQueryGenerator {
    String createFeedback(Feedback feedback);

    String getFeedbacksByPostId(int postId);

    String deleteFeedbackByPostId(int postId);
}
