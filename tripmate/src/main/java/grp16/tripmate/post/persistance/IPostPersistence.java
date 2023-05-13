package grp16.tripmate.post.persistance;

import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;
import grp16.tripmate.post.model.feedback.Feedback;
import grp16.tripmate.post.model.Post;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;

import java.util.List;

public interface IPostPersistence {

    boolean updatePost(Post post);

    boolean deletePost(int post_id);

    boolean hidePost(int post_id);

    boolean createPost(Post post) throws Exception;

    List<Post> getPostsByUserId(int userId);

    List<Post> getAllPosts(int loggedInUser);

    Post getPostByPostId(int postId);

    List<Feedback> getFeedbacks(IFeedbackPersistence feedbackDatabase, int postId) throws Exception;

    List<VehicleBooking> getVehicles(IVehicleBookingPersistence vehicleBookingDatabase, int postId);
}
