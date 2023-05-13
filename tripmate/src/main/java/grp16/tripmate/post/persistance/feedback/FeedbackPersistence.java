package grp16.tripmate.post.persistance.feedback;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.post.model.feedback.Feedback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedbackPersistence implements IFeedbackPersistence {
    private final IDatabaseExecutor databaseExecutor;
    private final IFeedbackQueryGenerator queryGenerator;

    public FeedbackPersistence(IDatabaseExecutor databaseExecutor, IFeedbackQueryGenerator queryGenerator) {
        this.databaseExecutor = databaseExecutor;
        this.queryGenerator = queryGenerator;
    }

    @Override
    public boolean createFeedback(Feedback feedback) {
        String query = queryGenerator.createFeedback(feedback);
        return databaseExecutor.executeInsertQuery(query);
    }

    @Override
    public List<Feedback> getFeedbacksByPostId(int post_id){
        String query = queryGenerator.getFeedbacksByPostId(post_id);
        return listToFeedback(databaseExecutor.executeSelectQuery(query));
    }

    @Override
    public boolean deleteFeedbackByPostId(int postId) {
        String query = queryGenerator.deleteFeedbackByPostId(postId);
        return databaseExecutor.executeDeleteQuery(query);
    }

    private List<Feedback> listToFeedback(List<Map<String, Object>> results) {
        List<Feedback> feedbacks = new ArrayList<>();
        for (Map<String, Object> result : results) {
            Feedback feedback = new Feedback();

            feedback.setId((Integer) result.get(FeedbackDbColumnNames.ID));
            feedback.setPostId((Integer) result.get(FeedbackDbColumnNames.POST_ID));
            feedback.setUserId((Integer) result.get(FeedbackDbColumnNames.USER_ID));
            feedback.setFeedback((String) result.get(FeedbackDbColumnNames.FEEDBACK));
            feedback.setRating(((BigDecimal) result.get(FeedbackDbColumnNames.RATING)).floatValue());

            feedbacks.add(feedback);
        }
        return feedbacks;
    }
}