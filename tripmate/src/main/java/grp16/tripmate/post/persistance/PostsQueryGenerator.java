package grp16.tripmate.post.persistance;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.post.model.IPost;
import grp16.tripmate.post.model.Post;

public class PostsQueryGenerator implements IPostsQueryGenerator {
    private final ILogger logger = new MyLoggerAdapter(this);
    private static PostsQueryGenerator instance;

    private PostsQueryGenerator() {
    }

    public static PostsQueryGenerator getInstance() {
        if (instance == null) {
            instance = new PostsQueryGenerator();
        }
        return instance;
    }

    @Override
    public String getCreatePostQuery(IPost postToCreate) {
        Post post = (Post) postToCreate;
        String query = "INSERT INTO " + PostDbColumnNames.TABLE_NAME + "(  " +
                PostDbColumnNames.OWNER + "," +
                PostDbColumnNames.TITLE + "," +
                PostDbColumnNames.SOURCE + "," +
                PostDbColumnNames.DESTINATION + "," +
                PostDbColumnNames.STARTDATE + "," +
                PostDbColumnNames.ENDDATE + "," +
                PostDbColumnNames.MINAGE + "," +
                PostDbColumnNames.MAXAGE + "," +
                PostDbColumnNames.CAPACITY + "," +
                PostDbColumnNames.DESCRIPTION + ")  " +
                "VALUES ( " +
                post.getOwner_id() + ", " +
                "'" + post.getTitle() + "', " +
                "'" + post.getSource() + "', " +
                "'" + post.getDestination() + "', " +
                "'" + post.getStartDate() + "', " +
                "'" + post.getEndDate() + "', " +
                post.getMinAge() + ", " +
                post.getMaxAge() + ", " +
                post.getCapacity() + ", " +
                "'" + post.getDescription() + "' );";
        logger.info(query);
        return query;
    }

    @Override
    public String getAllPosts(int loggedInUser) {
        String query = "SELECT  " +
                PostDbColumnNames.ID + ",  " +
                PostDbColumnNames.OWNER + ",  " +
                PostDbColumnNames.TITLE + ",  " +
                PostDbColumnNames.SOURCE + ",  " +
                PostDbColumnNames.DESTINATION + ",  " +
                PostDbColumnNames.STARTDATE + ",  " +
                PostDbColumnNames.ENDDATE + ",  " +
                PostDbColumnNames.MINAGE + ",  " +
                PostDbColumnNames.MAXAGE + ",  " +
                PostDbColumnNames.CAPACITY + ",  " +
                PostDbColumnNames.ISHIDDEN + ",  " +
                PostDbColumnNames.DESCRIPTION + "  " +
                "FROM " + PostDbColumnNames.TABLE_NAME + " " +
                "WHERE " + PostDbColumnNames.ISHIDDEN + " != 1 " +
                "AND " + PostDbColumnNames.OWNER + " != " + loggedInUser;
        logger.info(query);
        return query;
    }

    @Override
    public String getPostsByUserId(int userId) {
        String query = "SELECT  " +
                PostDbColumnNames.ID + ",  " +
                PostDbColumnNames.OWNER + ",  " +
                PostDbColumnNames.TITLE + ",  " +
                PostDbColumnNames.SOURCE + ",  " +
                PostDbColumnNames.DESTINATION + ",  " +
                PostDbColumnNames.STARTDATE + ",  " +
                PostDbColumnNames.ENDDATE + ",  " +
                PostDbColumnNames.MINAGE + ",  " +
                PostDbColumnNames.MAXAGE + ",  " +
                PostDbColumnNames.CAPACITY + ",  " +
                PostDbColumnNames.ISHIDDEN + ",  " +
                PostDbColumnNames.DESCRIPTION + "  " +
                "FROM " + PostDbColumnNames.TABLE_NAME + " " +
                "WHERE " + PostDbColumnNames.ISHIDDEN + " != 1 AND  " +
                PostDbColumnNames.OWNER + " = " + userId;
        logger.info(query);
        return query;
    }


    @Override
    public String getPostByPostId(int postId) {
        String query = "SELECT  " +
                PostDbColumnNames.ID + ",  " +
                PostDbColumnNames.OWNER + ",  " +
                PostDbColumnNames.TITLE + ",  " +
                PostDbColumnNames.SOURCE + ",  " +
                PostDbColumnNames.DESTINATION + ",  " +
                PostDbColumnNames.STARTDATE + ",  " +
                PostDbColumnNames.ENDDATE + ",  " +
                PostDbColumnNames.MINAGE + ",  " +
                PostDbColumnNames.MAXAGE + ",  " +
                PostDbColumnNames.CAPACITY + ",  " +
                PostDbColumnNames.ISHIDDEN + ",  " +
                PostDbColumnNames.DESCRIPTION + "  " +
                "FROM " + PostDbColumnNames.TABLE_NAME + " " +
                "WHERE " + PostDbColumnNames.ID + " = " + postId + ";";
        logger.info(query);
        return query;
    }

    @Override
    public String getUpdatePostQuery(IPost postToUpdate) {
        Post post = (Post) postToUpdate;
        String query = "UPDATE " + PostDbColumnNames.TABLE_NAME + "    SET " +
                PostDbColumnNames.TITLE + "='" + post.getTitle() + "', " +
                PostDbColumnNames.SOURCE + "='" + post.getSource() + "', " +
                PostDbColumnNames.DESTINATION + "='" + post.getDestination() + "', " +
                PostDbColumnNames.STARTDATE + "='" + post.getStartDate() + "', " +
                PostDbColumnNames.ENDDATE + "='" + post.getEndDate() + "', " +
                PostDbColumnNames.MINAGE + "=" + post.getMinAge() + ", " +
                PostDbColumnNames.MAXAGE + "=" + post.getMaxAge() + ", " +
                PostDbColumnNames.CAPACITY + "=" + post.getCapacity() + ", " +
                PostDbColumnNames.DESCRIPTION + "='" + post.getDescription() + "' " +
                "    WHERE " + PostDbColumnNames.ID + "=" + post.getId();
        logger.info(query);
        return query;
    }

    @Override
    public String deletePostQuery(int postId) {
        String query = "DELETE FROM " +
                PostDbColumnNames.TABLE_NAME +
                " WHERE " + PostDbColumnNames.ID + " = " + postId;
        logger.info(query);
        return query;
    }

    @Override
    public String hidePostQuery(int postId) {
        String query = "UPDATE " + PostDbColumnNames.TABLE_NAME + " " +
                "    SET " + PostDbColumnNames.ISHIDDEN + "=" + true + " " +
                "    WHERE " + PostDbColumnNames.ID + "=" + postId;
        logger.info(query);
        return query;
    }
}


