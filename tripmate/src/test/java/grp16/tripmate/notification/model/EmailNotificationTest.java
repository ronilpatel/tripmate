package grp16.tripmate.notification.model;

import grp16.tripmate.notification.model.factory.NotificationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailNotificationTest {

    INotification notification;

    public EmailNotificationTest() {
        notification = new EmailNotificationMock();
    }

    @Test
    void sendNotificationTest() throws Exception {
        String sendTo = "arpitpatel1501@gmail.com";
        String subject = "Tripmate subject";
        String body = "Tripmate notification body";
        boolean result = notification.sendNotification(sendTo, subject, body);
        Assertions.assertTrue(result);
    }

    @Test
    void sendNotificationNegativeTest() throws Exception {
        String sendTo = null;
        String subject = null;
        String body = null;
        boolean result = notification.sendNotification(sendTo, subject, body);
        Assertions.assertFalse(result);
    }
}