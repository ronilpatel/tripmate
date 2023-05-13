package grp16.tripmate.notification.model;

import grp16.tripmate.notification.model.factory.NotificationFactory;

public class EmailVerification implements IVerification {

    int uniqueNumber;

    @Override
    public boolean sendUniqueCode(String userEmail, String body, String subject) throws Exception {
        INotification iNotification;
        uniqueNumber = generateNumber();
        body += uniqueNumber;
        iNotification = NotificationFactory.getInstance().createEmailNotification();
        iNotification.sendNotification(userEmail, subject, body);
        return true;
    }

    @Override
    public boolean verifyCode(String code) throws InvalidTokenException {
        if (uniqueNumber == Integer.parseInt(code)) {
            return true;
        } else {
            throw new InvalidTokenException();
        }
    }

    private int generateNumber() {
        int min = 1000;
        int max = 9999;
        int range = (max - min + 1);
        double random = Math.random();
        double finalNumber = Math.floor((random * range) + min);
        return (int) finalNumber;
    }
}
