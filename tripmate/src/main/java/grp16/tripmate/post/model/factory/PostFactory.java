package grp16.tripmate.post.model.factory;

import grp16.tripmate.persistence.execute.DatabaseExecutor;
import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.post.persistance.IPostPersistence;
import grp16.tripmate.post.persistance.IPostsQueryGenerator;
import grp16.tripmate.post.persistance.PostPersistence;
import grp16.tripmate.post.persistance.PostsQueryGenerator;
import grp16.tripmate.post.persistance.feedback.FeedbackPersistence;
import grp16.tripmate.post.persistance.feedback.FeedbackQueryGenerator;
import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;
import grp16.tripmate.post.persistance.feedback.IFeedbackQueryGenerator;
import grp16.tripmate.post.model.IPost;
import grp16.tripmate.post.model.Post;
import grp16.tripmate.post.model.PostValidator;

public class PostFactory implements IPostFactory {

    private static IPostFactory instance;

    private PostFactory() {

    }

    public static IPostFactory getInstance() {
        if (instance == null) {
            instance = new PostFactory();
        }
        return instance;
    }

    @Override
    public IPost makeNewPost() {
        return new Post();
    }

    @Override
    public IPostPersistence makePostDatabase() {
        return new PostPersistence(makeNewDatabaseExecutor(), makePostQueryBuilder());
    }

    @Override
    public IPostsQueryGenerator makePostQueryBuilder() {
        return PostsQueryGenerator.getInstance();
    }

    @Override
    public IFeedbackPersistence makeFeedbackDatabase() {
        return new FeedbackPersistence(makeNewDatabaseExecutor(), makeFeedbackQueryBuilder());
    }

    @Override
    public IFeedbackQueryGenerator makeFeedbackQueryBuilder() {
        return FeedbackQueryGenerator.getInstance();
    }

    @Override
    public PostValidator makePostValidator() {
        return new PostValidator();
    }

    @Override
    public ILogger makeNewLogger(Object classObj) {
        return new MyLoggerAdapter(classObj);
    }

    @Override
    public IDatabaseExecutor makeNewDatabaseExecutor() {
        return new DatabaseExecutor();
    }
}