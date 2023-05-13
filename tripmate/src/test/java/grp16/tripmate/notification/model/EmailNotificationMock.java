package grp16.tripmate.notification.model;

public class EmailNotificationMock implements INotification {

    public boolean sendNotification(String sendTo, String subject, String body) {
        if (sendTo == null || subject == null || body == null) {
            return false;
        } else {
            return true;
        }
    }
}
