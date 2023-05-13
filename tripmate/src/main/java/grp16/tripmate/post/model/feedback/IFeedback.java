package grp16.tripmate.post.model.feedback;

import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;

public interface IFeedback {
    boolean createFeedback(IFeedbackPersistence database);
}
