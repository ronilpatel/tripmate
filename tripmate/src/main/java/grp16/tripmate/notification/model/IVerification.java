package grp16.tripmate.notification.model;

public interface IVerification {
    boolean sendUniqueCode(String userEmail, String body, String subject) throws Exception;

    boolean verifyCode(String code) throws InvalidTokenException;
}
