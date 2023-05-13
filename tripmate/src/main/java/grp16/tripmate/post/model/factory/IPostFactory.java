package grp16.tripmate.post.model.factory;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.post.persistance.IPostPersistence;
import grp16.tripmate.post.persistance.IPostsQueryGenerator;
import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;
import grp16.tripmate.post.persistance.feedback.IFeedbackQueryGenerator;
import grp16.tripmate.post.model.IPost;
import grp16.tripmate.post.model.PostValidator;

public interface IPostFactory {

    IPost makeNewPost();

    IPostPersistence makePostDatabase();

    PostValidator makePostValidator();

    IPostsQueryGenerator makePostQueryBuilder();

    IFeedbackPersistence makeFeedbackDatabase();

    IFeedbackQueryGenerator makeFeedbackQueryBuilder();

    ILogger makeNewLogger(Object classObj);

    IDatabaseExecutor makeNewDatabaseExecutor();
}
