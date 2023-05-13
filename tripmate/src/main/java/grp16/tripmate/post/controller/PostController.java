package grp16.tripmate.post.controller;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.post.persistance.IPostPersistence;
import grp16.tripmate.post.persistance.feedback.IFeedbackPersistence;
import grp16.tripmate.post.model.IPost;
import grp16.tripmate.post.model.Post;
import grp16.tripmate.post.model.PostValidator;
import grp16.tripmate.post.model.factory.IPostFactory;
import grp16.tripmate.post.model.factory.PostFactory;
import grp16.tripmate.post.model.feedback.Feedback;
import grp16.tripmate.session.SessionEndedException;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.user.persistence.UserDbColumnNames;
import grp16.tripmate.vehicle.persistence.VehicleBooking.IVehicleBookingPersistence;
import grp16.tripmate.vehicle.model.VehicleBooking.VehicleBookingFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/*
 **References
 **https://www.baeldung.com/spring-thymeleaf-request-parameters
 */

@Controller
public class PostController {
    private final ILogger logger;
    private final IPostFactory postFactory;
    private final IPostPersistence postDatabase;
    private final PostValidator validator;
    private final IFeedbackPersistence feedbackDatabase;
    private final IVehicleBookingPersistence vehicleBookingDatabase;

    PostController() {
        postFactory = PostFactory.getInstance();
        logger = postFactory.makeNewLogger(this);
        feedbackDatabase = postFactory.makeFeedbackDatabase();
        postDatabase = postFactory.makePostDatabase();
        validator = postFactory.makePostValidator();
        vehicleBookingDatabase = VehicleBookingFactory.getInstance().getVehicleBookingDatabase();
    }

    @GetMapping("/dashboard")
    public String getAllPosts(Model model) throws Exception{
        model.addAttribute("title", "Dashboard");
        try {
            IPost post = postFactory.makeNewPost();
            List<Post> posts = post.getAllPosts(postDatabase, SessionManager.getInstance().getLoggedInUserId());
            model.addAttribute("posts", posts);
            return "listPosts";
        } catch (SessionEndedException e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    @GetMapping("/createpost")
    public String getNewPost(Model model) {
        Post myPost = (Post) postFactory.makeNewPost();
        model.addAttribute("title", "New Post");
        model.addAttribute("post", myPost);
        return "createPost";
    }

    @PostMapping("/createpost")
    public String createPost(Model model, @ModelAttribute Post post) {
        model.addAttribute("title", "Create Post");
        try {
            post.validatePost(validator);
            post.createPost(postDatabase);
            return "redirect:/myposts";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            return "createPost";
        }
    }

    @GetMapping("/myposts")
    public String getUserPosts(Model model) {
        model.addAttribute("title", "My Posts");
        try {
            Post post = (Post) postFactory.makeNewPost();
            List<Post> posts = post.getPostsByUserId(postDatabase, SessionManager.getInstance().getLoggedInUserId());
            model.addAttribute("posts", posts);
            return "listPosts";
        } catch (SessionEndedException e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/viewpost/{id}")
    public String viewPost(Model model, @PathVariable("id") int postId) {
        model.addAttribute("title", "View Post");
        try {
            Post post = (Post) postFactory.makeNewPost();
            Post myPost = post.getPostByPostId(postDatabase, postId);
            logger.info(myPost.toString());
            model.addAttribute("isUpdateButtonVisible", myPost.getOwner_id() == (int) SessionManager.getInstance().getValue(UserDbColumnNames.ID));
            model.addAttribute("post", myPost);
            model.addAttribute("isFeedbackButtonVisible", myPost.isEligibleForFeedback());
            model.addAttribute("feedbacks", myPost.getFeedbacks(postDatabase, feedbackDatabase));
            model.addAttribute("canJoin", myPost.isEligibleToJoin());
            model.addAttribute("vehicles", myPost.getVehiclesAssociatedWithCurrentPost(postDatabase, vehicleBookingDatabase));
            return "viewPost";
        } catch (SessionEndedException e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage());
            return "redirect:/login";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/editpost/{id}")
    public String editPost(Model model, @PathVariable("id") int postId) {
        model.addAttribute("title", "Edit Post");
        try {
            Post post = (Post) postFactory.makeNewPost();
            Post myPost = post.getPostByPostId(postDatabase, postId);
            model.addAttribute("post", myPost);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        return "updatePost";
    }

    @PostMapping("/updatepost")
    public String updatePost(Model model, @ModelAttribute Post post) {
        model.addAttribute("title", "Update Post");
        try {
            post.validatePost(validator);
            post.updatePost(postDatabase);
            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        return "updatePost";
    }

    @PostMapping("/deletepost/{id}")
    public String deletePost(Model model, @PathVariable("id") int postId, RedirectAttributes redirectAttrs) {
        model.addAttribute("title", "Delete Post");
        try {
            Post post = (Post) postFactory.makeNewPost();
            Post myPost = post.getPostByPostId(postDatabase, postId);
            myPost.deletePost(postDatabase);
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/viewPost/" + postId;
        }
    }

    @PostMapping("/hidepost/{id}")
    public String hidePost(Model model, @PathVariable("id") int postId, RedirectAttributes redirectAttrs) {
        try {
            model.addAttribute("title", "Hide Post");
            Post post = (Post) postFactory.makeNewPost();
            Post myPost = post.getPostByPostId(postDatabase, postId);
            myPost.hidePost(postDatabase);
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/viewPost/" + postId;
        }
    }

    @GetMapping("/error")
    public String displayError(Model model) {
        model.addAttribute("error", "Some error has occurred");
        return "error";
    }


    @GetMapping("/feedback/{id}")
    public String loadFeedbackPage(Model model, @PathVariable("id") int postId) {
        try {
            model.addAttribute("post", postFactory.makeNewPost().getPostByPostId(postDatabase, postId));
            model.addAttribute("currentFeedback", new Feedback());
            model.addAttribute("title", "Feedback");
        } catch (Exception e) {
            model.addAttribute("error", "Post not found " + e.getMessage());
            e.printStackTrace();
        }
        return "feedback";
    }

    @PostMapping("/feedback/{id}")
    public String createFeedback(@PathVariable("id") int postId, @ModelAttribute Feedback feedback) {
        try {
            feedback.setPostId(postId);
            feedback.setUserId(SessionManager.getInstance().getLoggedInUserId());
            feedback.createFeedback(feedbackDatabase);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/dashboard";
    }
}
