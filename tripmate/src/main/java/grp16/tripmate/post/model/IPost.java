package grp16.tripmate.post.model;

import grp16.tripmate.post.persistance.IPostPersistence;
import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;
import grp16.tripmate.post.model.feedback.Feedback;
import grp16.tripmate.post.model.exception.MinAgeGreaterThanMaxAgeException;
import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;
import grp16.tripmate.post.model.exception.StartDateBeforeTodayException;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBooking;

import java.text.ParseException;
import java.util.List;

public interface IPost {

    boolean createPost(IPostPersistence database) throws Exception;

    List<Post> getAllPosts(IPostPersistence database, int loggedInUser) throws Exception;

    List<Post> getPostsByUserId(IPostPersistence database, int userid) throws Exception;

    Post getPostByPostId(IPostPersistence database, int postId) throws Exception;

    boolean updatePost(IPostPersistence database);

    boolean deletePost(IPostPersistence database);

    List<Feedback> getFeedbacks(IPostPersistence database, IFeedbackPersistence feedbackDatabase) throws Exception;

    boolean hidePost(IPostPersistence database);

    void validatePost(PostValidator validator) throws ParseException, StartDateAfterEndDateException, MinAgeGreaterThanMaxAgeException, StartDateBeforeTodayException;

    List<VehicleBooking> getVehiclesAssociatedWithCurrentPost(IPostPersistence database, IVehicleBookingPersistence vehicleBookingDatabase);
}
