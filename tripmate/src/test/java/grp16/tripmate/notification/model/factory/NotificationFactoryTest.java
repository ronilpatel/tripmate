package grp16.tripmate.notification.model.factory;

import grp16.tripmate.notification.model.INotification;
import grp16.tripmate.notification.model.IVerification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    private INotificationFactory factory;

    public NotificationFactoryTest() {
        factory = NotificationFactory.getInstance();
    }

    @Test
    void createEmailNotification() {
        Assertions.assertInstanceOf(INotification.class, factory.createEmailNotification());
    }

    @Test
    void createVerificationMethod() {
        Assertions.assertInstanceOf(IVerification.class, factory.createVerificationMethod());
    }
}