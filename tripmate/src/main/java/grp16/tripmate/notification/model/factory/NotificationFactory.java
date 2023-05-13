package grp16.tripmate.notification.model.factory;

import grp16.tripmate.notification.model.EmailNotification;
import grp16.tripmate.notification.model.EmailVerification;
import grp16.tripmate.notification.model.INotification;
import grp16.tripmate.notification.model.IVerification;

public class NotificationFactory implements INotificationFactory {
    private static INotificationFactory instance;

    private NotificationFactory() {

    }

    public static INotificationFactory getInstance() {
        if (instance == null) {
            instance = new NotificationFactory();
        }
        return instance;
    }

    @Override
    public INotification createEmailNotification() {
        return new EmailNotification();
    }

    @Override
    public IVerification createVerificationMethod() {
        return new EmailVerification();
    }
}
