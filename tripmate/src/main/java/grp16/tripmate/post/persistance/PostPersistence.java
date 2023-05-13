package grp16.tripmate.post.persistance;

import grp16.tripmate.persistence.execute.IDatabaseExecutor;
import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;
import grp16.tripmate.post.model.feedback.Feedback;
import grp16.tripmate.post.model.*;
import grp16.tripmate.post.model.factory.PostFactory;
import grp16.tripmate.session.SessionEndedException;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.model.Vehicle.VehicleFactory;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PostPersistence implements IPostPersistence {
    private final IPostsQueryGenerator queryGenerator;
    private final IDatabaseExecutor databaseExecutor;

    public PostPersistence(IDatabaseExecutor databaseExecutor, IPostsQueryGenerator queryGenerator) {
        this.queryGenerator = queryGenerator;
        this.databaseExecutor = databaseExecutor;
    }

    @Override
    public boolean createPost(Post post) throws SessionEndedException {
        post.setOwner_id(SessionManager.getInstance().getLoggedInUserId());
        String query = queryGenerator.getCreatePostQuery(post);
        return databaseExecutor.executeInsertQuery(query);
    }

    @Override
    public List<Post> getPostsByUserId(int userId) {
        String query = queryGenerator.getPostsByUserId(userId);
        return listToPosts(databaseExecutor.executeSelectQuery(query));
    }

    @Override
    public List<Post> getAllPosts(int loggedInUser) {
        String query = queryGenerator.getAllPosts(loggedInUser);
        return listToPosts(databaseExecutor.executeSelectQuery(query));
    }

    @Override
    public Post getPostByPostId(int postId) {
        String query = queryGenerator.getPostByPostId(postId);
        List<Post> posts = listToPosts(databaseExecutor.executeSelectQuery(query));
        if (posts.isEmpty()) {
            return null;
        }
        return posts.get(0);
    }

    @Override
    public boolean updatePost(Post post) {
        String query = queryGenerator.getUpdatePostQuery(post);
        return databaseExecutor.executeUpdateQuery(query);
    }

    @Override
    public boolean deletePost(int post_id) {
        PostFactory.getInstance().makeFeedbackDatabase().deleteFeedbackByPostId(post_id);
        VehicleFactory.getInstance().getVehicleDataBase();

        String query = queryGenerator.deletePostQuery(post_id);
        return databaseExecutor.executeDeleteQuery(query);
    }

    @Override
    public boolean hidePost(int post_id) {
        String query = queryGenerator.hidePostQuery(post_id);
        return databaseExecutor.executeUpdateQuery(query);
    }

    @Override
    public List<Feedback> getFeedbacks(IFeedbackPersistence database, int postId) throws Exception {
        return database.getFeedbacksByPostId(postId);
    }

    @Override
    public List<VehicleBooking> getVehicles(IVehicleBookingPersistence vehicleBookingDatabase, int postId) {
        return vehicleBookingDatabase.getVehicleBookingByPostId(postId);
    }

    public List<Post> listToPosts(List<Map<String, Object>> responseMaps) {
        List<Post> results = new ArrayList<>();
        for (Map<String, Object> responseMap : responseMaps) {
            Post post = (Post) PostFactory.getInstance().makeNewPost();
            post.setId((Integer) responseMap.get(PostDbColumnNames.ID));
            post.setTitle((String) responseMap.get(PostDbColumnNames.TITLE));
            post.setCapacity((Integer) responseMap.get(PostDbColumnNames.CAPACITY));
            post.setDescription((String) responseMap.get(PostDbColumnNames.DESCRIPTION));
            post.setEndDate(localDateTimeToDate((LocalDateTime) responseMap.get(PostDbColumnNames.ENDDATE)));
            post.setHidden((Integer) responseMap.get(PostDbColumnNames.ISHIDDEN));
            post.setDestination((String) responseMap.get(PostDbColumnNames.DESTINATION));
            post.setMaxAge((Integer) responseMap.get(PostDbColumnNames.MAXAGE));
            post.setMinAge((Integer) responseMap.get(PostDbColumnNames.MINAGE));
            post.setStartDate(localDateTimeToDate((LocalDateTime) responseMap.get(PostDbColumnNames.STARTDATE)));
            post.setSource((String) responseMap.get(PostDbColumnNames.SOURCE));
            post.setOwner_id((Integer) responseMap.get(PostDbColumnNames.OWNER));
            results.add(post);
        }
        return results;
    }

    private Date localDateTimeToDate(LocalDateTime ldt) {
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
