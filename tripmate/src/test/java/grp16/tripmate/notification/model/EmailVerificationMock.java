package grp16.tripmate.notification.model;

public class EmailVerificationMock implements IVerification {
    private int uniqueCode = 1111;

    @Override
    public boolean sendUniqueCode(String userEmail, String body, String subject) throws Exception {
        if (userEmail == null || body == null || subject == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean verifyCode(String code) throws InvalidTokenException {
        if (uniqueCode == Integer.parseInt(code)) {
            return true;
        } else {
            return false;
        }
    }
}
