package grp16.tripmate.user.controller;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.notification.model.IVerification;
import grp16.tripmate.notification.model.InvalidTokenException;
import grp16.tripmate.notification.model.factory.NotificationFactory;
import grp16.tripmate.user.persistence.IUserPersistence;
import grp16.tripmate.user.model.factory.IUserFactory;
import grp16.tripmate.user.model.User;
import grp16.tripmate.user.model.factory.UserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class VerificationController {
    private final ILogger logger = new MyLoggerAdapter(this);
    private final IVerification verification;
    private User user;

    private final IUserPersistence database;

    VerificationController() throws Exception {
        IUserFactory userFactory = UserFactory.getInstance();
        database = userFactory.makeUserDatabase();
        verification = NotificationFactory.getInstance().createVerificationMethod();
    }

    @PostMapping("/register")
    public String userVerification(@ModelAttribute User user) throws Exception {
        verification.sendUniqueCode(user.getUsername(), "Your user verification code is: ", "User Verification for Tripmate");
        this.user = user;
        return "userVerification";
    }

    @PostMapping("/verify")
    public String userVerificationCode(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String code = request.getParameter("code");

        try {
            this.verification.verifyCode(code);
            boolean isUserCreatedSuccessfully = this.user.createUser(database);
                if (isUserCreatedSuccessfully) {
                    logger.info(this.user.getUsername() + " Register SUCCESS");
                    return "redirect:/login";
                }
        } catch (InvalidTokenException e) {
            logger.info(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/login";
    }
}