package grp16.tripmate.notification.model;

public interface INotification {
    boolean sendNotification(String sendTo, String subject, String body) throws Exception;
}
