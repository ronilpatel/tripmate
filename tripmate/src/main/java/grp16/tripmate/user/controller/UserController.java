package grp16.tripmate.user.controller;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.notification.model.INotification;
import grp16.tripmate.notification.model.IVerification;
import grp16.tripmate.notification.model.InvalidTokenException;
import grp16.tripmate.notification.model.factory.NotificationFactory;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.user.persistence.IUserPersistence;
import grp16.tripmate.user.persistence.UserDbColumnNames;
import grp16.tripmate.user.model.encoder.IPasswordEncoder;
import grp16.tripmate.user.model.*;
import grp16.tripmate.user.model.encoder.PasswordEncoder;
import grp16.tripmate.user.model.factory.IUserFactory;
import grp16.tripmate.user.model.factory.UserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class UserController {
    private final ILogger logger = new MyLoggerAdapter(this);
    private final IUserFactory userFactory;
    private final IUserPersistence userDatabase;

    private final IPasswordEncoder passwordEncoder;
    private String emailForgetPassword = null;
    private final INotification notification;
    private final IVerification verification;

    private String codeMessage;


    public UserController() {
        userFactory = UserFactory.getInstance();
        userDatabase = UserFactory.getInstance().makeUserDatabase();
        passwordEncoder = UserFactory.getInstance().makePasswordEncoder();
        notification = NotificationFactory.getInstance().createEmailNotification();
        verification = NotificationFactory.getInstance().createVerificationMethod();
    }

    @GetMapping("/login")
    public String userLogin(Model model) {
        model.addAttribute("user", userFactory.makeNewUser());
        model.addAttribute("title", "Login");
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(Model model, @ModelAttribute User user) {
        logger.info(user.toString());
        try {
            user.validateUser(userDatabase, passwordEncoder);
            logger.info(user.getUsername() + " Login SUCCESS");
            return "redirect:/dashboard";
        } catch (InvalidUsernamePasswordException | IndexOutOfBoundsException | NoSuchAlgorithmException e) {
            model.addAttribute("error", "Invalid Username or Password");
            e.printStackTrace();
            return "login";
        }
    }

    @GetMapping("/register")
    public String userRegister(Model model) {
        model.addAttribute("user", userFactory.makeNewUser());
        model.addAttribute("title", "Register");
        return "register";
    }

    @GetMapping("/profile")
    public String userProfile(Model model) throws Exception {
        User loggedInUser = userFactory.makeNewUser().getUserById(userDatabase, SessionManager.getInstance().getLoggedInUserId());
        model.addAttribute("user", loggedInUser);
        logger.info("loaded user: " + loggedInUser);
        model.addAttribute("title", "View/Update Profile");
        return "viewProfile";
    }

    @PostMapping("/changeUserDetails")
    public String changeUserDetails(@ModelAttribute User user) throws Exception {
        logger.info("Change user to " + user);
        user.changeUserDetails(userDatabase);
        return "viewProfile";
    }

    @GetMapping("/")
    public String loadMainPage() {
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        SessionManager.getInstance().removeValue(UserDbColumnNames.ID);
        return "redirect:/login";
    }

    @GetMapping("/forget_password")
    public String forgetPassword(Model model) {
        model.addAttribute("title", "Reset password");
        model.addAttribute("email", this.emailForgetPassword);
        model.addAttribute("message", this.codeMessage);

        return "forgotPassword";
    }

    @PostMapping("/forget_password")
    public String sendResetPasswordCode(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Reset password");
        model.addAttribute("email", "");
        model.addAttribute("message", "");

        IUser user = UserFactory.getInstance().makeNewUser();
        try {
            if (user.checkUserExist(userDatabase, request.getParameter("email"))) {

            } else {
                model.addAttribute("error", "User Not exists");
                logger.info("User Not exists");
                return "redirect:/error";
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return "redirect:/error";
        }

        this.emailForgetPassword = request.getParameter("email");
        this.codeMessage = "Please enter the code that you got on " + this.emailForgetPassword;

        try {
            verification.sendUniqueCode(this.emailForgetPassword, "Your reset password code is: ", "User reset password for Tripmate");
        } catch (Exception e) {
            model.addAttribute("error", "User Not exists");
            logger.info("User Not exists");
            return "redirect:/error";
        }
        return "redirect:/forget_password";
    }

    @PostMapping("/reset_password")
    public String userVerificationCode(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        model.addAttribute("email", this.emailForgetPassword);
        model.addAttribute("message", this.codeMessage);
        String code = request.getParameter("code");

        try {
            verification.verifyCode(code);
            logger.info("VERIFY CODE");
            return "newPassword";
        } catch (InvalidTokenException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            e.printStackTrace();
            return "redirect:/forget_password";
        }
    }

    @GetMapping("/new_password")
    public String resetPassword(Model model) {

        model.addAttribute("title", "New password");
        model.addAttribute("email", this.emailForgetPassword);

        return "newPassword";
    }

    @PostMapping("/new_password")
    public String setNewPassword(Model model, HttpServletRequest request) throws Exception {
        IUser user = UserFactory.getInstance().makeNewUser();
        model.addAttribute("email", this.emailForgetPassword);
        String password = request.getParameter("password");
        if (user.changeUserPassword(userDatabase, this.emailForgetPassword, PasswordEncoder.getInstance().encodeString(password))) {
            notification.sendNotification(this.emailForgetPassword, "Password Updated", "Password Reset successfully");

            return "redirect:/login";
        } else {
            return "redirect:/error";
        }
    }
}