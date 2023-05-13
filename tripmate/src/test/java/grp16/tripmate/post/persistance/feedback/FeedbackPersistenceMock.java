package grp16.tripmate.post.persistance.feedback;

import grp16.tripmate.post.model.feedback.Feedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackPersistenceMock implements IFeedbackPersistence {
    private static final Map<Integer, List<Feedback>> feedbackDb = new HashMap<>();

    @Override
    public boolean createFeedback(Feedback feedback) {
        List<Feedback> feedbacks = feedbackDb.getOrDefault(feedback.getPostId(), new ArrayList<>());
        feedbackDb.put(feedback.getPostId(), feedbacks);
        return feedbacks.add(feedback);
    }


    @Override
    public boolean deleteFeedbackByPostId(int postId) {
        List<Feedback> feedbacks = feedbackDb.getOrDefault(postId, new ArrayList<>());
        feedbacks.clear();
        feedbackDb.remove(postId);
        return feedbackDb.containsKey(postId);
    }

    @Override
    public List<Feedback> getFeedbacksByPostId(int postId) {
        return feedbackDb.getOrDefault(postId, new ArrayList<>());
    }
}
