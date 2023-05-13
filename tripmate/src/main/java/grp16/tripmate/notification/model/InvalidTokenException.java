package grp16.tripmate.notification.model;

public class InvalidTokenException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid Token. Please try again";
    }
}
