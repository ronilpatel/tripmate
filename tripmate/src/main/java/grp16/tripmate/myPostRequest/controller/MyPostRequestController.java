package grp16.tripmate.myPostRequest.controller;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.notification.model.INotification;
import grp16.tripmate.notification.model.factory.NotificationFactory;
import grp16.tripmate.myPostRequest.persistence.IMyPostRequestPersistence;
import grp16.tripmate.myPostRequest.model.IMyPostRequest;
import grp16.tripmate.myPostRequest.model.MyPostRequest;
import grp16.tripmate.myPostRequest.model.factory.IMyPostRequestFactory;
import grp16.tripmate.myPostRequest.model.factory.MyPostRequestFactory;
import grp16.tripmate.myPostRequest.model.PostRequestStatus;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.user.persistence.UserDbColumnNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MyPostRequestController {

    private final IMyPostRequestFactory myPostRequestFactory;
    private final IMyPostRequestPersistence myPostRequestDB;
    private final IMyPostRequest myPostRequest;
    private final ILogger logger;
    private final INotification notification;

    MyPostRequestController() {
        myPostRequestFactory = MyPostRequestFactory.getInstance();
        myPostRequestDB = myPostRequestFactory.makeMyPostRequestDB();
        myPostRequest = myPostRequestFactory.makeMyPostRequest();
        logger = myPostRequestFactory.makeNewLogger(this);
        notification = NotificationFactory.getInstance().createEmailNotification();
    }

    @GetMapping("/my_post_requests")
    public String postRequest(Model model) {
        model.addAttribute("title", "Post Request");
        try {
            List<MyPostRequest> postRequests = myPostRequest.getMyPostRequests(myPostRequestDB);
            model.addAttribute("requests_count", postRequests.size());
            model.addAttribute("postRequests", postRequests);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e.getMessage());
        }
        return "myPostRequests";
    }

    @PostMapping("/join/{id}")
    public String join(@PathVariable("id") int postId) throws Exception {
        myPostRequest.createJoinRequest(myPostRequestDB, postId);
        MyPostRequest postRequests = myPostRequest.getPostOwnerDetails(myPostRequestDB, postId);

        notification.sendNotification(postRequests.getEmailCreator(), "Join Request for " + postRequests.getPostTitle(), SessionManager.getInstance().getValue(UserDbColumnNames.FIRSTNAME) + " " + SessionManager.getInstance().getValue(UserDbColumnNames.LASTNAME) + " requested for joining " + postRequests.getPostTitle());
        return "redirect:/my_requests";
    }

    @PostMapping("/accept_request/{request_id}")
    public String acceptRequest(@PathVariable("request_id") int requestId) throws Exception {
        myPostRequest.updateRequest(myPostRequestDB, requestId, PostRequestStatus.ACCEPT);
        MyPostRequest postRequests = myPostRequest.getPostRequesterDetails(myPostRequestDB, requestId);

        notification.sendNotification(postRequests.getEmailRequester(), "Update on request for joining " + postRequests.getPostTitle(), SessionManager.getInstance().getValue(UserDbColumnNames.FIRSTNAME) + " " + SessionManager.getInstance().getValue(UserDbColumnNames.LASTNAME) + " ACCEPT request for joining " + postRequests.getPostTitle());

        return "redirect:/my_post_requests";
    }

    @PostMapping("/decline_request/{request_id}")
    public String declineRequest(@PathVariable("request_id") int requestId) throws Exception {
        myPostRequest.updateRequest(myPostRequestDB, requestId, PostRequestStatus.DECLINE);
        MyPostRequest postRequests = myPostRequest.getPostRequesterDetails(myPostRequestDB, requestId);

        notification.sendNotification(postRequests.getEmailRequester(), "Update on request for joining " + postRequests.getPostTitle(), SessionManager.getInstance().getValue(UserDbColumnNames.FIRSTNAME) + " " + SessionManager.getInstance().getValue(UserDbColumnNames.LASTNAME) + " DECLINE requested for joining " + postRequests.getPostTitle());

        return "redirect:/my_post_requests";
    }

    @GetMapping("/my_requests")
    public String myRequest(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("title", "My Request");
        try {
            List<MyPostRequest> myRequestList = myPostRequest.getMyRequestByUserId(myPostRequestFactory, myPostRequestDB, SessionManager.getInstance().getLoggedInUserId());
            model.addAttribute("requests_count", myRequestList.size());
            model.addAttribute("myRequests", myRequestList);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            e.printStackTrace();
            return "redirect:/error";
        }
        return "myRequests";
    }

}
